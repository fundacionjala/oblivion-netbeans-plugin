/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.fundacionjala.oblivion.salesforce.project.nodes.lightning.LightningFolder;
import java.util.ArrayList;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Factory that creates a list of folder nodes according to a file object folder.
 * 
 * @author Marcelo Garnica
 */
class FolderNodeFactory {
    
    public static final String CLASSES_FOLDER_NAME = ClassesFolder.CLASSES_FOLDER;

    /**
     * Creates a list of folder nodes that will contain the contents of a given folder file object.
     * 
     * @param folder file object folder which contents will be displayed on the folder nodes.
     * @return a list of folder nodes.
     */
    static List<Node> createFolderNodes(FileObject folder) {
        List<Node> folderNodes = new ArrayList<>();
        try {
            if (folder.getName().equalsIgnoreCase(CLASSES_FOLDER_NAME)) {
                folderNodes.add(new ClassesFolder(folder));
                folderNodes.add(new TestClassesFolder(folder));
            } else if (folder.getName().equalsIgnoreCase(LightningFolder.LIGHTNING_FOLDER)) {
                folderNodes.add(new LightningFolder(folder));
            } else {
                folderNodes.add(new GenericFolder(folder));
            }
        } catch (DataObjectNotFoundException dataObjectNotFoundException) {
            Exceptions.printStackTrace(dataObjectNotFoundException);
        }
        return folderNodes;
    }
    
}
