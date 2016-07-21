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
 *
 * @author Amir Aranibar
 */
public class FilesWithoutCodeTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/filesWithoutCode/%s";
    
    @Test
    public void testEmptyFile() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("emptyFile.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithWhiteSpaces() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithWhiteSpaces.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithNewLines() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithNewLines.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithACommentLine() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithACommentLine.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithACommentBlock() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithACommentsBlock.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithCommentsWhiteSpacesAndNewLines() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithCommentsWhiteSpacesAndNewLines.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFileWithASimpleCharacter() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("fileWithASimpleCharacter.cls");
        parser.CompilationUnit();
        assertTrue(PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().size() == 1);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}
