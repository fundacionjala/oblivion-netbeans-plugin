/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Represents a list of nodes of a Salesforce Project.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectChildrenFactory extends AbstractFolderChildFactory {
    
    private final SalesforceProject project;
      
    public SalesforceProjectChildrenFactory(SalesforceProject project) {
        super(project.getProjectDirectory().getFileObject(SalesforceProject.SOURCE_FOLDER));
        this.project = project;
    }

    @Override
    protected boolean validateChild(FileObject childFileObject) {
        return true;
    }
    
    @Override
    protected Node[] createNodesForKey(FileObject sourceChild) {
        List<Node> nodes = new ArrayList<>();
        if (sourceChild.isFolder()) {
            nodes = FolderNodeFactory.createFolderNodes(sourceChild);
        } else {
            try {
                nodes.add(DataObject.find(sourceChild).getNodeDelegate());
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        Node[] nodesToReturn = new Node[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            nodesToReturn[i] = nodes.get(i).cloneNode();
        }
        return nodesToReturn;
    }

    /**
     * Class used to sort the folders and files of a Salesforce project.
     * 
     * The comparison is done first according to the FileObject type (Folder and File), if both FileObjects have the same type then the
     * name of the FileObject is used for comparison, if they have different types, folders are considered greater than files..
     */
    public static class FileObjectComparator implements Comparator<FileObject> {
        
        /**
         * Compares to FileObjects. if both FileObjects are folders or files the comparison is done by name, if not the
         * one that is a folder is always greater than the one that is a file.
         * 
         * @param fileObject1
         * @param fileObject2
         * @return the comparison result.
         */
        @Override
        public int compare(FileObject fileObject1, FileObject fileObject2) {
            int compareResult = 0;
            if ((fileObject1.isFolder() && fileObject2.isFolder()) || (!fileObject1.isFolder() && !fileObject2.isFolder())) {
                compareResult = fileObject1.getName().compareTo(fileObject2.getName());
            }
            else if (fileObject1.isFolder() && !fileObject2.isFolder()) {
                compareResult = -1;
            }
            else if (!fileObject1.isFolder() && fileObject2.isFolder()) {
                compareResult = 1;
            }
            return compareResult;
        }
    }
}
