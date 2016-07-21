/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a for statement is not well specified.
 *
 * @author Maria Garcia
 */
public class IllegalForStatementException extends ContextParseException {

    public IllegalForStatementException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key = "grammar.error.statement.for";
        currentToken = originalToken.next;

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
