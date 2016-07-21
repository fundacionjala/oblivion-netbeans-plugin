/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Class that creates the logical view of a Salesforce project.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectLogicalView implements LogicalViewProvider {
    
    private final SalesforceProject salesforceProject;
    
    public SalesforceProjectLogicalView(SalesforceProject salesforceProject) {
        this.salesforceProject = salesforceProject;
    }

    /**
     * Creates the project root logical node.
     * 
     * @return a Node that displays the contents of the project.
     */
    @Override
    public Node createLogicalView() {
        Node node;
        try {
            FileObject projectDirectory = salesforceProject.getProjectDirectory().getFileObject(SalesforceProject.SOURCE_FOLDER);
            DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
            Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
            node = new SalesforceProjectNode(nodeOfProjectFolder, salesforceProject);
        } catch(DataObjectNotFoundException donfe) {
            Exceptions.printStackTrace(donfe);
            node = new AbstractNode(Children.LEAF);
        }
        return node;
    }

    /**
     * TODO: implement me
     */
    @Override
    public Node findPath(Node node, Object o) {
        return null;
    }
    
}
