/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.search.provider;

import org.fundacionjala.oblivion.search.provider.SalesforceSearchProvider;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link SalesforceSearchProvider} class.
 * 
 * @author nelson_alcocer
 */
public class SalesforceSearchProviderTest {

    @ClassRule
    public static final TemporaryFolder temporaryfolder = new TemporaryFolder();

    private static File projectFolder;

    @BeforeClass
    public static void setUpClass() throws IOException {
        projectFolder = temporaryfolder.newFolder("testProject/");
        temporaryfolder.newFolder("testProject","src");
        temporaryfolder.newFolder("testProject","src","classes");
        temporaryfolder.newFile("testProject/src/package.xml");
        temporaryfolder.newFile("testProject/src/classes/Index.cls");
        temporaryfolder.newFile("testProject/src/classes/test1.cls");
        temporaryfolder.newFile("testProject/src/classes/test2.cls");
        temporaryfolder.newFile("testProject/src/classes/test3.cls");
        temporaryfolder.newFile("testProject/src/classes/test4.cls");
        temporaryfolder.newFile("testProject/src/classes/test5.cls");
    }

    /**
     * Test searchFile method from SearchProvider class.
     */
    @Test
    public void testSearchXmlFile() throws Exception {
        Project project = new SalesforceProject(FileUtil.toFileObject(projectFolder));
        SalesforceSearchProvider instance = new SalesforceSearchProvider();
        List<FileObject> result = instance.searchFile(project.getProjectDirectory(), "package");
        assertNotNull(result);
        assertEquals("package.xml", result.get(0).getNameExt());
    }
    
    /**
     * Test searchFile method from SearchProvider class.
     */
    @Test
    public void testSearchClsFile() throws Exception {
        Project project = new SalesforceProject(FileUtil.toFileObject(projectFolder));
        SalesforceSearchProvider instance = new SalesforceSearchProvider();
        List<FileObject> result = instance.searchFile(project.getProjectDirectory(), "Index");
        assertNotNull(result);
        assertEquals("Index.cls", result.get(0).getNameExt());
    }
    
    /**
     * Test searchFile method from SearchProvider class.
     */
    @Test
    public void testSearchFileWithManyFilesFound() throws Exception {
        Project project = new SalesforceProject(FileUtil.toFileObject(projectFolder));
        SalesforceSearchProvider instance = new SalesforceSearchProvider();
        List<FileObject> result = instance.searchFile(project.getProjectDirectory(), "test");
        assertNotNull(result);
        assertEquals(5, result.size());
    }
    
    /**
     * Test searchFile method from SearchProvider class.
     */
    @Test
    public void testSearchFileWithoutResults() throws Exception {
        Project project = new SalesforceProject(FileUtil.toFileObject(projectFolder));
        SalesforceSearchProvider instance = new SalesforceSearchProvider();
        List<FileObject> result = instance.searchFile(project.getProjectDirectory(), "noResults");
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
