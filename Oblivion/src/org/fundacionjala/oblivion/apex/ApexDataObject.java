/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex;

import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode;
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
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_Apex_LOADER=Files of Apex"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Apex_LOADER",
    mimeType = MimeType.APEX_CLASS_MIME_TYPE,
    extension = {"cls", "CLS"}
)
@DataObject.Registration(
    mimeType = MimeType.APEX_CLASS_MIME_TYPE,
    iconBase = "org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png",
    displayName = "#LBL_Apex_LOADER",
    position = 300
)
@ActionReferences({
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
        position = 100,
        separatorAfter = 200
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
        position = 300
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
        position = 400,
        separatorAfter = 500
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
        position = 600
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
        position = 700,
        separatorAfter = 800
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
        position = 900,
        separatorAfter = 1000
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
        position = 1100,
        separatorAfter = 1200
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
        position = 1300,
        separatorAfter = 1400
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "Salesforce", id = "org.fundacionjala.oblivion.apex.actions.RetrieveCodeAction"),
        position = 1500
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "Salesforce", id = "org.fundacionjala.oblivion.apex.actions.UploadFileAction"),
        position = 1600,
        separatorAfter = 1700        
    ),
    @ActionReference(
        path = MimeType.APEX_CLASS_ACTIONS_PATH,
        id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
        position = 1800
    )
    
})
public class ApexDataObject extends MultiDataObject {

    public ApexDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor(MimeType.APEX_CLASS_MIME_TYPE, true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
        displayName = "#LBL_Apex_EDITOR",
        iconBase = "org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png",
        mimeType = MimeType.APEX_CLASS_MIME_TYPE,
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
        preferredID = "Apex",
        position = 1000
    )
    @Messages("LBL_Apex_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }
    
    @Override
    protected Node createNodeDelegate() {
        return new SalesforceDataNode(this, Children.LEAF, getLookup());
    }
}
