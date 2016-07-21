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
 * Test call methods with and without arguments
 *
 * @author Amir Aranibar
 */
public class CallMethodTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/method/%s";

    @Test
    public void callMethodWithArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callMethodWithArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void callMethodWithoutArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callMethodWithoutArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void callMethodWithAllowedKeywordAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callMethodWithAllowedKeywordsAsIdentifier.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void callMethodWithForbiddenKeywordAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callMethodWithForbiddenKeywordsAsIdentifier.cls");
        parser.CompilationUnit();
        assertFalse("There should be an error", parser.getSyntaxErrors().isEmpty());
    }   

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
