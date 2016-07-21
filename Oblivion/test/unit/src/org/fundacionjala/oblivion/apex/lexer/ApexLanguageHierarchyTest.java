/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.lexer;

import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import java.util.List;
import org.junit.Test;
import org.netbeans.api.lexer.Token;

import static org.fundacionjala.oblivion.apex.testsupport.ApexTokenUtilities.*;
import static org.junit.Assert.assertTrue;


/**
 * Test the Apex lexer tokens from a given text
 * 
 * @author adrian_grajeda
 */
public class ApexLanguageHierarchyTest {
    
    @Test
    public void testTokenFromClass() {
        String text = "public class A implements Serializable {}";
        String[] expectedTokens = {"public", "class", "A", "implements", "Serializable"};
        
        List<Token<ApexTokenId>> tokens = loadTokenFromText(text);
        
        for (int i = 0; i < expectedTokens.length; i++) {
            assertTokenByContent(expectedTokens[i], tokens.get(i));
        }
    }

    @Test
    public void testTokensFromMethodDeclaration() {
        
        String text = "public void method(){}";
        List<Token<ApexTokenId>> tokens = loadTokenFromText(text);
        
        assertTokenByContent("public", tokens.get(0));
        assertTokenByContent("void", tokens.get(1));
        assertTokenByContent("method", tokens.get(2));
    }
    
    @Test
    public void testTokenFromStatement() {
        String text = "integer a = 5;";
        String[] expectedTokens = {"integer", "a", "=", "5", ";"};
        
        List<Token<ApexTokenId>> tokens = loadTokenFromText(text);
        
        for (int i = 0; i < expectedTokens.length; i++) {
            assertTokenByContent(expectedTokens[i], tokens.get(i));
        }
    }
    
    @Test
    public void testTokenFromStatementWithSpaces() {
        String text = "int a =           5;";
        String[] expectedTokens = {"int", "a", "=", "5", ";"};
        
        List<Token<ApexTokenId>> tokens = loadTokenFromText(text);
        
        for (int i = 0; i < expectedTokens.length; i++) {
            assertTokenByContent(expectedTokens[i], tokens.get(i));
        }
    }
    
    @Test
    public void testTokenFromEmptyText() {
        String text = "";
        List<Token<ApexTokenId>> tokens = loadTokenFromText(text);
        assertTrue(tokens.isEmpty());
    }
    
    @Test
    public void testTokenForMultilineComment() {
        String code = "/* "
            + "* a multi line comment"
            + "*/"
            + "public class Dummy{}";
        
        String expectedCommentToken = "/* "
            + "* a multi line comment"
            + "*/";
        String expectedClassToken[] = {"public", "class", "Dummy", "{", "}"};
        
        List<Token<ApexTokenId>> tokens = loadTokenFromText(code);
        assertTokenByContent(expectedCommentToken, tokens.get(0));
        assertTokenByContent(expectedClassToken[0], tokens.get(1));
        assertTokenByContent(expectedClassToken[1], tokens.get(2));
        assertTokenByContent(expectedClassToken[2], tokens.get(3));
        assertTokenByContent(expectedClassToken[3], tokens.get(4));
        assertTokenByContent(expectedClassToken[4], tokens.get(5));
    }
    
    @Test
    public void testTokenForMultilineCommentWithoutEnd() {
        String code = "/* "
            + ""
            + "public class Dummy{}";
        List<Token<ApexTokenId>> tokens = loadTokenFromText(code);
        assertTokenByContent(code, tokens.get(0));
    }
}
