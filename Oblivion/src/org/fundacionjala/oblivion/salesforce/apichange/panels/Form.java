/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.panels;

import org.fundacionjala.oblivion.salesforce.apichange.tools.MetadataManager;
import org.fundacionjala.oblivion.salesforce.apichange.tools.TagOfMetadata;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.fundacionjala.oblivion.apex.utils.APIVersion;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

@NbBundle.Messages({"ApiChangeForm_changedSuccessfully=Api changed successfully ",
    "ApiChangeForm_messageModifiedFile_title=Question",
    "ApiChangeForm_messageModifiedFile_1=The file ",
    "ApiChangeForm_messageModifiedFile_2=on the disk is more recent than the current buffer",
    "ApiChangeForm_messageModifiedFile_3=Do you want to reload it?",
    "ApiChangeForm_messageRemoveTag=Remove tags selected?",
    "ApiChangeForm_messageRemoveTag_title=Remove tags",
    "ApiChangeForm_labelSelectAll=Select all",
    "ApiChangeForm_labelRemoveAllSelected=Remove all selected",
    "ApiChangeForm_labelAdd=Add",
    "ApiChangeForm_apiVersion=API Version",
    "ApiChangeForm_fileFormatInvalid=Metadata File format invalid. If you cant see code, click  go to file botton."
})

/**
 * Class that implement a form for metadata file manager
 *
 * @author sergio_daza
 */
public final class Form extends javax.swing.JPanel {

    private final MetadataManager metadataManager;
    private final File fileMetaData;
    private Toolbar toolbar;
    private String newVersion;
    private Object[][] tagsOfTable;
    private boolean showPanel;
    private long lastModifiedMetadata;

    /**
     * Creates new form APIShow
     */
    public Form(String pathMetaData, MetadataManager metadataManager) {
        this.metadataManager = metadataManager;
        newVersion = "0.0";
        lastModifiedMetadata = 0;
        fileMetaData = new File(pathMetaData);
        showPanel = true;
        initComponents();
        displayForm();
    }

