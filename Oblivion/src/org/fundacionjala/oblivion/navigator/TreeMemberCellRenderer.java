/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import java.awt.Component;
import javax.swing.*;
import javax.swing.tree.*;
import org.openide.util.ImageUtilities;

/**
 * Class which define the DefaultTreeCellRenderer which shows the extracted information.
 *
 * @author nelson_alcocer
 */
class TreeMemberCellRenderer extends DefaultTreeCellRenderer {

    DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();

    /**
     * Create a component to show as JTree cell on navigation panel
     * @param tree
     * @param value
     * @param selected
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     * @return Component with member name and data type to show.
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
        boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel memberName = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        Component returnValue = null;
        if ((value != null) && (value instanceof ParserMutableTreeNode)) {
            ParserMutableTreeNode memberNode = (ParserMutableTreeNode) value;
            if (memberNode.getDataType() != null || memberNode.getUserObject() != null) {
                String type = memberNode.getDataType();
                String member = memberNode.getUserObject().toString();
                memberName.setIcon(createImageIcon(memberNode.getIconPath()));
                if(type.length() > 0) {
                    memberName.setText(member + ": " + type);
                }
                else {
                    memberName.setText(member);
                }
                returnValue = memberName;
            }
        }
        if (returnValue == null) {
            returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }
        return returnValue;
    }

    private ImageIcon createImageIcon(String path) {
        ImageIcon imageIcon = null;
            if (!"".equals(path.trim())) {
                imageIcon = new ImageIcon(ImageUtilities.loadImage(path));
            }
        return imageIcon;
    }
}
