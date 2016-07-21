/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle;

import java.io.IOException;
import org.fundacionjala.oblivion.gradle.DownloadProjectTask;
import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Basic tests for {@link DownloadProjectTask} class.
 * 
 * @author Adrian Grajeda
 */
public class DownloadProjectTaskTest {
    
    @Rule
    public TemporaryFolder baseFolder = new TemporaryFolder();
    
    private FileObject correctProject;
    private FileObject incorrectProject;
    private FileObject emptyIdProject;
    
    @Before
    public void init() throws IOException {
        correctProject   = FileUtil.toFileObject(baseFolder.newFolder("project1"));
        incorrectProject = FileUtil.toFileObject(baseFolder.newFolder("project2"));
        emptyIdProject   = FileUtil.toFileObject(baseFolder.newFolder("project3"));
        CredentialWrapper validCredential = new CredentialWrapper("user@user.com", "aPassword", "aToken", "aLoginType");
        CredentialWrapper invalidCredential = new CredentialWrapper("", "", "", "");
        CredentialManager.getDefaultStorage().save(correctProject.getPath(), validCredential);
        CredentialManager.getDefaultStorage().save(emptyIdProject.getPath(), invalidCredential);
    }

    @After
    public void teardown() {
        CredentialManager.getDefaultStorage().deleteProjectCredential(correctProject.getPath());
        CredentialManager.getDefaultStorage().deleteProjectCredential(emptyIdProject.getPath());
    }
    
    @Test
    public void testIsValid() {
        DownloadProjectTask instance = new DownloadProjectTask(correctProject.getPath(), "failed");
        assertTrue(instance.isValid());        
    }
    
    @Test
    public void testIsNotValid() {
        DownloadProjectTask instance = new DownloadProjectTask(incorrectProject.getPath(), "failed");
        assertFalse(instance.isValid());
    }
    
    @Test
    public void testEmptyId() {
        DownloadProjectTask instance = new DownloadProjectTask(emptyIdProject.getPath(), "failed");
        assertFalse(instance.isValid());
    }
}
