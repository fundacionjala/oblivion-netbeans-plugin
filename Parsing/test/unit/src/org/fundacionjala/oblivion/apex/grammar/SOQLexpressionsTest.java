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
 */
public class SOQLexpressionsTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/SOQLExpressionsTests/%s";
    private int expectedColumn;
    private int expectedLine;
    
    @Test
    public void testBasicSOQLAssignments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("basicSOQLAssignment.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithDotSeparatorAtSelectArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithDotSeparatorAtSelectArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithNestedQueryAtSelectArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLwithWithNestedQueryAtSelectArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithTwoLevelsOfNestedQueryAtSelectArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithTwoLevelsOfNestedQueryAtSelectArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithAliasAtFromStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithAliasAtFromStatement.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithWhereStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithWhereStatement.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithLiteralParameterAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithLiteralParameterAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithParenSeparatorAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithParenSeparatorAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithComparatorOperatorsAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithComparatorOperatorsAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithLikeOperatorAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithLikeOperatorAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithOperatorsAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithOperatorsAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithBooleanAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithBooleanAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithNullAtWhereArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithNullAtWhereArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithOperatorsAtWhereArgumentsAndOr() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithOperatorsAtWhereArgumentsAndOr.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithOrderByStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithOrderByStatement.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithManyArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithManyArguments.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithOffsetStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithOffsetStatement.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithLimitStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithLimitStatement.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithLimitWithValiableAsArgument() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithLimitWithValiableAsArgument.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithOffsetWithValiableAsArgument() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithOffsetWithValiableAsArgument.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithDataCategoryAndAbove() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithDataCategoryAndAbove.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithDataCategoryAndOtherSpecifications() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithDataCategoryAndOtherSpecifications.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLMapInitialization() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLMapInitialization.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLSetExpressionsAtWhere() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLSeExpressionsAtWhere.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLInAForEach() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLInAForEach.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testSOQLExpressionWITHWithSimpleExpression() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLExpressionWITHWithSimpleExpression.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithLimitWithoutArgument() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithLimitWithoutArgument.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLMissingCommaSeparator() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLMissingCommaSeparator.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithoutFromStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithoutFromStatement.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithoutSelectArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithoutSelectArguments.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithoutFromArguments() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithoutFromArguments.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithoutArgumentsAtOrderByStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithoutArgumentsAtOrderByStatement.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithoutArgumentsAtLimitStatement() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithoutArgumentsAtLimitStatement.cls");
        runParser(parser);
        assertFalse(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLAliasInSoqlFunctions() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLAliasInSoqlFunctions.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testSOQLNestedQueries() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLNestedQueries.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLVariablesAsPartOfClauseIn() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLVariablesAsPartOfClauseIn.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLAliasInSoqlFunctionsForSelectNested() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLAliasInSoqlFunctionsForSelectNested.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testSOQLMemberAccessInWhere() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLMemberAccessInWhere.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLVariablesAsPartOfClauseNotIn() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLVariablesAsPartOfClauseNotIn.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLMethodAccessInWhere() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLMethodAccessInWhere.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testSOQLUseCastInSoqlQuery() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLUseCastInSoqlQuery.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testMisuseCastInSoqlQuery() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLMisuseCastInSoqlQuery.cls");
        runParser(parser);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format("Unexpected: \".\" %nAt line 4, column 56"), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(4, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(56, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
        assertEquals(String.format("Unexpected: \"method\" %nAt line 4, column 61"), parser.getSyntaxErrors().get(1).getMessage());
        assertEquals(4, parser.getSyntaxErrors().get(1).currentToken.beginLine);
        assertEquals(61, parser.getSyntaxErrors().get(1).currentToken.beginColumn);
        assertEquals(String.format("Unexpected: \"]\" %nAt line 4, column 79"), parser.getSyntaxErrors().get(2).getMessage());
        assertEquals(4, parser.getSyntaxErrors().get(2).currentToken.beginLine);
        assertEquals(79, parser.getSyntaxErrors().get(2).currentToken.beginColumn);
    }    
        
    @Test
    public void testSOQLUseVariablesAfterLikeKeyword() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLUseVariablesAfterLikeKeyword.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testSOQLUseVariablesAfterLimitKeyword() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLUseVariablesAfterLimitKeyword.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testSOQLUseVariablesAfterOffsetKeyword() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLUseVariablesAfterOffsetKeyword.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testSOQLUseVariablesAfterLikeLimitOffsetKeywords() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLUseVariablesAfterLikeLimitOffsetKeywords.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithGroupByStatementWithIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithGroupByStatementWithIdentifier.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLWithGroupByStatementWithMethodInvocation() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLWithGroupByStatementWithMethodInvocation.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testSOQLDateLiteralExpression() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLDateLiteralExpression.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Test
    public void testSOQLColonMandatoryForVariable() throws FileNotFoundException, ParseException {
        expectedColumn = 79;
        expectedLine = 2;
        ApexParser parser = getParser("SOQLColonMandatoryForVariable.cls");
        runParser(parser);
        String expected = String.format("Expected expression after \"=\" but was found \"field2\" %nAt line %d, column %d", expectedLine, expectedColumn);
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(expected, parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }
    
    @Test
    public void testSOQLDateOnly() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("SOQLDateOnly.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }    
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
