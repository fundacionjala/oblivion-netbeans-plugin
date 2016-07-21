/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.filetype.wizard;

import org.fundacionjala.oblivion.ui.wizard.AbstractVisualPanel;
import org.fundacionjala.oblivion.ui.wizard.AbstractWizardDescriptor;
import org.openide.WizardDescriptor;

/**
 * Store setting for wizard
 *
 * @author sergio_daza
 */
public class SalesforceWizardDescriptor extends AbstractWizardDescriptor {

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wizardDescriptor = wiz;
        SalesforceVisualPanel cmp = (SalesforceVisualPanel) getComponent();
        wizardDescriptor.putProperty(SalesforceVisualPanel.FILE_NAME, cmp.getSalesforceName());
        wizardDescriptor.putProperty(SalesforceVisualPanel.FILE_VERSION, cmp.getApiVersion());
    }

    @Override
    protected AbstractVisualPanel createVisualComponent() {
        return new SalesforceVisualPanel();
    }

}
