/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test the assignment of a value to a variable
 *
 * @author Amir Aranibar
 */
public class AssignmentTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/assignment/%s";
    private final String RESULT_COMMENT = "No parser errors are expected";

    @Test
    public void assignThisKeyword() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("assignThisKeyword.cls");
        parser.CompilationUnit();
        assertTrue(RESULT_COMMENT, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void assignVariable() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("assignVariable.cls");
        parser.CompilationUnit();
        assertTrue(RESULT_COMMENT, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void assignLiteral() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("assignLiteral.cls");
        parser.CompilationUnit();
        assertTrue(RESULT_COMMENT, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void assignExpressionResult() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("assignExpressionResult.cls");
        parser.CompilationUnit();
        assertTrue(RESULT_COMMENT, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
