/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderNode;
import org.fundacionjala.oblivion.salesforce.project.nodes.PackageFolderNode;
import org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * Abstract action to execute a gradle task over a folder or a logical PackageFolder
 * @author Marcelo Garnica
 */
public abstract class AbstractFolderAction extends AbstractGradleAction {
    
    public AbstractFolderAction() {
        super();
    }
    
    protected abstract String getFilesParameter();
    
    protected abstract String getCommand();
    
    @Override
    protected AbstractGradleTask buildTask() {
        SalesforceProject salesforceProject = (SalesforceProject) ProjectUtils.getCurrentProject();
        String folder = salesforceProject.getProjectDirectory().getPath();
        CredentialWrapper credential = salesforceProject.getCredential();
        AbstractFolderNode currentFolderNode = ProjectUtils.getCurrentFolderNode();
        FileObject folderFileObject = currentFolderNode.getFileObject();
        GradleTaskDescriptor result = new GradleTaskDescriptor(getCommand(), folder, getDescription());
        SalesforceErrorsTopComponent.deleteError(salesforceProject.getProjectDirectory().getName(), folderFileObject.getPath());
        result.setResultHandler(new GradleDeployResultHandler(salesforceProject, folderFileObject, getErrorMessage()));
        result.addCredentialParameter(credential);        
        result.addParameterEntry(getFilesParameter(), AbstractFolderAction.this.getFilesParameterValue(currentFolderNode));
        return result;
    }
    
    private String getFilesParameterValue(AbstractFolderNode folderNode) {
        String filesParameter;
        if (folderNode instanceof PackageFolderNode) {
            PackageFolderNode packageFolder = (PackageFolderNode) folderNode;
            Children children = packageFolder.getChildren();
            List<String> files = new ArrayList<>();
            FileObject childFileObject;
            for (Node child : children.getNodes()) {
                childFileObject = child.getLookup().lookup(DataObject.class).getPrimaryFile();
                files.add(ProjectUtils.getPathRelativeToSrcFolder(childFileObject));
            }
            filesParameter = String.join(",", files);
        } else {
            filesParameter = ProjectUtils.getPathRelativeToSrcFolder(folderNode.getFileObject());
        }
        return filesParameter;
    }
    
    protected abstract String getDescription();
    
    protected abstract String getErrorMessage();
}
