/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.util.ArrayList;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Represents a logical package folder node.
 * 
 * @author Marcelo Garnica
 */
public class PackageFolderNode extends AbstractFolderNode {
    
    public static final String PACKAGE_FOLDER_ICON = "org/fundacionjala/oblivion/salesforce/resources/PackageFolder_Icon.png";
    
    private final PackageChildFactory childFactory;

    public PackageFolderNode(String packageName, FileObject folderObject) throws DataObjectNotFoundException {
        super(folderObject, packageName, PACKAGE_FOLDER_ICON);
        childFactory = new PackageChildFactory(folderObject);
        setChildren(Children.create(childFactory, false));
    }
    
    /**
     * Adds a new node into the package child factory.
     * 
     * @param classFileObject class file object to be added.
     */
    public void addClassFile(FileObject classFileObject) {
        childFactory.addClassFile(classFileObject);
    }
    
    /**
     * Factory that creates the child nodes of a package folder.
     */
    private class PackageChildFactory extends AbstractFolderChildFactory {
        
        private final List<FileObject> childFiles;

        private PackageChildFactory(FileObject folderObject) {
            super(folderObject);
            childFiles = new ArrayList<>();
        }

        /**
         * Validates if the child file object has the class extension.
         * 
         * @param childFileObject the file object to be validated.
         * @return whether the child file object is valid.
         */
        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return childFileObject.getExt().equalsIgnoreCase(CLASS_FILE_EXTENSION);
        }
        
        /**
         * Create a list of file object which will be used to create child Nodes.
         * 
         * @param toPopulate list of file objects to be populated.
         * @return true as the list has been completely populated.
         */
        @Override
        protected boolean createKeys(List<FileObject> toPopulate) {
            addChangeListener();
            for (FileObject childFile : childFiles) {
                toPopulate.add(childFile);
            }
            return true;
        }
        
        /**
         * Create a Node for a given class file that was put into the list passed into createKeys.\
         * The node's clone is returned due to an issue when closing and reopening a project.
         * see more on: 
         *              http://comments.gmane.org/gmane.comp.java.netbeans.modules.openide.devel/62890
         *              https://netbeans.org/bugzilla/show_bug.cgi?id=221817 
         *              http://hg.netbeans.org/core-main/rev/0c9de3ddbd5c
         * 
         * @param classFile the class file for which a node will be created.
         * @return The node to be displayed on the logical view.
         */
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
                nodeToReturn.addPropertyChangeListener(PackageFolderNode.this);
            }
            return nodeToReturn;
        }
        
        /**
         * Validates and Adds a new class file into the list of child files.
         * 
         * @param classFileObject the class file to be added.
         */
        private void addClassFile(FileObject classFileObject) {
            if (validateChild(classFileObject)) {
                childFiles.add(classFileObject);
                refresh(false);
            }
        }
    }
}
