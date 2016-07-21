/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree.visitors;

import org.fundacionjala.oblivion.apex.ast.tree.visitors.DeclaredVariablesByScope;
import org.fundacionjala.oblivion.apex.ast.tree.visitors.DeclaredVariablesVisitor;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for {@link DeclaredVariablesVisitor}
 *
 * @author adrian_grajeda
 */
public class DeclaredVariablesVisitorTest {

    @Test
    public void testDeclaredVariablesInClassLevel() throws ParseException {
        final String inputFile = "resources/SimpleClass.cls";
        CompilationUnitTree unit = getCompilationUnitTree(inputFile);
        DeclaredVariablesVisitor visitor = new DeclaredVariablesVisitor();

        unit.accept(visitor, null);

        assertEquals(1, visitor.getScope().getAllScopes().size());
        Tree classScope = TreeNavigationUtils.findClassTreeByName("SimpleClass", unit).get(0);
        Collection<VariableTree> variables = visitor.getScope().getVariableListByScope(classScope);

        assertVariableByName("code", variables);
        assertVariableByName("name", variables);
        assertVariableByName("lastName", variables);
    }

    @Test
    public void testUnDeclaredVariablesInClassLevel() throws ParseException {
        final String inputFile = "resources/SimpleClass.cls";
        CompilationUnitTree unit = getCompilationUnitTree(inputFile);
        DeclaredVariablesVisitor visitor = new DeclaredVariablesVisitor();

        unit.accept(visitor, null);

        List<Tree> scopes = new ArrayList<>(visitor.getScope().getAllScopes());

        assertEquals(1, scopes.size());
        Tree classScope = scopes.get(0);
        Collection<VariableTree> variables = visitor.getScope().getVariableListByScope(classScope);

        assertVariableNotDeclared("id", variables);
    }

    @Test
    public void testDeclaredVariablesInClassAndMethodLevel() throws ParseException {
        final String inputFile = "resources/DummyClass.cls";
        CompilationUnitTree unit = getCompilationUnitTree(inputFile);
        DeclaredVariablesVisitor visitor = new DeclaredVariablesVisitor();

        unit.accept(visitor, null);

        assertEquals(2, visitor.getScope().getAllScopes().size());
        Tree classScope = TreeNavigationUtils.findClassTreeByName("SimpleClass", unit).get(0);
        Collection<VariableTree> variables = visitor.getScope().getVariableListByScope(classScope);

        assertVariableByName("code", variables);
        assertVariableByName("name", variables);
        assertVariableByName("lastName", variables);

        Tree methodBody = TreeNavigationUtils.findMethodTreeByName("getFullName", unit).get(0).getBody();
        variables = visitor.getScope().getVariableListByScope(methodBody);
        assertVariableByName("full", variables);

    }

    @Test
    public void testDeclaredVariablesInClassAndMethodLevelWithParameter() throws ParseException {
        final String inputFile = "resources/DummyParameterClass.cls";
        CompilationUnitTree unit = getCompilationUnitTree(inputFile);
        DeclaredVariablesVisitor visitor = new DeclaredVariablesVisitor();

        unit.accept(visitor, null);

        assertEquals(3, visitor.getScope().getAllScopes().size());
        Tree classScope = TreeNavigationUtils.findClassTreeByName("SimpleClass", unit).get(0);
        Collection<VariableTree> variables = visitor.getScope().getVariableListByScope(classScope);

        assertVariableByName("code", variables);
        assertVariableByName("name", variables);
        assertVariableByName("lastName", variables);

        Tree methodBody = TreeNavigationUtils.findMethodTreeByName("getFullName", unit).get(0).getBody();
        variables = visitor.getScope().getVariableListByScope(methodBody);
        assertVariableByName("full", variables);

        methodBody = TreeNavigationUtils.findMethodTreeByName("updateName", unit).get(0);
        variables = visitor.getScope().getVariableListByScope(methodBody);
        assertVariableByName("newName", variables);
    }

    private CompilationUnitTree getCompilationUnitTree(String fileName) throws ParseException {
        InputStream resource = DeclaredVariablesByScope.class.getResourceAsStream(fileName);
        ApexParser parser = new ApexParser(resource);
        parser.setTreeFactory(new ApexTreeFactory());
        return parser.CompilationUnit();
    }

    private void assertVariableByName(String expectedVariableName, Collection<VariableTree> variables) {
        for (VariableTree current : variables) {
            if (current.getName().equals(expectedVariableName)) {
                assertEquals(expectedVariableName, current.getName().toString());
                return;
            }
        }
        fail("Expected variable name doesn't match with declared variables");
    }

    private void assertVariableNotDeclared(String unexpectedVariableName, Collection<VariableTree> variables) {
        for (VariableTree current : variables) {
            if (current.getName().equals(unexpectedVariableName)) {
                fail("An Unexpected variable name its declared");
                return;
            }
        }
        assertTrue(String.format("variable with name %s, is not declared", unexpectedVariableName), Boolean.TRUE);
    }
}
