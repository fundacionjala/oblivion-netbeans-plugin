/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.nodes.lightning;

import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderChildFactory;
import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderNode;
import org.fundacionjala.oblivion.salesforce.project.nodes.GenericFolder;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Represents a Lightning application container folder.
 * @author Marcelo Garnica
 */
public class ApplicationFolder extends AbstractFolderNode {
    
   public ApplicationFolder(FileObject folderObject) throws DataObjectNotFoundException {
        super(folderObject, folderObject.getName());
        setChildren(Children.create(new LightningFolderChildFactory(folderObject), false));
    }
    
   /*
     * Factory class that will create the file nodes for the valid components of the application.
    */
    private class LightningFolderChildFactory extends AbstractFolderChildFactory {

        public LightningFolderChildFactory(FileObject folderFileObject) {
            super(folderFileObject);
        }
        
        @Override
        protected Node createNodeForKey(FileObject key) {
            Node nodeToReturn = null;
            try {
                if (key.isFolder()) {
                    nodeToReturn = new GenericFolder(key);
                } else {
                    nodeToReturn = new LightningFileNode(key);
                }
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (nodeToReturn != null) {
                nodeToReturn = nodeToReturn.cloneNode();
                nodeToReturn.addPropertyChangeListener(ApplicationFolder.this);
            }
            return nodeToReturn;
        }

        /**
         * Validates if childFileObject should be part of the children nodes, for now it's accepting all files as child nodes.
         * 
         * @param childFileObject the file object to be validated.
         * @return whether the child file object is valid.
         */
        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return true;
        }
    }
}
