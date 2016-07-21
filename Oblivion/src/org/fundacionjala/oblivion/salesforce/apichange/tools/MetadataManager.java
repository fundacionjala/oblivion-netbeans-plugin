/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.tools;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that manages the metadata file contents
 *
 * @author sergio_daza
 */
public class MetadataManager {

    private final String rootTagName;
    private final FormatMetadataFile formatMetadataFile;
    private final MetadataFileManager managerMetadataFile;
    private boolean isValidForDisplay;
    private static final String TAG_API_VERSION_NAME = "apiVersion";

    public MetadataManager(String pathMetadata, String rootTagName) {
        this.rootTagName = rootTagName;
        formatMetadataFile = new FormatMetadataFile(pathMetadata);
        managerMetadataFile = new MetadataFileManager(pathMetadata);
        isValidForDisplay = true;
    }

    /**
     * Recovers api version current of metadata file
     *
     * @return a String that contains the api version current
     */
    public String ReadAPICurrent() {
        String APIVersionCurrent = "0";
        Document documentMetadata = managerMetadataFile.openMetadata();
        if (managerMetadataFile.getIsValidMetadataFile()) {
            NodeList listOfBooks = documentMetadata.getElementsByTagName(TAG_API_VERSION_NAME);
            for (int i = 0; i < listOfBooks.getLength(); i++) {
                Node firstBookNode = listOfBooks.item(i);
                APIVersionCurrent = firstBookNode.getFirstChild().getNodeValue();
            }
        }
        return APIVersionCurrent;
    }

    /**
     * @return boolean isValidForDisplay, is true if not exist tags nested otherwise false
     */
    public boolean isValidForDisplay() {
        return isValidForDisplay;
    }

    /**
     * Recovers metadata file tags, exept apiVersion tag
     *
     * @return a List <TagOfMetadata>
     */
    public List<TagOfMetadata> getMetadataTags() {
        isValidForDisplay = true;
        List<TagOfMetadata> metadataTags = new ArrayList<>();
        Document documentMetadata = managerMetadataFile.openMetadata();
        if (managerMetadataFile.getIsValidMetadataFile()) {
            NodeList listNodeTags = documentMetadata.getElementsByTagName(rootTagName);
            if (listNodeTags.getLength() == 1) {
                Element firstNameElement = (Element) listNodeTags.item(0);
                NodeList nodeTag = listNodeTags.item(0).getChildNodes();
                for (int j = 0; j < nodeTag.getLength(); j++) {
                    if (nodeTag.item(j).getNodeType() == Node.ELEMENT_NODE) {
                         // Excluded apiVersion tag from the list
                        if (!TAG_API_VERSION_NAME.equals(nodeTag.item(j).getNodeName())) {
                             // If exist tag nested, no is valid for display
                            if (nodeTag.item(j).getChildNodes().getLength() > 1) {
                                isValidForDisplay = false;
                            }
                            boolean add = metadataTags.add(new TagOfMetadata((String) nodeTag.item(j).getNodeName(), (String) nodeTag.item(j).getFirstChild().getNodeValue()));
                        }
                    }
                }
            } else {
                isValidForDisplay = false;
            }
        }
        return metadataTags;
    }

    /**
     * Change the value to current apiVersion tag
     *
     * @param newApi New value for the apiVersion tag
     */
    public void changeApi(String newApi) {
        Document documentMetadata = managerMetadataFile.openMetadata();
        if (managerMetadataFile.getIsValidMetadataFile()) {
            documentMetadata.getElementsByTagName(TAG_API_VERSION_NAME).item(0).getFirstChild().setNodeValue(newApi);
            saveMetadata(documentMetadata);
        }
    }

    /**
     * Save all tags modified from form panel
     *
     * @param tagsOfMetadata values all tags except apiVersion tag
     * @param newApi Value for apiVersion tag
     */
    public void saveTags(List<TagOfMetadata> tagsOfMetadata, String newApi) {
        Document documentMetadata = managerMetadataFile.openMetadata();
        if (managerMetadataFile.getIsValidMetadataFile()) {
            documentMetadata.getElementsByTagName(TAG_API_VERSION_NAME).item(0).getFirstChild().setNodeValue(newApi);
            removeAllTags(documentMetadata);
            addTags(documentMetadata, tagsOfMetadata);
            saveMetadata(documentMetadata);
        }
    }

    /**
     * Remove all tags
     *
     * @param documentMetadata Document metadata that contains all nodes
     */
    public void removeAllTags(Document documentMetadata) {
        List<TagOfMetadata> currentTagsOfMetadata = getMetadataTags();
        for (TagOfMetadata currentTagOfMetadata : currentTagsOfMetadata) {
            if (currentTagOfMetadata.isValid()) {
                Element element = (Element) documentMetadata.getElementsByTagName(currentTagOfMetadata.getName()).item(0);
                element.getParentNode().removeChild(element);
            }
        }
    }

    /**
     * Add new nodes in document
     *
     * @param documentMetadata Document metadata without nodes
     * @param tagsOfMetadata Values all tags except apiVersion tag
     */
    public void addTags(Document documentMetadata, List<TagOfMetadata> tagsOfMetadata) {
        for (TagOfMetadata tagOfMetadata : tagsOfMetadata) {
            if (tagOfMetadata.isValid()) {
                Element element = documentMetadata.getDocumentElement();
                Element node = documentMetadata.createElement(tagOfMetadata.getName());
                node.setTextContent(tagOfMetadata.getValue());
                element.appendChild(node);
            }
        }
    }

    /**
     * Save metadata file and remove white lines
     *
     * @param documentMetadata Document with all new nodes
     */
    public void saveMetadata(Document documentMetadata) {
        managerMetadataFile.saveMetadata(documentMetadata);
        formatMetadataFile.formatFile();
    }

}
