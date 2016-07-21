/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.credentials;

/**
 * Class which wraps the elements of a Salesforce credential.
 * @author Marcelo Garnica
 */
public class CredentialWrapper {
    private String userName;
    private String password;
    private String securityToken;
    private String loginType;
    
    public CredentialWrapper() {
        this("", "", "", "");
    }
    
    public CredentialWrapper(String userName, String password, String securityToken, String loginType) {
        this.userName = userName;
        this.password = password;
        this.securityToken = securityToken;
        this.loginType = loginType;        
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getSecurityToken() {
        return securityToken;
    }
    
    public String getLoginType() {
        return loginType;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
    
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    
    public boolean isValid() {
        return userName != null && !userName.isEmpty() 
               && password != null && !password.isEmpty()
               && securityToken != null && !securityToken.isEmpty()
               && loginType != null && !loginType.isEmpty();
    }
}
