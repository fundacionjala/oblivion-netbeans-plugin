/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import static org.fundacionjala.oblivion.apex.editor.preferences.BracesValueOptions.*;
import static org.fundacionjala.oblivion.apex.editor.preferences.OrderingValueOptions.*;

/**
 * This class manages the formatting options for the current project.
 *
 * @author Amir Aranibar
 */
public final class FormatOptions implements IFormatOptions {

    private static final FormatOptions INSTANCE = new FormatOptions();

    private Map<String, String> defaultValues;

    /**
     * Singleton
     *
     * @return the single instance of this class
     */
    public static FormatOptions getInstance() {
        return INSTANCE;
    }

    public FormatOptions() {
        createDefaultValues();
    }

    @Override
    public void setOptionValue(Preferences preferences, String key, String value) {
        preferences.put(key, value);
    }

    @Override
    public String getOptionValue(Preferences preferences, String key) {
        if (defaultValues.containsKey(key)) {
            return preferences.get(key, defaultValues.get(key));
        }

        return preferences.get(key, "undefined");
    }

    /**
     * Initialize the default options.
     *
     */
    private void createDefaultValues() {
        defaultValues = new HashMap<>();
        defaultValues.put(PreferencesFormatOptions.BRACES_IN_CLASS_DECLARATION.name(), SAME_LINE);
        defaultValues.put(PreferencesFormatOptions.BRACES_IN_METHOD_DECLARATION.name(), SAME_LINE);
        defaultValues.put(PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION.name(), SAME_LINE);
        defaultValues.put(PreferencesFormatOptions.SORT_MEMBERS_BY_VISIBILITY.name(), VISIBLE_MEMBERS);
        defaultValues.put(PreferencesFormatOptions.MEMBERS_SORT_ORDER.name(), MEMBERS_SORT_ORDER_LIST);
        defaultValues.put(PreferencesFormatOptions.MEMBERS_VISIBILITY.name(), VISIBILITY_LIST);
        defaultValues.put(PreferencesFormatOptions.CHECK_SORT_MEMBERS_IN_GROUPS_ALPHABETICALLY.name(), CHECK_SORT_MEMBERS_IN_GROUPS_ALPHABETICALLY);
    }
}
