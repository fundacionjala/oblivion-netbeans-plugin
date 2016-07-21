/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.salesforceErrors;

import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;

/**
 * This class contain data of the error line.
 * 
 * @author sergio_daza
 */
public class NotificationItem {

    public final String nameProject;
    public final String description;
    public final String path;
    public final String pathProject;
    public final int line;
    public final int column;
    public static final String FAILED = "Failed";

    public NotificationItem(String nameProject, String pathProject, String description, String path, int line, int column) {
        this.nameProject = nameProject;
        this.description = description;
        this.path =  "/" + path;
        this.pathProject = pathProject;
        this.line = line;
        this.column = column;
    }

    /**
     * Builds the location string of the error to show.
     * 
     * @return 
     */
    public String location() {
        return (line > 0 && column > 0) ? new String("At line: " + line + ", column: " + column) : FAILED;
    }

    /**
     * Builds the path of the file.
     * 
     * @return 
     */
    public String pathFile() {
        if (!pathProject.isEmpty() && !path.isEmpty()) {
            return pathProject + SalesforceProject.SOURCE_FOLDER + path;
        } else {
            return "";
        }
    }
}
