/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Factory which will create the child node elements for Class files, it will generate logical package folders if there
 * are files that contain the package folder pattern.
 *
 * @author Marcelo Garnica
 */
public abstract class AbstractClassChildFactory extends AbstractFolderChildFactory {

    public static final String PACKAGE_PATTERN = "//@@package";

    private final Map<String, PackageFolderNode> packages;
    private final Map<String, String> packagesNames;
    private final PropertyChangeListener propertyChangeListener;

    public AbstractClassChildFactory(FileObject folderFileObject, PropertyChangeListener propertyChangeListener) {
        super(folderFileObject);
        packages = new HashMap<>();
        this.packagesNames = new HashMap<>();
        this.propertyChangeListener = propertyChangeListener;
    }

    /**
     * Create a Node for a given class file that was put into the list passed into createKeys. The node's clone is
     * returned due to an issue when closing and reopening a project. see more on:
     * http://comments.gmane.org/gmane.comp.java.netbeans.modules.openide.devel/62890
     * https://netbeans.org/bugzilla/show_bug.cgi?id=221817 http://hg.netbeans.org/core-main/rev/0c9de3ddbd5c
     *
     * @param classFile the class file for which a node will be created.
     * @return The node to be displayed on the logical view.
     */
    @Override
    protected Node createNodeForKey(FileObject classFile) {
        String packageName = getPackageName(classFile);
        Node nodeToReturn = null;
        try {
            if (!packageName.isEmpty()) {
                PackageFolderNode packageFolder;
                if (packages.containsKey(packageName)) {
                    packageFolder = packages.get(packageName);
                } else {
                    packageFolder = new PackageFolderNode(packageName, folderFileObject);
                    packages.put(packageName, packageFolder);
                    nodeToReturn = packageFolder;
                }
                packageFolder.addClassFile(classFile);
            } else {
                nodeToReturn = DataObject.find(classFile).getNodeDelegate();
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (nodeToReturn != null) {
            nodeToReturn = nodeToReturn.cloneNode();
            nodeToReturn.addPropertyChangeListener(propertyChangeListener);
        }
        return nodeToReturn;
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
        this.packagesNames.clear();
        FileObject[] childrenFileObjects = folderFileObject.getChildren();
        Arrays.sort(childrenFileObjects, new ClassFileComparator(this.packagesNames));
        for (FileObject childFileObject : childrenFileObjects) {
            if (validateChild(childFileObject)) {
                toPopulate.add(childFileObject);
            }
        }
        return true;
    }

    /**
     * Retrieves the package name of a give class file, the PACKAGE_PATTERN should be on the first line of the file.
     *
     * @param classFileObjectthe class file for which a node will be created.
     * @return the classes package name, if there is not a package, it returns an empty string.
     */
    private String getPackageName(FileObject classFileObject) {
        String packageName = "";
        if (this.packagesNames.containsKey(classFileObject.getPath())) {
            packageName = this.packagesNames.get(classFileObject.getPath());
        }
        return packageName;
    }
    
    /**
     * Class used to sort the folders and files of a Salesforce project.
     * 
     * The comparison is done first according to the FileObject type (Folder and File), if both FileObjects have the same type then the
     * name of the FileObject is used for comparison, if they have different types, folders are considered greater than files..
     */
    private static class ClassFileComparator implements Comparator<FileObject> {

        private final Map<String, String> packageMap;
        
        public ClassFileComparator(Map<String, String> packageMap) {
            this.packageMap = packageMap;
        }
        
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
            if (fileObject1.isFolder() && fileObject2.isFolder()) {
                compareResult = fileObject1.getName().compareTo(fileObject2.getName());
            } else if (!fileObject1.isFolder() && !fileObject2.isFolder()) {
                String packageName1 = getPackageName(fileObject1);
                String packageName2 = getPackageName(fileObject2);
                this.packageMap.put(fileObject1.getPath(), packageName1);
                this.packageMap.put(fileObject2.getPath(), packageName2);
                if (packageName1 == "" && packageName2 == "") {
                    compareResult = fileObject1.getName().compareTo(fileObject2.getName());
                } else if (packageName1 == "" && packageName2 != "") {
                    compareResult = 1;
                } else if (packageName1 != "" && packageName2 == "") {
                    compareResult = -1;
                } else {
                    compareResult = packageName1.compareTo(packageName2);
                }
            } else if (fileObject1.isFolder() && !fileObject2.isFolder()) {
                compareResult = -1;
            }
            else if (!fileObject1.isFolder() && fileObject2.isFolder()) {
                compareResult = 1;
            }
            return compareResult;
        }
        
        /**
        * Retrieves the package name of a give class file, the PACKAGE_PATTERN should be on the first line of the file.
        *
        * @param classFileObjectthe class file for which a node will be created.
        * @return the classes package name, if there is not a package, it returns an empty string.
        */
       private String getPackageName(FileObject classFileObject) {
           String packageName = "";
           try {
               if (!classFileObject.asLines().isEmpty()) {
                   packageName = buildPackageName(classFileObject.asLines().get(0));
               }
           } catch (IOException ex) {
               Exceptions.printStackTrace(ex);
           }
           return packageName;
       }
       
       /**
        * Builds the package name of the first line of a file only if it contains the PACKAGE_PATTERN.
        * @param line - The line on which the package name will be generated.
        * @return The package name or an empty string if it doesn't have the PACKAGE_PATTERN.
        */
       private String buildPackageName(String line) {
           String packageName = "";
           if (line.startsWith(PACKAGE_PATTERN)) {
               packageName = line.substring(PACKAGE_PATTERN.length()).replaceAll(";", "").trim();
           }
           return packageName;
       }
    }
}
