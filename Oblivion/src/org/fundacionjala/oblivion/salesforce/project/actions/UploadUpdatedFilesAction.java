/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;

@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.salesforce.project.actions.UploadUpdatedFilesAction"
)
@ActionRegistration(
    iconBase = "org/fundacionjala/oblivion/apex/resources/upload.png",
    displayName = "#CTL_UploadUpdatedFiles",
    key = UploadUpdatedFilesAction.SHORTCUT
)
@ActionReferences({
    @ActionReference(path = "Toolbars/Build", position = 150),
    @ActionReference(path = "Shortcuts", name = UploadUpdatedFilesAction.SHORTCUT)
})
@Messages({"CTL_UploadUpdatedFiles=Save Updated files to Salesforce",
           "gradle.updateOrganization.description=Updating organization.",
           "gradle.updateOrganization.error=There was a problem while updating the organization, please try again."})
public final class UploadUpdatedFilesAction extends AbstractGradleAction {

    public static final String SHORTCUT = "O-U";
    
    private static final String COMMAND = "update";

    @Override
    protected AbstractGradleTask buildTask() {
        SalesforceProject salesforceProject = (SalesforceProject)ProjectUtils.getCurrentProject();
        String folder = salesforceProject.getProjectDirectory().getPath();
        CredentialWrapper credential = salesforceProject.getCredential();
        GradleTaskDescriptor result = new GradleTaskDescriptor(COMMAND, folder, Bundle.gradle_updateOrganization_description());
        FileObject sourceFolder = salesforceProject.getProjectDirectory().getFileObject(SalesforceProject.SOURCE_FOLDER);
        SalesforceErrorsTopComponent.deleteProject(salesforceProject.getProjectDirectory().getName());
        result.setResultHandler(new GradleDeployResultHandler(salesforceProject, sourceFolder, Bundle.gradle_updateOrganization_error()));
        result.addCredentialParameter(credential);
        return result;
    }

    @Override
    public String getName() {
        return Bundle.CTL_UploadUpdatedFiles();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
    
    
}
