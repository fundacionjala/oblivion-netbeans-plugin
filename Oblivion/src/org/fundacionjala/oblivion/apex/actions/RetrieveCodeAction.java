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
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.apex.actions.RetrieveCodeAction"
)
@ActionRegistration(
    displayName = "#CTL_RetrieveCodeAction", key = RetrieveCodeAction.SHORTCUT
)
@ActionReferences({
    @ActionReference(path = MimeType.APEX_CLASS_ACTIONS_EDITOR_POPUP, position = 151, separatorAfter = 175),
    @ActionReference(path = MimeType.APEX_TRIGGER_ACTIONS_EDITOR_POPUP, position = 151, separatorAfter = 175),
    @ActionReference(path = MimeType.XHTML_ACTIONS_EDITOR_POPUP, position = 151, separatorAfter = 175),
    @ActionReference(path = MimeType.XML_ACTIONS_EDITOR_POPUP, position = 151, separatorAfter = 175),
    @ActionReference(path = MimeType.XHTML_ACTIONS_PATH, position = 151, separatorAfter = 175),
    @ActionReference(path = MimeType.XML_ACTIONS_PATH, position = 151, separatorAfter = 175),
    @ActionReference(path = "Shortcuts", name = RetrieveCodeAction.SHORTCUT)
})
@Messages({"CTL_RetrieveCodeAction=Refresh from server",
    "gradle.retrieveCode.description=Refreshing from server",
    "gradle.retrieveCode.error=Couldn't refresh the file, please try again."})
public final class RetrieveCodeAction extends AbstractFileAction {

    public static final String SHORTCUT = "O-R";
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
        return Bundle.CTL_RetrieveCodeAction();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    protected String getDescription() {
        return Bundle.gradle_retrieveCode_description();
    }

    @Override
    protected String getErrorMessage() {
        return Bundle.gradle_retrieveCode_error();
    }
}
