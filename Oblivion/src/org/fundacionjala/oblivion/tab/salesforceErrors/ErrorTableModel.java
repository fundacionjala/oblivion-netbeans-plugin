/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.salesforceErrors;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import org.openide.util.NbBundle;

@NbBundle.Messages({"SalesforceErrors_TitleDescription=Description",
    "SalesforceErrors_TitlePath=Path",
    "SalesforceErrors_TitleLocation=Location"
})

/**
 * This class define the model for the JTable that it has the  errors list.
 * 
 * @author sergio_daza
 */
public class ErrorTableModel extends DefaultTableModel {
    
    private static final Logger LOG = Logger.getLogger(ErrorTableModel.class.getName());
    Object[][] row = {};
    Object[] col = {Bundle.SalesforceErrors_TitleDescription(), Bundle.SalesforceErrors_TitlePath(), Bundle.SalesforceErrors_TitleLocation()};

    ErrorTableModel() {
        try {
            for (Object c : col) {
                addColumn(c);
            }
            for (Object[] r : row) {
                addRow(r);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return columnIndex == 1 ? String.class : JLabel.class;
    }
}
