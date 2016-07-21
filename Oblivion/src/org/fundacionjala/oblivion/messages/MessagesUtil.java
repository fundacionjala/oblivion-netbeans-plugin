/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.messages;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Utility class to display messages in dialog box.
 * 
 * @author Marcelo Garnica
 */
public class MessagesUtil {

    public static boolean ARE_UNIT_TESTS_RUNNING = false;
    private static final DialogDisplayer dialogDisplayer = DialogDisplayer.getDefault();
    
    /**
     * Displays a meesge according to its type.
     * 
     * @param message - The message to be displayed.
     * @param messageType - The message's type.
     */
    public static void showMessage(String message, MessageType messageType) {
        if (!ARE_UNIT_TESTS_RUNNING) {
            dialogDisplayer.notify(new NotifyDescriptor.Message(message, messageType.getType()));
        }
    }
    
    /**
     * Displays an error message.
     * @param errorMessage - The error message.
     */
    public static void showError(String errorMessage) {
        showMessage(errorMessage, MessageType.ERROR);
    }
    
    /**
     * Displays a warning message.
     * @param warningMessage 
     */
    public static void showWarning(String warningMessage) {
        showMessage(warningMessage, MessageType.WARNING);
    }
    
    /**
     * Displays an information message.
     * @param infoMessage 
     */
    public static void showInfo(String infoMessage) {
        showMessage(infoMessage, MessageType.INFO);
    }
    
    /**
     * Displays a plain message.
     * @param plainMessage 
     */
    public static void showPlain(String plainMessage) {
        showMessage(plainMessage, MessageType.PLAIN);
    }
    
    /**
     * Displays an exception's error message.
     * 
     * @param exception 
     */
    public static void showException(Throwable exception) {
        showError(exception.getLocalizedMessage());
    }
}
