/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 * A Set of all possible modifiers of Apex language.
 *
 * TODO: convert to enum
 * @author Adrian Grajeda
 */
public class ModifierSet {

    /* Definitions of the bits in the modifiers field.  */
    public static final int GLOBAL = 0x0000;
    public static final int PUBLIC = 0x0001;
    public static final int PROTECTED = 0x0002;
    public static final int PRIVATE = 0x0004;
    public static final int ABSTRACT = 0x0008;
    public static final int STATIC = 0x0010;
    public static final int FINAL = 0x0020;
    public static final int TRANSIENT = 0x0040;

    /**
     * A set of accessors that indicate whether the specified modifier is in the set.
     */
    public boolean isPublic(int modifiers) {
        return (modifiers & PUBLIC) != 0;
    }

    public boolean isProtected(int modifiers) {
        return (modifiers & PROTECTED) != 0;
    }

    public boolean isPrivate(int modifiers) {
        return (modifiers & PRIVATE) != 0;
    }

    public boolean isStatic(int modifiers) {
        return (modifiers & STATIC) != 0;
    }

    public boolean isAbstract(int modifiers) {
        return (modifiers & ABSTRACT) != 0;
    }

    public boolean isFinal(int modifiers) {
        return (modifiers & FINAL) != 0;
    }

    public boolean isTransient(int modifiers) {
        return (modifiers & TRANSIENT) != 0;
    }

    /**
     * Removes the given modifier.
     */
    static int removeModifier(int modifiers, int mod) {
        return modifiers & ~mod;
    }

    public static int getValueOf(Token modifierKeyword) {
        if ( modifierKeyword.kind == ApexParserConstants.PUBLIC_KEYWORD) {
            return PUBLIC;
        } else if (modifierKeyword.kind == ApexParserConstants.PROTECTED_KEYWORD) {
            return PROTECTED;
        } else if (modifierKeyword.kind == ApexParserConstants.PRIVATE_KEYWORD) {
            return PRIVATE;
        } else if (modifierKeyword.kind == ApexParserConstants.ABSTRACT_KEYWORD) {
            return ABSTRACT;
        } else if (modifierKeyword.kind == ApexParserConstants.STATIC_KEYWORD) {
            return STATIC;
        } else if (modifierKeyword.kind == ApexParserConstants.FINAL_KEYWORD) {
            return FINAL;
        } else if (modifierKeyword.kind == ApexParserConstants.TRANSIENT_KEYWORD) {
            return TRANSIENT;
        } else {
            return -1;
        }
    }
}
