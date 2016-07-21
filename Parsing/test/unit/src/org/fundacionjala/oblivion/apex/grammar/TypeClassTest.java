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
 * Verifies if declaration rules are according the scenarios described at:
 * @see: https://developer.salesforce.com/docs/atlas.en-us.apexcode.meta/apexcode/apex_methods_system_type.htm
 * @author sergio_daza
 */
public class TypeClassTest extends AbstractTestGrammar {
    
    private final static String RESOURCE_FOLDER = "resources/TypeClass/%s";
    
    @Test
    public void testInstantiatingATypeBasedOnItsName() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("InstantiatingATypeBasedOnItsName.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void testClassProperty() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassProperty.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testTypeMethods() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TypeMethods.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Test
    public void testClassNameAndNamespace() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassNameAndNamespace.cls");
        parser.CompilationUnit();
        assertTrue(NO_PARSER_ERRORS_ARE_EXPECTED, parser.getSyntaxErrors().isEmpty());
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}
