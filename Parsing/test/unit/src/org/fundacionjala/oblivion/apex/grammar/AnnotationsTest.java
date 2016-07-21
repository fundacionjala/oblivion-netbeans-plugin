/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.testsupport.TestScenarioLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import static org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar.NO_PARSER_ERRORS_ARE_EXPECTED;
import static org.junit.Assert.*;

/**
 * Verifies the different scenarios on which annotations are supported.
 *
 * @author Marcelo Garnica
 */
@RunWith(Parameterized.class)
public class AnnotationsTest extends AbstractTestGrammar{

    private static final String RESOURCE_FILE = "resources/AnnotationsScenarios.xml";
    private final static String RESOURCE_FOLDER = "resources/annotations/%s";
    private final String scenario;
    private final String content;
    private final String errors;

    public AnnotationsTest(String scenario, String errors, String content) {
        this.scenario = scenario;
        this.content = content;
        this.errors = errors;
    }

    @Test
    public void testScenarios() throws ParseException {
        ApexParser parser = new ApexParser(new StringReader(String.format(content)));
        parser.setTreeFactory(new ApexTreeFactory());
        parser.CompilationUnit();
        List<ParseException> actualErrors = parser.getSyntaxErrors();
        String messageFormat = "Test Scenario: %s - %s";
        String[] expectedErrors = errors.isEmpty() ? new String[0] : errors.split("@@");
        assertEquals(String.format(messageFormat, scenario, "number of Errors"), expectedErrors.length, actualErrors.size());
        for (int i = 0; i < expectedErrors.length; i++) {
            assertEquals(String.format(messageFormat, scenario, "error"), String.format(expectedErrors[i].trim()), actualErrors.get(i).getMessage());
        }
    }

    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        String resourceFile = AnnotationsTest.class.getResource(RESOURCE_FILE).getFile();
        return TestScenarioLoader.load(resourceFile, "scenario", "errors", "testContent");
    }
    
    @Test
    public void testFutureAnnotation() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("FutureAnnotation.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFutureAnnotationWithCalloutInFalse() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("FutureAnnotationWithCalloutInFalse.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testFutureAnnotationWithCalloutInTrue() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("FutureAnnotationWithCalloutInTrue.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
