/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.autocomplete;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.editor.autocomplete.MethodItem;
import org.fundacionjala.oblivion.apex.editor.autocomplete.RecoverDeclarations;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class for test auto complete code
 *
 * @author sergio_daza
 */
public class TestAutocompleteCode extends AbstractTestGrammar {

    private static final String RESOURCE_FOLDER = "resources/%s";

    @Test
    public void testEmpty() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("Empty.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("Empty.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        assertEquals(0, recoverDeclarations.classes.size());
    }
    
    @Test
    public void testEmptyClass() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("WithMethods.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("WithMethods.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        assertEquals(4, recoverDeclarations.collectionOfDeclarations.get("WithMethods").item_methods.size());
    }
    
    @Test
    public void testWithVariables() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("WithVariables.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("WithVariables.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        assertEquals(3, recoverDeclarations.collectionOfDeclarations.get("WithVariables").variables.size());
    }

    @Test
    public void testWithAttributes() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("WithAttributes.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("WithAttributes.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        assertEquals(3, recoverDeclarations.collectionOfDeclarations.get("WithAttributes").attributes.size());
    }
    
    @Test
    public void testWithVariablesAndMethods() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("WithVariablesAndMethods.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("WithVariablesAndMethods.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        assertEquals(3, recoverDeclarations.collectionOfDeclarations.get("WithVariablesAndMethods").attributes.size());
        assertEquals(9, recoverDeclarations.collectionOfDeclarations.get("WithVariablesAndMethods").variables.size());
        assertEquals(5, recoverDeclarations.collectionOfDeclarations.get("WithVariablesAndMethods").item_methods.size());
    }

    @Test
    public void testCheckSpecificVariable() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("CheckSpecificVariable.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("CheckSpecificVariable.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        List<VariableTreeImpl> variables = recoverDeclarations.collectionOfDeclarations.get("CheckSpecificVariable").variables;
        assertEquals(true,variables.get(1).getName().toString().equals("variable2"));
    }
    
    @Test
    public void testCheckSpecificMethod() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("CheckSpecificMethod.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("CheckSpecificMethod.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        List<MethodItem> methods = recoverDeclarations.collectionOfDeclarations.get("CheckSpecificMethod").item_methods;
        MethodItem method = new MethodItem("method2","Boolean",0,null,null);
        method.variablesMethod = new ArrayList<>();
        assertEquals(true,methods.contains(method));
    }
    
    @Test
    public void testCheckSpecificMethodWithParameters() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult) getParserResult("CheckSpecificMethodWithParameters.cls");
        StyledDocument styledDocument = (StyledDocument) getDocument("CheckSpecificMethodWithParameters.cls");
        RecoverDeclarations recoverDeclarations = new RecoverDeclarations(1, styledDocument, parserResult.getCompilationUnit());
        recoverDeclarations.SearchIdentifier();
        List<MethodItem> methods = recoverDeclarations.collectionOfDeclarations.get("CheckSpecificMethodWithParameters").item_methods;
        assertEquals(2,methods.get(0).variablesMethod.size());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
