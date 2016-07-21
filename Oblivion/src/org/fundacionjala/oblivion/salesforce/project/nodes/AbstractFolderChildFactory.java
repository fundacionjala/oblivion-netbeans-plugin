/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.util.Arrays;
import java.util.List;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;
import org.openide.nodes.ChildFactory;

/**
 * Factory which will create the child node elements of a folder node. Children classes must override either
 * <code>createNodesForKey</code> or <code>createNodeForKey</code>, the implementation of these methods must always
 * return the created node(s) clone due to an issue when closing and reopening a project. see more on:
 * @see http://comments.gmane.org/gmane.comp.java.netbeans.modules.openide.devel/62890
 * @see https://netbeans.org/bugzilla/show_bug.cgi?id=221817
 * @see http://hg.netbeans.org/core-main/rev/0c9de3ddbd5c
 *
 * @author Marcelo Garnica
 */
public abstract class AbstractFolderChildFactory extends ChildFactory<FileObject> implements FileChangeListener {

    protected final FileObject folderFileObject;

    protected boolean hasChangeListener = false;

    public AbstractFolderChildFactory(FileObject folderFileObject) {
        this.folderFileObject = folderFileObject;
    }

    /**
     * Validates if the child file object should be part of the folder node.
     *
     * @param childFileObject the file object to be validated.
     * @return whether the child file object is valid.
     */
    protected abstract boolean validateChild(FileObject childFileObject);

    /**
     * Adds this class instance as a change listener for the folderFileObject. Only if it has not been added before.
     */
    protected void addChangeListener() {
        if (!hasChangeListener) {
            folderFileObject.addFileChangeListener(this);
            hasChangeListener = true;
        }
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
        FileObject[] childrenFileObjects = folderFileObject.getChildren();
        Arrays.sort(childrenFileObjects, new SalesforceProjectChildrenFactory.FileObjectComparator());
        for (FileObject childFileObject : childrenFileObjects) {
            if (validateChild(childFileObject)) {
                toPopulate.add(childFileObject);
            }
        }
        return true;
    }

    /**
     * Folder created event.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileFolderCreated(FileEvent fe) {
        refresh(false);
    }

    /**
     * File created event. The list of nodes will be updated after a new file was created.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileDataCreated(FileEvent fe) {
        refresh(false);
    }

    /**
     * File modified event. The list of nodes should be updated after a new file was modified.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileChanged(FileEvent fe) {
        refresh(false);
    }

    /**
     * File deleted event. The list of nodes will be updated after a file was deleted.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileDeleted(FileEvent fe) {
        refresh(false);
    }

    /**
     * File renamed event. The list of nodes will be updated after a new file was renamed.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileRenamed(FileRenameEvent fe) {
        refresh(false);
    }

    /**
     * File attribute change event.
     *
     * @param fe FileEvent containing the event information.
     */
    @Override
    public void fileAttributeChanged(FileAttributeEvent fe) {

    }
}
