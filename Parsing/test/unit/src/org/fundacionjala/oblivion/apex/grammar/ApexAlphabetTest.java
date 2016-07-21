/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Verify the alphabet of Apex on the expressions.
 * 
 * @author sergio_daza
 */
public class ApexAlphabetTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/ApexAlphabet/%s";
    private int expectedColumn;
    private int expectedLine;
    
    @Test
    public void testIdentifierName() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IdentifierName.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDoubleQuotes() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 22;
        ApexParser parser = getParser("DoubleQuotes.cls");
        runParser(parser);
        String expectedTemplate = "Wrong local variable declaration. Found: \"\"\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testCapitalLetterNWithTilde() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 16;
        ApexParser parser = getParser("CapitalLetterNWithTilde.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"Ñ\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testSmallLetterNWithTilde() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 16;
        ApexParser parser = getParser("SmallLetterNWithTilde.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"ñ\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testDollarSign() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 16;
        ApexParser parser = getParser("DollarSign.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"$\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testIdenfifierBeginWithUnderscore() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 16;
        ApexParser parser = getParser("IdenfifierBeginWithUnderscore.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"_\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
