/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree.visitors;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;
import com.sun.source.tree.PackageTree;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link TreeVisitor} implementations that finds and collects all the {@link Tree} that matches
 * the given {@link Criteria}.
 * 
 * @author Adrian Grajeda
 */
public class TreeFinder<T extends Tree> implements TreeVisitor<List<T>, T> {
    private final Criteria criteria;
    private final List<T> result;

    public TreeFinder(Criteria criteria) {
        this.criteria = criteria;
        result = new ArrayList<>();
    }

    @Override
    public List<T> visitAnnotatedType(AnnotatedTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitAnnotation(AnnotationTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitMethodInvocation(MethodInvocationTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitAssert(AssertTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitAssignment(AssignmentTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitCompoundAssignment(CompoundAssignmentTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitBinary(BinaryTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitBlock(BlockTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitBreak(BreakTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitCase(CaseTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitCatch(CatchTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitClass(ClassTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitConditionalExpression(ConditionalExpressionTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitContinue(ContinueTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitDoWhileLoop(DoWhileLoopTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitErroneous(ErroneousTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitExpressionStatement(ExpressionStatementTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitEnhancedForLoop(EnhancedForLoopTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitForLoop(ForLoopTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitIdentifier(IdentifierTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitIf(IfTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitImport(ImportTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitArrayAccess(ArrayAccessTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitLabeledStatement(LabeledStatementTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitLiteral(LiteralTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitMethod(MethodTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitModifiers(ModifiersTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitNewArray(NewArrayTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitNewClass(NewClassTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitLambdaExpression(LambdaExpressionTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitParenthesized(ParenthesizedTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitReturn(ReturnTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitMemberSelect(MemberSelectTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitMemberReference(MemberReferenceTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitEmptyStatement(EmptyStatementTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitSwitch(SwitchTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitSynchronized(SynchronizedTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitThrow(ThrowTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitCompilationUnit(CompilationUnitTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitTry(TryTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitParameterizedType(ParameterizedTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitUnionType(UnionTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitIntersectionType(IntersectionTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitArrayType(ArrayTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitTypeCast(TypeCastTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitPrimitiveType(PrimitiveTypeTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitTypeParameter(TypeParameterTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitInstanceOf(InstanceOfTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitUnary(UnaryTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitVariable(VariableTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitWhileLoop(WhileLoopTree node, T p) {
        return checkForCriteria(node);
    }

    @Override
    public List<T> visitWildcard(WildcardTree node, T p) {
        return checkForCriteria(node);
    }
    

    @Override
    public List<T> visitOther(Tree node, T p) {
        return checkForCriteria(node);
    }

    public List<T> getResult() {
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private List<T> checkForCriteria(Tree node) {
        if (criteria.apply(node)) {
            result.add((T) node);
        }
        return result;
    }

    @Override
    public List<T> visitPackage(PackageTree pt, T p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Defines the criteria to apply in a given Tree. If the criteria match the node is added to the result list.
     * @param <T> the type of node to apply the criteria
     */
    public static interface Criteria {
        
        /**
         * Test if the node match with the criteria
         * @param tree the node
         * @return true if match the criteria, false other wise
         */
        boolean apply(Tree tree);

        /**
         * Checks if the given node is the expected type for this criteria.
         * 
         * @param node the node to validate the type
         * @return true if the node is of the expected type
         */
        boolean checkNodeType(Tree node);
    }
}
