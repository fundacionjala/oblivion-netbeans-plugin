/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.grammar.controlFlowStatements;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test rules of If statement.
 *
 * @author Maria Garcia
 */
public class IfStatementTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "../resources/controlFlowStatements/%s";

    @Test
    public void ifWithoutCondition() throws FileNotFoundException, ParseException {
        int expectedLine = 3;
        int expectedColumn = 12;
        ApexParser parser = getParser("ifStatement.cls");
        runParser(parser);
        String expected = "Expected boolean expression. Found: \"public\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testIfStatementWithSoqlQueries() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IfStatementWithSoqlQueries.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testIfStatementWithSoqlQueriesAndOperatorOr() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IfStatementWithSoqlQueriesAndOperatorOr.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testIfStatementWithSoqlQueriesAndOperatorAnd() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IfStatementWithSoqlQueriesAndOperatorAnd.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testIfStatementWithSoqlQueriesWithDifferentOperators() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("IfStatementWithSoqlQueriesWithDifferentOperators.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
