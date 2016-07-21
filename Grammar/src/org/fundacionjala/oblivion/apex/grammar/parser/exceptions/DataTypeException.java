/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 * Represents an exception that occurs when a data type is not specified.
 *
 * @author Maria Garcia
 */
public class DataTypeException extends ContextParseException {

    public DataTypeException(ParseException originalError) {
        super(originalError);
    }

    @Override
    protected void init() {
        currentToken = originalToken.next;
        String key = "grammar.error.type";

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
