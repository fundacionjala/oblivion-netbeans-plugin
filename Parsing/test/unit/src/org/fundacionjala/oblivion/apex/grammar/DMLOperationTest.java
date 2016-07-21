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
 * Verifies if declaration rules are according the scenarios described at:
 * @see: https://developer.salesforce.com/docs/atlas.en-us.apexcode.meta/apexcode/apex_dml_section.htm
 * @author sergio_daza
 */
public class DMLOperationTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/DMLOperation/%s";
    private int expectedColumn;
    private int expectedLine;
    
    @Test
    public void testDMLInsert() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLInsert.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLUpdate() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpdate.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLUpsert() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpsert.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLUpsertWithOptField() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpsertWithOptField.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLDelete() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLDelete.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLUndelete() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUndelete.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLMerge() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLMerge.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testDMLInsertAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLInsertAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLUpdateAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpdateAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLUpsertAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpsertAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLUpsertWithOptFieldAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUpsertWithOptField.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLDeleteAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLDeleteAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLUndeleteAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLUndeleteAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testDMLMergeAsMethod() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("DMLMergeAsMethod.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
