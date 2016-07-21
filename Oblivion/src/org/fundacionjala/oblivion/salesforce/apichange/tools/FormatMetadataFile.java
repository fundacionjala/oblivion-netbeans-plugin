/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 * Class that remove lines white in metadata file
 *
 * @author sergio_daza
 */
public class FormatMetadataFile {

    private final String path;

    public FormatMetadataFile(String path) {
        this.path = path;
    }

    /**
     * Method that remove white lines in metadata file
     */
    public void formatFile() {
        writeFile(readFile());
    }

    /**
     * Open and read metadata file.
     *
     * @return a list<String> with the content of file
     */
    public List<String> readFile() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            FileObject fileObject = FileUtil.toFileObject(file);
            lines = fileObject.asLines();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return lines;
    }

    /**
     * Save file omitting white lines
     *
     * @param lines List<String> with the content of file
     */
    public void writeFile(List<String> lines) {
        try {
            BufferedWriter bufferedWriter = null;
            bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (String line : lines) {
                if (!"".equals(line.trim())) {
                    bufferedWriter.write(line + "\n");
                }
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
