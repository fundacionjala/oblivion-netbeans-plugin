/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.grammar;

import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test that the parser calculates the right line and column when a file has tab characters.
 * 
 * @author Marcelo Garnica
 */
public class FilesWithTabsTest extends AbstractTestGrammar {
    
    @Test
    public void correctLineAndColumnErrorOnClass() throws Exception {
        ApexParser parser = getParser("ClassWithTabs.cls");
        runParser(parser);
        ParseException firstError = parser.getSyntaxErrors().get(0);
        String expectedMessage = String.format("Unexpected: \"}\" %nAt line 4, column 2");
        assertEquals(expectedMessage, firstError.getMessage());
    }
    
    @Test
    public void correctLineAndColumnErrorOnTrigger() throws Exception {
        ApexParser parser = getParser("TriggerWithTabs.cls");
        parser.TriggerCompilationUnit();
        ParseException firstError = parser.getSyntaxErrors().get(0);
        String expectedMessage = "line 4, column 1";
        assert(firstError.getMessage().contains(expectedMessage));
    }

    @Override
    protected String getResourceFolder() {
        return "resources/%s";
    }
}
