/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import java.io.IOException;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 * Factory that creates a Salesforce project instance from a valid folder structure.
 * 
 * @author Marcelo Garnica
 */
@ServiceProvider(service = ProjectFactory.class)
public class SalesforceProjectFactory implements ProjectFactory2{

    public static final ImageIcon PROJECT_IMAGE_ICON = new ImageIcon(ImageUtilities.loadImage(SalesforceProjectInformation.APEX_PROJECT_ICON));

    private static final String PACKAGE_FILE = "/src/package.xml";

    /**
     * Verifies if a given directory has a valid Salesforce project structure.
     * 
     * @param projectDirectory - a directory which might refer to a Salesforce project.
     * @return Result instance if the directory has a valid structure, or null if the directory is not recognized.
     */
    @Override
    public ProjectManager.Result isProject2(FileObject projectDirectory) {
        ProjectManager.Result result = null;
        if (projectDirectory.getFileObject(SalesforceProject.SOURCE_FOLDER) != null && projectDirectory.getFileObject(PACKAGE_FILE) != null) {
            result = new ProjectManager.Result(PROJECT_IMAGE_ICON);
        }
        return result;
    }

    /**
     * Test whether a given directory probably refers to a Salesforce project.
     * 
     * @param projectDirecotry - a directory which might refer to a Salesforce project.
     * @return true if it is valid Salesforce project.
     */
    @Override
    public boolean isProject(FileObject projectDirecotry) {
        return isProject2(projectDirecotry) != null;
    }

    /**
     * Create a Salesforce project that resides on disk.
     * 
     * @param projectDirectory - a directory which might refer to a Salesforce project.
     * @param projectState - a callback permitting the project to indicate when it is modified.
     * @return a new Salesforce project, if the directory is not recognized, it returns null.
     * @throws IOException 
     */
    @Override
    public Project loadProject(FileObject projectDirectory, ProjectState projectState) throws IOException {
        return isProject(projectDirectory) ? new SalesforceProject(projectDirectory) : null;
    }

    @Override
    public void saveProject(Project prjct) throws IOException, ClassCastException {
    }
}
