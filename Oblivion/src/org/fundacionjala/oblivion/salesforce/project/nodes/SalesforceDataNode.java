/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import java.awt.Image;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * Represents a Salesforce Data Node.
 * @author Marcelo Garnica
 */
public class SalesforceDataNode extends DataNode {   
    public static final String HAS_ERRORS_ATTRIBUTE = "HasErrors";
    private static final Image CLASS_ERROR_ICON = ImageUtilities.loadImage("org/fundacionjala/oblivion/apex/resources/apexDevIconError.png");
    private static final Image CLASS_ICON = ImageUtilities.loadImage("org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png");
        
    private boolean hasLocalErrors;
    private boolean hasServerErrors;

    public SalesforceDataNode(DataObject obj, Children ch) {
        super(obj, ch);
        hasLocalErrors = false;
        hasServerErrors = false;
    }
    
    public SalesforceDataNode(DataObject obj, Children ch, Lookup lookup) {
        super(obj, ch, lookup);
        hasLocalErrors = false;
        hasServerErrors = false;
    }
    
    @Override
    public Object getValue(String attributeName) {
        if (attributeName.equalsIgnoreCase(HAS_ERRORS_ATTRIBUTE)) {
            return hasErrors();
        }
        return super.getValue(attributeName);
    }
    
    public boolean getHasLocalErrors() {
        return hasLocalErrors;
    }
    
    public boolean getHasServerErrors() {
        return hasServerErrors;
    }
    
    public void setHasLocalErrors(boolean hasLcoalErrors) {
        boolean oldHasLcoalErrors = this.hasLocalErrors;
        this.hasLocalErrors = hasLcoalErrors;
        if (oldHasLcoalErrors != this.hasLocalErrors) {
            fireIconChange();
            firePropertyChange(PROP_ICON, oldHasLcoalErrors, this.hasLocalErrors);
        }
    }
    
    public void setHasServerErrors(boolean hasServerErrors) {
        boolean oldHasServerErrors = this.hasServerErrors;
        this.hasServerErrors = hasServerErrors;
        if (oldHasServerErrors != this.hasServerErrors) {
            fireIconChange();
            firePropertyChange(PROP_ICON, oldHasServerErrors, this.hasServerErrors);
        }
    }
    
    /**
     * Checks if the object node has any errors.
     * @return whether there is any local or server error.
     */
    public boolean hasErrors() {
        return hasLocalErrors || hasServerErrors;
    }
    
    /**
     * Gets the Icon to be displayed for the Node. if there is any error, the error icon will be returned.
     * 
     * @param type - constant from BeanInfo, the icon size.
     * @return Icon to represent the node.
     */
    @Override
    public Image getIcon(int type) {
        if (hasErrors()) {
            return CLASS_ERROR_ICON;
        } else {
            return CLASS_ICON;
        }
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
}
