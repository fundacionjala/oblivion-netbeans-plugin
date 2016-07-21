/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar.WRONG_METHOD_DECLARATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test rules for method declarations.
 *
 * @author Maria Garcia
 */
public class MethodDeclarationGrammarTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/method/%s";
    int expectedLine;
    int expectedColumn;

    @Test
    public void methodWithWrongModifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("badModifierAccess.cls");
        runParser(parser);
        expectedLine = 2;
        expectedColumn = 13;
        String expected = WRONG_METHOD_DECLARATION + " \"void\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Test
    public void methodWithoutBody() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 1;
        ApexParser parser = getParser("methodWithoutBody.cls");
        runParser(parser);
        String expected = WRONG_METHOD_DECLARATION + " \"}\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Test
    public void methodWithBracketsInSignature() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn = 23;
        ApexParser parser = getParser("badSignatureWithBrackets.cls");
        runParser(parser);
        String expected = WRONG_METHOD_DECLARATION + " \"[\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void methodReturnsThis() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodReturnsThis.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void methodReturnsExpression() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodReturnsExpression.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodOverride() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodOverride.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithOverrideAfterType() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn = 17;
        ApexParser parser = getParser("methodWithOverrideAfterType.cls");
        runParser(parser);
        String expectedTemplate = WRONG_METHOD_DECLARATION + " \"override\" %nAt line %d, column %d";
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testMethodWithOverrideBeforeModifiers() throws FileNotFoundException, ParseException {        
        ApexParser parser = getParser("methodWithOverrideBeforeModifiers.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodVirtual() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodVirtual.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithVirtualAfterType() throws FileNotFoundException, ParseException {
        expectedLine = 2;
        expectedColumn = 17;
        ApexParser parser = getParser("methodWithVirtualAfterType.cls");
        runParser(parser);
        String expectedTemplate = WRONG_METHOD_DECLARATION + " \"virtual\" %nAt line %d, column %d";
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
        String expected = String.format(expectedTemplate, expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testMethodWithVirtualBeforeModifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodWithVirtualBeforeModifiers.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithAllowedKeywordAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodWithAllowedKeywordsAsIdentifier.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithForbiddenKeywordAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("methodWithForbiddenKeywordsAsIdentifier.cls");
        parser.CompilationUnit();
        assertFalse("There should be an error", parser.getSyntaxErrors().isEmpty());
    }   

    @Test
    public void testMethodWithFirstOrLastAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("firstLastIdentifier.cls");
        parser.CompilationUnit();
        assertTrue("There should'nt be errors", parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void localVariablesWithKeywordsAsIdentifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("localVariablesWithIdentifiersAsKeywords.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithFinalVariableInBody() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("MethodWithFinalVariableInBody.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testMethodWithOtherModifierOfVariableInBody() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("MethodWithOtherModifierOfVariableInBody.cls");
        runParser(parser);
        expectedLine = 3;
        expectedColumn = 9;
        String expected = WRONG_METHOD_DECLARATION + " \"static\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testErroneousExpression() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 18;
        ApexParser parser = getParser("ErroneousExpression.cls");
        runParser(parser);
        String expected = String.format("Unexpected: \";\" %nAt line %d, column %d", expectedLine, expectedColumn);
        String resulMessage = parser.getSyntaxErrors().get(0).getMessage();
        assertEquals(expected, resulMessage);
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testErroneousExpressionWithSubType() throws FileNotFoundException, ParseException {
        expectedLine = 3;
        expectedColumn = 25;
        ApexParser parser = getParser("ErroneousExpressionWithSubType.cls");
        runParser(parser);
        String expected = String.format("Unexpected: \";\" %nAt line %d, column %d", expectedLine, expectedColumn);
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
