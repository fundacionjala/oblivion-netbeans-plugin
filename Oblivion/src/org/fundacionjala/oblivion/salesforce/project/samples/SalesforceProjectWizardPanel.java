/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fundacionjala.oblivion.salesforce.project.samples;

import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.ui.wizard.AbstractVisualPanel;
import org.fundacionjala.oblivion.ui.wizard.AbstractWizardDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Panel just asking for basic info.
 */
public class SalesforceProjectWizardPanel extends AbstractWizardDescriptor {

    protected SalesforceProjectPanelVisual cmp;

    public SalesforceProjectWizardPanel() {
        cmp = new SalesforceProjectPanelVisual(this);
        cmp.setName(NbBundle.getMessage(SalesforceProjectWizardPanel.class, "LBL_CreateProjectStep"));
    }

    @Override
    public AbstractVisualPanel createVisualComponent() {
        return cmp;
    }

    @Override
    public HelpCtx getHelp() {
        return new HelpCtx(SalesforceProjectWizardPanel.class.toString());
    }


    @Override
    public void readSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        cmp.read(settings);
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        cmp.store(settings);
    }
    
    public CredentialWrapper getCredential() {
        return cmp.getCredential();
    }
    
    public boolean isDownloadProjectChecked() {
        return cmp.isDownloadProjectChecked();
    }
}
