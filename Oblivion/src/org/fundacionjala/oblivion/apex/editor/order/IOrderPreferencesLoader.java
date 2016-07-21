/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier;
import org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType;

/**
 * Defines the methods to get the user preferences to be used by the order process.
 * 
 * @author Amir Aranibar
 */
public interface IOrderPreferencesLoader {

    /**
     * Loads the ordering options values defined in the editor preferences.
     *
     * @param document the document to load its preferences defined by the user.
     */
    void loadPreferences(Document document);

    /**
     * Gets an array of class members ordered by the user preferences.
     *
     * @return the array of class members.
     */
    ClassMemberType[] getSortedMembers();

    /**
     * Gets an array of access modifiers ordered by the user preferences.
     *
     * @return the array of access modifiers.
     */
    AccessModifier[] getSortedModifiers();

    /**
     * Defines if the user set the order alphabetically. true if the options is
     * activated, otherwise false.
     *
     * @return the option to order alphabetically.
     */
    boolean orderAlphabetically();

    /**
     * Defines if the user sets the order by visibility. true if the options is
     * activated, otherwise false.
     *
     * @return the option to order by visibility.
     */
    boolean sortByVisibility();
}
