/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.filetype.iterator;

import org.fundacionjala.oblivion.salesforce.filetype.iterator.Bundle;
import org.fundacionjala.oblivion.salesforce.filetype.wizard.AbstractSalesforceFileTypeWizardIterator;
import org.fundacionjala.oblivion.salesforce.filetype.wizard.SalesforceVisualPanel;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle.Messages;

/**
 * Uploads a file to salesforce extension
 *
 * @author Sergio Daza
 */
@TemplateRegistration(folder = "Salesforce",
    displayName = "#TranslationWizardIterator_displayName",
    description = "../resource/Translation.html",
    content = "../template/template.translation",
    scriptEngine = "freemarker",
    position = 114)
@Messages({"TranslationWizardIterator_displayName=Translation", "TranslationWizardIterator_caption=Create Translation"})
public final class TranslationWizardIterator extends AbstractSalesforceFileTypeWizardIterator {

    private static final String METADATA_NAME = "Translation";
    private static final String METADATA_FILE_NAME = ".translation-meta.xml";
    private static final String DESTINATION_FOLDER = "src/translations";
    private static final String TITLE_PANEL = Bundle.TranslationWizardIterator_caption();
    private static final boolean SHOW_VERSION = false;

    @Override
    public String getName() {
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
