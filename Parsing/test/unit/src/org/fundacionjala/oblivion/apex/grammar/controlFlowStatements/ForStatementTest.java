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
 * Test rules of For statement.
 *
 * @author Maria Garcia
 */
public class ForStatementTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "../resources/controlFlowStatements/%s";

    @Test
    public void forWrongDefinition() throws FileNotFoundException, ParseException {
        int expectedLine = 3;
        int expectedColumn = 14;
        ApexParser parser = getParser("forStatement.cls");
        runParser(parser);
        String expected = "Wrong \"for\" definition. Found: \")\" %nAt line %d, column %d";
        assertTrue(!parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected, expectedLine, expectedColumn), parser.getSyntaxErrors().get(0).getMessage());
        assertEquals(expectedLine, parser.getSyntaxErrors().get(0).currentToken.beginLine);
        assertEquals(expectedColumn, parser.getSyntaxErrors().get(0).currentToken.beginColumn);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
