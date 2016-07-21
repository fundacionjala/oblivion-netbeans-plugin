/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.declarationFinder;

/**
 * Enum for possible variable types
 * 
 * @author sergio_daza
 */
public enum TypeOfIdentifier {
    TYPE_VARIABLE_NAME,
    EXTERNAL_VARIABLE_NAME,
    SUPER_VARIABLE_NAME,
    EXTENDS_CLASS_VARIABLE_NAME,
    LOCAL_METHOD_INVOCATION,
    LOCAL_VARIABLE_NAME,
    LOCAL_ARRAY_VARIABLE_NAME,
    UNKNOWN
}
