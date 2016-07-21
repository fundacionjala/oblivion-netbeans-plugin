/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.credentials;

/**
 * Manage the credentials
 * 
 * @author Adrian Grajeda
 */
public class CredentialManager {
   
    public static CredentialStorage getDefaultStorage() {
        return new FileSystemCredentialStorage();
    }
    
}
