/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.salesforce.project.nodes.lightning.LightningFolder;
import org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent;
import org.openide.filesystems.FileObject;

/**
 * Abstract action to execute a gradle task over a file
 * @author Marcelo Garnica
 */
public abstract class AbstractFileAction extends AbstractGradleAction {
    
    protected abstract String getFilesParameter();
    
    protected abstract String getCommand();
        
    @Override
    protected AbstractGradleTask buildTask() {
        SalesforceProject salesforceProject = (SalesforceProject) ProjectUtils.getCurrentProject();
        String folder = salesforceProject.getProjectDirectory().getPath();
        CredentialWrapper credential = salesforceProject.getCredential();
        FileObject currentFile = ProjectUtils.getCurrentFile();
        GradleTaskDescriptor result = new GradleTaskDescriptor(getCommand(), folder, getDescription());
        SalesforceErrorsTopComponent.deleteError(salesforceProject.getProjectDirectory().getName(), currentFile.getPath());
        result.setResultHandler(new GradleDeployResultHandler(salesforceProject,currentFile, getErrorMessage()));
        result.addCredentialParameter(credential);
        result.addParameterEntry(getFilesParameter(), getFileParameterValue());
        return result;
    }
    
    private String getFileParameterValue() {
        FileObject currentFile = ProjectUtils.getCurrentFile();
        String fileParameter;
        if (currentFile.getParent().getParent().getName().equalsIgnoreCase(LightningFolder.LIGHTNING_FOLDER)) {
            fileParameter = ProjectUtils.getPathRelativeToSrcFolder(currentFile.getParent());
        } else {
            fileParameter = ProjectUtils.getCurrentFilePathRelativeToSrcFolder();
        }
        return fileParameter;
    }
    
    protected abstract String getDescription();
    
    protected abstract String getErrorMessage();
}
