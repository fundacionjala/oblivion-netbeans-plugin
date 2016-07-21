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
 * Verifies if declaration rules are according the scenarios described at:
 * @see:http://www.salesforce.com/us/developer/docs/apexcode/salesforce_apex_language_reference.pdf
 * @author Alvaro Torrez
 */
public class ClassGrammarTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/classTest/%s";
	private int expectedLine;
    private int expectedColumn;
    
    @Test
    public void testPublicClassWithVirtualDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicClassWithVirtualDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testPublicClassWithoutVirtualDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicClassWithoutVirtualDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty()); 
    }
    
    @Test
    public void testClassWithVirtualAndPublicDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classWithVirtualAndPublicDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty()); 
    }
    
    @Test
    public void testClassWithGlobalAndVirtualDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classWithGlobalAndVirtualDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testClassWithVirtualAndGlobalDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classWithVirtualAndGlobalDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testTwoClassesOuterInFile() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("twoClassesOuterInFile.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testNestedClasses() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("nestedClasses.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testClassWithEnum() throws FileNotFoundException, ParseException {
        expectedLine = 8;
        expectedColumn  = 1;
        ApexParser parser = getParser("classWithEnum.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"public\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testClassWithInterface() throws FileNotFoundException, ParseException {
        expectedLine = 5;
        expectedColumn  = 1;
        ApexParser parser = getParser("classWithInterface.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"public\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testThreeClassesOuterInFile() throws FileNotFoundException,ParseException {
        expectedLine = 5;
        expectedColumn  = 1;
        ApexParser parser = getParser("threeClassesOuterInFile.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"public\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void testClassWithVirtualAfterClass() throws FileNotFoundException, ParseException {
        int expectedLine = 1;
        int expectedColumn = 14;
        ApexParser parser = getParser("classWithVirtualAfterClass.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"virtual\" %nAt line %d, column %d";
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testClassWithGenericInterface() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classWithGeneircInterface.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testClassWithInvalidGenericInterface() throws FileNotFoundException, ParseException {
       
        int expectedLine = 1;
        int expectedColumn = 54;
        ApexParser parser = getParser("classWithInvalidGenericInterface.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"<>\" %nAt line %d, column %d";
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void classAttributesWithKeywordsAsIdentifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classAttributesWithIdentifiersAsKeywords.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }
   
    @Test
    public void classMultipleModifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("classMultipleModifiers.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }
   
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
