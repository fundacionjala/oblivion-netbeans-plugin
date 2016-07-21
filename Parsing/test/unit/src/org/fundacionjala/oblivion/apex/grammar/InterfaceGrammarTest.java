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
public class InterfaceGrammarTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/interfaceTest/%s";

    @Test
    public void testPublicInterfaceWithVirtualDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicInterfaceWithVirtualDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testPublicInterfaceWithoutVirtualDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicInterfaceWithoutVirtualDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty()); 
    }
    
    @Test
    public void testInterfaceWithVirtualAndPublicDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("interfaceWithVirtualAndPublicDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty()); 
    }
    
    @Test
    public void testInterfaceWithVirtualAfterInterface() throws FileNotFoundException, ParseException {
        int expectedLine = 1;
        int expectedColumn = 18;
        ApexParser parser = getParser("interfaceWithVirtualAfterInterface.cls");
        runParser(parser);
        String expectedTemplate = "Unexpected: \"virtual\" %nAt line %d, column %d";
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
