/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.fundacionjala.oblivion.salesforce.project.nodes.TestClassesFolder;
import org.fundacionjala.oblivion.salesforce.project.nodes.ClassesFolder;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceProjectChildrenFactory;
import java.io.File;
import java.io.IOException;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link SalesforceProjectChildrenFactory} class.
 * 
 * @author Marcelo Garnica
 */
public class SalesforceProjectChildrenFactoryTest {
    
    @ClassRule
    public static final TemporaryFolder temporaryfolder = new TemporaryFolder();
    
    private static File projectFolder;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        projectFolder = temporaryfolder.newFolder("testProject/");
        temporaryfolder.newFolder("testProject","src");
        temporaryfolder.newFolder("testProject","src","classes");
        temporaryfolder.newFile("testProject/src/package.xml");
    }    

    /**
     * Test the quantity of nodes created.
     */
    @Test
    public void testQuantityNodes() {
        SalesforceProjectChildrenFactory instance = new SalesforceProjectChildrenFactory(new SalesforceProject(FileUtil.toFileObject(projectFolder)));
        Children result = Children.create(instance, false);
        assertEquals(3, result.getNodesCount());
    }

    /**
     * Tests that two nodes are created for the classes folder.
     */
    @Test
    public void testCreatedNodes() {
        SalesforceProjectChildrenFactory instance = new SalesforceProjectChildrenFactory(new SalesforceProject(FileUtil.toFileObject(projectFolder)));
        Children children = Children.create(instance, false);
        Node classesNode = children.findChild(ClassesFolder.CLASSES_FOLDER);
        Node testClassesNode = children.findChild(TestClassesFolder.TEST_FOLDER_NAME);
        assertNotNull(classesNode);
        assertNotNull(testClassesNode);
    }    
}
