/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

/**
 * Refreshes a file from salesforce.
 *
 * @author Marcelo Garnica
 */
@ActionID(
    category = "Salesforce/folder/Actions",
    id = "org.fundacionjala.oblivion.apex.actions.RefreshFolderAction"
)

@ActionRegistration(
    displayName = "#CTL_RefreshFolder"
)

@NbBundle.Messages({"CTL_RefreshFolder=Update folder elements from server",
    "gradle.refreshFolder.description=Updating folder elements from server.",
    "gradle.refreshFolder.error=There was a problem while updating the folder elements, please try again."})
public class RefreshFolderAction extends AbstractFolderAction {
    
    private static final String COMMAND = "retrieve";
    private static final String FILES_PARAMETER = "files";

    @Override
    protected String getFilesParameter() {
        return FILES_PARAMETER;
    }

    @Override
    protected String getCommand() {
        return COMMAND;
    }

    @Override
    public String getName() {
        return Bundle.CTL_RefreshFolder();
    }

    @Override
    protected String getDescription() {
        return Bundle.gradle_refreshFolder_description();
    }

    @Override
    protected String getErrorMessage() {
        return Bundle.gradle_refreshFolder_error();
    }
    
}
