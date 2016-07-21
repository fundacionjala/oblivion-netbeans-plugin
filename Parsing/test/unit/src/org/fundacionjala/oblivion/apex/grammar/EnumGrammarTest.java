/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the rules for enum types.
 *
 * @author Maria Garcia
 */
public class EnumGrammarTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/enumTests/%s";
    int expectedLine;
    int expectedColumn;

    @Test
    public void enumWithEmptyBody() throws FileNotFoundException, ParseException {
        expectedLine = 1;
        expectedColumn = 23;
        ApexParser parser = getParser("enumWithEmptyBody.cls");
        runParser(parser);
        String expectedTemplate = "Enum body cannot be empty. Expected at least one element but was found \"}\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals(expected, syntaxError.getMessage());
        assertEquals(expectedLine, syntaxError.currentToken.beginLine);
        assertEquals(expectedColumn, syntaxError.currentToken.beginColumn);
    }

    @Test
    public void enumWithWrongBodyDefinition() throws FileNotFoundException, ParseException {
        expectedLine = 1;
        expectedColumn = 24;
        ApexParser parser = getParser("enumWithWrongBodyDefinition.cls");
        runParser(parser);
        String expected = String.format("Unexpected character \",\" %nAt line %d, column %d", expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(expected, parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Test
    public void enumWithoutName() throws FileNotFoundException, ParseException {
        expectedLine = 1;
        expectedColumn = 13;
        ApexParser parser = getParser("enumWithoutName.cls");
        runParser(parser);
        String expected = String.format("Expected enum identifier but was found: \"{\" %nAt line %d, column %d", expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(expected, parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void enumWithKeywordsAsIdentifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("enumWithKeywordsAsIdentifiers.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
