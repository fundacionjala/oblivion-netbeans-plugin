/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fundacionjala.oblivion.apex.wizard.Bundle;
import org.fundacionjala.oblivion.ui.wizard.AbstractFileTypeWizardIterator;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle.Messages;

@TemplateRegistration(folder = "Salesforce", 
                      displayName = "#ApexClassWizardIterator_displayName", 
                      description = "ApexClass.html", 
                      content="ApexTemplate.cls", 
                      scriptEngine = "freemarker",
                      position = 100)
@Messages({"ApexClassWizardIterator_displayName=Apex Class", "ApexClassWizardIterator_panelTitle=Create New Apex Class"})
public final class ApexClassWizardIterator extends AbstractFileTypeWizardIterator {

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> buildPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardPanels = new ArrayList<>();
        wizardPanels.add(new ApexClassWizardDescriptor());
        return wizardPanels;
    }

    @Override
    protected Map<String, String> getTemplateParameters() {
        String apexClassName = (String)wizard.getProperty(ApexClassWizardPanel.APEX_CLASS_NAME);        
        Map<String, String> templateParameters = new HashMap<>();
        templateParameters.put(ApexClassWizardPanel.APEX_CLASS_NAME, apexClassName);
        return templateParameters;
    }

    @Override
    protected String getNewFileName() {
        String apexClassName = (String)wizard.getProperty(ApexClassWizardPanel.APEX_CLASS_NAME);
        return apexClassName + ".cls";
    }

    @Override
    protected Map<String, String> getMetadataParameters() {
        String apiVersion = (String) wizard.getProperty(ApexClassWizardPanel.APEX_CLASS_VERSION);
        Map<String, String> metadataParameters = new HashMap<>();
        metadataParameters.put("apiVersion", apiVersion);
        metadataParameters.put("status", "Active");
        return metadataParameters;
    }

    @Override
    protected String getMetadataFileName() {
        String apexClassName = (String)wizard.getProperty(ApexClassWizardPanel.APEX_CLASS_NAME);
        return apexClassName + ".cls-meta.xml";
    }

    @Override
    protected String getMetadataName() {
        return "ApexClass";
    }

    @Override
    protected String getDestinationFolder() {
        return "src/classes";
    }

    @Override
    protected Boolean getWithMetadataFile() {
        return true;
    }

    @Override
    protected Object getName() {
        return Bundle.ApexClassWizardIterator_panelTitle();
    }
    
}
