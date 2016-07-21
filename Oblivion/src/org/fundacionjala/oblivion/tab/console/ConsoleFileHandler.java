/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.fundacionjala.oblivion.tab.output.OutputHandler;
import org.openide.util.Exceptions;

/**
 * This class handles the temporary file that it is used from the console
 * 
 * @author sergio_daza
 */
public class ConsoleFileHandler {
    
    private static final String NAME_TEMP_FILE = "/tempFileOfConsole.cls";
    private static final String TEMP_FOLDER = "/temp";
    private static final String NEWLINE = "\n";
    private String pathDir= "";

    /**
     * Sets the path with the path of current project.
     * 
     * @param pathDir 
     */
    public void setPathDir(String pathDir) {
        this.pathDir = pathDir;
        checkFile();
    }
    
    /**
     * This method checks if exist the temporary file, if not exist, it create a new file.
     */
    private boolean checkFile() {
        boolean existsTempFile = false;
        File tempFolder = new File(pathDir + TEMP_FOLDER);
        if (!tempFolder.isDirectory()) {
            tempFolder.mkdir();
        }
        File tempFile = new File(pathDir + TEMP_FOLDER + NAME_TEMP_FILE);
        if (tempFile.exists()) {
            existsTempFile = true;
        } else {
            try {
                if (tempFile.createNewFile()) {
                    existsTempFile = true;
                    setCode("// ADD SOME APEX CODE HERE");
                }
            } catch (IOException ex) {
                OutputHandler.danger(ex.toString());
            }
        }
        return existsTempFile;
    }
    
    /**
     * Returns the code  that contains on temporary file.
     * 
     * @return 
     */
    public String getCode() {
        StringBuilder result = new StringBuilder();
        FileReader fr;
        if (checkFile()) {
            try {
                File tempFile = new File(pathDir + TEMP_FOLDER + NAME_TEMP_FILE);
                fr = new FileReader(tempFile);
                BufferedReader br = new BufferedReader(fr);
                String linea = "";
                if((linea = br.readLine()) != null) {
                    result.append(linea);
                    while ((linea = br.readLine()) != null) {
                        result.append(NEWLINE);
                        result.append(linea);
                    }
                }
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return result.toString();
    }
    
    /**
     * This method saves the code on temporary file.
     * 
     * @param text 
     */
    public void setCode(String text) {
        if (checkFile()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(pathDir + TEMP_FOLDER + NAME_TEMP_FILE));
                bw.write(text);
                bw.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    /**
     * Returns the file path of the current project.
     * 
     * @return 
     */
    public String getPathTempFile() {
        return pathDir + TEMP_FOLDER + NAME_TEMP_FILE;
}
    
    /**
     * Returns the path of current project.
     * 
     * @return 
     */
    public String getPatdDir() {
        return pathDir;
    }
}
