/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */
package org.fundacionjala.oblivion.salesforce.project.nodes;

import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractClassChildFactory;
import org.fundacionjala.oblivion.salesforce.project.nodes.ClassesFolder;
import java.awt.Image;
import java.beans.BeanInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.gradle.tooling.GradleConnectionException;
import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link ClassesFolder} class.
 *
 * @author Marcelo Garnica
 */
public class ClassesFolderTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testClassNodesAreCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes"));
        temporaryFolder.newFile("classes/FirstClass.cls");
        temporaryFolder.newFile("classes/SecondClass.cls");
        ClassesFolder classesFolder = new ClassesFolder(classesFolderFileObject);
        Children classChildren = classesFolder.getChildren();
        assertEquals(2, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("FirstClass"));
        assertNotNull(classChildren.findChild("SecondClass"));
    }

    @Test
    public void testOtherFileNodesAreNotCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes2"));
        temporaryFolder.newFile("classes2/NewClass.cls");
        temporaryFolder.newFile("classes2/xmlFile.xml");
        temporaryFolder.newFile("classes2/NewClass2.cls2");
        ClassesFolder classesFolder = new ClassesFolder(classesFolderFileObject);
        Children classChildren = classesFolder.getChildren();
        assertEquals(1, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("NewClass"));
        assertNull(classChildren.findChild("xmlFile"));
        assertNull(classChildren.findChild("NewClass2"));
    }

    @Test
    public void testPackagFolderNodeAreCreated() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes3"));
        File newClass = temporaryFolder.newFile("classes3/NewClass.cls");
        FileWriter fileWriter = new FileWriter(newClass);
        fileWriter.write(AbstractClassChildFactory.PACKAGE_PATTERN + " my.new.package.works;");
        fileWriter.close();
        ClassesFolder classesFolder = new ClassesFolder(classesFolderFileObject);
        Children classChildren = classesFolder.getChildren();
        assertEquals(1, classChildren.getNodesCount());
        assertNotNull(classChildren.findChild("my.new.package.works"));
        assertNull(classChildren.findChild("NewClass"));
        Children packageChildren = classChildren.findChild("my.new.package.works").getChildren();
        assertNotNull(packageChildren.findChild("NewClass"));
    }

    @Test
    public void testIconChangesWhenThereAreErrors() throws IOException {
        FileObject classesFolder = FileUtil.toFileObject(temporaryFolder.newFolder("classesErrorsNode"));
        FileObject newClass = FileUtil.toFileObject(temporaryFolder.newFile("classesErrorsNode/NewClass.cls"));
        int iconType = BeanInfo.ICON_COLOR_32x32;
        ClassesFolder classesFolderNode = new ClassesFolder(classesFolder);
        Image classNormalIcon = classesFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        String errorMessage = "Something went wrong";
        String serverMessageTemplate = "{ \"errors\" : [ { \"fileName\": \"%s\", \"problem\":\"%s\", \"line\":%d, \"column\":%d } ] }";
        String serverMessage = String.format(serverMessageTemplate, "classesErrorsNode/NewClass.cls", errorMessage, 1, 1);
        GradleDeployResultHandler deployHandler = new GradleDeployResultHandler(newClass, errorMessage);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(serverMessage, new Throwable(serverMessage));
        deployHandler.onFailure(gradleConnectionException);
        Image classErrorIcon = classesFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        assertNotSame(classNormalIcon, classErrorIcon);
    }

    @Test
    public void testIconChangesWithPackagesNodes() throws IOException {
        FileObject classesFolder = FileUtil.toFileObject(temporaryFolder.newFolder("classesPackageNode"));
        FileObject newClass = FileUtil.toFileObject(temporaryFolder.newFile("classesPackageNode/NewClass.cls"));
        FileWriter fileWriter = new FileWriter(FileUtil.toFile(newClass));
        fileWriter.write(AbstractClassChildFactory.PACKAGE_PATTERN + " new.package;");
        fileWriter.close();
        int iconType = BeanInfo.ICON_COLOR_32x32;
        ClassesFolder classesFolderNode = new ClassesFolder(classesFolder);
        Node packageFolderNode = classesFolderNode.getChildren().findChild("new.package");
        Image folderNormalIcon = classesFolderNode.getIcon(iconType);
        Image packageNormalIcon = packageFolderNode.getIcon(iconType);
        Image classNormalIcon = packageFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        String errorMessage = "Something went wrong";
        String serverMessageTemplate = "{ \"errors\" : [ { \"fileName\": \"%s\", \"problem\":\"%s\", \"line\":%d, \"column\":%d } ] }";
        String serverMessage = String.format(serverMessageTemplate, "classesPackageNode/NewClass.cls", errorMessage, 1, 1);
        GradleDeployResultHandler deployHandler = new GradleDeployResultHandler(newClass, errorMessage);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(serverMessage, new Throwable(serverMessage));
        deployHandler.onFailure(gradleConnectionException);
        Image folderErrorIcon = classesFolderNode.getIcon(iconType);
        Image packageErrorIcon = packageFolderNode.getIcon(iconType);
        Image classErrorIcon = packageFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        assertNotSame(folderNormalIcon, folderErrorIcon);
        assertNotSame(packageNormalIcon, packageErrorIcon);
        assertNotSame(classNormalIcon, classErrorIcon);
    }

    @Test
    public void testIconsDontChanges() throws IOException {
        FileObject classesFolder = FileUtil.toFileObject(temporaryFolder.newFolder("classesNode"));
        FileObject newClass = FileUtil.toFileObject(temporaryFolder.newFile("classesNode/NewClass.cls"));
        int iconType = BeanInfo.ICON_COLOR_32x32;
        ClassesFolder classesFolderNode = new ClassesFolder(classesFolder);
        Image folderNormalIcon = classesFolderNode.getIcon(iconType);
        Image classNormalIcon = classesFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        String errorMessage = "Something went wrong";
        String serverMessage = "{ \"errors\" : [ ] }";
        GradleDeployResultHandler deployHandler = new GradleDeployResultHandler(newClass, errorMessage);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(serverMessage, new Throwable(serverMessage));
        deployHandler.onFailure(gradleConnectionException);
        Image folderErrorIcon = classesFolderNode.getIcon(iconType);
        Image classErrorIcon = classesFolderNode.getChildren().findChild("NewClass").getIcon(iconType);
        assertSame(folderNormalIcon, folderErrorIcon);
        assertSame(classNormalIcon, classErrorIcon);
    }

    @Test
    public void testPackageFolderNodeAreNotCreatedWhenTheAnnotationIsNotInTheFirstLine() throws DataObjectNotFoundException, IOException {
        FileObject classesFolderFileObject = FileUtil.toFileObject(temporaryFolder.newFolder("classes4"));
        File newClass = temporaryFolder.newFile("classes4/NewClass.cls");
        FileWriter fileWriter = new FileWriter(newClass);
        fileWriter.write("//my.new.package.does.not.work " + AbstractClassChildFactory.PACKAGE_PATTERN + " my.new.package.works;");
        fileWriter.close();
        ClassesFolder classesFolder = new ClassesFolder(classesFolderFileObject);
        Children classChildren = classesFolder.getChildren();
        assertEquals(1, classChildren.getNodesCount());
        assertNull(classChildren.findChild("my.new.package.works"));
        assertNotNull(classChildren.findChild("NewClass"));
    }
}
