/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.trigger.wizard;

import org.fundacionjala.oblivion.ui.wizard.AbstractVisualPanel;
import org.fundacionjala.oblivion.ui.wizard.AbstractWizardDescriptor;
import org.openide.WizardDescriptor;

public class ApexTriggerWizardDescriptor extends AbstractWizardDescriptor {

    @Override
    protected AbstractVisualPanel createVisualComponent() {
        return new ApexTriggerVisualPanel();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        ApexTriggerVisualPanel cmp = (ApexTriggerVisualPanel)getComponent();
        wiz.putProperty(ApexTriggerVisualPanel.APEX_TRIGGER_NAME, cmp.getApexTriggerName());
        wiz.putProperty(ApexTriggerVisualPanel.APEX_SOBJECT_NAME, cmp.getSobjectName());
        wiz.putProperty(ApexTriggerVisualPanel.APEX_TRIGGER_OPERATIONS, cmp.getOperations());
        wiz.putProperty(ApexTriggerVisualPanel.APEX_TRIGGER_VERSION, cmp.getApiVersion());
        wizardDescriptor = wiz;
    }
}
