/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 * Represents an exception when a variable declaration is wrong.
 *
 * @author Maria Garcia
 */
public class IllegalVariableDeclarationException extends ContextParseException {

    private static final String ASSIGN = "=";

    public IllegalVariableDeclarationException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key = null;
        currentToken = originalToken.next;

        if (originalToken.image.equals(ASSIGN) ) {
            key = "grammar.error.expression";
        } else {
            key = "grammar.error.unexpected.char";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
