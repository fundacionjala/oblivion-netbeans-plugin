/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

/**
 * Action to upload a folder to salesforce.
 * @author Marcelo Garnica
 */
@ActionID(
    category = "Salesforce/folder/Actions",
    id = "org.fundacionjala.oblivion.apex.actions.UploadFolderAction"
)

@ActionRegistration(
    displayName = "#CTL_UploadFolder"
)

@NbBundle.Messages({"CTL_UploadFolder=Save folder elements to server",
    "gradle.uploadFolder.description=Saving folder elements to server.",
    "gradle.uploadFolder.error=There was a problem while saving the folder elements, please try again."})
public class UploadFolderAction extends AbstractFolderAction {
    
    private static final String COMMAND = "upload";
    private static final String FILES_PARAMETER = "files";

    @Override
    public String getName() {
        return Bundle.CTL_UploadFolder();
    }

    @Override
    protected String getFilesParameter() {
        return FILES_PARAMETER;
    }

    @Override
    protected String getCommand() {
        return COMMAND;
    }

    @Override
    protected String getDescription() {
        return Bundle.gradle_uploadFolder_description();
    }

    @Override
    protected String getErrorMessage() {
        return Bundle.gradle_uploadFolder_error();
    }
}