/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.customizer;

import java.awt.Dialog;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.salesforce.project.customizer.Bundle;
import org.netbeans.spi.project.ui.CustomizerProvider;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Class which provides the Properties customizer window for a Salesforce project.
 * @author Marcelo Garnica
 */
@NbBundle.Messages({"LBL_CAT_Credential=Credentials"})
public class SalesforceProjectCustomizer implements CustomizerProvider {
    
    public static final String CREDENTIAL_CATEGORY = "Credentials";

    private final SalesforceProject project;
    
    public SalesforceProjectCustomizer(SalesforceProject project) {
        this.project = project;
    }
    
    @Override
    public void showCustomizer() {
        CategoryProvider categoryProvider = new CategoryProvider(project);
        Dialog dialog = ProjectCustomizer.createCustomizerDialog(getCategories(), 
                                                                 categoryProvider,
                                                                 CREDENTIAL_CATEGORY,
                                                                 categoryProvider.getOKListener(),
                                                                 HelpCtx.DEFAULT_HELP);
        categoryProvider.setDialog(dialog);
        dialog.setVisible(true);
    }
    
    private Category[] getCategories() {
        return new Category[]{ ProjectCustomizer.Category.create(CREDENTIAL_CATEGORY, Bundle.LBL_CAT_Credential(), null) };
    }
}
