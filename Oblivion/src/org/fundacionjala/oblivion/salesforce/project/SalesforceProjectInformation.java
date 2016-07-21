/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.fundacionjala.oblivion.tab.console.ConsoleTopComponent;
import org.fundacionjala.oblivion.tab.testReport.TestResultTopComponent;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.util.ImageUtilities;

/**
 * Class that contains the information of a Salesforce project.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectInformation implements ProjectInformation {
    @StaticResource
    public static final String APEX_PROJECT_ICON = "org/fundacionjala/oblivion/salesforce/resources/SalesforceProject_Icon.png";
    
    private final SalesforceProject salesforceProject;
    
    public SalesforceProjectInformation(SalesforceProject salesforceProject) {
        this.salesforceProject = salesforceProject;
    }
    
    /**
     * Get a programmatic code name suitable for use in build scripts or other references.
     * 
     * @return the project directory name as a code name.
     */
    @Override
    public String getName() {
        return getProject().getProjectDirectory().getName();
    }

    /**
     * Get a readable display name for the project.
     * 
     * @return the project directory name as a display name.
     */
    @Override
    public String getDisplayName() {
        try {
            if(ConsoleTopComponent.getNameProject().isEmpty()) {
                ConsoleTopComponent.setProject(salesforceProject);
            }
        } catch (Exception e) {
        }
        return getName();
    }

    /**
     * Gets the icon for a Salesforce project.
     * 
     * @return the Salesforce project image icon.
     */
    @Override
    public Icon getIcon() {
        return new ImageIcon(ImageUtilities.loadImage(APEX_PROJECT_ICON));
    }

    /**
     * Gets the associated project.
     * 
     * @return the current Salesforce project.
     */
    @Override
    public Project getProject() {
        return salesforceProject;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
        TestResultTopComponent.addProject(salesforceProject.getProjectDirectory().getName(), salesforceProject.getProjectDirectory().getPath());
        try {
            if(!ConsoleTopComponent.getNameProject().equals(getName())) {
                ConsoleTopComponent.setProject(salesforceProject);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
        try {
            if(ConsoleTopComponent.getNameProject().equals(getName())) {
                ConsoleTopComponent.resetConsole();
            }
        } catch (Exception e) {
        }
    }
    
}
