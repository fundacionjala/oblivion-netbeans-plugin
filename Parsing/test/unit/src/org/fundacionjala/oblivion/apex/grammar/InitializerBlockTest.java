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
 * @author Nelson Alcocer
 * @author Sergio Daza
 */
public class InitializerBlockTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/initializerBlock/%s";
    private int expectedColumn;
    private int expectedLine;
    
    @Test
    public void testInitializerBlockEmpty() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockEmpty.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockStatic() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockStatic.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    } 
    
    @Test
    public void testInitializerBlockNested() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockNested.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedClass() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedClass.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedClassPosition() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedClass.cls");
        runParser(parser);
        String expected = "Unexpected: \"public\" %nAt line 3, column 9";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void testInitializerBlockWithNestedMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedMethod.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedMethodPosition() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedClass.cls");
        runParser(parser);
        String expected = "Unexpected: \"public\" %nAt line 3, column 9";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void testInitializerBlockWithNestedEnum() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedEnum.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedEnumPosition() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedEnum.cls");
        runParser(parser);
        String expected = "Unexpected: \"enum\" %nAt line 3, column 9";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void testInitializerBlockWithNestedStatements() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedEnum.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    public void testInitializerBlockWithNestedStatementsPosition() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedEnum.cls");
        runParser(parser);
        String expected = "Unexpected: \"static\" %nAt line 8, column 9";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void testInitializerBlockWithNestedInitialization() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedInitialization.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedConditionalAndLoop() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedInitialization.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedProperty() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedProperty.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testInitializerBlockWithNestedPropertyPosition() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithNestedProperty.cls");
        runParser(parser);
        String expected = "Unexpected: \"integer\" %nAt line 3, column 9";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void testInitializerBlockWithPublicModifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InitializerBlockWithPublicModifier.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
           
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
