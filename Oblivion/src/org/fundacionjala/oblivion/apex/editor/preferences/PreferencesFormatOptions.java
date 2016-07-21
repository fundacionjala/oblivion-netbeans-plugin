/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

/**
 * Defines the options to set in the reformat editor.
 *
 * @author Amir Aranibar
 */
public enum PreferencesFormatOptions {

    //options for left braces
    BRACES_IN_CLASS_DECLARATION,
    BRACES_IN_METHOD_DECLARATION,
    BRACES_IN_OTHER_DECLARATION,

    //options for right braces
    AFTER_CLASS_DECLARATION,
    AFTER_METHOD_DECLARATION,
    AFTER_OTHER_DECLARATION,
    
    //options for ordering
    SORT_MEMBERS_BY_VISIBILITY,
    CHECK_SORT_MEMBERS_IN_GROUPS_ALPHABETICALLY,
    MEMBERS_SORT_ORDER,
    MEMBERS_VISIBILITY
}
