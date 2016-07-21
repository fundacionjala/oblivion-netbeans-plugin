/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.filetype.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.ui.wizard.AbstractFileTypeWizardIterator;
import org.openide.WizardDescriptor;

/**
 * intermediate class between AbstractFileTypeWizardIterator and class iterator, contains common code in class iterators
 *
 * @author sergio_daza
 */
public abstract class AbstractSalesforceFileTypeWizardIterator extends AbstractFileTypeWizardIterator {

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> buildPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardPanels = new ArrayList<>();
        wizardPanels.add((WizardDescriptor.Panel<WizardDescriptor>) new SalesforceWizardDescriptor());
        return wizardPanels;
    }

    @Override
    protected Map<String, String> getTemplateParameters() {
        String apexClassName = (String) wizard.getProperty(SalesforceVisualPanel.FILE_NAME);
        Map<String, String> templateParameters = new HashMap<>();
        templateParameters.put(SalesforceVisualPanel.FILE_NAME, apexClassName);
        return templateParameters;
    }

    @Override
    protected String getNewFileName() {
        String apexClassName = (String) wizard.getProperty(SalesforceVisualPanel.FILE_NAME);
        return apexClassName;
    }

    @Override
    protected Map<String, String> getMetadataParameters() {
        String apiVersion = (String) wizard.getProperty(SalesforceVisualPanel.FILE_VERSION);
        Map<String, String> metadataParameters = new HashMap<>();
        metadataParameters.put("apiVersion", apiVersion);
        metadataParameters.put("status", "Active");
        return metadataParameters;
    }

    @Override
    public void setupWizard() {
        this.wizard.putProperty(SalesforceVisualPanel.TITLE_PANEL_PARAM, getName());
        this.wizard.putProperty(SalesforceVisualPanel.HAS_METADATA_PARAM, getWithMetadataFile());
    }

     /**
     * This method checks if a name is already used.
     * 
     * @return a boolean, true if the name is already used
     */
    @Override
    public boolean isUsedName(){
        boolean result = false;
            File file = new File(ProjectUtils.getCurrentProjectPath() + "/"+getDestinationFolder());
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File item : files) {
                    if(item.getName().trim().toLowerCase().equals(getNewFileName()+"."+getMetadataName().toLowerCase())) {
                        result = true;
                        break;
                    }
                }
            }
        return result;
    }
    
}
