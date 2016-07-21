/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.trigger.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fundacionjala.oblivion.apex.trigger.wizard.Bundle;
import org.fundacionjala.oblivion.ui.wizard.AbstractFileTypeWizardIterator;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle.Messages;

@TemplateRegistration(folder = "Salesforce", 
                      displayName = "#ApexTrigger_displayName", 
                      description = "apexTrigger.html", 
                      iconBase = "org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png",
                      content = "/org/fundacionjala/oblivion/apex/trigger/ApexTrigger.trigger",
                      scriptEngine = "freemarker",
                      position = 200)
@Messages({"ApexTrigger_displayName=Apex Trigger", "ApexTrigger_panelTitle=Create New Apex Trigger"})
public final class ApexTriggerWizardIterator extends AbstractFileTypeWizardIterator {

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> buildPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardPanels = new ArrayList<>();
        wizardPanels.add(new ApexTriggerWizardDescriptor());
        return wizardPanels;
    }

    @Override
    protected Map<String, String> getTemplateParameters() {
        String apexTriggerName = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_TRIGGER_NAME);
        String sobjectName = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_SOBJECT_NAME);
        String operators = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_TRIGGER_OPERATIONS);        
        Map<String, String> templateParameters = new HashMap<>();
        templateParameters.put(ApexTriggerVisualPanel.APEX_TRIGGER_NAME, apexTriggerName);
        templateParameters.put(ApexTriggerVisualPanel.APEX_SOBJECT_NAME, sobjectName);
        templateParameters.put(ApexTriggerVisualPanel.APEX_TRIGGER_OPERATIONS, operators);
        return templateParameters;
    }

    @Override
    protected String getNewFileName() {
        String apexTriggerName = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_TRIGGER_NAME);
        return apexTriggerName + ".trigger";
    }

    @Override
    protected Map<String, String> getMetadataParameters() {
        String apiVersion = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_TRIGGER_VERSION);
        Map<String, String> metadataParameters = new HashMap<>();
        metadataParameters.put("apiVersion", apiVersion);
        metadataParameters.put("status", "Active");
        return metadataParameters;
    }

    @Override
    protected String getMetadataFileName() {
        String apexTriggerName = (String) wizard.getProperty(ApexTriggerVisualPanel.APEX_TRIGGER_NAME);
        return apexTriggerName + ".trigger-meta.xml";
    }

    @Override
    protected String getMetadataName() {
        return "ApexTrigger";
    }

    @Override
    protected String getDestinationFolder() {
        return "src/triggers";
    }

    @Override
    protected Boolean getWithMetadataFile() {
        return true;
    }

    @Override
    protected String getName() {
        return Bundle.ApexTrigger_panelTitle();
    }
    
}
