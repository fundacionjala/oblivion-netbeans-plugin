/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import org.fundacionjala.oblivion.salesforce.project.customizer.SalesforceProjectCustomizer;
import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.salesforce.project.actions.SalesforceActionProvider;
import org.fundacionjala.oblivion.salesforce.project.actions.SalesforceDeleteProjectOperation;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 * Class which defines the characteristics of a Salesforce Project.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProject implements Project {
    
    public static final String SOURCE_FOLDER = "/src";
    
    private final FileObject projectDir;
    
    private Lookup lookup;

    public SalesforceProject(FileObject projectDir) {
        this.projectDir = projectDir;
    }

    /**
     * Gets the associated directory where the project is located.
     * 
     * @return the project directory.
     */
    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    /**
     * Gets the abilities of a salesforce project.
     * 
     * @return a lookup instance that contains the project's abilities.
     */
    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            lookup = Lookups.fixed(new Object[]{
                this,
                new SalesforceProjectInformation(this),
                new SalesforceProjectLogicalView(this),
                new SalesforceActionProvider(this),
                new SalesforceProjectCustomizer(this),
                new SalesforceDeleteProjectOperation(this)
            });
        }
        return lookup;
    }
    
    /**
     * Retrieves the credential associated to the project.
     * 
     * @return the credential.
     */
    public CredentialWrapper getCredential() {
        return CredentialManager.getDefaultStorage().getCredentialForProject(projectDir.getPath());
    }
}
