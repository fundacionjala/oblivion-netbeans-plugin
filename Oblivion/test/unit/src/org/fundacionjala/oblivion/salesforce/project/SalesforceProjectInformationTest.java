/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project;

import org.fundacionjala.oblivion.salesforce.project.SalesforceProjectInformation;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link SalesforceProjectInformation} class.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectInformationTest {
    @ClassRule
    public static final TemporaryFolder temporaryfolder = new TemporaryFolder();
    
    private static SalesforceProject salesforceProject;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        File projectFolder = temporaryfolder.newFolder("testProject/");
        temporaryfolder.newFolder("testProject","src");
        temporaryfolder.newFolder("testProject","src","classes");
        temporaryfolder.newFile("testProject/src/package.xml");
        salesforceProject = new SalesforceProject(FileUtil.toFileObject(projectFolder));
    }    

    /**
     * Test of getName method, of class ApexProjectInformation.
     */
    @Test
    public void testGetName() {
        SalesforceProjectInformation instance = new SalesforceProjectInformation(salesforceProject);
        String expResult = "testProject";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDisplayName method, of class ApexProjectInformation.
     */
    @Test
    public void testGetDisplayName() {
        SalesforceProjectInformation instance = new SalesforceProjectInformation(salesforceProject);
        String expResult = "testProject";
        String result = instance.getDisplayName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIcon method, of class ApexProjectInformation.
     */
    @Test
    public void testGetIcon() {
        SalesforceProjectInformation instance = new SalesforceProjectInformation(salesforceProject);
        Icon result = instance.getIcon();
        assertNotNull(result);
    }

    /**
     * Test of getProject method, of class ApexProjectInformation.
     */
    @Test
    public void testGetProject() {
        SalesforceProjectInformation instance = new SalesforceProjectInformation(salesforceProject);
        Project result = instance.getProject();
        assertEquals(salesforceProject, result);
    }    
}
