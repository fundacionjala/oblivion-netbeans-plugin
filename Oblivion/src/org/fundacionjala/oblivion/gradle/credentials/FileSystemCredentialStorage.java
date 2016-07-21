/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.credentials;

import org.netbeans.api.keyring.Keyring;

/**
 * Store the credentials in the file system.
 * 
 * @author adrian_grajeda
 * @author Marcelo Garnica
 */
public class FileSystemCredentialStorage implements CredentialStorage {
    
    private static final String KEYRING_TEMPLATE_KEY = "%s%s";
    private static final String USER_NAME_KEY = "userName";
    private static final String PASSWORD_KEY = "password";
    private static final String SECURITY_TOKEN_KEY = "securityToken";
    private static final String LOGIN_TYPE_KEY = "loginType";
    
    @Override
    public void save(String projectPath, CredentialWrapper credential) {
        String userNameKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, USER_NAME_KEY);
        String passwordKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, PASSWORD_KEY);
        String securityTokenKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, SECURITY_TOKEN_KEY);
        String loginTypeKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, LOGIN_TYPE_KEY);
        saveToKeyring(userNameKey, credential.getUserName());
        saveToKeyring(passwordKey, credential.getPassword());
        saveToKeyring(securityTokenKey, credential.getSecurityToken());
        saveToKeyring(loginTypeKey, credential.getLoginType());
    }

    @Override
    public CredentialWrapper getCredentialForProject(String projectPath) {
        String userNameKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, USER_NAME_KEY);
        String passwordKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, PASSWORD_KEY);
        String securityTokenKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, SECURITY_TOKEN_KEY);
        String loginTypeKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, LOGIN_TYPE_KEY);
        return new CredentialWrapper(getKeyringValue(userNameKey), 
                                     getKeyringValue(passwordKey), 
                                     getKeyringValue(securityTokenKey), 
                                     getKeyringValue(loginTypeKey));
        
    }
    
    @Override
    public void deleteProjectCredential(String projectPath) {
        String userNameKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, USER_NAME_KEY);
        String passwordKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, PASSWORD_KEY);
        String securityTokenKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, SECURITY_TOKEN_KEY);
        String loginTypeKey = String.format(KEYRING_TEMPLATE_KEY, projectPath, LOGIN_TYPE_KEY);
        Keyring.delete(userNameKey);
        Keyring.delete(passwordKey);
        Keyring.delete(securityTokenKey);
        Keyring.delete(loginTypeKey);
    }
    
    private void saveToKeyring(String key, String value) {
        Keyring.save(key, value.toCharArray(), null);
    }
    
    private String getKeyringValue(String key) {
        String toReturn = "";
        char[] keyValue = Keyring.read(key);
        if (keyValue != null) {
            toReturn = new String(keyValue);
        }
        return toReturn;
    }    
}
