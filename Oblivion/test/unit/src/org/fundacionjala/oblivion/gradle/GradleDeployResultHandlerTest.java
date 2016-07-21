/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import org.fundacionjala.oblivion.gradle.GradleDeployResultHandler;
import java.io.IOException;
import java.util.List;
import org.gradle.tooling.GradleConnectionException;
import org.fundacionjala.oblivion.apex.errors.ServerErrors;
import org.fundacionjala.oblivion.messages.MessagesUtil;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import static org.junit.Assert.*;

/**
 *Test class for {@link GradleDeployResultHandler}
 * @author Marcelo Garnica
 */
public class GradleDeployResultHandlerTest {
    
    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testErrosWithRightFormatForAFile() throws IOException {
        FileObject myClass = FileUtil.toFileObject(temporaryFolder.newFile("MyClass.cls"));
        String errorMessage = "Something went wrong";
        String serverMessageTemplate = "{ \"errors\" : [ { \"fileName\": \"%s\", \"problem\":\"%s\", \"line\":%d, \"column\":%d } ] }";
        String serverMessage = String.format(serverMessageTemplate, "classes/MyClass.cls", errorMessage, 1, 1);
        GradleDeployResultHandler instance = new GradleDeployResultHandler(myClass, errorMessage);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(serverMessage, new Throwable(serverMessage));
        instance.onFailure(gradleConnectionException);
        List<ErrorDescription> errors = ServerErrors.getInstance().getErrors(myClass);
        assertFalse(errors.isEmpty());
        String expectedDocumentMessage = String.format(GradleDeployResultHandler.DEPLOY_ERROR_MESSAGE_FORMAT, errorMessage, 1, 1);
        assertEquals(expectedDocumentMessage, errors.get(0).getDescription());
    }
    
    @Test
    public void testErrosWithRightFormatForAFolder() throws IOException {
        FileObject classesFolder = FileUtil.toFileObject(temporaryFolder.newFolder("classes"));
        FileObject firstClass = FileUtil.toFileObject(temporaryFolder.newFile("classes/FirstClass.cls"));
        FileObject secondClass = FileUtil.toFileObject(temporaryFolder.newFile("classes/SecondClass.cls"));
        FileObject thirdClass = FileUtil.toFileObject(temporaryFolder.newFile("classes/ThirdClass.cls"));
        String classErrorTemplate = "{ \"fileName\": \"%s\", \"problem\":\"%s\", \"line\":%d, \"column\":%d }";
        String firstClassError = String.format(classErrorTemplate, "classes/FirstClass.cls", "FirstError", 1, 1);
        String thirdClassError = String.format(classErrorTemplate, "classes/ThirdClass.cls", "ThirdError", 1, 1);
        String serverMessageTemplate = "{ \"errors\" : [ %s,%s ] }";
        String serverMessage = String.format(serverMessageTemplate, firstClassError, thirdClassError);
        GradleDeployResultHandler instance = new GradleDeployResultHandler(classesFolder, null);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(serverMessage, new Throwable(serverMessage));
        instance.onFailure(gradleConnectionException);
        List<ErrorDescription> errors = ServerErrors.getInstance().getErrors(firstClass);
        assertFalse(errors.isEmpty());
        String expectedDocumentMessage = String.format(GradleDeployResultHandler.DEPLOY_ERROR_MESSAGE_FORMAT, "FirstError", 1, 1);
        assertEquals(expectedDocumentMessage, errors.get(0).getDescription());
        errors = ServerErrors.getInstance().getErrors(secondClass);
        assertTrue(errors.isEmpty());
        errors = ServerErrors.getInstance().getErrors(thirdClass);
        assertFalse(errors.isEmpty());
        expectedDocumentMessage = String.format(GradleDeployResultHandler.DEPLOY_ERROR_MESSAGE_FORMAT, "ThirdError", 1, 1);
        assertEquals(expectedDocumentMessage, errors.get(0).getDescription());
    }
    
    @Test
    public void testErrosWithWrongFormat() throws IOException {
        MessagesUtil.ARE_UNIT_TESTS_RUNNING = true;
        FileObject myClass = FileUtil.toFileObject(temporaryFolder.newFile("MyNewClass.cls"));
        String errorMessage = "Something went wrong";
        GradleDeployResultHandler instance = new GradleDeployResultHandler(myClass, errorMessage);
        GradleConnectionException gradleConnectionException = new GradleConnectionException(errorMessage, new Throwable(errorMessage));
        instance.onFailure(gradleConnectionException);
        List<ErrorDescription> errors = ServerErrors.getInstance().getErrors(myClass);
        assertTrue(errors.isEmpty());
    }

}
