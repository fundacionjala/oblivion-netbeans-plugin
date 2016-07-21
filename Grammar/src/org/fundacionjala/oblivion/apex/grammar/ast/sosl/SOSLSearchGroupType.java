/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

/**
 * Represents the possible search groups in a SOSL IN clause 
 * @author Pablo Romero
 */
public enum SOSLSearchGroupType {
    ALL_FIELDS,
    EMAIL_FIELDS,
    NAME_FIELDS,
    PHONE_FIELDS,
    SIDEBAR_FIELDS
}
