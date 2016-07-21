/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import static org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier.*;
import static org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType.*;

/**
 * The options to display in the editor and reformat the left braces.
 *
 * @author Amir Aranibar
 */
public class OrderingValueOptions {

    public static final String VISIBLE_MEMBERS = "true";
    public static final String CHECK_SORT_MEMBERS_IN_GROUPS_ALPHABETICALLY = "false";
    public static final String VISIBILITY_LIST = String.format(
            "%s,%s,%s,%s,%s",
            GLOBAL,
            PUBLIC,
            PRIVATE,
            PROTECTED,
            DEFAULT
    );
    public static final String MEMBERS_SORT_ORDER_LIST = String.format(
            "%s,%s,%s,%s,%s,%s,%s,%s,%s",
            STATIC_FIELD,
            STATIC_INITIALIZER,
            STATIC_METHOD,
            FIELD,
            INSTANCE_INITIALIZER,
            CONSTRUCTOR,
            METHOD,
            STATIC_CLASSE,
            CLASS
    );
}
