/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.testsupport;

import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants;
import org.fundacionjala.oblivion.apex.lexer.ApexLanguageHierarchy;
import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;

import static org.junit.Assert.assertEquals;
/**
 * Helper class that allow to work in unit test with token and lexers
 *
 * @author adrian_grajeda
 */
public class ApexTokenUtilities {

    public static final TokenId WHITE_SPACE_TOKENID = ApexLanguageHierarchy.getToken(ApexParserConstants.WHITESPACE);

    /**
     * Get all the tokens (except white-spaces) using the Apex language from the given text. 
     * 
     * The test should have a apex valid fragment. For
     * example: <p>      
     * {@code 
     *    public Number sum(Number a, Number b) {
     *    return a + b;
     *  }
     * }
     * </p>
     * @param text the input test to extract the tokens
     * @return a list with all the tokens
     */
    public static List<Token<ApexTokenId>> loadTokenFromText(String text) {
        Language<ApexTokenId> language = ApexTokenId.getLanguage(MimeType.APEX_CLASS_MIME_TYPE);
        TokenHierarchy<String> tokenHeirarHierarchy = TokenHierarchy.<String>create(text, language);
        
        TokenSequence<ApexTokenId> sequence = tokenHeirarHierarchy.tokenSequence(language);
        List<Token<ApexTokenId>> result = new ArrayList<>(sequence.tokenCount());
        while (sequence.moveNext()) {
            Token<ApexTokenId> token = sequence.token();
            if (WHITE_SPACE_TOKENID.equals(token.id())) {
                continue;
            }
            result.add(sequence.token());
        }
        return result;
    }

    /**
     * Verifies is the token's text is equals to the expected text.
     * 
     * @param expected the expected text
     * @param token the token to compare the text content 
     */
    public static void assertTokenByContent(String expected, Token<ApexTokenId> token) {
        assertEquals(expected, token.text().toString().trim());    
    }
}
