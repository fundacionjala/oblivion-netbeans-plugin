/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle;

import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;

/**
 * Encapsulates the information to execute the "download" task of SFDC tool.
 * 
 * @author Adrian Grajeda
 */
public class DownloadProjectTask extends AbstractGradleTask {
    
    private static final String DOWNLOAD_COMMAND = "retrieve";
    
    private CredentialWrapper credential;

    /* Creates a new instance of this class that allows to execute the gradle "download" task from SFDC tool
     * 
     * @param folder the base project folder to download the salesforce code
     * @param errorMessage a friendly message if something went wrong
     */
    public DownloadProjectTask(String folder, String errorMessage) {
        this(folder, "", errorMessage);
    }

    /**
     * Creates a new instance of this class that allows to execute the gradle "download" task from SFDC tool
     * 
     * @param folder the base project folder to download the salesforce code
     * @param description more information about the task
     * @param errorMessage a friendly message if something went wrong
     */
    public DownloadProjectTask(String folder, String description, String errorMessage) {
        super(DOWNLOAD_COMMAND, folder, description);
        setResultHandler(new GradleResultHandler(errorMessage));
        credential = CredentialManager.getDefaultStorage().getCredentialForProject(getFolder());
        addCredentialParameter(credential);
    }

    @Override
    public boolean isValid() {
        return credential.isValid();
    }    
}
