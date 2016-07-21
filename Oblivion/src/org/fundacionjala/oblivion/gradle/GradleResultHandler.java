/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.ResultHandler;
import org.fundacionjala.oblivion.messages.MessagesUtil;
import org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent;

/**
 * Handles the result of a gradle execution.
 * 
 * @author Marcelo Garnica
 */
public class GradleResultHandler implements ResultHandler<Object> {
    
    private static final Logger LOG = Logger.getLogger(GradleResultHandler.class.getName());
    static final String ERROR_MESSAGE_FORMAT = "%s%n%nDetails:%n%s";
    
    protected final GradleOutputStream outputStream;
    protected final GradleOutputStream errorOutputStream;
    protected final String errorMessage;

    public GradleResultHandler(String errorMessage) {
        this.errorMessage = errorMessage;
        outputStream = new GradleOutputStream();
        errorOutputStream = new GradleOutputStream();
    }
    
    public GradleOutputStream getErrorOutputStream() {
        return errorOutputStream;
    }    
    
    public GradleOutputStream getOutputStream() {
        return outputStream;
    }    

    @Override
    public void onComplete(Object t) {
        LOG.log(Level.INFO, outputStream.toString());
    }

    @Override
    public void onFailure(GradleConnectionException gradleConnectionException) {
        LOG.log(Level.INFO, outputStream.toString());
        LOG.log(Level.INFO, errorOutputStream.toString());
        Throwable realException = getRealException(gradleConnectionException);
        SalesforceErrorsTopComponent.addNofitfy(null,getDisplayableErrorMessage(realException));
        MessagesUtil.showError(getDisplayableErrorMessage(realException));
    }
    
    /**
     * Gets the real underlying exception from a GradleConnectionException.
     * @param gradleConnectionException - The main gradle connection exception.
     * @return realException - The underlying exception.
     */
    protected Throwable getRealException(GradleConnectionException gradleConnectionException) {
        Throwable lastException = gradleConnectionException;
        Throwable realException = null;
        while (lastException.getCause() != null) {
            realException = lastException;
            lastException = lastException.getCause();
        }
        return realException;
    }
    
    /**
     * Gets error the message of an exception.
     * @param exception - The exception that has the error message.
     * @return The exception error message.
     */
    protected String getExceptionMessage(Throwable exception) {
        String exceptionMessage;
        if (exception.getLocalizedMessage() != null) {
            exceptionMessage = exception.getLocalizedMessage();
        } else if (exception.getMessage() != null) {
            exceptionMessage = exception.getMessage();
        } else {
            exceptionMessage = exception.toString();
        }
        return exceptionMessage;
    }

    /**
     * Retrieves the error message to be displayed on the dialog box.
     * @return The message to be displayed.
     */
    protected String getDisplayableErrorMessage(Throwable exception) {
        if (exception != null) {
            return String.format(ERROR_MESSAGE_FORMAT, errorMessage, getExceptionMessage(exception));
        }
        return null;
    }
}
