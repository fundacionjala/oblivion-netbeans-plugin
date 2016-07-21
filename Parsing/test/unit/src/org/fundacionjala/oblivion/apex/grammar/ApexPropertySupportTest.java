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
 * Verifies that the Property grammar covers the scenarios described at:
 * @see:http://www.salesforce.com/us/developer/docs/apexcode/salesforce_apex_language_reference.pdf
 * @author Alejandro Ruiz
 */
public class ApexPropertySupportTest extends AbstractTestGrammar {
    private final static String RESOURCE_FOLDER = "resources/properties/%s";

    @Test
    public void publicGetProperty() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicGetProperty.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void publicGetPropertyWithParenthesisReporsError() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("publicGetPropertyWithParenthesis.cls");
        runParser(parser);
        System.out.println(parser.getSyntaxErrors());
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void propertyWithMoreThan2Accessors() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("propertyWithAccessors.cls");
        runParser(parser);
        String expected = "Unexpected: \"get\" %nAt line 3, column 19";
        assertTrue("There should be an error", !parser.getSyntaxErrors().isEmpty());
        assertEquals(String.format(expected), parser.getSyntaxErrors().get(0).getMessage());
    }
    
    @Test
    public void propertiesWithKeywordsAsIdentifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("propertiesWithKeywordsAsIdentifiers.cls");
        runParser(parser);
        assertTrue(parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
