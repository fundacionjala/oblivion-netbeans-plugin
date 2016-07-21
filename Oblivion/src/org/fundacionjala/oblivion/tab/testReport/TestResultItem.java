/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.io.File;

/**
 * This class contain the test information.
 *
 * @author sergio_daza
 */
public class TestResultItem {

    private final String testName;
    private final String testFolderPath;
    private final String testFilePath;
    private final int errors;
    private final int success;
    private Status status = Status.failed;
    private static final String EMPTY_STRING = "";
    private static final String RESULT_FILE = "/index.html";
    private static final String TEMPORARY_FOLDER = "/temp/reports/";
    private static final String PROTOCOL = "file://";
    private static final String SEPARATOR = "/";

    public TestResultItem(String testFolderPath, String testFilePath, Status status, int errors, int success) {
        this.testFolderPath = testFolderPath;
        this.testFilePath = testFilePath;
        this.testName = getTestName(testFolderPath);
        if (status == Status.running) {
            this.status = status;
        } else {
            File file = new File(testFolderPath + RESULT_FILE);
            if (file.isFile()) {
                if (file.length() > 10) {
                    if (errors > 0) {
                        this.status = Status.errors;
                    } else {
                        this.status = status;
                    }
                }
            }
        }
        this.errors = errors;
        this.success = success;
    }

    /**
     * It return the project path.
     * 
     * @return 
     */
    public String getProjectPath() {
        int pos = testFolderPath.indexOf(TEMPORARY_FOLDER);
        if(pos > 0) { 
            return testFolderPath.substring(0, pos);
        } else {
            return "";
        }
    }

    /**
     * It returns the test name from a path.
     * 
     * @param test
     * @return 
     */
    private String getTestName(String test) {
        String[] split = test.split(SEPARATOR);
        return split[split.length - 1];
    }

    /**
     * It returns the file path.
     * 
     * @return 
     */
    public String getFilePath() {
        return testFilePath;
    }

    /**
     * It returns the test name.
     * 
     * @return 
     * @see #getTestName(String) 
     */
    public String getTestName() {
        return testName;
    }

    /**
     * It return the current status.
     * 
     * @return 
     */
    public Status getStatus() {
        return status;
    }

    /**
     * It returns the test folder path.
     * 
     * @return 
     */
    public String getTestFolderPath() {
        return testFolderPath;
    }

    /**
     * It returns the url of test result (file://.../temp/reports/TestFolderPath/index.html).
     * 
     * @return 
     */
    public String getUrl() {
        if (Status.running != status && Status.failed != status) {
            return PROTOCOL + testFolderPath + RESULT_FILE;
        } else {
            return EMPTY_STRING;
        }
    }

    /**
     * It returns the success number on a String.
     * 
     * @return 
     * @see #getErrorsToString() 
     */
    public String getSuccessToString() {
        return EMPTY_STRING + success;
    }

    /**
     * It returns the errors number on a String.
     * 
     * @return 
     * @see #getSuccessToString() 
     */
    public String getErrorsToString() { 
        return EMPTY_STRING + errors;
    }

    /**
     * Enum for the test states
     */
    public enum Status {
        running,
        successful,
        errors,
        failed
    }

}
