/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.util.prefs.Preferences;
import org.fundacionjala.oblivion.apex.editor.preferences.IFormatOptions;

/**
 *
 * @author Amir Aranibar
 */
public class DummyFormatOptions implements IFormatOptions {

    private String option;

    public DummyFormatOptions() {
        option = "";
    }

    @Override
    public void setOptionValue(Preferences preferences, String key, String value) {
        option = value;
    }

    @Override
    public String getOptionValue(Preferences preferences, String key) {
        return option;
    }
}
