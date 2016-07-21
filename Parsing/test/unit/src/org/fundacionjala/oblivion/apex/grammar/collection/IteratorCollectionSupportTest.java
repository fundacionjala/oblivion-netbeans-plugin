/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.collection;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Verifies that the Iterator grammar 
 * 
 * @author sergio_daza
 */
public class IteratorCollectionSupportTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/%s";
    
    @Test
    public void testBasicSOQLAssignments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IteratorWithGenericType.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testImplementsIterator() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ImplementsIterator.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testUsingIterator() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("UsingIterator.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}
