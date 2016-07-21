/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Basic tests for {@link GradleDeployResultHandler} class.
 * @author Alvaro Torrez
 */
public class GradleDeployResultHandlerTastTest {
    
    @Rule
    public TemporaryFolder baseFolder = new TemporaryFolder();
    
    private FileObject fileobject;
    
    @Before
    public void init() throws IOException{
        baseFolder.newFolder("folder1");
        baseFolder.newFolder("folder2");
        baseFolder.newFolder("folder3");
        baseFolder.newFile("test1.cls");
        baseFolder.newFile("test2.cls");
        baseFolder.newFile("test3.cls");
        baseFolder.newFile("test4.cls");
        File source = baseFolder.getRoot();
        fileobject = FileUtil.toFileObject(source);
    }
    
    @Test
    public void testGradleDeployResultHandlerSize() {
        GradleDeployResultHandler instance = new GradleDeployResultHandler(fileobject, "MessageError");
        assertEquals(4, instance.getDocuments().keySet().size());
    }
    
    @Test
    public void testGradleDeployResultHandlerNames() {
        GradleDeployResultHandler instance = new GradleDeployResultHandler(fileobject, "MessageError");
        boolean resultFile1 = instance.getDocuments().containsKey("test1.cls");
        boolean resultFile2 = instance.getDocuments().containsKey("test2.cls");
        boolean resultFile3 = instance.getDocuments().containsKey("test3.cls");
        boolean resultFile4 = instance.getDocuments().containsKey("test4.cls");
        assertTrue(resultFile1);
        assertTrue(resultFile2);
        assertTrue(resultFile3);
        assertTrue(resultFile4);
    }
    
    @Test
    public void testGradleDeployResultHandlerOneFile() throws IOException {
        File OneFile = baseFolder.newFile("OneFile.cls");
        FileObject Onefileobject = FileUtil.toFileObject(OneFile);
        GradleDeployResultHandler instance = new GradleDeployResultHandler(Onefileobject, "MessageError");
        assertEquals(1,instance.getDocuments().keySet().size());
    }
}
