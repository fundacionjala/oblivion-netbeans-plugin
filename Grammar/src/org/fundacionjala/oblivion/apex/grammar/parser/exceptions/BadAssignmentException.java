/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a variable assignment is wrong.
 *
 * @author Maria Garcia
 */
public class BadAssignmentException extends ContextParseException {

    public BadAssignmentException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        currentToken = originalToken.next;
        String key = "grammar.error.variable.local";

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
