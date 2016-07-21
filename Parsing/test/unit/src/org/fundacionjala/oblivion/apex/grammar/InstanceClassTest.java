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
 * Verifies the different scenarios to instance a class are supported.
 *
 * @author Amir Aranibar
 */
public class InstanceClassTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/classTest/%s";

    @Test
    public void testInstanceClassInDiferentWays() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("instanceClass.cls");
        parser.CompilationUnit();
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testInstanceClassInWrongsWays() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("wrongInstanceClass.cls");
        parser.CompilationUnit();
        assertFalse(parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testInstanceClassAsArgumentWays() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("instanceClassAsArgument.cls");
        parser.CompilationUnit();
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
