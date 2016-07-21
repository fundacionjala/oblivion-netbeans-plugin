/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.tools;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.util.Exceptions;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Class that manages the file in drive disk
 *
 * @author sergio_daza
 */
public class MetadataFileManager {

    private final String path;
    private boolean isValidMetadataFile;

    public MetadataFileManager(String path) {
        this.path = path;
        isValidMetadataFile = true;
    }

    /**
     * Create new metadata file empty
     *
     * @param apiVersion New value for the apiVersion tag
     * @param tagName Root tag's name
     */
    public void newMetadata(String apiVersion, String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document newDocument = implementation.createDocument("http://soap.sforce.com/2006/04/metadata", tagName, null);
            newDocument.setXmlVersion("1.0");
            Element root = newDocument.getDocumentElement();
            Element childElement = newDocument.createElement("apiVersion");
            childElement.setTextContent(apiVersion);
            root.appendChild(childElement);
            childElement = newDocument.createElement("status");
            childElement.setTextContent("Active");
            root.appendChild(childElement);
            saveMetadata(newDocument);
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Open metadata file
     *
     * @return Document with all metadata tags
     */
    public Document openMetadata() {
        Document doc = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(path));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            //Exceptions.printStackTrace(ex);
            isValidMetadataFile = false;
        }
        return doc;
    }

    /**
     * Save metadata file
     *
     * @param document Document with all nodes
     */
    public void saveMetadata(Document document) {
        try {
            document.normalize();
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult streamResult = new StreamResult(new File(path));
            DOMSource domSource = new DOMSource(document);
            tf.transform(domSource, streamResult);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Exist metadata file
     *
     * @return a boolean, true if exist file into disk
     */
    public boolean existMetadataFile() {
        File metadataFile = new File(path);
        return metadataFile.isFile();
    }

    /**
     * @return is false If an exception occurs in openMetadata()
     */
    public boolean getIsValidMetadataFile() {
        return isValidMetadataFile;
    }
}
