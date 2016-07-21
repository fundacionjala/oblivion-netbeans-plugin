/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.trigger;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Apex Trigger grammar. 
 * 
 * @author Marcelo Garnica
 */
public class TriggerGrammarTest extends AbstractTestGrammar {
    private final static String RESOURCE_FOLDER = "../resources/trigger/%s";
    private int expectedLine;
    private int expectedColumn;
    private String expectedToken;

    @Test
    public void triggerParsingWithoutErrors() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TriggerTest.trigger");
        parser.TriggerCompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void triggerWithContextVariables() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TriggerTestWithContextVariables.trigger");
        parser.TriggerCompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void triggerParsingWithErrors() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TriggerTestWithErrors.trigger");
        parser.TriggerCompilationUnit();
        assertFalse("Parser errors are expected", parser.getSyntaxErrors().isEmpty());
        assertTrue("There should be more than one error", parser.getSyntaxErrors().size() > 1);
    }
    
    @Test
    public void triggerParsingWithTriggerOperationError() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn  = 42;
        expectedToken = "undel";
        ApexParser parser = getParser("TriggerTestWithTriggerOperationError.trigger");
        parser.TriggerCompilationUnit();
        String expectedTemplate = "Expected trigger operation but was found: \"%s\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedToken, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertFalse("Parser errors are expected", parser.getSyntaxErrors().isEmpty());
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void triggerParsingWithTriggerTypeAfterParenthesisError() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn  = 36;
        expectedToken = "afte";
        ApexParser parser = getParser("TriggerTestWithTriggerTypeAfterParenthesisError.trigger");
        parser.TriggerCompilationUnit();
        String expectedTemplate = "Expected \"before\" or \"after\" keyword but was found: \"%s\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedToken, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertFalse("Parser errors are expected", parser.getSyntaxErrors().isEmpty());
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void triggerParsingWithTriggerTypeAfterCommaError() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn  = 49;
        expectedToken = "afte";
        ApexParser parser = getParser("TriggerTestWithTriggerTypeAfterCommaError.trigger");
        parser.TriggerCompilationUnit();
        String expectedTemplate = "Expected \"before\" or \"after\" keyword but was found: \"%s\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedToken, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertFalse("Parser errors are expected", parser.getSyntaxErrors().isEmpty());
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }
    
    @Test
    public void triggerWithInvalidContextVariables() throws FileNotFoundException, ParseException {
        expectedLine = 4;
        expectedColumn  = 12;
        expectedToken = ".";
        ApexParser parser = getParser("TriggerTestWithInvalidContextVariables.trigger");
        parser.TriggerCompilationUnit();
        String expectedTemplate = "Unexpected: \"%s\" %nAt line %d, column %d";
        String expected = String.format(expectedTemplate, expectedToken, expectedLine, expectedColumn);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        ParseException syntaxError = parser.getSyntaxErrors().get(0);
        assertFalse("Parser errors are expected", parser.getSyntaxErrors().isEmpty());
        assertEquals("Expected : "+expected, expected, syntaxError.getMessage());
        assertEquals("Expected line: " + expectedLine, expectedLine, syntaxError.currentToken.beginLine);
        assertEquals("Expected column: " + expectedColumn, expectedColumn, syntaxError.currentToken.beginColumn);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
