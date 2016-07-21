/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import static org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar.NO_PARSER_ERRORS_ARE_EXPECTED;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sergio_daza
 */
public class SpecialKeywordAsIdentifierTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/specialKeywordAsIdentifier/%s";
    
    @Test
    public void testKeywordAsAttribute() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordAsAttribute.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordOnDeclarationOfMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordOnDeclarationOfMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordOnLoops() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordOnLoops.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordAsMethodName() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordAsMethodName.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordOnEnum() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordOnEnum.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordAfterAKeywordThis() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordAfterAKeywordThis.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testKeywordAsInnerClassName() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("KeywordAsInnerClassName.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testkeywordDataAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("testkeywordDataAsIdentifier.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testkeywordGroupAsIdentifier() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("testkeywordGroupAsIdentifier.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}
