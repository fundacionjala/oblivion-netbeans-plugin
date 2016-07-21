/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a try-catch statement is not well specified.
 *
 * @author Maria Garcia
 */
public class IllegalTryCatchStatementException extends ContextParseException {

    private static final String CATCH = "catch";

    public IllegalTryCatchStatementException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        currentToken = originalToken.next;

        if (originalToken.image.equals(RIGHT_BRACE)){
            key = "grammar.error.statement.try";
        } else if (originalToken.image.equalsIgnoreCase(CATCH) || originalToken.image.equals(LEFT_PARENTHESIS)) {
            key = "grammar.error.statement.catch";
        } else if (currentToken.image.equals(RIGHT_PARENTHESIS)) {
            key = "grammar.error.statement.catch.condition";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
