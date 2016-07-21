/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;

/**
 * Encapsulates all the information needed to execute a gradle task.
 * 
 * The information required are
 * <ul> 
 *  <li>Task Name</li>
 *  <li>working directory</li>
 *  <li>parameters</li>
 * </ul>
 * 
 * @author Marcelo Garnica
 */
public abstract class AbstractGradleTask {
    
    private static final String PARAMETER = "-P%s=%s";
    private static final String LOGIN_TYPE_PARAM = "login";
    private static final String SECURITY_TOKEN_PARAM = "token";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_NAME_PARAM = "username";
    
    static final String STACK_TRACE_PARAM_VALUE = "--stacktrace";
    
    protected final String task;
    protected final Map<String, String> parameters;
    protected String folder;
    protected String description;
    private GradleResultHandler resultHandler;
    

    public AbstractGradleTask(String task, String folder) {
        this(task, folder, "");
    }
    
    public String getParameter(String key) {
        return parameters.get(key);
    }
    
    public AbstractGradleTask(String task, String folder, String description) {
        this.task = task;
        this.folder = folder;
        this.description = description;
        parameters = new HashMap<>();
    }
    
    /**
     * Validates if the task can be executed.
     * 
     * @return whether the task can be executed or not.
     */
    public abstract boolean isValid();
    
    /**
     * Retrieves the task execution parameters.
     * 
     * @return the task execution parameters in a string array.
     */
    public String[] getExecutionParameters() {
        List<String> gradleParameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            gradleParameters.add(String.format(PARAMETER, entry.getKey(), entry.getValue())); 
        }
        gradleParameters.add(STACK_TRACE_PARAM_VALUE);
        return gradleParameters.toArray(new String[gradleParameters.size()]);
    }
    /**
     * Adds a new parameter for the task execution.
     * 
     * @param name The parameter's name.
     * @param value  The parameter's value.
     */
    public void addParameterEntry(String name, String value) {
        parameters.put(name, value);
    }
    
    /**
     * Adds the credentials parameters to the list of parameters.
     * @param credential - The credential wrapper which has the credentials parameters.
     */
    public void addCredentialParameter(CredentialWrapper credential) {
        addParameterEntry(USER_NAME_PARAM, credential.getUserName());
        addParameterEntry(PASSWORD_PARAM, credential.getPassword());
        addParameterEntry(SECURITY_TOKEN_PARAM, credential.getSecurityToken());
        addParameterEntry(LOGIN_TYPE_PARAM, credential.getLoginType());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.task);
        hash = 53 * hash + Objects.hashCode(this.parameters);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractGradleTask other = (AbstractGradleTask) obj;
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        return Objects.equals(this.parameters, other.parameters);
    }
    
    public String getFolder() {
        return folder;
    }    

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getName() {
        return task;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public GradleResultHandler getResultHandler() {
        return resultHandler;
    }
    
    public void setResultHandler(GradleResultHandler resultHanlder) {
        this.resultHandler = resultHanlder;
    }
}
