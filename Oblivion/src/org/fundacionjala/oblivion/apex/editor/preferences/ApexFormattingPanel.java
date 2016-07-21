/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import org.openide.util.Exceptions;

/**
 * Defines the methods to manage the formatting options.
 *
 * @author Amir Aranibar
 */
abstract class ApexFormattingPanel extends javax.swing.JPanel {

    protected final String folderPath = "/org/fundacionjala/oblivion/apex/editor/preferences/sourcecode/";
    protected String filePath = "";
    private static final String END_OF_LINE = "\n";

    /**
     * Sets all components with the defined options in the project preferences.
     *
     * @param preferences preferences of the project
     */
    abstract void load(Preferences preferences);

    /**
     * Saves all options in the project preferences to be used in the reformat
     * process.
     *
     * @param preferences preferences of the project
     */
    abstract void store(Preferences preferences);

    /**
     * Gets a sample of formatted code with the saved options.
     *
     * @param preferences preferences of the project
     * @return formatted code with the saved options
     */
    abstract String getPreviewText(Preferences preferences);

    /**
     * Gets a list of components that contain the formatting options.
     *
     * @return list of components
     */
    abstract List<JComponent> getComponentsToListen();

    /**
     * This method opens and read a file that is inside the .jar of project.
     *
     * @return
     */
    public String getCode() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(folderPath + filePath)));
            String line;
            if ((line = br.readLine()) != null) {
                result.append(line);
                while ((line = br.readLine()) != null) {
                    result.append(END_OF_LINE);
                    result.append(line);
                }
            }
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result.toString();
    }
}
