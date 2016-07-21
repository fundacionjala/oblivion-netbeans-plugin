/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import org.fundacionjala.oblivion.gradle.GradleResultHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link GradleResultHandler}
 * @author Marcelo Garnica
 */
public class GradleResultHandlerTest {
    
    @Test
    public void testGetDisplayableErrorMessage() {
        String expectedErrorMessage = null;
        GradleResultHandler instance = new GradleResultHandler(expectedErrorMessage);
        Throwable throwable = null;
        String actualErrorMessage = instance.getDisplayableErrorMessage(throwable);
        assertEquals(expectedErrorMessage, actualErrorMessage);        
    }
    
    @Test
    public void testGetCredentialNotFoundError() {
        String errorMessage = "An error message.";
        String exceptionMessage = "There is no credential test10";
        String expectedErrorMessage = String.format(GradleResultHandler.ERROR_MESSAGE_FORMAT, errorMessage, exceptionMessage);
        Throwable throwable = new Throwable(exceptionMessage, null);
        GradleResultHandler instance = new GradleResultHandler(errorMessage);        
        String actualErrorMessage = instance.getDisplayableErrorMessage(throwable);
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}
