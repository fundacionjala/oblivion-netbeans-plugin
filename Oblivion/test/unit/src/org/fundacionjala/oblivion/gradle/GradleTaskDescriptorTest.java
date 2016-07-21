/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import static org.fundacionjala.oblivion.gradle.AbstractGradleTask.STACK_TRACE_PARAM_VALUE;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link GradleTaskDescriptor}
 *
 * @author Adrian Grajeda
 */
public class GradleTaskDescriptorTest {
    
    private GradleTaskDescriptor instance;

    @Test
    public void testArgumentListNoParameters() {
        instance = new GradleTaskDescriptor("update", "/home/test");
        String[] expected = new String[]{STACK_TRACE_PARAM_VALUE};
        assertArrayEquals(expected, instance.getExecutionParameters());
    }

    @Test
    public void testArgumentListWithParameters() {
        instance = new GradleTaskDescriptor("update", "/home/test");
        instance.addParameterEntry("dummy", "foo");
        String[] expected = new String[]{"-Pdummy=foo", STACK_TRACE_PARAM_VALUE};
        assertArrayEquals(expected, instance.getExecutionParameters());
    }
    
    @Test
    public void testArgumentListWithDuplicateParameters() {
        instance = new GradleTaskDescriptor("update", "/home/test");
        instance.addParameterEntry("dummy", "foo");
        instance.addParameterEntry("dummy", "fooOverride");
        String[] expected = new String[]{"-Pdummy=fooOverride", STACK_TRACE_PARAM_VALUE};
        assertArrayEquals(expected, instance.getExecutionParameters());
    }
}
