/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a class is not well defined.
 *
 * @author Maria Garcia
 */
public class ClassTypeException extends ContextParseException {

    private static final String WITH = "with";
    private static final String WITHOUT = "without";
    private static final String SHARING = "sharing";

    public ClassTypeException(ParseException originalError) {
        super(originalError);
    }

    @Override
    public void init() {
        String key;
        currentToken = originalToken.next;

        if (originalToken.image.equalsIgnoreCase(WITH) || originalToken.image.equalsIgnoreCase(WITHOUT)) {
            key = "grammar.error.class.sharing";
        } else if (originalToken.image.equalsIgnoreCase(SHARING)){
            key = "grammar.error.class";
        } else {
            key = "grammar.error.unexpected";
        }

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
