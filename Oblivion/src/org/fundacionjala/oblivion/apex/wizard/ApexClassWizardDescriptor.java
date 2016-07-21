/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.wizard;

import org.fundacionjala.oblivion.ui.wizard.AbstractVisualPanel;
import org.fundacionjala.oblivion.ui.wizard.AbstractWizardDescriptor;
import org.openide.WizardDescriptor;

/**
 * Stores the information of the apex class wizard.
 * @author Marcelo Garnica
 */
public class ApexClassWizardDescriptor extends AbstractWizardDescriptor {

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        ApexClassWizardPanel cmp = (ApexClassWizardPanel)getComponent();
        wiz.putProperty(ApexClassWizardPanel.APEX_CLASS_NAME, cmp.getApexClassName());
        wiz.putProperty(ApexClassWizardPanel.APEX_CLASS_VERSION, cmp.getApiVersion());
        wizardDescriptor = wiz;
    }

    @Override
    protected AbstractVisualPanel createVisualComponent() {
        return new ApexClassWizardPanel();
    }
}
