/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractClassChildFactory;
import org.fundacionjala.oblivion.salesforce.project.nodes.TestClassesFolder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link TestClassesFolder} class.
 * 
 * @author Marcelo Garnica
 */
public class TestClassesFolderTest {
    
    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();       

    @Test
    public void testOnlyTestClassesNodesAreCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes"));
        temporaryFolder.newFile("classes/FirstClassTest.cls");
        temporaryFolder.newFile("classes/SecondClassTest.cls");
        temporaryFolder.newFile("classes/MainClass.cls");
        TestClassesFolder testClassesFolder = new TestClassesFolder(classesFolderFileObject);
        Children classChildren = testClassesFolder.getChildren();
        assertEquals(2, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("FirstClassTest"));
        assertNotNull(classChildren.findChild("SecondClassTest"));
        assertNull(classChildren.findChild("MainClass"));
    }
    
    @Test
    public void testOtherFileNodesAreNotCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes2"));
        temporaryFolder.newFile("classes2/NewClassTest.cls");
        temporaryFolder.newFile("classes2/xmlFile.xml");
        temporaryFolder.newFile("classes2/NewClass2.cls2");
        TestClassesFolder testClassesFolder = new TestClassesFolder(classesFolderFileObject);
        Children classChildren = testClassesFolder.getChildren();
        assertEquals(1, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("NewClassTest"));
        assertNull(classChildren.findChild("xmlFile"));
        assertNull(classChildren.findChild("NewClass2"));
    }
    
    @Test
    public void testPackagFolderNodeAreCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes3"));
        File newClass = temporaryFolder.newFile("classes3/NewClassTest.cls");
        FileWriter fileWriter = new FileWriter(newClass);
        fileWriter.write(AbstractClassChildFactory.PACKAGE_PATTERN + " my.new.package.works;");
        fileWriter.close();
        TestClassesFolder classesFolder = new TestClassesFolder(classesFolderFileObject);
        Children classChildren = classesFolder.getChildren();        
        assertEquals(1, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("my.new.package.works"));
        assertNull(classChildren.findChild("NewClassTest"));
        Children packageChildren = classChildren.findChild("my.new.package.works").getChildren();
        assertNotNull(packageChildren.findChild("NewClassTest"));
    }
}
