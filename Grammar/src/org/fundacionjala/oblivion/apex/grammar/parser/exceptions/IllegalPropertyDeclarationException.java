/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a property is not well specified.
 *
 * @author Maria Garcia
 */
public class IllegalPropertyDeclarationException extends ContextParseException {

    private static final String GET = "get";
    private static final String SET = "set";

    public IllegalPropertyDeclarationException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        currentToken = originalToken.next;

        if ((originalToken.image.equalsIgnoreCase(GET) || originalToken.image.equalsIgnoreCase(SET))
            && currentToken.image.equals(RIGHT_BRACE)) {
            key = "grammar.error.property.accesor";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
