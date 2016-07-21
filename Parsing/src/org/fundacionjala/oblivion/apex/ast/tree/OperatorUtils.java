/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree.Kind;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants;

/**
 * Utility method to handle and parse operators
 *
 * @author Adrian Grajeda
 */
class OperatorUtils {

    static Kind parseTokenToKind(Token token) {
        switch (token.getId()) {
            case ApexParserConstants.ASSIGN_OPERATOR:
                return Kind.ASSIGNMENT;
            case ApexParserConstants.STARASSIGN_OPERATOR:
                return Kind.MULTIPLY_ASSIGNMENT;
            case ApexParserConstants.SLASH_OPERATOR:
                return Kind.DIVIDE_ASSIGNMENT;
            case ApexParserConstants.MINUSASSIGN_OPERATOR:
                return Kind.MINUS_ASSIGNMENT;
            case ApexParserConstants.PLUSASSIGN_OPERATOR:
                return Kind.PLUS_ASSIGNMENT;
            case ApexParserConstants.REMASSIGN_OPERATOR:
                return Kind.REMAINDER_ASSIGNMENT;
            case ApexParserConstants.LSHIFTASSIGN_OPERATOR:
                return Kind.LEFT_SHIFT_ASSIGNMENT;
            case ApexParserConstants.RSIGNEDSHIFTASSIGN_OPERATOR:
                return Kind.RIGHT_SHIFT_ASSIGNMENT;
            case ApexParserConstants.RUNSIGNEDSHIFTASSIGN_OPERATOR:
                return Kind.UNSIGNED_RIGHT_SHIFT_ASSIGNMENT;
            case ApexParserConstants.ANDASSIGN_OPERATOR:
                return Kind.AND_ASSIGNMENT;
            case ApexParserConstants.ORASSIGN_OPERATOR:
                return Kind.OR_ASSIGNMENT;
            case ApexParserConstants.XORASSIGN_OPERATOR:
                return Kind.XOR_ASSIGNMENT;
            default:
                throw new IllegalArgumentException(String.format("Unexpected assign operator [%s]", token.getImage()));
        }
    }

}
