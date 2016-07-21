/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.editor.AbstractTestFormat;
import org.fundacionjala.oblivion.apex.editor.preferences.ReformatOption;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Amir Aranibar
 */
public class ReformatTreeVisitorTest extends AbstractTestFormat {

    private final static String RESOURCE_FOLDER = "resources/%s";
    private final static String ERROR_MESSAGE = "There is a null variable before to execute the unit test";
    private final static String INPUT_FILE = "ClassToBeNavigated.cls";

    @Test
    public void testVisitMethod() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitMethod(methodTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitMethodNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 2, 4);
        reformatTreeVisitor.visitMethod(methodTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitClass() throws BadLocationException, ParseException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        ClassTree classTree = TreeNavigationUtils.findClassTreeByName("TestClass", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitClass(classTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitClassNotReformat() throws BadLocationException, ParseException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        ClassTree classTree = TreeNavigationUtils.findClassTreeByName("TestClass", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 0, 1);
        reformatTreeVisitor.visitClass(classTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitVariable() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        VariableTree variableTree = TreeNavigationUtils.findVariableTreeByName("myInt", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitVariable(variableTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitVariableNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        VariableTree variableTree = TreeNavigationUtils.findVariableTreeByName("myInt", unit).get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 0, 2);
        reformatTreeVisitor.visitVariable(variableTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitTry() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        TryTree tryTree = (TryTree) getStatementTreeByClassName(TryTree.class, methodTree);

        if (tryTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitTry(tryTree, optionsToReformat);

        assertEquals(optionsToReformat.size(), 3);
    }

    @Test
    public void testVisitTryNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        TryTree tryTree = (TryTree) getStatementTreeByClassName(TryTree.class, methodTree);

        if (tryTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 5, 10);
        reformatTreeVisitor.visitTry(tryTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitCatch() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        TryTree tryTree = (TryTree) getStatementTreeByClassName(TryTree.class, methodTree);

        if (tryTree == null) {
            fail(ERROR_MESSAGE);
        }

        CatchTree catchTree = tryTree.getCatches().get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitCatch(catchTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitCatchNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        TryTree tryTree = (TryTree) getStatementTreeByClassName(TryTree.class, methodTree);

        if (tryTree == null) {
            fail(ERROR_MESSAGE);
        }

        CatchTree catchTree = tryTree.getCatches().get(0);

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 10, 20);
        reformatTreeVisitor.visitCatch(catchTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitIf() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        IfTree IfTree = (IfTree) getStatementTreeByClassName(IfTree.class, methodTree);

        if (IfTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitIf(IfTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitIfNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        IfTree IfTree = (IfTree) getStatementTreeByClassName(IfTree.class, methodTree);

        if (IfTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 0, 10);
        reformatTreeVisitor.visitIf(IfTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitWhileLoop() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        WhileLoopTree whileLoopTree = (WhileLoopTree) getStatementTreeByClassName(WhileLoopTree.class, methodTree);

        if (whileLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitWhileLoop(whileLoopTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitWhileLoopNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        WhileLoopTree whileLoopTree = (WhileLoopTree) getStatementTreeByClassName(WhileLoopTree.class, methodTree);

        if (whileLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 0, 10);
        reformatTreeVisitor.visitWhileLoop(whileLoopTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitForLoop() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        ForLoopTree forLoopTree = (ForLoopTree) getStatementTreeByClassName(ForLoopTree.class, methodTree);

        if (forLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitForLoop(forLoopTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitForLoopNotReformat() throws ParseException, BadLocationException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        ForLoopTree forLoopTree = (ForLoopTree) getStatementTreeByClassName(ForLoopTree.class, methodTree);

        if (forLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 0, 10);
        reformatTreeVisitor.visitForLoop(forLoopTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitDoWhileLoop() throws BadLocationException, ParseException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        DoWhileLoopTree doWhileLoopTree = (DoWhileLoopTree) getStatementTreeByClassName(DoWhileLoopTree.class, methodTree);

        if (doWhileLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE);
        reformatTreeVisitor.visitDoWhileLoop(doWhileLoopTree, optionsToReformat);

        assertFalse(optionsToReformat.isEmpty());
    }

    @Test
    public void testVisitDoWhileLoopNotReformat() throws BadLocationException, ParseException, IOException {
        List<ReformatOption> optionsToReformat = new ArrayList<>();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);
        DoWhileLoopTree doWhileLoopTree = (DoWhileLoopTree) getStatementTreeByClassName(DoWhileLoopTree.class, methodTree);

        if (doWhileLoopTree == null) {
            fail(ERROR_MESSAGE);
        }

        ReformatTreeVisitor reformatTreeVisitor = getReformatTreeVisitor(INPUT_FILE, 90, 100);
        reformatTreeVisitor.visitDoWhileLoop(doWhileLoopTree, optionsToReformat);

        assertTrue(optionsToReformat.isEmpty());
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private CompilationUnitTree getCompilationUnitTree(String fileName) throws ParseException {
        InputStream resource = getClass().getResourceAsStream(String.format(getResourceFolder(), fileName));
        ApexParser parser = new ApexParser(resource);
        parser.setTreeFactory(new ApexTreeFactory());

        return parser.CompilationUnit();
    }

    private StatementTree getStatementTreeByClassName(Class<?> clazz, MethodTree methodTree) {
        StatementTree statementTree = null;
        for (StatementTree statement : methodTree.getBody().getStatements()) {
            if (clazz.isInstance(statement)) {
                statementTree = statement;
            }
        }

        return statementTree;
    }

    private ReformatTreeVisitor getReformatTreeVisitor(String inputFile) throws ParseException, BadLocationException, IOException {
        return getReformatTreeVisitor(inputFile, 0, 1000000);
    }

    private ReformatTreeVisitor getReformatTreeVisitor(String inputFile, int startOffset, int endOffset) throws ParseException, BadLocationException, IOException {
        String content = getTextFromFile(inputFile);
        StyledDocument document = buildDocument(content);

        return new ReformatTreeVisitor(document, startOffset, endOffset);
    }
}
