/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.util.prefs.Preferences;

/**
 * Defines the methods to manage the options for the current project.
 *
 * @author Amir Aranibar
 */
public interface IFormatOptions {

    /**
     * Saves a formatting option in the project preferences.
     *
     * @param preferences the preferences of the project
     * @param key the identifier for the option
     * @param value the value of the option
     */
    void setOptionValue(Preferences preferences, String key, String value);

    /**
     * Gets a formatting option from the project preferences given a key.
     *
     * @param preferences the preferences of the project
     * @param key the identifier for the option
     * @return the value of the option
     */
    String getOptionValue(Preferences preferences, String key);
}
