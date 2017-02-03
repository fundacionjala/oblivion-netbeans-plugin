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
import com.sun.source.tree.PackageTree;
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

/**
 * Adapter class that implements {@link TreeVisitor} with empty logic, so you just override the method that you are interested in.
 * 
 * @author Adrian Grajeda
 */
public abstract class ApexTreeVisitorAdapter<R,P> implements TreeVisitor<R, P> {

    @Override
    public R visitAnnotatedType(AnnotatedTypeTree att, P p) {
        return null;
    }

    @Override
    public R visitAnnotation(AnnotationTree at, P p) {
        return null;
    }

    @Override
    public R visitMethodInvocation(MethodInvocationTree mit, P p) {
        return null;
    }

    @Override
    public R visitAssert(AssertTree at, P p) {
        return null;
    }

    @Override
    public R visitAssignment(AssignmentTree at, P p) {
        return null;
    }

    @Override
    public R visitCompoundAssignment(CompoundAssignmentTree cat, P p) {
        return null;
    }

    @Override
    public R visitBinary(BinaryTree bt, P p) {
        return null;
    }

    @Override
    public R visitBlock(BlockTree bt, P p) {
        return null;
    }

    @Override
    public R visitBreak(BreakTree bt, P p) {
        return null;
    }

    @Override
    public R visitCase(CaseTree ct, P p) {
        return null;
    }

    @Override
    public R visitCatch(CatchTree ct, P p) {
        return null;
    }

    @Override
    public R visitClass(ClassTree ct, P p) {
        return null;
    }

    @Override
    public R visitConditionalExpression(ConditionalExpressionTree cet, P p) {
        return null;
    }

    @Override
    public R visitContinue(ContinueTree ct, P p) {
        return null;
    }

    @Override
    public R visitDoWhileLoop(DoWhileLoopTree dwlt, P p) {
        return null;
    }

    @Override
    public R visitErroneous(ErroneousTree et, P p) {
        return null;
    }

    @Override
    public R visitExpressionStatement(ExpressionStatementTree est, P p) {
        return null;
    }

    @Override
    public R visitEnhancedForLoop(EnhancedForLoopTree eflt, P p) {
        return null;
    }

    @Override
    public R visitForLoop(ForLoopTree flt, P p) {
        return null;
    }

    @Override
    public R visitIdentifier(IdentifierTree it, P p) {
        return null;
    }

    @Override
    public R visitIf(IfTree iftree, P p) {
        return null;
    }

    @Override
    public R visitImport(ImportTree it, P p) {
        return null;
    }

    @Override
    public R visitArrayAccess(ArrayAccessTree aat, P p) {
        return null;
    }

    @Override
    public R visitLabeledStatement(LabeledStatementTree lst, P p) {
        return null;
    }

    @Override
    public R visitLiteral(LiteralTree lt, P p) {
        return null;
    }

    @Override
    public R visitMethod(MethodTree mt, P p) {
        return null;
    }

    @Override
    public R visitModifiers(ModifiersTree mt, P p) {
        return null;
    }

    @Override
    public R visitNewArray(NewArrayTree nat, P p) {
        return null;
    }

    @Override
    public R visitNewClass(NewClassTree nct, P p) {
        return null;
    }

    @Override
    public R visitLambdaExpression(LambdaExpressionTree let, P p) {
        return null;
    }

    @Override
    public R visitParenthesized(ParenthesizedTree pt, P p) {
        return null;
    }

    @Override
    public R visitReturn(ReturnTree rt, P p) {
        return null;
    }

    @Override
    public R visitMemberSelect(MemberSelectTree mst, P p) {
        return null;
    }

    @Override
    public R visitMemberReference(MemberReferenceTree mrt, P p) {
        return null;
    }

    @Override
    public R visitEmptyStatement(EmptyStatementTree est, P p) {
        return null;
    }

    @Override
    public R visitSwitch(SwitchTree st, P p) {
        return null;
    }

    @Override
    public R visitSynchronized(SynchronizedTree st, P p) {
        return null;
    }

    @Override
    public R visitThrow(ThrowTree tt, P p) {
        return null;
    }

    @Override
    public R visitCompilationUnit(CompilationUnitTree cut, P p) {
        return null;
    }

    @Override
    public R visitTry(TryTree tt, P p) {
        return null;
    }

    @Override
    public R visitParameterizedType(ParameterizedTypeTree ptt, P p) {
        return null;
    }

    @Override
    public R visitUnionType(UnionTypeTree utt, P p) {
        return null;
    }

    @Override
    public R visitIntersectionType(IntersectionTypeTree itt, P p) {
        return null;
    }

    @Override
    public R visitArrayType(ArrayTypeTree att, P p) {
        return null;
    }

    @Override
    public R visitTypeCast(TypeCastTree tct, P p) {
        return null;
    }

    @Override
    public R visitPrimitiveType(PrimitiveTypeTree ptt, P p) {
        return null;
    }

    @Override
    public R visitTypeParameter(TypeParameterTree tpt, P p) {
        return null;
    }

    @Override
    public R visitInstanceOf(InstanceOfTree iot, P p) {
        return null;
    }

    @Override
    public R visitUnary(UnaryTree ut, P p) {
        return null;
    }

    @Override
    public R visitVariable(VariableTree vt, P p) {
        return null;
    }

    @Override
    public R visitWhileLoop(WhileLoopTree wlt, P p) {
        return null;
    }

    @Override
    public R visitWildcard(WildcardTree wt, P p) {
        return null;
    }

    @Override
    public R visitOther(Tree tree, P p) {
        return null;
    }

    @Override
    public R visitPackage(PackageTree pt, P p) {
        return null;
    }
}
