/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.salesforceErrors;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class repaints  the columns that contain icons.
 * 
 * @author sergio_daza
 */
public class RenderErrors  extends DefaultTableCellRenderer{
    
    private static final Logger LOG = Logger.getLogger(RenderErrors.class.getName());
    private final String ICON_PROJECT = "/org/fundacionjala/oblivion/salesforce/resources/SalesforceProject_Icon.png";
    private final String ICON_ERRORS = "/org/fundacionjala/oblivion/tab/salesforceErrors/error.png";
    private final String ICON_FAILED = "/org/fundacionjala/oblivion/tab/salesforceErrors/failed.gif";
    
    public void fillColor(JTable jTable, JLabel jLabel, boolean isSelected) {
        try {
            if (jTable.getModel().getRowCount() > 0) {
                if (isSelected) {
                    jLabel.setBackground(jTable.getSelectionBackground());
                    jLabel.setForeground(jTable.getSelectionForeground());
                } else {
                    jLabel.setBackground(jTable.getBackground());
                    jLabel.setForeground(jTable.getForeground());
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value instanceof JLabel && row < table.getModel().getRowCount()) {
            JLabel label = (JLabel) value;
            ImageIcon imageIcon;
            String path = "";
            try {
                path = (String) table.getValueAt(row, 1);
            } catch (ArrayIndexOutOfBoundsException | ClassCastException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
            if (path.trim().isEmpty() && column == 0) {
                imageIcon = new ImageIcon(getClass().getResource(ICON_PROJECT));
                label.setIcon(imageIcon);
            } else if (!label.getText().trim().isEmpty() && column == 2) {
                String icon = (label.getText().trim().equals(NotificationItem.FAILED))? ICON_FAILED: ICON_ERRORS;
                imageIcon = new ImageIcon(getClass().getResource(icon));
                label.setIcon(imageIcon);
            }
            label.setOpaque(true);
            fillColor(table, label, isSelected);
            return label;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
