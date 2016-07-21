/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle;

/**
 * Encapsulates all the information needed to execute a general gradle task.
 * 
 * @author Adrian Grajeda
 */
public class GradleTaskDescriptor extends AbstractGradleTask {

    public GradleTaskDescriptor(String task, String folder) {
        super(task, folder);
    }
    
    public GradleTaskDescriptor(String task, String folder, String description) {
        super(task, folder, description);
    }

    /**
     * Validates the task name.
     * 
     * @return if the task name is valid.
     */
    @Override
    public boolean isValid() {
        return !task.isEmpty();
    }
}
