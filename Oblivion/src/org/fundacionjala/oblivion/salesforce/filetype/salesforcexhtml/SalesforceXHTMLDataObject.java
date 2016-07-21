/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.filetype.salesforcexhtml;

import java.io.IOException;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Register extension associated with text/x-salesforce+xhtml.
 *
 * @author Sergio Daza
 */
@Messages({
    "LBL_Salesforcehtml_LOADER=Files of Salesforcehtml"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Salesforcehtml_LOADER",
    mimeType = MimeType.XHTML_MIME_TYPE,
    extension = {"component", "COMPONENT", "page", "PAGE", "resource", "RESOURCE", "auradoc", "AURADOC", "cmp", "CMP", "evt", "EVT", "intf", "INTF", "design", "DESIGN"}
)
@DataObject.Registration(
    mimeType = MimeType.XHTML_MIME_TYPE,
    iconBase = "org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png",
    displayName = "#LBL_Salesforcehtml_LOADER",
    position = 300
)
@ActionReferences({
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
        position = 100,
        separatorAfter = 200
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
        position = 300
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
        position = 400,
        separatorAfter = 500
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
        position = 600
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
        position = 700,
        separatorAfter = 800
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
        position = 900,
        separatorAfter = 1000
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
        position = 1100,
        separatorAfter = 1200
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
        position = 1300
    ),
    @ActionReference(
        path = MimeType.XHTML_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
        position = 1400
    )
})
public class SalesforceXHTMLDataObject extends MultiDataObject {

    public SalesforceXHTMLDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor(MimeType.XHTML_MIME_TYPE, true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
        displayName = "#LBL_Salesforcehtml_EDITOR",
        iconBase = "org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png",
        mimeType = MimeType.XHTML_MIME_TYPE,
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
        preferredID = "Salesforcehtml",
        position = 1000
    )
    @Messages("LBL_Salesforcehtml_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

}
