/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.util.Lookup;

/**
 * Provides validation and executes default actions over a Salesforce project.
 * @author Marcelo Garnica
 */
public class SalesforceActionProvider implements ActionProvider {
    private final SalesforceProject salesforceProject;

    public SalesforceActionProvider(SalesforceProject salesforceProject) {
        this.salesforceProject = salesforceProject;
    }
    
    @Override
    public String[] getSupportedActions() {
        return new String[]{ COMMAND_DELETE };
    }

    @Override
    public void invokeAction(String command, Lookup context) throws IllegalArgumentException {
        if (command.equalsIgnoreCase(COMMAND_DELETE)) {
            DefaultProjectOperations.performDefaultDeleteOperation(salesforceProject);
        }
    }

    @Override
    public boolean isActionEnabled(String command, Lookup context) throws IllegalArgumentException {
        boolean isActionEnabled = false;
        if (command.equalsIgnoreCase(COMMAND_DELETE)) {
            isActionEnabled = true;
        }
        return isActionEnabled;
    }

}
