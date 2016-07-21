/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;

/**
 * Represents an exception when a trigger is not well defined.
 *
 * @author Pablo Romero
 */
public class TriggerTypeException extends ContextParseException {
   
    private static final String AFTER = "after";
    private static final String BEFORE = "before";
    private static final String RETURN = "return";

    public TriggerTypeException(ParseException originalError) {
        super(originalError);
    }

    @Override
    public void init() {
        String key;
        currentToken = originalToken.next;
        if (originalToken.image.equalsIgnoreCase(BEFORE) || originalToken.image.equalsIgnoreCase(AFTER)) {
            key = "grammar.error.trigger.operation";
        } 
        else if(originalToken.image.equalsIgnoreCase(LEFT_PARENTHESIS) || originalToken.image.equalsIgnoreCase(COMMA)){
            key = "grammar.error.trigger.type";
        }
        else {
            key = "grammar.error.unexpected";
        }
        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
