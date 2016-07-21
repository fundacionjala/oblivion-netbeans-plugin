/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.lexer;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexLexerCharStream;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexLexerTokenManager;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.LexerTokenManagerWrapper;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 * Reads input characters and groups them into tokens.
 *
 * The base implementation of the class follows the reference
 * @see https://platform.netbeans.org/tutorials/nbm-javacc-lexer.html
 *
 * @author maria_garcia
 */
public class ApexLexer implements Lexer<ApexTokenId> {

    private final LexerRestartInfo<ApexTokenId> info;
    private final ApexLexerTokenManager apexParserTokenManager;

    /**
     * Constructor
     * @param info The information required for the lexer
     */
    public ApexLexer(LexerRestartInfo<ApexTokenId> info) {
        this.info = info;
        ApexLexerCharStream stream = new ApexLexerCharStream(info.input());
        apexParserTokenManager = new LexerTokenManagerWrapper(stream);
    }

    /**
     * Return a token based on characters of the input and possibly additional input properties
     * @return A token object
     */
    @Override
    public org.netbeans.api.lexer.Token<ApexTokenId> nextToken() {
        Token token = apexParserTokenManager.getNextToken();
        if (info.input().readLength() < 1) {
            return null;
        }
        return info.tokenFactory().createToken(ApexLanguageHierarchy.getToken(token.kind));
    }

    /**
     * It is called by lexer's infrastructure to return present lexer's state
     * once the lexer has recognized and returned a token.
     * @return Null because the lexer is in a default state
     */
    @Override
    public Object state() {
        return null;
    }

    /**
     * Infrastructure calls this method when it no longer needs this lexer for lexing so it becomes unused
     */
    @Override
    public void release() {
    }
}
