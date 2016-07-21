/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import org.fundacionjala.oblivion.salesforce.project.SalesforceProjectFactory;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProjectInformation;
import java.io.File;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link SalesforceProjectFactory} class.
 * @author Marcelo Garnica
 */
public class SalesforceProjectFactoryTest {
    
    @ClassRule
    public static final TemporaryFolder temporaryfolder = new TemporaryFolder();
    
    private static File projectFolder;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        projectFolder = temporaryfolder.newFolder("testProject");
        temporaryfolder.newFolder("testProject", "src");
        temporaryfolder.newFolder("testProject","src","classes");
        temporaryfolder.newFile("testProject/src/package.xml");
    }

    /**
     * Test of isProject2 method, of class ApexProjectFactory.
     * @throws java.io.IOException
     */
    @Test
    public void testIsProject() throws IOException {
        FileObject projectDirectory = FileUtil.toFileObject(projectFolder);
        SalesforceProjectFactory instance = new SalesforceProjectFactory();
        ProjectManager.Result result = instance.isProject2(projectDirectory);
        assertNotNull(result);
    }
    
    /**
     * Test of isProject2 method, of class ApexProjectFactory.
     * @throws java.io.IOException
     */
    @Test
    public void testIsNotProject() throws IOException {
        File projectFolder2 = temporaryfolder.newFolder("testProject2/");
        FileObject projectDirectory = FileUtil.toFileObject(projectFolder2);
        SalesforceProjectFactory instance = new SalesforceProjectFactory();
        ProjectManager.Result result = instance.isProject2(projectDirectory);
        assertNull(result);
    }

    /**
     * Test of loadProject method, of class ApexProjectFactory.
     * @throws java.io.IOException
     */
    @Test
    public void testLoadProject() throws IOException {
        SalesforceProjectFactory instance = new SalesforceProjectFactory();
        Project result = instance.loadProject(FileUtil.toFileObject(projectFolder), null);
        assertNotNull(result);
        assertEquals(projectFolder.getName(), result.getProjectDirectory().getName());
        SalesforceProjectInformation projectInfo = result.getLookup().lookup(SalesforceProjectInformation.class);
        assertEquals(projectFolder.getName(), projectInfo.getName());
    }   
}
