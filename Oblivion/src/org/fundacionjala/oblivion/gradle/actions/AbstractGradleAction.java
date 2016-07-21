/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.actions;

import java.awt.event.ActionEvent;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleTaskExecutor;
import org.openide.util.HelpCtx;
import org.openide.util.actions.SystemAction;

/**
 * Base class to create a gradle task action.
 * 
 * @author adrian_grajeda
 */
public abstract class AbstractGradleAction extends SystemAction {
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        GradleTaskExecutor executor = new GradleTaskExecutor(buildTask());
        executor.execute();
    }

    
    /**
     * The sub classes should implement this method configuring a {@link AbstractGradleTask}
     * that will be used to create a {@link GradleTaskExecutor} and execute a gradle task
     * @return an instance of the gradle task
     */
    protected abstract AbstractGradleTask buildTask();
    
    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
    
}
