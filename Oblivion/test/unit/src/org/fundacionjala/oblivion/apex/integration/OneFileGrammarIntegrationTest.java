/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.integration;

import java.io.FileNotFoundException;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.integration.IntegrationTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;

/**
 * Verifies if declaration rules are according the scenarios described at:
 *
 * @see:http://www.salesforce.com/us/developer/docs/apexcode/salesforce_apex_language_reference.pdf
 * @author Amir Aranibar
 */
@Category(IntegrationTests.class)
public class OneFileGrammarIntegrationTest extends AbstractTestGrammar {

    private final String RESOURCE_FOLDER = "resources/%s";

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void testClassWithTheMostUsedExpressions() throws FileNotFoundException, ParseException {
        testFile("MostUsedExpressions.cls");
    }

    @Test
    public void testInterfaceDeclaration() throws FileNotFoundException, ParseException {
        testFile("InterfaceDeclaration.cls");
    }

    @Test
    public void testEnumDeclaration() throws FileNotFoundException, ParseException {
        testFile("EnumDeclaration.cls");
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private void testFile(String fileName) throws FileNotFoundException, ParseException {
        ApexParser parser = getParser(fileName);
        runParser(parser);
        List<ParseException> errors = parser.getSyntaxErrors();
        int totalErrors = errors.size();

        for (ParseException error : errors) {
            errorCollector.addError(error);
        }

        errorCollector.checkThat("Total errors: " + totalErrors, totalErrors, is(0));
    }
}
