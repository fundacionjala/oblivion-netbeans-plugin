/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Represents a generic folder that is part of a salesforce project source folder.
 * @author Marcelo Garnica
 */
public class GenericFolder extends AbstractFolderNode {
    
    public GenericFolder(FileObject folderFileObject) throws DataObjectNotFoundException {
        super(folderFileObject, folderFileObject.getName());
        setChildren(Children.create(new GenericFolderChildFactory(folderFileObject), false));
    }
    
    private class GenericFolderChildFactory extends AbstractFolderChildFactory {

        public GenericFolderChildFactory(FileObject folderFileObject) {
            super(folderFileObject);
        }
        
        @Override
        protected Node createNodeForKey(FileObject key) {
            Node nodeToReturn = null;
            try {
                nodeToReturn = DataObject.find(key).getNodeDelegate();
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (nodeToReturn != null) {
                nodeToReturn = nodeToReturn.cloneNode();
                nodeToReturn.addPropertyChangeListener(GenericFolder.this);
            }
            return nodeToReturn;
        }

        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return true;
        }
        
    }
    
}
