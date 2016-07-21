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
 * Tests the rules for explicit calls to constructors.
 *
 * @author Maria Garcia
 */
public class ExplicitConstructorTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/constructor/%s";
    int expectedLine;
    int expectedColumn;

    @Test
    public void thisNotTheFirstStatementInConstructor() throws FileNotFoundException, ParseException {
        expectedLine = 5;
        expectedColumn = 13;
        ApexParser parser = getParser("constructorWithThis.cls");
        runParser(parser);
        String expected = "Unexpected: \"(\" %nAt line %d, column %d";
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Test
    public void superNotTheFirstStatementInConstructor() throws FileNotFoundException, ParseException {
        expectedLine = 5;
        expectedColumn = 14;
        ApexParser parser = getParser("constructorWithSuper.cls");
        runParser(parser);
        String expected = "Unexpected: \"(\" %nAt line %d, column %d";
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Test
    public void superAndThisAsPrimaryPrefixStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("constructorWithThisAndSuperPrefix.cls");
        parser.CompilationUnit();
        String resultComment = "No error should be displayed since this call doesn't have to be the first statement";
        assertTrue(resultComment, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void superAsPrimarySuffixStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("constructorWithSuperSuffix.cls");
        runParser(parser);
        expectedLine = 3;
        expectedColumn = 12;
        String expected = "Unexpected: \".\" %nAt line %d, column %d";
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void thisAsPrimarySuffixStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("constructorWithThisSuffix.cls");
        runParser(parser);
        expectedLine = 3;
        expectedColumn = 12;
        String expected = "Unexpected: \".\" %nAt line %d, column %d";
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
