/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.nodes.lightning;

import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderChildFactory;
import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderNode;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Represents the Lightning components folder, it contains the lightning applications.
 * @author Marcelo Garnica
 */
public class LightningFolder extends AbstractFolderNode {

    public static final String LIGHTNING_FOLDER = "aura";

    public LightningFolder(FileObject folderObject) throws DataObjectNotFoundException {
        super(folderObject, LIGHTNING_FOLDER);
        setChildren(Children.create(new LightningFolderChildFactory(folderObject), false));
    }
    
    /*
     * Factory class that will create the file nodes for the lightning components.
    */
    private class LightningFolderChildFactory extends AbstractFolderChildFactory {

        public LightningFolderChildFactory(FileObject folderFileObject) {
            super(folderFileObject);
        }
        
        @Override
        protected Node createNodeForKey(FileObject key) {
            Node nodeToReturn = null;
            try {
                nodeToReturn = new ApplicationFolder(key);
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (nodeToReturn != null) {
                nodeToReturn = nodeToReturn.cloneNode();
                nodeToReturn.addPropertyChangeListener(LightningFolder.this);
            }
            return nodeToReturn;
        }

        /**
         * Verifies that the childFileObject should be part of the Lightning folder children.
         * Only folders can be child objects.
         * 
         * @param childFileObject the file object to be validated.
         * @return whether the child file object is valid.
         */
        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return childFileObject.isFolder();
        }
    }
}