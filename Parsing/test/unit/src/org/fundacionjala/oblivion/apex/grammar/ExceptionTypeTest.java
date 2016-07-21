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
 * @author Maria Garcia
 */
public class ExceptionTypeTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "resources/exceptionType/%s";

    @Test
    public void extendsFromException() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("extendsFromException.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void declaredAsAVariableAndPropertyType() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("declaredAsVariablePropertyType.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void passedAsAParamType() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("declaredAsVariablePropertyType.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void inControlFlowStatements() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("inControlFlowStatements.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void asAReturnType() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("asAReturnType.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Test
    public void insideTryCatchClause() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("inTryCatchClause.cls");
        parser.CompilationUnit();
        assertTrue("No parser errors are expected", parser.getSyntaxErrors().isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
