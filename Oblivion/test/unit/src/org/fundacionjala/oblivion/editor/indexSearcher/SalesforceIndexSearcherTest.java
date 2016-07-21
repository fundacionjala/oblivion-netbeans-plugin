/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor.indexSearcher;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.rules.TemporaryFolder;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.api.IndexSearcher;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Marcelo Garnica
 */
public class SalesforceIndexSearcherTest {
    
    @ClassRule
    public static final TemporaryFolder temporaryfolder = new TemporaryFolder();
    
    /**
     * Test of getTypes method, of class SalesforceIndexSearcher.
     */
    @Test
    public void testGetTypes() throws IOException {
        File projectFolder = temporaryfolder.newFolder("testProject");
        temporaryfolder.newFolder("testProject", "src");
        temporaryfolder.newFolder("testProject", "src", "classes");
        temporaryfolder.newFolder("testProject", "src", "pages");
        temporaryfolder.newFolder("testProject", "build");
        temporaryfolder.newFolder("testProject", ".repo");
        temporaryfolder.newFolder("testProject", "other");
        temporaryfolder.newFolder("testProject", "TEMP");
        temporaryfolder.newFile("testProject/src/classes/MyPageController.cls");
        temporaryfolder.newFile("testProject/src/pages/MyPage.page");
        temporaryfolder.newFile("testProject/build/MyPage.extension");
        temporaryfolder.newFile("testProject/.repo/MyPage.page.i");
        temporaryfolder.newFile("testProject/src/package.xml");
        temporaryfolder.newFile("testProject/src/MyProject.data");
        temporaryfolder.newFile("testProject/other/MyParticular.other");
        temporaryfolder.newFile("testProject/TEMP/MyPage.page.temporal");
        Project project = new SalesforceProject(FileUtil.toFileObject(projectFolder));
        SalesforceIndexSearcher salesforceIndexSearcher = new SalesforceIndexSearcher();
        Set<? extends IndexSearcher.Descriptor> searchResult = salesforceIndexSearcher.getTypes(project, "MyP", QuerySupport.Kind.EXACT, null);
        assertNotNull(searchResult);
        assertFalse(searchResult.isEmpty());
        assertEquals(3, searchResult.size());
        assertTrue(contains(searchResult, "MyPage.page"));
        assertTrue(contains(searchResult, "MyPageController.cls"));
        assertTrue(contains(searchResult, "MyParticular.other"));
        assertFalse(contains(searchResult, "MyPage.extension"));
        assertFalse(contains(searchResult, "MyPage.page.i"));
        assertFalse(contains(searchResult, "MyPage.page.temporal"));
        assertFalse(contains(searchResult, "MyProject.data"));
    }

    private boolean contains(Set<? extends IndexSearcher.Descriptor> searchResult, String elementName) {
        boolean contains = false;
        for (IndexSearcher.Descriptor result : searchResult) {
            if (result.getTypeName().equals(elementName)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
