/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import javax.swing.Action;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.DownloadProjectTask;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.actions.Bundle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;


/**
 * Download all the code from a Salesforce organization.
 * 
 * @see CreateGradleCredentialsAction
 * @author Alejandro Ruiz
 * @author Adrian Grajeda
 */
@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.salesforce.project.actions.DownloadProjectAction"
)
@ActionRegistration(
    iconBase = "org/fundacionjala/oblivion/apex/resources/download.png",
    displayName = "#CTL_DownloadProject"
    
)

@NbBundle.Messages({"CTL_DownloadProject=Download project from SalesForce", 
                    "downloadProject.description=Download the SalesForce content into this project",
                    "gradle.downloadProject.description=Downloading Project.",
                    "gradle.downloadProject.error=There was a problem while downloading the project, please try again."})
public class DownloadProjectAction extends AbstractGradleAction {
    

    public DownloadProjectAction() {
        putValue(Action.SHORT_DESCRIPTION, Bundle.downloadProject_description());
    }

    /**
     * Get the value of folder
     *
     * @return the value of folder
     */
    public String getFolder() {
        return ProjectUtils.getCurrentProjectPath();
    }


    @Override
    protected AbstractGradleTask buildTask()  {
        return new DownloadProjectTask(getFolder(), 
            Bundle.gradle_downloadProject_description(), 
            Bundle.gradle_downloadProject_error());
    }

    @Override
    public String getName() {
        return Bundle.CTL_DownloadProject();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
}
