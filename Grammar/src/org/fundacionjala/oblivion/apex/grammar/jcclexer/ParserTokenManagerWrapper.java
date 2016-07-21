/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.jcclexer;

/**
 * Wrapper class that handle the error thrown by {@link ApexParserTokenManager} class when a multi line comment is
 * incomplete.
 *
 * @author Adrian Grajeda
 */
public class ParserTokenManagerWrapper extends ApexParserTokenManager {

    private static final int COMMENT_TOKEN_ID = ApexParserConstants.MULTI_LINE_COMMENT;

    private static final int DEFAULT_LEXER_STATE = 0;

    /**
     * Creates a new instance of this class
     *
     * @param stream the stream to read the chars
     */
    public ParserTokenManagerWrapper(CharStream stream) {
        super(stream);
    }

    @Override
    public Token getNextToken() {
        try {
            return super.getNextToken();
        } catch (Error lexerError) {
            jjmatchedKind = COMMENT_TOKEN_ID;
            Token matchedToken = jjFillToken();
            curLexState = DEFAULT_LEXER_STATE;
            return matchedToken;
        }
    }
}
