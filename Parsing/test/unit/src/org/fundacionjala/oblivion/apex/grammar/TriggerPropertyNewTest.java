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
public class TriggerPropertyNewTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/trigger/%s";

    @Test
    public void callNewPropertyOfTrigger() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callNewPropertyOfTrigger.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void callNewPropertyOfTriggerAndCallMethodInline() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callNewPropertyOfTriggerAndCallMethodInline.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void callNewPropertyOfCustomClass() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("callNewPropertyOfCustomClass.cls");
        parser.CompilationUnit();
        ParseException firstError = parser.getSyntaxErrors().get(0);
        String expectedMessage = "line 3, column 34";
        assertEquals(PARSER_ERRORS_ARE_EXPECTED, 1, parser.getSyntaxErrors().size());
        assert(firstError.getMessage().contains(expectedMessage));
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
