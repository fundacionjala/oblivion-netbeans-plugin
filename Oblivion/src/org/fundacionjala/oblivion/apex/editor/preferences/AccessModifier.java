/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

/**
 * Represents the access modifiers of a class member.
 *
 * @author Amir Aranibar
 */
public enum AccessModifier {

    GLOBAL("Global"),
    PUBLIC("Public"),
    PRIVATE("Private"),
    PROTECTED("Protected"),
    DEFAULT("Default");

    private final String text;

    private AccessModifier(final String text) {
        this.text = text;
    }

    /**
     * Gets an access modifier specified for a string.
     *
     * @param text the string to specify the access modifier.
     * @return access modifier specified for the parameter text.
     */
    public static AccessModifier getEnum(final String text) {
        AccessModifier accessModifier = null;

        if (text.equals(GLOBAL.toString())) {
            accessModifier = GLOBAL;
        } else if (text.equals(PUBLIC.toString())) {
            accessModifier = PUBLIC;
        } else if (text.equals(PRIVATE.toString())) {
            accessModifier = PRIVATE;
        } else if (text.equals(PROTECTED.toString())) {
            accessModifier = PROTECTED;
        } else if (text.equals(DEFAULT.toString())) {
            accessModifier = DEFAULT;
        } else {
            throw new IllegalArgumentException("No Enum specified for this string");
        }

        return accessModifier;
    }

    @Override
    public String toString() {
        return text;
    }
}
