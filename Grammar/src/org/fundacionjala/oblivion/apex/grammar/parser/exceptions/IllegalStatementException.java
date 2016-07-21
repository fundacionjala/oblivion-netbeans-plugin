/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when an statement is not properly specified.
 *
 * @author Maria Garcia
 */
public class IllegalStatementException extends ContextParseException {

    private static final String BREAK = "break";
    private static final String RETURN = "return";
    private static final String CONTINUE = "continue";

    public IllegalStatementException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key = null;
        currentToken = originalToken.next;

        if (originalToken.image.equalsIgnoreCase(BREAK) || currentToken.image.equalsIgnoreCase(CONTINUE)) {
            key = "grammar.error.separator.semicolon";
        } else if (originalToken.image.equalsIgnoreCase(RETURN)) {
            key = "grammar.error.statement.return";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
