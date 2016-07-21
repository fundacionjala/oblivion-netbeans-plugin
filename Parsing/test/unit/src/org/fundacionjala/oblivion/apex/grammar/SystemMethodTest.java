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
 * Verifies if declaration rules are according the scenarios
 * 
 * @author sergio_daza
 */
public class SystemMethodTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/SystemMethod/%s";
    
    @Test
    public void testSystemMethodWithBlock() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SystemMethodWithBlock.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testSystemMethodWithBlockAndStatements() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SystemMethodWithBlockAndStatements.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testAssignValueToTheSystemAttribute() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("AssignValueToTheSystemAttribute.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testSystemIntoVariableDeclaration() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SystemIntoVariableDeclaration.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}

