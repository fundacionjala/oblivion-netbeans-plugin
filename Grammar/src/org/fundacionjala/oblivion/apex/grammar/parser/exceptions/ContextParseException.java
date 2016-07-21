/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 * Represents a generic exception from which the others will derived.
 *
 * @author Maria Garcia
 */
public class ContextParseException extends ParseException {

    private static final long serialVersionUID = 0l;
    private static final String BUNDLE_MESSAGES = "org.fundacionjala.oblivion.apex.grammar.parser.exceptions.ExceptionMessagesBundle";
    
    protected static final String COMMA = ",";
    protected static final String RIGHT_BRACE = "}";
    protected static final String RIGHT_PARENTHESIS = ")";
    protected static final String LEFT_BRACE = "{";
    protected static final String LEFT_PARENTHESIS = "(";
    protected final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_MESSAGES);
    protected String message;
    protected ParseException originalError;
    protected Token originalToken;

    public ContextParseException(ParseException originalError) {
        this.originalError = originalError;
        originalToken = originalError.currentToken;
        init();
    }

    public ParseException getOriginalError() {
        return originalError;
    }

    /**
     * Constructs a new error message with the specified detail message.
     *
     * @return String that contains the error message customized.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Constructs a message by evaluating the context rule's tokens
     *
     * @return String that contains the customized message
     */
    protected void init() {
        currentToken = originalError.currentToken.next;
        String key = "grammar.error.unexpected";

        message = String.format(bundle.getString(key), currentToken.image, currentToken.beginLine, currentToken.beginColumn);
    }
}
