/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.wizard;

import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import org.fundacionjala.oblivion.apex.utils.APIVersion;
import org.fundacionjala.oblivion.apex.wizard.Bundle;
import org.fundacionjala.oblivion.ui.wizard.AbstractVisualPanel;
import org.openide.WizardDescriptor;

public final class ApexClassWizardPanel extends AbstractVisualPanel {
    static String APEX_CLASS_NAME = "name";
    static String APEX_CLASS_VERSION = "api_version";

    /**
     * Creates new form newClassVisualPanel1
     */
    public ApexClassWizardPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return Bundle.ApexClassWizardIterator_panelTitle();
    }
    
    public String getApexClassName() {
        return apexClassName.getText().trim();
    }
    
    public String getApiVersion() {
        return apiVersion.getSelectedItem().toString();
    }
    
    @Override
    public boolean isValid(WizardDescriptor wizardDescriptor) {
        boolean isValid = true;
        String message = "";
        if (isValid && !isValidInput(getApexClassName())) {
            isValid = false;
            message = "Name cannot be empty and cannot have white spaces.";
        } else if (isValid && !ifValidName(getApexClassName())) {
            isValid = false;
            message = "The name contains invalid characters.";
        }
        wizardDescriptor.setValid(isValid);
        wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
        return isValid;
    }
    
    private boolean isValidInput(String input) {
        return !input.isEmpty() && !input.contains(" ");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        apexClassName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        apiVersion = new javax.swing.JComboBox();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ApexClassWizardPanel.class, "ApexClassWizardPanel.jPanel1.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ApexClassWizardPanel.class, "ApexClassWizardPanel.jLabel1.text")); // NOI18N

        apexClassName.setText(org.openide.util.NbBundle.getMessage(ApexClassWizardPanel.class, "ApexClassWizardPanel.apexClassName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ApexClassWizardPanel.class, "ApexClassWizardPanel.jLabel2.text")); // NOI18N

        apiVersion.setModel(new javax.swing.DefaultComboBoxModel(APIVersion.getAllAsText()));
        apiVersion.setSelectedIndex(31);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(apexClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(apiVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(apexClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(apiVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 138, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apexClassName;
    private javax.swing.JComboBox apiVersion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    @Override
    protected void addDocumentListener(DocumentListener documentListener) {
        apexClassName.getDocument().addDocumentListener(documentListener);
    }

    @Override
    protected void addChangeListener(ChangeListener changeListener) {
        
    }
}
