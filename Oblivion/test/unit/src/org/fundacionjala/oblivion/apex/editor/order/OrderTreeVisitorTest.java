/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.editor.AbstractTestFormat;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Amir Aranibar
 */
public class OrderTreeVisitorTest extends AbstractTestFormat {

    private final static String RESOURCE_FOLDER = "resources/%s";
    private final static String INPUT_FILE = "ClassToBeNavigated.cls";

    @Test
    public void testVisitor() throws ParseException, BadLocationException, IOException {
        ClassMemberGrouper classMemberGrouper = new ClassMemberGrouper();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        OrderTreeVisitor orderTreeVisitor = getOrderTreeVisitor(INPUT_FILE);
        unit.accept(orderTreeVisitor, classMemberGrouper);

        List<ClassMember<?>> members = classMemberGrouper.getAllMembers();
        int methodsCount = 0;
        int variablesCount = 0;

        for (ClassMember<?> member : members) {
            if (member.getType() instanceof MethodTree) {
                methodsCount++;
            } else if (member.getType() instanceof VariableTree) {
                variablesCount++;
            }
        }

        Assert.assertEquals(2, variablesCount);
        Assert.assertEquals(1, methodsCount);
        Assert.assertEquals(2, classMemberGrouper.getClasses().size());
        Assert.assertEquals(6, classMemberGrouper.getAllMembers().size());
    }

    @Test
    public void testVisitMethod() throws ParseException, BadLocationException, IOException {
        ClassMemberGrouper classMemberGrouper = new ClassMemberGrouper();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        MethodTree methodTree = TreeNavigationUtils.findMethodTreeByName("doSomething", unit).get(0);

        OrderTreeVisitor orderTreeVisitor = getOrderTreeVisitor(INPUT_FILE);
        orderTreeVisitor.visitMethod(methodTree, classMemberGrouper);

        List<ClassMember<?>> members = classMemberGrouper.getAllMembers();
        int methodsCount = 0;

        for (ClassMember<?> member : members) {
            if (member.getType() instanceof MethodTree) {
                methodsCount++;
            }
        }

        Assert.assertTrue(methodsCount > 0);
    }

    @Test
    public void testVisitClass() throws ParseException, BadLocationException, IOException {
        ClassMemberGrouper classMemberGrouper = new ClassMemberGrouper();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        ClassTree testClass = TreeNavigationUtils.findClassTreeByName("TestClass", unit).get(0);
        ClassTree testInnerClass = TreeNavigationUtils.findClassTreeByName("TestInnerClass", unit).get(0);

        OrderTreeVisitor orderTreeVisitor = getOrderTreeVisitor(INPUT_FILE);
        orderTreeVisitor.visitClass(testClass, classMemberGrouper);
        orderTreeVisitor.visitClass(testInnerClass, classMemberGrouper);

        Assert.assertEquals(2, classMemberGrouper.getClasses().size());
    }

    @Test
    public void testVisitVariable() throws ParseException, BadLocationException, IOException {
        ClassMemberGrouper classMemberGrouper = new ClassMemberGrouper();
        CompilationUnitTree unit = getCompilationUnitTree(INPUT_FILE);
        VariableTree myString = TreeNavigationUtils.findVariableTreeByName("myString", unit).get(0);
        VariableTree myInt = TreeNavigationUtils.findVariableTreeByName("myInt", unit).get(0);
        VariableTree myInt2 = TreeNavigationUtils.findVariableTreeByName("myInt2", unit).get(0);

        OrderTreeVisitor orderTreeVisitor = getOrderTreeVisitor(INPUT_FILE);
        orderTreeVisitor.visitVariable(myString, classMemberGrouper);
        orderTreeVisitor.visitVariable(myInt, classMemberGrouper);
        orderTreeVisitor.visitVariable(myInt2, classMemberGrouper);

        List<ClassMember<?>> members = classMemberGrouper.getAllMembers();
        int variablesCount = 0;

        for (ClassMember<?> member : members) {
            if (member.getType() instanceof VariableTree) {
                variablesCount++;
            }
        }

        Assert.assertTrue(variablesCount > 0);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private OrderTreeVisitor getOrderTreeVisitor(String inputFile) throws ParseException, BadLocationException, IOException {
        String content = getTextFromFile(inputFile);
        StyledDocument document = buildDocument(content);

        return new OrderTreeVisitor(document);
    }

    private CompilationUnitTree getCompilationUnitTree(String fileName) throws ParseException {
        InputStream resource = getClass().getResourceAsStream(String.format(getResourceFolder(), fileName));
        ApexParser parser = new ApexParser(resource);
        parser.setTreeFactory(new ApexTreeFactory());

        return parser.CompilationUnit();
    }
}
