/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;

import static org.fundacionjala.oblivion.salesforce.project.nodes.TestClassesFolder.TEST_FILE_SUFFIX;

/**
 * Represents the classes folder node, which will contain all the classes files.
 * 
 * @author Marcelo Garnica
 */
class ClassesFolder extends AbstractFolderNode {

    public static final String CLASSES_FOLDER = "classes";

    public ClassesFolder(FileObject folderObject) throws DataObjectNotFoundException {
        super(folderObject, CLASSES_FOLDER);
        setChildren(Children.create(new ClassesChildFactory(folderObject), false));
    }
    
    /*
     * Factory class that will create the file nodes for the valid class files.
    */
    private class ClassesChildFactory extends AbstractClassChildFactory {

        public ClassesChildFactory(FileObject folderFileObject) {
            super(folderFileObject, ClassesFolder.this);
        }

        /**
         * Validates if the class file object should be part of the classes folder node, to be valid it shouldn't contain
         * the Test keyword on its name and should have the cls extension.
         * 
         * @param childFileObject the file object to be validated.
         * @return whether the child file object is valid.
         */
        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return !childFileObject.isFolder() && !childFileObject.getName().contains(TEST_FILE_SUFFIX) && childFileObject.getExt().equals(CLASS_FILE_EXTENSION);
        }
        
    }    
}
