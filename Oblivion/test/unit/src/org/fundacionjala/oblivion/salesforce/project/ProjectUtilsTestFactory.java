/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project;

import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.openide.util.Lookup;


/**
 * Utility class that allows to set a default Lookup instance so the {@link ProjectUtil} class 
 * search the current file or current project using the given lookup instance.
 * 
 * 
 * @author Adrian Grajeda
 */
public class ProjectUtilsTestFactory {
    /**
     * Allow to setup a lookup.
     * 
     * {@code 
     *    Lookup mockLookup = mock(Lookup);
     *    when(mockLookup.lookup(Project.class)).thenReturn(fakeProject);
     * } 
     * 
     * @param lookup 
     */
    public static void setDefaultLookup(Lookup lookup) {
        ProjectUtils.lookup = lookup;
    }
    
}
