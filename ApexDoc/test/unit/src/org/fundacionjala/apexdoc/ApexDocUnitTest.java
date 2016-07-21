/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.apexdoc;

import java.util.List;
import org.fundacionjala.apexdoc.utils.ProposalItem;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sergio_daza
 */
public class ApexDocUnitTest {

    ApexDocumentation documentation = new ApexDocumentation(1, "/org/fundacionjala/apexdoc/files/ApexDoc_test.json");

    @Test
    public void classDocumentationTest() {
        List<ProposalItem> classList = documentation.getClassList();
        assertEquals(6,classList.size());
    }
    
    @Test
    public void methodDocumentationTest() {
        List<ProposalItem> classList = documentation.getClassList();
        String className = classList.get(0).name;
        List<ProposalItem> methodList = documentation.getClassMap().get(className.toLowerCase());
        assertEquals(3,methodList.size());
    }
}
