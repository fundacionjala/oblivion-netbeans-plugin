/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Exception that occurs when specifying parameters.
 *
 * @author Maria Garcia
 */
public class BadParameterException extends ContextParseException {

    public BadParameterException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        currentToken = originalToken.next;
        
        if (originalToken.image.equals(COMMA)
            && (currentToken.image.equals(RIGHT_PARENTHESIS) || currentToken.image.equals(RIGHT_BRACE))) {
            key = "grammar.error.unexpected.char";
        } else if (currentToken.image.equals(COMMA) || currentToken.image.equals(RIGHT_PARENTHESIS)) {
            key = "grammar.error.parameter.identifier";
        } else if (!currentToken.image.equals(RIGHT_PARENTHESIS)) {
            key = "grammar.error.method";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }

}
