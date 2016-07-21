/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Class that will deploy all files from a project into Salesforce
 * @author Marcelo Garnica
 */

@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.salesforce.project.actions.DeployProjectAction"
)
@ActionRegistration(
    displayName = "#CTL_DeployProject",
    key = DeployProjectAction.SHORTCUT
)
@ActionReferences({
    @ActionReference(path = "Shortcuts", name = DeployProjectAction.SHORTCUT)
})
@NbBundle.Messages({"CTL_DeployProject=Deploy Project to Salesforce",
           "gradle.deployProject.description=Deploying Project.",
           "gradle.deployProject.error=There was a problem while deploying the project, please try again."})
public class DeployProjectAction extends AbstractGradleAction {
    
    public static final String SHORTCUT = "O-D";
    
    private static final String COMMAND = "upload";

    @Override
    protected AbstractGradleTask buildTask() {
        SalesforceProject salesforceProject = (SalesforceProject)ProjectUtils.getCurrentProject();
        String folder = salesforceProject.getProjectDirectory().getPath();
        CredentialWrapper credential = salesforceProject.getCredential();
        GradleTaskDescriptor result = new GradleTaskDescriptor(COMMAND, folder, Bundle.gradle_deployProject_description());
        FileObject sourceFolder = salesforceProject.getProjectDirectory().getFileObject(SalesforceProject.SOURCE_FOLDER);
        result.setResultHandler(new GradleDeployResultHandler(sourceFolder, Bundle.gradle_deployProject_error()));
        result.addCredentialParameter(credential);
        return result;
    }

    @Override
    public String getName() {
        return Bundle.CTL_DeployProject();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
}
