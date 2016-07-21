/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a while statement is not well specified.
 *
 * @author Maria Garcia
 */
public class IllegalWhileStatementException extends ContextParseException {

    private static final String WHILE = "while";

    public IllegalWhileStatementException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        if (originalToken.image.equalsIgnoreCase(WHILE)) {
            key = "grammar.error.statement.definition";
            currentToken = originalToken.next;
            message = String.format(bundle.getString(key), WHILE, currentToken.image, currentToken.beginLine, currentToken.beginColumn);
        } else {
            key = "grammar.error.statement.condition";
            currentToken = originalToken;
            message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
        }
    }
}
