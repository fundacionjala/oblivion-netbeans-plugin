/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import org.fundacionjala.oblivion.navigator.MethodMutableTreeNode;
import org.fundacionjala.oblivion.navigator.VariableMutableTreeNode;
import org.fundacionjala.oblivion.navigator.ParserMutableTreeNode;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import java.net.URISyntaxException;
import java.util.List;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.junit.Test;
import org.netbeans.modules.csl.spi.ParserResult;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link ParserMutableTreeNode} class.
 *
 * @author nelson_alcocer
 */
public class ParserMutableTreeNodeTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "../apex/grammar/resources/navigationpanel/%s";

    @Test
    public void testClassWithPrivateMembers() throws URISyntaxException {
        ParserResult parser = getParserResult("ClassWithPrivateMembers.cls");
        CompilationUnitTree tree = ((ApexLanguageParser.ApexParserResult) parser).getCompilationUnit();
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);
        assertEquals(1, foundClasses.size());
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(foundClasses.get(0));
        for (MethodTree method : foundMethods) {
            MethodMutableTreeNode node = new MethodMutableTreeNode(null, method);
            assertTrue(node.getIsPrivate());
        }
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(foundClasses.get(0));
        for (VariableTree variable : foundVariables) {
            VariableMutableTreeNode node = new VariableMutableTreeNode(null, variable);
            assertTrue(node.getIsPrivate());
        }
    }

    @Test
    public void testClassWithNoPrivateMembers() throws URISyntaxException {
        ParserResult parser = getParserResult("ClassWithNoPrivateMembers.cls");
        CompilationUnitTree tree = ((ApexLanguageParser.ApexParserResult) parser).getCompilationUnit();
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);
        assertEquals(1, foundClasses.size());
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(foundClasses.get(0));
        for (MethodTree method : foundMethods) {
            MethodMutableTreeNode node = new MethodMutableTreeNode(null, method);
            assertFalse(node.getIsPrivate());
        }
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(foundClasses.get(0));
        for (VariableTree variable : foundVariables) {
            VariableMutableTreeNode node = new VariableMutableTreeNode(null, variable);
            assertFalse(node.getIsPrivate());
        }
    }

    @Test
    public void testMethodsWithListReturnType() throws URISyntaxException {
        ParserResult parser = getParserResult("VariableMethodWithListReturnType.cls");
        CompilationUnitTree tree = ((ApexLanguageParser.ApexParserResult) parser).getCompilationUnit();
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);
        assertEquals(1, foundClasses.size());
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(foundClasses.get(0));
        ParserMutableTreeNode node = new MethodMutableTreeNode(null, foundMethods.get(0));
        assertEquals(node.getDataType(), "List<integer>");
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(foundClasses.get(0));
        node = new VariableMutableTreeNode(null, foundVariables.get(0));
        assertEquals(node.getDataType(), "List<integer>");
    }

    @Test
    public void testMethodsWithSetReturnType() throws URISyntaxException {
        ParserResult parser = getParserResult("VariableMethodWithSetReturnType.cls");
        CompilationUnitTree tree = ((ApexLanguageParser.ApexParserResult) parser).getCompilationUnit();
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);
        assertEquals(1, foundClasses.size());
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(foundClasses.get(0));
        ParserMutableTreeNode node = new MethodMutableTreeNode(null, foundMethods.get(0));
        assertEquals(node.getDataType(), "Set<integer>");
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(foundClasses.get(0));
        node = new VariableMutableTreeNode(null, foundVariables.get(0));
        assertEquals(node.getDataType(), "Set<integer>");
    }

    @Test
    public void testMethodsWithMapReturnType() throws URISyntaxException {
        ParserResult parser = getParserResult("VariableMethodWithMapReturnType.cls");
        CompilationUnitTree tree = ((ApexLanguageParser.ApexParserResult) parser).getCompilationUnit();
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);
        assertEquals(1, foundClasses.size());
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(foundClasses.get(0));
        ParserMutableTreeNode node = new MethodMutableTreeNode(null, foundMethods.get(0));
        assertEquals(node.getDataType(), "Map<String, String>");
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(foundClasses.get(0));
        node = new VariableMutableTreeNode(null, foundVariables.get(0));
        assertEquals(node.getDataType(), "Map<String, String>");
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

}
