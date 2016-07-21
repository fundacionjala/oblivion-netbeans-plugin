/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.credentials;

/**
 * Defines the way that the project stores and recovers the credentials.
 * The credentials are related to a project folder
 * 
 * @author adrian_grajeda
 * @author Marcelo Garnica
 */
public interface CredentialStorage {
    
    /**
     * Save the credential identifier for the given project path
     * @param projectPath the project path
     * @param credentialId the identifier
     */
    void save(String projectPath, CredentialWrapper credential);
    
    /**
     * Get the credential identifier for the given project path
     * @param projectPath the project folder path
     * @return the credential or null if there is no credentials defined
     */
    CredentialWrapper getCredentialForProject(String projectPath);
    
    /**
     * Deletes the credential for the given project path
     * @param projectPath the project folder path
     */
    void deleteProjectCredential(String projectPath);
}
