/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.nodes.lightning;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.fundacionjala.oblivion.apex.actions.RetrieveCodeAction;
import org.fundacionjala.oblivion.apex.actions.UploadFileAction;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;

/**
 * Represents a File element that is under a Lightning application folder.
 * @author Marcelo Garnica
 */
public class LightningFileNode extends SalesforceDataNode {

    private final FileObject fileObject;

    public LightningFileNode(FileObject fileObject) {
        super(fileObject.getLookup().lookup(DataObject.class), Children.LEAF, fileObject.getLookup());
        this.fileObject = fileObject;
    }
    
    @Override
    public Action[] getActions(boolean context) {
        Action[] actionsToReturn = getDataObject().getNodeDelegate().getActions(context);
        if (!MimeType.getMimeTypes().contains(this.fileObject.getMIMEType())) {
            List<Action> actions = new ArrayList<>(Arrays.asList(actionsToReturn));
            actions.add(1, null);
            actions.add(2, SystemAction.get(RetrieveCodeAction.class));
            actions.add(3, SystemAction.get(UploadFileAction.class));
            actions.add(4, null);
            actionsToReturn = actions.toArray(new Action[]{});
        }
        return actionsToReturn;
    }
    
    /**
     * Gets the Icon to be displayed for the Node.
     * 
     * @param type - constant from BeanInfo, the icon size.
     * @return Icon to represent the node.
     */
    @Override
    public Image getIcon(int type) {
        return getDataObject().getNodeDelegate().getIcon(type);
    }
}
