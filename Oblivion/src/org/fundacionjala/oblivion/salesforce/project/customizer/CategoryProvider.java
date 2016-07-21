/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.customizer;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.salesforce.project.exceptions.InvalidCategoryException;
import org.fundacionjala.oblivion.salesforce.project.ui.ProjectCredentialsPanel;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;

/**
 * Class which provides the categories and their UI elements for the Properties window.
 * @author Marcelo Garnica
 */
public class CategoryProvider implements ProjectCustomizer.CategoryComponentProvider {
    
    private final SalesforceProject project;
    private Dialog dialog;
    private ProjectCredentialsPanel credentialPanel;

    public CategoryProvider(SalesforceProject project) {
        this.project = project;        
    }

    @Override
    public JComponent create(ProjectCustomizer.Category category) {
        if (category.getName().equals(SalesforceProjectCustomizer.CREDENTIAL_CATEGORY)) {
            credentialPanel = new ProjectCredentialsPanel(false);
            return credentialPanel;
        }
        throw new InvalidCategoryException();
    }

    void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    ActionListener getOKListener() {
        return new OKListener();
    }
    
    /**
     * Class that will handle the OK event on the Properties window.
     */
    private class OKListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dialog != null) {
                dialog.dispose();
                if (credentialPanel != null && credentialPanel.isValid(null)) {                  
                    CredentialWrapper credential = credentialPanel.getCredential();
                    CredentialManager.getDefaultStorage().save(project.getProjectDirectory().getPath(), credential);
                }
            }
        }
    }
}
