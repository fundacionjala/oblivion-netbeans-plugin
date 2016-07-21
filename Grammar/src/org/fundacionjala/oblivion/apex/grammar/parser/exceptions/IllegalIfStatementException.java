/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when an if-then-else statement is not well specified.
 *
 * @author Maria Garcia
 */
public class IllegalIfStatementException extends ContextParseException {

    private static final String IF = "if";

    public IllegalIfStatementException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        currentToken = originalToken.next;

        if (originalToken.image.equalsIgnoreCase(IF)) {
            key = "grammar.error.statement.definition";
            message = String.format(bundle.getString(key), IF, currentToken.image, currentToken.beginLine, currentToken.beginColumn);
        } else if (originalToken.image.equals(RIGHT_BRACE) || originalToken.image.equals(LEFT_PARENTHESIS)
                   || currentToken.image.equals(LEFT_PARENTHESIS)) {
            key = "grammar.error.statement.condition";
            message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
        } else {
            key = "grammar.error.unexpected";
            message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
        }
    }
}
