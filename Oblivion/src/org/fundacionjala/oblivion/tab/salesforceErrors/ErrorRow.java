/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.salesforceErrors;

/**
 * This class contain the  information about on error line for removing, 
 * collapsing and expanding to lines.
 * 
 * @author sergio_daza
 */
public class ErrorRow {

    public TypeRow typeRow;
    private final NotificationItem notificationItem;

    public ErrorRow(TypeRow typeRow, NotificationItem notificationItem) {
        this.typeRow = typeRow;
        this.notificationItem = notificationItem;
    }

    public String pathFile() {
        return notificationItem.pathFile();
    }

    public String nameProject() {
        return notificationItem.nameProject;
    }

    public int lineError() {
        return notificationItem.line;
    }

    public int columnError() {
        return notificationItem.column;
    }
    
    public String description() {
        return notificationItem.description;
    }

    /**
     * ENUM for type of line according to content of table line
     */
    public enum TypeRow {
        PROJECT_NAME,
        ERROR
    }
}
