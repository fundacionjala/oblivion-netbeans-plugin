/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.ui.wizard;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * Represents a Wizard iterator which handles the transition between panel when creating a new file type.
 *
 * @author Marcelo Garnica
 */
public abstract class AbstractFileTypeWizardIterator extends AbstractWizardIterator {

    private static final String SALESFORCE_METADATA_NAMESPACE = "http://soap.sforce.com/2006/04/metadata";

    protected abstract Map<String, String> getTemplateParameters();

    protected abstract String getNewFileName();

    protected abstract Map<String, String> getMetadataParameters();

    protected abstract String getMetadataFileName();

    protected abstract String getMetadataName();

    protected abstract String getDestinationFolder();

    protected abstract Boolean getWithMetadataFile();
    
    protected abstract Object getName();
    
    @Override
    public Set<FileObject> instantiate() throws IOException {
        if(!isUsedName()) {
            FileObject pathDestinationFolder = ProjectUtils.getCurrentProject().getProjectDirectory();
            File destinationFolder = new File(pathDestinationFolder.getPath() + "/" + getDestinationFolder());
            if (!destinationFolder.isDirectory()) {
                destinationFolder.mkdir();
            }

            FileObject template = Templates.getTemplate(wizard);
            DataObject templateDataObject = DataObject.find(template);

            FileObject targetFolder = ProjectUtils.getCurrentProject().getProjectDirectory().getFileObject(getDestinationFolder());
            DataFolder targetFolderDataObject = DataFolder.findFolder(targetFolder);

            DataObject createdDataFile = templateDataObject.createFromTemplate(targetFolderDataObject, getNewFileName(), getTemplateParameters());
            FileObject createdFile = createdDataFile.getPrimaryFile();
            if (getWithMetadataFile()) {
                createMetadataFile(targetFolder);
            }
            return Collections.singleton(createdFile);
        } else {
            return Collections.EMPTY_SET;
        }
                
    }

    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed
    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    @Override
    protected String[] createSteps() {
        String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
        assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
        String[] res = new String[(beforeSteps.length - 1) + panels.size()];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
            }
        }
        return res;
    }

    /**
     * Creates the metadata file for the file that was created by the wizard.
     *
     * @param targetFolder The folder on which the metadata file will be created.
     */
    private void createMetadataFile(FileObject targetFolder) {
        try {
            Document newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = newXmlDocument.createElementNS(SALESFORCE_METADATA_NAMESPACE, getMetadataName());
            Element childElement;
            for (Map.Entry<String, String> node : getMetadataParameters().entrySet()) {
                childElement = newXmlDocument.createElement(node.getKey());
                childElement.setTextContent(node.getValue());
                rootElement.appendChild(childElement);
            }
            FileObject createdData = targetFolder.createData(getMetadataFileName());
            DOMImplementationLS domImplementation = (DOMImplementationLS) newXmlDocument.getImplementation();
            LSSerializer serializer = domImplementation.createLSSerializer();
            serializer.writeToURI(rootElement, createdData.toURI().toString());
        } catch (ParserConfigurationException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    /**
     * This method checks if a name is already used.
     * 
     * @return a boolean, true if the name is already used
     */
    public boolean isUsedName(){
        boolean result = false;
            File file = new File(ProjectUtils.getCurrentProjectPath() + "/"+getDestinationFolder());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File item : files) {
                    if(item.getName().trim().toLowerCase().equals(getNewFileName().toLowerCase())) {
                        result = true;
                        break;
                    }
                }
            }
        return result;
    }
    
}
