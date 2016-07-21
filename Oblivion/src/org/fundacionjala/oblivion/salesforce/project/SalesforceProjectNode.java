/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.fundacionjala.oblivion.salesforce.project.actions.DeployProjectAction;
import org.fundacionjala.oblivion.salesforce.project.actions.DownloadProjectAction;
import org.fundacionjala.oblivion.salesforce.project.actions.UploadUpdatedFilesAction;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceProjectChildrenFactory;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * Class that is the root node for a Salesforce project logical view.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectNode extends FilterNode {
    
    private final SalesforceProject salesforceProject;    
    
    SalesforceProjectNode(Node nodeOfProjectFolder, SalesforceProject salesforceProject) throws DataObjectNotFoundException {
        super(nodeOfProjectFolder, Children.create(new SalesforceProjectChildrenFactory(salesforceProject), false), 
              new ProxyLookup(new Lookup[]{ Lookups.singleton(salesforceProject), nodeOfProjectFolder.getLookup() }));
        this.salesforceProject = salesforceProject;
    }
    
    /**
     * Get the set of actions of this node. This set is used to construct the context menu for the node.
     * 
     * @param context - whether to find actions for context meaning or for the node itself. 
     * @return A list of valid actions for a Salesforce project.
     */
    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>();
        actions.add(CommonProjectActions.newFileAction());
        actions.add(CommonProjectActions.deleteProjectAction());
        actions.add(CommonProjectActions.customizeProjectAction());
        actions.add(CommonProjectActions.closeProjectAction());
        actions.add(null);
        actions.addAll(Utilities.actionsForPath("Projects/Actions"));
        actions.add(null);
        actions.add(SystemAction.get(DownloadProjectAction.class));
        actions.add(SystemAction.get(UploadUpdatedFilesAction.class));
        actions.add(SystemAction.get(DeployProjectAction.class));
        Action[] actionsToReturn = actions.toArray(new Action[]{});
        return actionsToReturn;
    }
    
    /**
     * Gets the Icon to be displayed for the Node.
     * 
     * @param type - constant from BeanInfo, the icon size.
     * @return Icon to represent the node.
     */
    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(SalesforceProjectInformation.APEX_PROJECT_ICON);
    }
    
    /**
     * Gets the Icon to be displayed for the Node in the open state.
     * 
     * @param type - constant from BeanInfo, the icon size.
     * @return Icon to represent the node is opened.
     */
    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }
    
    /**
     * Gets the name to be displayed on the node.
     * 
     * @return the node's display name.
     */
    @Override
    public String getDisplayName() {
        return salesforceProject.getProjectDirectory().getName();
    }
}
