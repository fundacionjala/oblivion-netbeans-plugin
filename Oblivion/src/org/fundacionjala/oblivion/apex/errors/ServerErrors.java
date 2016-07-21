/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.openide.filesystems.FileObject;

/**
 * Stores the server errors that the deployment tasks generate. The errors are stored by file.
 * 
 * @author Marcelo Garnica
 */
public class ServerErrors {
    
    private static ServerErrors instance;
    
    public static ServerErrors getInstance() {
        if (instance == null) {
            instance = new ServerErrors();
        }
        return instance;
    }
    
    private final Map<String, List<ErrorDescription>> serverErrors;
    
    private ServerErrors() {
        serverErrors = new HashMap<>();
    }
    
    /**
     * Adds the deployment errors from a given file.
     * @param file - The file with errors.
     * @param errors - The file's errors.
     */
    public void addErrors(FileObject file, List<ErrorDescription> errors) {
        if (!errors.isEmpty()) {
            serverErrors.put(file.getPath(), errors);
        }
    }

    /**
     * Removes the deployment errors from a given file.
     * @param file - The file with errors.
     */
    public void removeErrors(FileObject file) {
        serverErrors.remove(file.getPath());
    }
    
    /**
     * Retrieves the errors from a given file. if there is no errors, an empty list is returned.
     * @param file - The file which errors should be retrieved.
     * @return The file's errors.
     */
    public List<ErrorDescription> getErrors(FileObject file) {
        String filePath = file.getPath();
        if (!serverErrors.containsKey(filePath) || serverErrors.get(filePath) == null) {
            return new ArrayList<>();
        }
        return serverErrors.get(filePath);
    }
}