    /**
     * Shows or hides the form
     */
    public void displayForm() {
        if (fileMetaData.isFile() && showPanel) {
            lastModifiedMetadata = fileMetaData.lastModified();
            jPanelToShowInputForm.setVisible(true);
            jLabelMessageForInvalidFile.setVisible(false);
        } else {
            jPanelToShowInputForm.setVisible(false);
            jLabelMessageForInvalidFile.setVisible(true);
        }
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void refreshForm() {
        String apiCurrent = metadataManager.ReadAPICurrent();
        apiVersion.setSelectedItem(apiCurrent);
        reloadTableValues();
        refreshTable();
        toolbar.setSaveEnable(false);
        displayForm();
    }

    public void reloadTableValues() {
        List<TagOfMetadata> listTag = metadataManager.getMetadataTags();
        tagsOfTable = new Object[listTag.size()][3];
        for (int i = 0; i < listTag.size(); i++) {
            tagsOfTable[i][0] = false;
            tagsOfTable[i][1] = listTag.get(i).getName();
            tagsOfTable[i][2] = listTag.get(i).getValue();
        }
        showPanel = metadataManager.isValidForDisplay();
    }

    private void refreshTable() {
        JCheckBox checkBox = new javax.swing.JCheckBox();
        String[] tableTitle = {"", "Tag", "Value"};
        DefaultTableModel tableModel = new DefaultTableModel(tagsOfTable, tableTitle) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        jTableTags.setModel(tableModel);
        jTableTags.getColumn("").setPreferredWidth(5);
        jTableTags.getColumn("Tag").setPreferredWidth(150);
        jTableTags.getColumn("Value").setPreferredWidth(150);
        checkBox.setAlignmentX(0);
        jTableTags.getColumn("").setCellEditor(new DefaultCellEditor(checkBox));
    }

    private Object[][] getTableValues() {
        Object[][] tableValues = new Object[jTableTags.getRowCount()][jTableTags.getColumnCount()];
        for (int i = 0; i < jTableTags.getRowCount(); i++) {
            for (int j = 0; j < jTableTags.getColumnCount(); j++) {
                if (j != 0) {
                    tableValues[i][j] = (Object) jTableTags.getValueAt(i, j);
                } else {
                    Boolean valueCheckbox = (Boolean) jTableTags.getValueAt(i, j);
                    tableValues[i][j] = (Object) valueCheckbox;
                }
            }
        }
        return tableValues;
    }

    public void saveMetadata() {
        try {
            Object[][] tableValues = getTableValues();
            List<TagOfMetadata> tagsXML = new ArrayList<>();
            for (Object[] tableValue : tableValues) {
                tagsXML.add(new TagOfMetadata((String) tableValue[1], (String) tableValue[2]));
            }
            metadataManager.saveTags(tagsXML, newVersion);
            reloadTableValues();
            refreshTable();
            toolbar.setSaveEnable(false);
            JOptionPane.showMessageDialog(null, Bundle.ApiChangeForm_changedSuccessfully());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void modifiedFile() {
        if (!fileMetaData.isFile()) {
            displayForm();
            toolbar.displayToolbar();
            showPanel = false;
        } else {
            if (lastModifiedMetadata != fileMetaData.lastModified() && fileMetaData.isFile()) {
                int n = JOptionPane.showConfirmDialog(
                    null,
                    Bundle.ApiChangeForm_messageModifiedFile_1()
                    + fileMetaData.getName()
                    + Bundle.ApiChangeForm_messageModifiedFile_2()
                    + "\n \n " + Bundle.ApiChangeForm_messageModifiedFile_3(),
                    Bundle.ApiChangeForm_messageModifiedFile_title(),
                    JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    refreshForm();
                }
                lastModifiedMetadata = fileMetaData.lastModified();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas1 = new java.awt.Canvas();
        jPanelToShowInputForm = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        apiVersion = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabelRemove = new javax.swing.JLabel();
        jLabelAdd = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTags = new javax.swing.JTable();
        jLabelMessageForInvalidFile = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, Bundle.ApiChangeForm_apiVersion());

        apiVersion.setModel(new javax.swing.DefaultComboBoxModel(APIVersion.getAllAsText()));
        apiVersion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        apiVersion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                apiChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Form.class, "Form.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(Form.class, "Form.jLabel1.toolTipText")); // NOI18N

        jLabelRemove.setForeground(new java.awt.Color(7, 135, 228));
        org.openide.awt.Mnemonics.setLocalizedText(jLabelRemove, Bundle.ApiChangeForm_labelRemoveAllSelected());
        jLabelRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClickDelete(evt);
            }
        });

        jLabelAdd.setForeground(new java.awt.Color(7, 135, 228));
        org.openide.awt.Mnemonics.setLocalizedText(jLabelAdd, Bundle.ApiChangeForm_labelAdd());
        jLabelAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelAddMouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(7, 135, 228));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, Bundle.ApiChangeForm_labelSelectAll());
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(7, 135, 228));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(Form.class, "Form.jLabel5.text")); // NOI18N

        jTableTags.setBorder(null);
        jTableTags.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "tag1", "45"},
                {null, "tag2", "ewrt"},
                {null, "tag3", "3546"}
            },
            new String [] {
                "Title 1", "Tag", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableTags.setAlignmentX(0.0F);
        jTableTags.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTableTagsComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(jTableTags);
        if (jTableTags.getColumnModel().getColumnCount() > 0) {
            jTableTags.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(Form.class, "Form.jTableTags.columnModel.title0")); // NOI18N
            jTableTags.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(Form.class, "Form.jTableTags.columnModel.title1")); // NOI18N
        }

        javax.swing.GroupLayout jPanelToShowInputFormLayout = new javax.swing.GroupLayout(jPanelToShowInputForm);
        jPanelToShowInputForm.setLayout(jPanelToShowInputFormLayout);
        jPanelToShowInputFormLayout.setHorizontalGroup(
            jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelToShowInputFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelToShowInputFormLayout.createSequentialGroup()
                        .addGroup(jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelToShowInputFormLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apiVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelToShowInputFormLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jLabelAdd)
                        .addGap(30, 30, 30))))
        );
        jPanelToShowInputFormLayout.setVerticalGroup(
            jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelToShowInputFormLayout.createSequentialGroup()
                .addGroup(jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apiVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelToShowInputFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabelRemove)
                    .addComponent(jLabelAdd))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelMessageForInvalidFile.setForeground(new java.awt.Color(7, 135, 228));
        org.openide.awt.Mnemonics.setLocalizedText(jLabelMessageForInvalidFile, Bundle.ApiChangeForm_fileFormatInvalid());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelToShowInputForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabelMessageForInvalidFile)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMessageForInvalidFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelToShowInputForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(236, 236, 236)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void apiChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_apiChanged
        newVersion = (String) apiVersion.getSelectedItem();
        toolbar.setSaveEnable(true);
    }//GEN-LAST:event_apiChanged

    private void onClickDelete(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onClickDelete
        int select = JOptionPane.showConfirmDialog(null, Bundle.ApiChangeForm_messageRemoveTag(), Bundle.ApiChangeForm_messageRemoveTag_title(), JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            List<TagOfMetadata> currentTag = new ArrayList<>();
            Object[][] tagValues = getTableValues();
            for (Object[] tagValue : tagValues) {
                if ((Boolean) tagValue[0] != true) {
                    boolean add = currentTag.add(new TagOfMetadata((String) tagValue[1], (String) tagValue[2]));
                }
            }
            Object[][] newValuesTable = new Object[currentTag.size()][3];
            for (int i = 0; i < currentTag.size(); i++) {
                newValuesTable[i][0] = false;
                newValuesTable[i][1] = currentTag.get(i).getName();
                newValuesTable[i][2] = currentTag.get(i).getName();
            }
            tagsOfTable = newValuesTable;
            refreshTable();
            toolbar.setSaveEnable(true);
        }
    }//GEN-LAST:event_onClickDelete

    private void jLabelAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelAddMouseClicked
        DefaultTableModel modelo = (DefaultTableModel) jTableTags.getModel();
        Object[] newTag = new Object[3];
        newTag[0] = false;
        newTag[1] = "";
        newTag[2] = "";
        modelo.addRow(newTag);
        tagsOfTable = getTableValues();
        toolbar.setSaveEnable(true);
    }//GEN-LAST:event_jLabelAddMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        selectAll();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jTableTagsComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTableTagsComponentAdded
        toolbar.setSaveEnable(true);
    }//GEN-LAST:event_jTableTagsComponentAdded

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        modifiedFile();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox apiVersion;
    private java.awt.Canvas canvas1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelAdd;
    private javax.swing.JLabel jLabelMessageForInvalidFile;
    private javax.swing.JLabel jLabelRemove;
    private javax.swing.JPanel jPanelToShowInputForm;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableTags;
    // End of variables declaration//GEN-END:variables

    private void selectAll() {
        for (int i = 0; i < tagsOfTable.length; i++) {
            jTableTags.setValueAt(true, i, 0);
        }
    }

}
