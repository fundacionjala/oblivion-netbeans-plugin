/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.openide.filesystems.FileObject;

/**
 * Salesforce project delete operation, gathers the information that should be deleted when the "Delete Project" action
 * is executed. 
 * @author Marcelo Garnica
 */
public class SalesforceDeleteProjectOperation implements DeleteOperationImplementation {
    private final SalesforceProject salesforceProject;
    private final String projectPath;

    public SalesforceDeleteProjectOperation(SalesforceProject salesforceProject) {
        this.salesforceProject = salesforceProject;
        this.projectPath = salesforceProject.getProjectDirectory().getPath();
    }

    @Override
    public void notifyDeleting() throws IOException {        
    }

    @Override
    public void notifyDeleted() throws IOException {
        CredentialManager.getDefaultStorage().deleteProjectCredential(projectPath);
    }

    @Override
    public List<FileObject> getMetadataFiles() {
        return new ArrayList<>();
    }

    @Override
    public List<FileObject> getDataFiles() {
        List<FileObject> files = new ArrayList<>();
        files.add(salesforceProject.getProjectDirectory());
        return files;
    }

}
