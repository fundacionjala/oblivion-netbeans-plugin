/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.messages;

import org.openide.NotifyDescriptor;

/**
 * Enum that represents each type of message.
 * 
 * @author Marcelo Garnica
 */
public enum MessageType {
    PLAIN(NotifyDescriptor.PLAIN_MESSAGE),
    INFO(NotifyDescriptor.INFORMATION_MESSAGE),
    ERROR(NotifyDescriptor.ERROR_MESSAGE),    
    WARNING(NotifyDescriptor.WARNING_MESSAGE);
    
    private final int messageType;
    
    private MessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getType() {
        return messageType;
    }
}
