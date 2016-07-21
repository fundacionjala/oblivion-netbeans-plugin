/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.filetype.iterator;

import org.fundacionjala.oblivion.salesforce.filetype.wizard.AbstractSalesforceFileTypeWizardIterator;
import org.fundacionjala.oblivion.salesforce.filetype.wizard.SalesforceVisualPanel;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;

/**
 * Iterator for .app salesforce extension
 *
 * @author Sergio Daza
 */
@TemplateRegistration(folder = "Salesforce",
    displayName = "#AppWizardIterator_displayName",
    description = "../resource/App.html",
    content = "../template/template.app",
    scriptEngine = "freemarker",
    position = 102)
@Messages({"AppWizardIterator_displayName=App", "AppWizardIterator_caption=Create New App"})

public final class AppWizardIterator extends AbstractSalesforceFileTypeWizardIterator {

    private static final String METADATA_NAME = "App";
    private static final String METADATA_FILE_NAME = ".app-meta.xml";
    private static final String DESTINATION_FOLDER = "src/applications";
    private static final String TITLE_PANEL = NbBundle.getMessage(AppWizardIterator.class, "AppWizardIterator_caption");
    private static final boolean SHOW_VERSION = false;

    @Override
    protected String getName() {
        return TITLE_PANEL;
    }

    @Override
    protected String getMetadataFileName() {
        String metadataFileName = (String) wizard.getProperty(SalesforceVisualPanel.FILE_NAME);
        return metadataFileName + METADATA_FILE_NAME;
    }

    @Override
    protected String getMetadataName() {
        return METADATA_NAME;
    }

    @Override
    protected String getDestinationFolder() {
        return DESTINATION_FOLDER;
    }

    @Override
    protected Boolean getWithMetadataFile() {
        return SHOW_VERSION;
    }

}
