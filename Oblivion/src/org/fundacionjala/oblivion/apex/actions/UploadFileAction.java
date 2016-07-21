/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Uploads a file to salesforce.
 *
 * @author Marcelo Garnica
 */
@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.apex.actions.UploadFileAction"
)
@ActionRegistration(
    displayName = "#CTL_UploadFile", key = UploadFileAction.SHORTCUT
)

@ActionReferences({
    @ActionReference(path = MimeType.APEX_CLASS_ACTIONS_EDITOR_POPUP, position = 150, separatorBefore = 125),
    @ActionReference(path = MimeType.APEX_TRIGGER_ACTIONS_EDITOR_POPUP, position = 150, separatorBefore = 125),
    @ActionReference(path = MimeType.XHTML_ACTIONS_EDITOR_POPUP, position = 150, separatorBefore = 125),
    @ActionReference(path = MimeType.XML_ACTIONS_EDITOR_POPUP, position = 150, separatorBefore = 125),
    @ActionReference(path = MimeType.XHTML_ACTIONS_PATH, position = 150, separatorBefore = 125),
    @ActionReference(path = MimeType.XML_ACTIONS_PATH, position = 150, separatorBefore = 125),
    @ActionReference(path = "Shortcuts", name = UploadFileAction.SHORTCUT)
})

@NbBundle.Messages({"CTL_UploadFile=Save to server",
    "gradle.uploadFile.description=Saving file to server.",
    "gradle.uploadFile.error=There was a problem while saving the file, please try again."})
public class UploadFileAction extends AbstractFileAction {

    public static final String SHORTCUT = "O-S";
    private static final String COMMAND = "upload";
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
        return Bundle.CTL_UploadFile();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    protected String getDescription() {
        return Bundle.gradle_uploadFile_description();
    }

    @Override
    protected String getErrorMessage() {
        return Bundle.gradle_uploadFile_error();
    }
}
