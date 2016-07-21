/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 * Represents the Test Classes folder node, which will contain all the test classes files.
 * 
 * @author Marcelo Garnica
 */
class TestClassesFolder extends AbstractFolderNode {
    
    public static final String TEST_FOLDER_NAME = "test";
    public static final String TEST_FILE_SUFFIX = "Test";

    public TestClassesFolder(FileObject folderObject) throws DataObjectNotFoundException {
        super(folderObject, TEST_FOLDER_NAME);
        setChildren(Children.create(new TestClassChildFactory(folderObject), false));
    }
    
    /*
     * Factory class that will create the file nodes for the valid test class files.
    */
    private class TestClassChildFactory extends AbstractClassChildFactory {

        public TestClassChildFactory(FileObject folderFileObject) {
            super(folderFileObject, TestClassesFolder.this);
        }
        
        /**
         * Validates if the class file object should be part of the test classes folder node, to be valid it should contain
         * the Test keyword on its name and should have the cls extension.
         * 
         * @param childFileObject the file object to be validated.
         * @return whether the child file object is valid.
         */
        @Override
        protected boolean validateChild(FileObject childFileObject) {
            return !childFileObject.isFolder() && childFileObject.getName().contains(TEST_FILE_SUFFIX) && childFileObject.getExt().equals(CLASS_FILE_EXTENSION);
        }
        
    }
    
}
