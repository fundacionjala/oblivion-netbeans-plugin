/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.VariableTree;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.ast.PropertyTree;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerDeclarationTree;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests the abstract syntax tree structure after it's built.
 *
 * @author Maria Garcia
 */
public class ASTreeBuildingTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "../../grammar/resources/ast/%s";

    @Test
    public void buildTreeWithEmptyClass() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("EmptyClass.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        assertEquals("The class shouldn't have members because is empty", 0, classNode.getMembers().size());
    }

    @Test
    public void buildTreeWithImplementsAndExtends() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ImplementsAndExtends.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        IdentifierTree extendsClause = (IdentifierTree)classNode.getExtendsClause();
        IdentifierTree implementsClause = (IdentifierTree)classNode.getImplementsClause().get(0);
        
        String expectedExtendsFrom = "Other";
        String expectedImplementsFrom = "Serializable";
        
        assertEquals(expectedExtendsFrom, extendsClause.getName().toString());
        assertEquals(expectedImplementsFrom, implementsClause.getName().toString());
    }

    @Test
    public void buildTreeWithTwoMembers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithTwoMembers.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        assertEquals(2, classNode.getMembers().size());
        List<VariableTree> variables = TreeNavigationUtils.findVariableTreeByName("var", tree);
        assertEquals("The test class contains one field declaration with 'var' name", 1, variables.size());
        List<MethodTree> methods = TreeNavigationUtils.findMethodTreeByName("doSomething", tree);
        assertEquals("The test class contains one method called 'doSomething'", 1, methods.size());
    }

    @Test
    public void buildTreeWithMethodHasTwoChildren() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("MethodWithTwoChildren.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);

        MethodTree method = (MethodTree)classNode.getMembers().get(0);
        assertChildTreeExists(VariableTreeImpl.class, method.getBody());
        assertChildTreeExists(ReturnTreeImpl.class, method.getBody());
    }
    
    @Test
    public void methodTreeWithModifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("MethodWithTwoChildren.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        MethodTree method = (MethodTree)classNode.getMembers().get(0);
        assertNotNull(method);
        ModifiersTree modifiers = method.getModifiers();
        assertNotNull(modifiers);
        Set<Modifier> flags = modifiers.getFlags();
        assertEquals(2, flags.size());
        assertTrue(flags.contains(Modifier.PUBLIC));
        assertTrue(flags.contains(Modifier.STATIC));
        assertFalse(flags.contains(Modifier.ABSTRACT));
        assertFalse(flags.contains(Modifier.PRIVATE));
    }

    @Test
    public void buildTreeWithAPropertyMember() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithProperty.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);

        List<MethodTree> accessors = TreeNavigationUtils.findMethodTreeByName("get", tree);
        assertEquals("This test property should contain a get accessor", 1, accessors.size());

        accessors = TreeNavigationUtils.findMethodTreeByName("set", tree);
        assertEquals("This test property should contain a set accessor", 1, accessors.size());
        
        PropertyTree property = (PropertyTree)classNode.getMembers().get(0);
        assertNotNull(property);
        ModifiersTree propertyModifiers = property.getModifiers();
        assertNotNull(propertyModifiers);
        Set<Modifier> flags = propertyModifiers.getFlags();
        assertEquals(1, flags.size());
        assertTrue(flags.contains(Modifier.PROTECTED));
        assertFalse(flags.contains(Modifier.STATIC));
    }
    
    @Test
    public void propertyTreeWithModifiers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithProperty.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        PropertyTree property = (PropertyTree)classNode.getMembers().get(0);
        assertNotNull(property);
        ModifiersTree propertyModifiers = property.getModifiers();
        assertNotNull(propertyModifiers);
        Set<Modifier> flags = propertyModifiers.getFlags();
        assertEquals(1, flags.size());
        assertTrue(flags.contains(Modifier.PROTECTED));
        assertFalse(flags.contains(Modifier.STATIC));
    }

    @Test
    public void buildTreeWhenSyntaxErrorsExist() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithErrors.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        assertNotNull(tree);
    }
    
    @Test
    public void buildTreeWithLiteralInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("myString", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.STRING_LITERAL , initializer.getKind());
        LiteralTree literalInitializer = (LiteralTree)initializer;
        assertEquals("\'I am a String\'", ((Token)literalInitializer.getValue()).getImage());
    }
    
    @Test
    public void buildTreeWithMethodInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("myInteger", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.METHOD_INVOCATION , initializer.getKind());
        MethodInvocationTreeImpl methodInitializer = (MethodInvocationTreeImpl)initializer;
        assertTrue(((IdentifierTree)methodInitializer.getMethodSelect()).getName().equals("someFunction"));
    }
    
    @Test
    public void buildTreeWithArrayInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("myArray", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_ARRAY , initializer.getKind());
        NewArrayTreeImpl arrayInitializer = (NewArrayTreeImpl)initializer;
        assertTrue(((IdentifierTree)arrayInitializer.getType()).getName().toString().equalsIgnoreCase("Integer"));
        assertTrue(arrayInitializer.getDimensions().isEmpty());
        assertFalse(arrayInitializer.getInitializers().isEmpty());
        assertEquals(3, arrayInitializer.getInitializers().size());
        LiteralTree literalInitializer = (LiteralTree)arrayInitializer.getInitializers().get(0);
        assertEquals("11", ((Token)literalInitializer.getValue()).getImage());
        literalInitializer = (LiteralTree)arrayInitializer.getInitializers().get(1);
        assertEquals("25", ((Token)literalInitializer.getValue()).getImage());
        literalInitializer = (LiteralTree)arrayInitializer.getInitializers().get(2);
        assertEquals("300", ((Token)literalInitializer.getValue()).getImage());
    }
    
    @Test
    public void buildTreeWithConstructorInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("firstA", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree classInitializer = (NewClassTree)initializer;
        assertTrue(((IdentifierTree)classInitializer.getIdentifier()).getName().equals("A"));
        assertTrue(classInitializer.getArguments().isEmpty());
        assertTrue(classInitializer.getTypeArguments().isEmpty());
    }
    
    @Test
    public void buildTreeWithConstructorParamsInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("secondA", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree classInitializer = (NewClassTree)initializer;
        assertTrue(((IdentifierTree)classInitializer.getIdentifier()).getName().equals("A"));
        assertTrue(classInitializer.getTypeArguments().isEmpty());
        assertFalse(classInitializer.getArguments().isEmpty());
        assertEquals(2, classInitializer.getArguments().size());
        IdentifierTree arg = (IdentifierTree)classInitializer.getArguments().get(0);
        assertTrue(arg.getName().equals("arg1"));
        arg = (IdentifierTree)classInitializer.getArguments().get(1);
        assertTrue(arg.getName().equals("arg2"));
    }
    
    @Test
    public void buildTreeWithListInitializer() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("firstList", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree listInitializer = (NewClassTree)initializer;
        assertTrue(listInitializer.getArguments().isEmpty());
        assertTrue(((IdentifierTree)listInitializer.getIdentifier()).getName().equals("List"));
        assertFalse(listInitializer.getTypeArguments().isEmpty());
        assertEquals(1, listInitializer.getTypeArguments().size());
        IdentifierTree typeArgument = (IdentifierTree) listInitializer.getTypeArguments().get(0);
        assertTrue(typeArgument.getName().equals("B"));
    }
    
    @Test
    public void buildTreeWithListAndInitializers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("secondList", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree listInitializer = (NewClassTree)initializer;
        assertTrue(((IdentifierTree)listInitializer.getIdentifier()).getName().equals("List"));
        assertFalse(listInitializer.getTypeArguments().isEmpty());
        assertEquals(1, listInitializer.getTypeArguments().size());
        IdentifierTree typeArgument = (IdentifierTree) listInitializer.getTypeArguments().get(0);
        assertTrue(typeArgument.getName().equals("B"));
        assertFalse(listInitializer.getArguments().isEmpty());
        assertEquals(3, listInitializer.getArguments().size());
        IdentifierTree paramArgument = (IdentifierTree) listInitializer.getArguments().get(0);
        assertTrue(paramArgument.getName().equals("b1"));
        paramArgument = (IdentifierTree) listInitializer.getArguments().get(1);
        assertTrue(paramArgument.getName().equals("b2"));
        paramArgument = (IdentifierTree) listInitializer.getArguments().get(2);
        assertTrue(paramArgument.getName().equals("b3"));
    }
    
    @Test
    public void buildTreeWithSetAndInitializers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("mySet", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree setInitializer = (NewClassTree)initializer;
        assertTrue(((IdentifierTree)setInitializer.getIdentifier()).getName().equals("Set"));
        assertFalse(setInitializer.getTypeArguments().isEmpty());
        assertEquals(1, setInitializer.getTypeArguments().size());
        IdentifierTree typeArgument = (IdentifierTree) setInitializer.getTypeArguments().get(0);
        assertTrue(typeArgument.getName().equals("C"));
        assertFalse(setInitializer.getArguments().isEmpty());
        assertEquals(3, setInitializer.getArguments().size());
        IdentifierTree paramArgument = (IdentifierTree) setInitializer.getArguments().get(0);
        assertTrue(paramArgument.getName().equals("c1"));
        paramArgument = (IdentifierTree) setInitializer.getArguments().get(1);
        assertTrue(paramArgument.getName().equals("c2"));
        paramArgument = (IdentifierTree) setInitializer.getArguments().get(2);
        assertTrue(paramArgument.getName().equals("c3"));
    }
    
    @Test
    public void buildTreeWithMapAndInitializers() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("ClassWithInitializers.cls");
        CompilationUnitTree compilationUnit = parser.CompilationUnit();
        List<VariableTree> varList = TreeNavigationUtils.findVariableTreeByName("myMap", compilationUnit);
        assertEquals(1, varList.size());
        ExpressionTree initializer = varList.get(0).getInitializer();
        assertEquals(Kind.NEW_CLASS , initializer.getKind());
        NewClassTree mapInitializer = (NewClassTree)initializer;
        assertTrue(((IdentifierTree)mapInitializer.getIdentifier()).getName().equals("Map"));
        assertFalse(mapInitializer.getTypeArguments().isEmpty());
        assertEquals(2, mapInitializer.getTypeArguments().size());
        IdentifierTree typeArgument = (IdentifierTree) mapInitializer.getTypeArguments().get(0);
        assertTrue(typeArgument.getName().equals("K"));
        typeArgument = (IdentifierTree) mapInitializer.getTypeArguments().get(1);
        assertTrue(typeArgument.getName().equals("V"));
        assertFalse(mapInitializer.getArguments().isEmpty());
        assertEquals(3, mapInitializer.getArguments().size());
        PairExpressionTreeImpl paramArgument = (PairExpressionTreeImpl) mapInitializer.getArguments().get(0);
        IdentifierTree keyParam = (IdentifierTree)paramArgument.getKey();
        IdentifierTree valueParam = (IdentifierTree)paramArgument.getValue();
        assertTrue(keyParam.getName().equals("k1"));
        assertTrue(valueParam.getName().equals("v1"));
        paramArgument = (PairExpressionTreeImpl) mapInitializer.getArguments().get(1);
        keyParam = (IdentifierTree)paramArgument.getKey();
        valueParam = (IdentifierTree)paramArgument.getValue();
        assertTrue(keyParam.getName().equals("k2"));
        assertTrue(valueParam.getName().equals("v2"));
        paramArgument = (PairExpressionTreeImpl) mapInitializer.getArguments().get(2);
        keyParam = (IdentifierTree)paramArgument.getKey();
        valueParam = (IdentifierTree)paramArgument.getValue();
        assertTrue(keyParam.getName().equals("k3"));
        assertTrue(valueParam.getName().equals("v3"));
    }
    
    @Test
    public void buildTreeWithAnnotations() throws ParseException, FileNotFoundException {
        ApexParser parser = getParser("ClassWithAnnotations.cls");
        CompilationUnitTree tree = parser.CompilationUnit();
        ClassTree classNode = (ClassTree)tree.getTypeDecls().get(0);
        assertNotNull(classNode);
        ModifiersTree classModifiers = classNode.getModifiers();
        assertNotNull(classModifiers);
        List<? extends AnnotationTree> classAnnotations = classModifiers.getAnnotations();
        assertNotNull(classAnnotations);
        assertEquals(1, classAnnotations.size());
        assertTrue(hasAnnotation(classAnnotations, "isTest"));
        assertFalse(hasAnnotation(classAnnotations, "otherAnnotation"));
        assertFalse(hasAnnotation(classAnnotations, ""));

        List<MethodTree> classMethods = TreeNavigationUtils.findMethodTreeByName("testMe", tree);
        assertEquals(1, classMethods.size());
        MethodTree classMethod = classMethods.get(0);
        assertNotNull(classMethod);
        ModifiersTree methodModifiers = classMethod.getModifiers();
        assertNotNull(methodModifiers);
        List<? extends AnnotationTree> methodAnnotations = methodModifiers.getAnnotations();
        assertNotNull(methodAnnotations);
        assertEquals(3, methodAnnotations.size());
        assertTrue(hasAnnotation(methodAnnotations, "testMethod"));
        assertTrue(hasAnnotation(methodAnnotations, "override"));
        assertTrue(hasAnnotation(methodAnnotations, "virtual"));
        assertFalse(hasAnnotation(methodAnnotations, "global"));
        assertFalse(hasAnnotation(methodAnnotations, "public"));
        
        List<VariableTree> classVariables = TreeNavigationUtils.findVariableTreeByName("property", tree);
        assertEquals(1, classVariables.size());
        VariableTree classProperty = classVariables.get(0);
        assertNotNull(classProperty);
        ModifiersTree propertyModifiers = classProperty.getModifiers();
        assertNotNull(propertyModifiers);
        List<? extends AnnotationTree> propertyAnnotations = propertyModifiers.getAnnotations();
        assertNotNull(propertyAnnotations);
        assertEquals(1, propertyAnnotations.size());
        assertTrue(hasAnnotation(propertyAnnotations, "TestVisible"));
        assertFalse(hasAnnotation(propertyAnnotations, "override"));
        assertFalse(hasAnnotation(propertyAnnotations, "global"));
        assertFalse(hasAnnotation(propertyAnnotations, "protected"));
        
    }
    
    @Test
    public void buildTreeWithEmptyTrigger() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("EmptyTrigger.trigger");
        CompilationUnitTree tree = parser.TriggerCompilationUnit();
        TriggerDeclarationTree triggerNode = (TriggerDeclarationTree)tree.getTypeDecls().get(0);
        assertEquals("The trigger shouldn't have members because it is empty", 0, triggerNode.getMembers().size());
    }
    
    @Test
    public void buildTriggerTreeWithInnerClass() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TriggerWithInnerClass.trigger");
        CompilationUnitTree tree = parser.TriggerCompilationUnit();
        TriggerDeclarationTree triggerNode = (TriggerDeclarationTree)tree.getTypeDecls().get(0);
        assertEquals(1, triggerNode.getMembers().size());
        List<ClassTree> classList = TreeNavigationUtils.findAllClasses(tree);
        assertEquals("The test trigger contains one class declaration with 'Account' name", "Account", classList.get(0).getSimpleName().toString());
    }
    
     @Test
    public void buildTriggerTreeWithTwoMembersAndReturn() throws FileNotFoundException, ParseException {
        ApexParser parser = getParser("TriggerWithTwoMembers.trigger");
        CompilationUnitTree tree = parser.TriggerCompilationUnit();
        TriggerDeclarationTree triggerNode = (TriggerDeclarationTree)tree.getTypeDecls().get(0);
        assertEquals(2, triggerNode.getMembers().size());
        List<VariableTree> variables = TreeNavigationUtils.findVariableTreeByName("adress", tree);
        assertEquals("The test class contains one field declaration with 'adress' name", 1, variables.size());
        List<MethodTree> methods = TreeNavigationUtils.findMethodTreeByName("myMethod", tree);
        assertEquals("The test class contains one method called 'doSomething'", 1, methods.size());
    }
    
    private boolean hasAnnotation(List<? extends AnnotationTree> annotations, String annotation) {
        boolean hasAnnotation = false;
        for (AnnotationTree annotationTree : annotations) {
            if (annotationTree.getAnnotationType() instanceof IdentifierTree) {
                IdentifierTree identifier = (IdentifierTree) annotationTree.getAnnotationType();
                if (identifier.getName().toString().equalsIgnoreCase(annotation)) {
                    hasAnnotation = true;
                    return hasAnnotation;
                }
            }
        }
        return hasAnnotation;
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private void assertChildTreeExists(Class child, BlockTree node) {
        for (Tree current : node.getStatements() ) {
            if (child.isInstance(current)) {
                assertTrue(String.format("%s is part of %s", child, node), Boolean.TRUE);
                return;
            }
        }
        assertFalse(String.format("%s couldn't be found in %s", child, node), Boolean.TRUE);
    }
}
