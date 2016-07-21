/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.openide.util.Exceptions;

/**
 * This class is for recover information inside on the specific tags that
 * contain a class name declared.
 * 
 * @author sergio_daza
 */
public class RecoverInformation {
    
    /**
     * Checks if exist the file.
     * 
     * @param path
     * @return 
     */
    private boolean checkFile(String path) {
        boolean existsTempFile = false;
        File tempFile = new File(path);
        if (tempFile.exists()) {
            existsTempFile = true;
        } 
        return existsTempFile;
    }
    
    /**
     * Recover the text from file.
     * 
     * @param path
     * @return 
     */
    public String getCode(String path) {
        StringBuilder result = new StringBuilder();
        FileReader fileReader;
        if (checkFile(path)) {
            try {
                File tempFile = new File(path);
                fileReader = new FileReader(tempFile);
                try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                    String linea;
                    while ((linea = bufferedReader.readLine()) != null) {
                        result.append(linea);
                    }
                }
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return result.toString().toLowerCase();
    }
    
    /**
     * This method searches one or more tags in the text.
     * 
     * @param path
     * @param tags
     * @return 
     */
    public Map<String, TagHTML>testResult(String path, Map<String, TagHTML> tags){
        String htmlCode = getCode(path);
        for(int i=0; i<htmlCode.length(); i++) {
            char charAt = htmlCode.charAt(i);
            for(TagHTML tag : tags.values()) {
                 if(!tag.located()) {
                    tag.isTag(charAt);
                }
            }
        }
        return tags;
    }

}
