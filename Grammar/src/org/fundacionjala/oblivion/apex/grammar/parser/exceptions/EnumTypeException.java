/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when an enum is bad defined.
 *
 * @author Maria Garcia
 */
public class EnumTypeException extends ContextParseException{

    private static final String ENUM = "enum";

    public EnumTypeException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        String key;
        currentToken = originalToken.next;

        if (originalToken.image.equals(LEFT_BRACE) && currentToken.image.equals(RIGHT_BRACE)) {
            key = "grammar.error.enum.body";
        } else if (currentToken.image.equals(COMMA)){
            key = "grammar.error.unexpected.char";
        } else if (originalToken.image.equalsIgnoreCase(ENUM)){
            key = "grammar.error.enum.name";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}

