/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

/**
 * Represents the type of a class member.
 *
 * @author Amir Aranibar
 */
public enum ClassMemberType {

    STATIC_FIELD("Static Fields"),
    STATIC_INITIALIZER("Static Initializers"),
    STATIC_METHOD("Static Methods"),
    FIELD("Fields"),
    INSTANCE_INITIALIZER("Instance initializers"),
    CONSTRUCTOR("Constructors"),
    METHOD("Methods"),
    STATIC_CLASSE("Static Classes"),
    CLASS("Classes");

    private final String text;

    private ClassMemberType(final String text) {
        this.text = text;
    }

    /**
     * Gets a class member type specified for a string.
     *
     * @param text the string to specify the class member type.
     * @return class member type specified for the parameter text.
     */
    public static ClassMemberType getEnum(final String text) {
        ClassMemberType classMemberType = null;

        if (text.equals(STATIC_FIELD.toString())) {
            classMemberType = STATIC_FIELD;
        } else if (text.equals(STATIC_INITIALIZER.toString())) {
            classMemberType = STATIC_INITIALIZER;
        } else if (text.equals(STATIC_METHOD.toString())) {
            classMemberType = STATIC_METHOD;
        } else if (text.equals(FIELD.toString())) {
            classMemberType = FIELD;
        } else if (text.equals(INSTANCE_INITIALIZER.toString())) {
            classMemberType = INSTANCE_INITIALIZER;
        } else if (text.equals(CONSTRUCTOR.toString())) {
            classMemberType = CONSTRUCTOR;
        } else if (text.equals(METHOD.toString())) {
            classMemberType = METHOD;
        } else if (text.equals(STATIC_CLASSE.toString())) {
            classMemberType = STATIC_CLASSE;
        } else if (text.equals(CLASS.toString())) {
            classMemberType = CLASS;
        } else {
            throw new IllegalArgumentException("No Enum specified for this string");
        }

        return classMemberType;
    }

    @Override
    public String toString() {
        return text;
    }
}
