/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import static java.awt.image.ImageObserver.ABORT;
import static java.awt.image.ImageObserver.ALLBITS;
import static java.awt.image.ImageObserver.FRAMEBITS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class manages the elements graphs on result column.
 *
 * @author sergio_daza
 */
public class Renderer extends DefaultTableCellRenderer {

    private static final Logger LOG = Logger.getLogger(DefaultTableCellRenderer.class.getName());
    private static final String ICON_PROGRES_BAR_RUNNING = "/org/fundacionjala/oblivion/tab/testReport/icons/loading.gif";
    private static final String ICON_PROGRES_BAR_FINISH = "/org/fundacionjala/oblivion/tab/testReport/icons/finish.png";
    private static final String ICON_PROGRES_BAR_FAILED = "/org/fundacionjala/oblivion/tab/testReport/icons/failed.gif";
    private static final String ICON_PROGRES_BAR_ERROR = "/org/fundacionjala/oblivion/tab/testReport/icons/error.png";
    private static final String LEFT_DELIMITER = "(";
    private static final String RIGHT_DELIMITER = ")";
    private static final String BLANK_SPACE = " ";

    public void fillColor(JTable jTable, JLabel jLabel, boolean isSelected) {
        if (jTable != null) {
            if (isSelected) {
                jLabel.setBackground(jTable.getSelectionBackground());
                jLabel.setForeground(jTable.getSelectionForeground());
            } else {
                jLabel.setBackground(jTable.getBackground());
                jLabel.setForeground(jTable.getForeground());
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        if (value instanceof JLabel && table != null) {
            JLabel label = (JLabel) value;
            ImageIcon imageIcon = null;
            if (label.getText().equals(TestResultItem.Status.running.name())) {
                imageIcon = new ImageIcon(getClass().getResource(ICON_PROGRES_BAR_RUNNING));
            } else if (label.getText().equals(TestResultItem.Status.successful.name())) {
                imageIcon = new ImageIcon(getClass().getResource(ICON_PROGRES_BAR_FINISH));
            } else if (label.getText().equals(TestResultItem.Status.failed.name())) {
                imageIcon = new ImageIcon(getClass().getResource(ICON_PROGRES_BAR_FAILED));
            } else if (label.getText().equals(TestResultItem.Status.errors.name())) {
                imageIcon = new ImageIcon(getClass().getResource(ICON_PROGRES_BAR_ERROR));
                String messageErrors = BLANK_SPACE;
                try {
                    messageErrors = (String) table.getModel().getValueAt(row, 2);
                } catch (ArrayIndexOutOfBoundsException e) {
                    LOG.log(Level.INFO, e.toString());
                }
                label.setText(LEFT_DELIMITER + messageErrors + RIGHT_DELIMITER + BLANK_SPACE + label.getText());
            }
            label.setOpaque(true);
            label.enableInputMethods(false);
            if (imageIcon != null) {
                label.setIcon(imageIcon);
                imageIcon.setImageObserver(new CellImageObserver(table, row, 3));
            }
            fillColor(table, label, isSelected);
            label.setForeground(new java.awt.Color(0, 35, 255));
            return label;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * This class manages the gifs animated.
     */
    class CellImageObserver implements ImageObserver {

        private final JTable table;
        private final int row;
        private final int col;

        CellImageObserver(JTable table, int row, int col) {
            this.table = table;
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
            if ((flags & (FRAMEBITS | ALLBITS)) != 0) {
                try {
                    Rectangle rect = table.getCellRect(row, col, false);
                    table.repaint(rect);
                } catch (ArrayIndexOutOfBoundsException e) {
                    LOG.log(Level.INFO, e.toString());
                }
            }
            return (flags & (ALLBITS | ABORT)) == 0;
        }
    }
}
