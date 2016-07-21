/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * This class is the DefaultTableModel that it will using on the JTable that  contains gifs animated.
 * 
 * @author sergio_daza
 */
public class ModelTestReport extends DefaultTableModel {
    
    private static final Logger LOG = Logger.getLogger(PanelOfTestReport.class.getName());
    private final Object[][] row = {};
    private final Object[] col = {Bundle.TestReport_CaptionTest(), Bundle.TestReport_CaptionPassed(), Bundle.TestReport_CaptionFailed(), Bundle.TestReport_CaptionResult()};

    ModelTestReport() {
        for (Object c : col) {
            addColumn(c);
        }
        for (Object[] r : row) {
            addRow(r);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        Class<?> columnClass = super.getColumnClass(columnIndex);
        try {
            if (getRowCount() > 0 && columnIndex >= 0 && getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.INFO, e.toString());
        }
        return columnClass;
    }
}
