/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.collection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.fundacionjala.oblivion.apex.testsupport.TestScenarioLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;

/**
 * Verifies that the List grammar covers the scenarios described at:
 *
 * @see:http://www.salesforce.com/us/developer/docs/apexcode/salesforce_apex_language_reference.pdf
 * @author Alejandro Ruiz
 */
@RunWith(Parameterized.class)
public class ListCollectionSupportTest {

    private final static String RESOURCE_FILE = "resources/ListCollectionSupportScenarios.xml";

    private String id;
    private String testContent;

    @Parameters()
    public static Iterable<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        String resourceFile = ListCollectionSupportTest.class.getResource(String.format(RESOURCE_FILE)).getFile();
        return TestScenarioLoader.load(resourceFile);
    }

    public ListCollectionSupportTest(String id, String testContent) {
        this.id = id;
        this.testContent = testContent;
    }

    @Test
    public void testScenarios() throws FileNotFoundException, ParseException {
        ApexParser parser = new ApexParser(new StringReader(testContent));
        parser.setTreeFactory(new ApexTreeFactory());
        parser.CompilationUnit();
        assertTrue("The scenario: \'" + id + "\' should not have errors", parser.getSyntaxErrors().isEmpty());
    }
}
