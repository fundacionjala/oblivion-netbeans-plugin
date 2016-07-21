/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.actions;

import org.fundacionjala.oblivion.salesforce.project.actions.DownloadProjectAction;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtilsTestFactory;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link DownloadProjectAction}
 *
 * @author adrian grajeda
 */
public class DownloadProjectActionTest {

    private static final String TEMP_TEST_FILE = String.format("%s/oblivionTest/projectAction", System.getProperty("java.io.tmpdir"));

    private SalesforceProject project;
    @Mock
    private FileObject file;
    @Mock
    private Lookup lookup;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        project = new SalesforceProject(file);
        initLookup(TEMP_TEST_FILE);
    }

    @Test
    public void testGetFolder() {
        DownloadProjectAction instance = new DownloadProjectAction();
        assertEquals(TEMP_TEST_FILE, instance.getFolder());
    }

    @Test
    public void testGetFolderAfterSelectAnotherProject() {
        DownloadProjectAction instance = new DownloadProjectAction();
        assertEquals(TEMP_TEST_FILE, instance.getFolder());
        String secondpath = System.getProperty("user.dir");
        initLookup(secondpath);
        assertEquals(secondpath, instance.getFolder());

    }

    private void initLookup(String folder) {
        when(lookup.lookup(eq(Project.class))).thenReturn(project);
        when(file.getPath()).thenReturn(folder);
        ProjectUtilsTestFactory.setDefaultLookup(lookup);
    }

}
