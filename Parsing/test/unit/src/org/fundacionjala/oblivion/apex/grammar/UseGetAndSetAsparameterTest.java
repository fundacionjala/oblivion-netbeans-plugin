/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar.NO_PARSER_ERRORS_ARE_EXPECTED;
import static org.junit.Assert.assertTrue;

/**
 * Verifies if get and set can use on  different scenarios.
 *
 * @author sergio_daza
 */
public class UseGetAndSetAsparameterTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/GetAndSetAsparameter/%s";

    @Test
    public void testUseGetAsAnClassMember() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("UseGetAsAnClassMember.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testUseSetAsAnClassMember() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("UseSetAsAnClassMember.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testUseGetIntoSoqlQuery() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("UseGetIntoSoqlQuery.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testUseGetOnReturn() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("UseGetOnReturn.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
