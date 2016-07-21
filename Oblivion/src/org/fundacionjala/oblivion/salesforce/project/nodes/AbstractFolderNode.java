/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.fundacionjala.oblivion.apex.actions.RefreshFolderAction;
import org.fundacionjala.oblivion.apex.actions.UploadFolderAction;
import org.fundacionjala.oblivion.apex.utils.ImageUtils;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

import static org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode.HAS_ERRORS_ATTRIBUTE;
import org.openide.util.actions.SystemAction;

/**
 * Represents a folder node.
 * 
 * @author Marcelo Garnica
 */
public abstract class AbstractFolderNode extends FilterNode implements PropertyChangeListener {
    
    public static final String CLASS_FILE_EXTENSION = "cls";
    public static final String ERROR_ICON_PATH = "org/fundacionjala/oblivion/salesforce/resources/errorIcon.png";
    
    private final String displayName;
    
    private final String iconFileDirection;
    
    private final FileObject folderFileObject;
    
    protected boolean hasErrors;
    private Image normalImage;
    private Image errorImage;

    public AbstractFolderNode(FileObject folderFileObject, AbstractFolderChildFactory childFactory,String displayName, String iconFileDirection) throws DataObjectNotFoundException {
        super(DataObject.find(folderFileObject).getNodeDelegate(), Children.create(childFactory, false));
        this.folderFileObject = folderFileObject;
        this.displayName = displayName;
        this.iconFileDirection = iconFileDirection;
        this.hasErrors = false;
    }
    
    public AbstractFolderNode(FileObject folderFileObject, String displayName, String iconFileDirection) throws DataObjectNotFoundException {
        super(DataObject.find(folderFileObject).getNodeDelegate());
        this.folderFileObject = folderFileObject;
        this.displayName = displayName;
        this.iconFileDirection = iconFileDirection;
        this.hasErrors = false;
    }
    
    public AbstractFolderNode(FileObject folderFileObject, String displayName) throws DataObjectNotFoundException {
        this(folderFileObject, displayName, null);
    }
    
    public FileObject getFileObject() {
        return this.folderFileObject;
    }
    
    /**
     * Get the set of actions of this node. This set is used to construct the context menu for the node.
     * 
     * @param context - whether to find actions for context meaning or for the node itself. 
     * @return A list of valid actions for a Salesforce project.
     */
    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        actions.add(1, null);
        actions.add(2, SystemAction.get(UploadFolderAction.class));
        actions.add(3, SystemAction.get(RefreshFolderAction.class));
        actions.add(4, null);
        return actions.toArray(new Action[]{});  
    }

    /**
     * Gets the Icon to be displayed for the Node.
     * 
     * @param type - constant from BeanInfo, the icon size.
     * @return Icon to represent the node.
     */
    @Override
    public Image getIcon(int type) {
        if (normalImage == null) {
            if (iconFileDirection != null && !iconFileDirection.isEmpty()) {
                normalImage = ImageUtilities.loadImage(iconFileDirection);
            } else {
                normalImage = super.getIcon(type);
            }
        }
        Image returnImage = normalImage;
        if (hasErrors) {
            if (errorImage == null) {
                Image errorIcon = ImageUtilities.loadImage(ERROR_ICON_PATH);
                errorImage = ImageUtils.mergeImages(returnImage, errorIcon);
            }            
            returnImage = errorImage;
        }
        return returnImage;
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
        return displayName;
    }
    
    /**
     * Gets the name of the node.
     * 
     * @return the node's display name.
     */
    @Override
    public String getName() {
        return displayName;
    }
    
    /**
     * Retrieves a named attribute.
     * @param attributeName - the name of the attribute.
     * @return The value of the attribute.
     */
    @Override
    public Object getValue(String attributeName) {
        if (attributeName.equalsIgnoreCase(HAS_ERRORS_ATTRIBUTE)) {
            return hasErrors;
        }
        return super.getValue(attributeName);
    }
    
    /**
     * Method called when a property has changed.
     * @param evt - Represents the event source and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Node.PROP_ICON)) {
            Node[] nodes = getChildren().getNodes();
            boolean oldHasErrors = hasErrors;
            hasErrors = false;
            for (Node node : nodes) {
                boolean nodeHasErrors = (boolean)node.getValue(SalesforceDataNode.HAS_ERRORS_ATTRIBUTE);
                if (nodeHasErrors) {
                    hasErrors = true;
                    break;
                }                    
            }
            if (oldHasErrors != hasErrors) {
                fireIconChange();
                firePropertyChange(PROP_ICON, oldHasErrors, hasErrors);
            }
        }
    }
}
