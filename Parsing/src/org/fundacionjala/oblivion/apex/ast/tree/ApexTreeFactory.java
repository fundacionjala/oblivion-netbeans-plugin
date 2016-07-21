/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
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
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeKind;
import org.fundacionjala.oblivion.apex.grammar.ast.AccessorTree;
import org.fundacionjala.oblivion.apex.grammar.ast.CompoundStatementExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.ConstructorTree;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLMergeTree;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationEnum;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationTree;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLUpsertTree;
import org.fundacionjala.oblivion.apex.grammar.ast.PairExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.PropertyTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLConditionExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionSelectTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionFromTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionWhereTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLFieldExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLFromArgumentTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLGroupByExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLLimitExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLOffsetExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLOrderByExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLSelectFunctionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLSetExpressionValuesTree;
import org.fundacionjala.oblivion.apex.grammar.ast.TreeFactory;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryOperator;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLFindExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLInExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerCompilationUnitTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerDeclarationTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerOperation;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerParameterTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerType;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLObjectFieldExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLReturningExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLSearchGroupType;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLUpdateExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLUpdateType;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithDataCategoryExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithDivisionFilterExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithNetworkExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithSnippetExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.ModifiersTree;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;
import org.fundacionjala.oblivion.apex.grammar.parser.ModifierSet;

/**
 * Implements the {@link TreeFactory} for Apex AST
 *
 * @author Adrian Grajeda
 */
public class ApexTreeFactory implements TreeFactory {

    @Override
    public CompilationUnitTree createCompilationUnit(Tree typeDeclaration) {
        return new CompilationUnitTreeImpl(typeDeclaration);
    }

    @Override
    public ClassTree createClass(ModifiersTree modifier, List<Token> sharingRules, Token type, Token name, Token blockStart, Token blockEnd, List<? extends Tree> members) {
        return createClass(modifier, sharingRules, type, name, blockStart, blockEnd, members, new ArrayList<Tree>(), null);
    }

    @Override
    public ClassTree createClass(ModifiersTree modifier, List<Token> sharingRules, Token type, Token name, Token blockStart, Token blockEnd, List<? extends Tree> members, List<? extends Tree> implementsClause, Tree extendsClause) {
        return new ClassTreeImpl(modifier, convertList(sharingRules), createIdentifier(type), new TokenJCCAdapter(name), new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd), implementsClause, extendsClause, members);
    }

    @Override
    public ModifiersTree createModifier(LinkedHashMap<Integer, Token> modifier) {
        return new ModifiersTreeImpl(convertMap(modifier), Collections.<AnnotationTree>emptyList());
    }

    @Override
    public ModifiersTree createModifier(LinkedHashMap<Integer, Token> modifier, List<? extends AnnotationTree> annotations) {
        return new ModifiersTreeImpl(convertMap(modifier), annotations);
    }

    @Override
    public VariableTree createVariable(Tree type, Token name, ModifiersTree modifier) {
        return new VariableTreeImpl(type, new TokenJCCAdapter(name), modifier);
    }

    @Override
    public VariableTree createVariable(Tree type, Token name, ModifiersTree modifier, ExpressionTree expression) {
        VariableTreeImpl variable = new VariableTreeImpl(type, new TokenJCCAdapter(name), modifier);
        variable.setInitializer(expression);
        return variable;
    }

    @Override
    public SOQLFieldExpressionTree createSOQLFieldExpression(Name name, Token operator, ExpressionTree value) {
        return new SOQLFieldExpressionTreeImpl(name, operator, value);
    }

    @Override
    public AssignmentTree createAssignment(VariableTree variable, ExpressionTree value) {
        return new AssignmentTreeImpl(variable, value);
    }

    @Override
    public LiteralTree createIntegerLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.INT_LITERAL, new TokenJCCAdapter(value));
    }

    @Override
    public LiteralTree createDoubleLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.DOUBLE_LITERAL, new TokenJCCAdapter(value));
    }

    @Override
    public LiteralTree createBooleanLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.BOOLEAN_LITERAL, new TokenJCCAdapter(value));
    }

    @Override
    public LiteralTree createNullLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.NULL_LITERAL, new TokenJCCAdapter(value));
    }

    /**
     * Creates a tree node for a long literal expression.
     *
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    @Override
    public LiteralTree createLongLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.LONG_LITERAL, new TokenJCCAdapter(value));
    }

    @Override
    public MemberSelectTree createMemberSelect(Token identifier, ExpressionTree expression) {
        return new MemberSelectTreeImpl(new TokenJCCAdapter(identifier), expression);
    }

    @Override
    public MethodInvocationTree createMethodInvocation(ExpressionTree methodSelect, List<? extends ExpressionTree> arguments) {
        return new MethodInvocationTreeImpl(null, methodSelect, arguments);
    }

    @Override
    public BinaryTree createPlusOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.PLUS, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createMinusOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.MINUS, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createMultiplyOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.MULTIPLY, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createDivideOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.DIVIDE, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createRemainderOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.REMAINDER, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createLeftShiftOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.LEFT_SHIFT, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createRightShiftOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.RIGHT_SHIFT, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createLessThanOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.LESS_THAN, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createLessThanEqualOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.LESS_THAN_EQUAL, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createGreaterThanOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.GREATER_THAN, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createGreaterThanEqualOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.GREATER_THAN_EQUAL, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createEqualOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.EQUAL_TO, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createNotEqualOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.NOT_EQUAL_TO, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createAndOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.AND, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createConditionalAndOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.CONDITIONAL_AND, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createConditionalOROperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.CONDITIONAL_OR, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createOROperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.OR, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public BinaryTree createXOROperation(ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(Tree.Kind.XOR, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public UnaryTree createUnary(Kind kind, ExpressionTree expression, Token operator) {
        return new UnaryTreeImpl(kind, expression, new TokenJCCAdapter(operator));
    }

    @Override
    public BlockTree createBlock(StatementTree statement) {
        return createBlock(Collections.singleton(statement), false, null, null);
    }

    @Override
    public BlockTree createEmptyBlock() {
        return createBlock(Collections.EMPTY_SET, false, null, null);
    }

    @Override
    public StatementTree createStatement(VariableTree variableDeclaration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StatementTree createStatement(ClassTree nestedClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StatementTree createStatement(BlockTree block) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MethodTree createMethod(ModifiersTree modifier, Tree type, ExpressionTree identifier, BlockTree body, List<? extends VariableTree> parameters) {
        return new MethodTreeImpl(modifier, type, identifier, body, parameters);
    }

    @Override
    public MethodTree createMethod(ModifiersTree modifier, Tree type, ExpressionTree identifier, List<? extends VariableTree> parameters, Token endToken) {
        org.fundacionjala.oblivion.apex.Token startToken = null;
        if (type instanceof BaseTree) {
            startToken = ((BaseTree) type).getToken();
        }
        return new MethodTreeImpl(modifier, type, identifier, parameters, startToken, new TokenJCCAdapter(endToken));
    }

    @Override
    public ConditionalExpressionTree createConditional(ExpressionTree condition, ExpressionTree trueExpression, ExpressionTree falseExpression) {
        return new ConditionalExpressionTreeImpl(condition, trueExpression, falseExpression);
    }

    @Override
    public UnaryTree createPrefixIncrement(ExpressionTree expression, Token operator) {
        return new UnaryTreeImpl(Tree.Kind.PREFIX_INCREMENT, expression, new TokenJCCAdapter(operator));
    }

    @Override
    public UnaryTree createPrefixDecrement(ExpressionTree expression, Token operator) {
        return new UnaryTreeImpl(Tree.Kind.PREFIX_DECREMENT, expression, new TokenJCCAdapter(operator));
    }

    @Override
    public UnaryTree createPostfixIncrement(ExpressionTree expression, Token operator) {
        return new UnaryTreeImpl(Tree.Kind.POSTFIX_INCREMENT, expression, new TokenJCCAdapter(operator));
    }

    @Override
    public UnaryTree createPostfixDecrement(ExpressionTree expression, Token operator) {
        return new UnaryTreeImpl(Tree.Kind.POSTFIX_DECREMENT, expression, new TokenJCCAdapter(operator));
    }

    @Override
    public InstanceOfTree createInstanceOf(ExpressionTree expression, Tree type) {
        return new InstanceOfTreeImp(expression, type);
    }

    @Override
    public ParenthesizedTree createParenthesized(ExpressionTree exp) {
        return new ParenthesizedTreeImpl(exp);
    }

    @Override
    public TypeCastTree createTypeCast(ExpressionTree expression, Tree type) {
        return new TypeCastTreeImpl(expression, type);
    }

    @Override
    public Tree createType(Token token, TypeKind kind) {
        return new PrimitiveTypeTreeImpl(new TokenJCCAdapter(token), kind);
    }

    @Override
    public ClassTree createInterface(ModifiersTree modifier, Token name, List<? extends Tree> members, Tree extendsClause) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClassTree createEnum(Token name, List<? extends ExpressionTree> body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModifiersTree createModifier(int modifier, Token name, ModifierSet modifiers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IdentifierTree createIdentifier(Name name) {
        return new IdentifierTreeImpl((org.fundacionjala.oblivion.apex.ast.tree.Name)name);
    }
    
    @Override
    public IdentifierTree createIdentifier(Name name, Token modifier) {
        return new IdentifierTreeImpl((org.fundacionjala.oblivion.apex.ast.tree.Name)name, new TokenJCCAdapter(modifier));
    }

    @Override
    public IdentifierTree createIdentifier(Token name) {
        return new IdentifierTreeImpl(new TokenJCCAdapter(name));
    }

    @Override
    public IdentifierTree createIdentifier(List<IdentifierTree> names) {
        return new IdentifierTreeImpl(names);
    }

    @Override
    public SOQLExpressionSelectTree createSOQLExpressionSelect(List<IdentifierTree> identifiers, List<SOQLSelectFunctionTree> functions, List<SOQLExpressionTree> querys) {
        return new SOQLExpressionSelectTreeImpl(identifiers, functions, querys);
    }

    @Override
    public SOQLExpressionFromTree createSOQLExpressionFrom(List<SOQLFromArgumentTree> arguments) {
        return new SOQLExpressionFromTreeImpl(arguments);
    }

    @Override
    public SOQLFromArgumentTree createSOQLFromArgumentTree(IdentifierTree argument, IdentifierTree alias) {
        return new SOQLFromArgumentTreeImpl(argument, alias);
    }

    @Override
    public SOQLExpressionWhereTree createSOQLExpressionWhere(ExpressionTree optionsStatementWhere) {
        return new SOQLExpressionWhereTreeImpl(optionsStatementWhere);
    }

    @Override
    public SOQLOrderByExpressionTree createSOQLOrderByExpression(List<IdentifierTree> identifiers) {
        return new SOQLOrderByExpressionTreeImpl(identifiers);
    }
    
    @Override
    public SOQLGroupByExpressionTree createSOQLGroupByExpression(List<IdentifierTree> identifiers, List<MethodInvocationTree> methodInvocations) {
        return new SOQLGroupByExpressionTreeImpl(identifiers,methodInvocations);
    }
    
    @Override
    public SOQLConditionExpressionTree createSOQLConditionExpression(ExpressionTree SoqlConditionExpression) {
        return new SOQLConditionExpressionTreeImpl(SoqlConditionExpression);
    }

    @Override
    public SOQLSetExpressionValuesTree createSOQLSetExpressionValues(List<ExpressionTree> values) {
        return new SOQLSetExpressionValuesTreeImpl(values);
    }

    @Override
    public SOQLLimitExpressionTree createSOQLLimitExpressionTree(ExpressionTree limit) {
        return new SOQLLimitExpressionTreeImpl(limit);
    }

    @Override
    public SOQLOffsetExpressionTree createSOQLOffsetExpressionTree(ExpressionTree offset) {
        return new SOQLOffsetExpressionTreeImpl(offset);
    }

    @Override
    public DMLOperationTree createDMLOperation(DMLOperationEnum dmlOperationEnum) {
        return new DMLOperationTreeImpl(dmlOperationEnum);
    }

    @Override
    public DMLUpsertTree createDMLUpsert(DMLOperationEnum dmlOperationEnum, ExpressionTree optionalExpression) {
        return new DMLUpsertTreeImpl(dmlOperationEnum, optionalExpression);
    }

    @Override
    public DMLMergeTree createDMLMerge(DMLOperationEnum dmlOperationEnum, ExpressionTree mergeRecordsExpression) {
        return new DMLMergeTreeImpl(dmlOperationEnum, mergeRecordsExpression);
    }

    @Override
    public CompoundStatementExpressionTree createCompoundStatementExpression(StatementTree parentStatement, List<StatementTree> childrenStatements) {
        return new CompoundStatementExpressionTreeImpl(parentStatement, childrenStatements);
    }

    @Override
    public SOQLSelectFunctionTree createSOQLSelectFunction(Name name, Name field, Name alias) {
        return new SOQLSelectFunctionTreeImpl(name, field, alias);
    }

    @Override
    public AccessorTree createAccessor(ModifiersTree modifier, IdentifierTree accessor, BlockTree body) {
        return new AccessorTreeImpl(modifier, accessor, body);
    }

    @Override
    public AccessorTree createAccessor(ModifiersTree modifier, IdentifierTree accessor, Token endToken) {
        org.fundacionjala.oblivion.apex.Token startToken = null;
        if (accessor instanceof BaseTree) {
            startToken = ((BaseTree) accessor).getToken();
        }
        return new AccessorTreeImpl(modifier, accessor, startToken, new TokenJCCAdapter(endToken));
    }

    @Override
    public PropertyTree createProperty(ModifiersTree modifier, Tree type, Token identifier, Token blockStart, Token blockEnd, List<? extends AccessorTree> accessors) {
        return new PropertyTreeImpl(modifier, type, new TokenJCCAdapter(identifier), accessors, new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd));
    }

    @Override
    public ArrayAccessTree createArrayAccess(ExpressionTree expression, ExpressionTree index) {
        return new ArrayAccessTreeImpl(expression, index);
    }

    @Override
    public CompoundAssignmentTree createCompoundAssignment(ExpressionTree variable, ExpressionTree assignment, Token operator) {
        TokenJCCAdapter wrapper = new TokenJCCAdapter(operator);
        Tree.Kind kind = OperatorUtils.parseTokenToKind(wrapper);
        return new CompoundAssignmentTreeImpl(kind, wrapper, variable, assignment);
    }

    @Override
    public StatementTree createStaticStatement(StatementTree block) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public org.fundacionjala.oblivion.apex.ast.tree.Name createName(List<ExpressionTree> identifiers) {
        return TreeUtils.createName(identifiers);
    }

    @Override
    public NewArrayTree createNewArray(Token blockStart, Token blockEnd, Tree type, ExpressionTree dimension) {
        return new NewArrayTreeImpl(new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd), type, dimension);
    }

    @Override
    public NewArrayTree createNewArray(Token blockStart, Token blockEnd, Tree type, List<? extends ExpressionTree> initializers) {
        return new NewArrayTreeImpl(new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd), type, initializers);
    }

    @Override
    public NewClassTree createNewClass(ExpressionTree type, Token blockStart, Token blockEnd, List<? extends ExpressionTree> arguments) {
        return new NewClassTreeImpl(type, new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd), arguments);
    }

    @Override
    public NewClassTree createNewCollection(Token type, Token blockStart, Token blockEnd, List<? extends Tree> typeArguments, List<? extends ExpressionTree> arguments) {
        return new NewClassTreeImpl(createIdentifier(type), new TokenJCCAdapter(blockStart), new TokenJCCAdapter(blockEnd), typeArguments, arguments);
    }

    @Override
    public PairExpressionTree createPair(ExpressionTree key, ExpressionTree value) {
        return new PairExpressionTreeImpl(key, value);
    }

    @Override
    public EmptyStatementTree createEmptyStatement() {
        return new EmptyStatementTreeImpl();
    }

    @Override
    public ExpressionStatementTree createExpressionStatement(ExpressionTree expression) {
        return new ExpressionStatementTreeImpl(expression);
    }

    @Override
    public IfTree createIf(ExpressionTree condition, StatementTree thenStatement) {
        return createIf(condition, thenStatement, null);
    }

    @Override
    public IfTree createIf(ExpressionTree condition, StatementTree thenStatement, StatementTree elseStatement) {
        return new IfTreeImpl(condition, thenStatement, elseStatement);
    }

    @Override
    public SOQLExpressionTree createSOQLExpression(SOQLExpressionSelectTree selectSOQLExpression, SOQLExpressionFromTree fromSOQLExpression, List<ExpressionTree> optionalStatements) {
        return new SOQLExpressionTreeImpl(selectSOQLExpression, fromSOQLExpression, optionalStatements);
    }

    @Override
    public WhileLoopTree createWhileLoop(ExpressionTree condition, StatementTree statement) {
        return new WhileLoopTreeImpl(condition, statement);
    }

    @Override
    public DoWhileLoopTree createDoWhileLoop(ExpressionTree condition, StatementTree statement, Token startToken, Token endToken) {
        return new DoWhileLoopTreeImpl(condition, statement, new TokenJCCAdapter(startToken), new TokenJCCAdapter(endToken));
    }

    @Override
    public EnhancedForLoopTree createEnhancedForLoop(VariableTree variable, ExpressionTree expression, StatementTree statement) {
        return new EnhancedForLoopTreeImpl(variable, expression, statement);
    }

    @Override
    public BreakTree createBreak() {
        return new BreakTreeImpl();
    }

    @Override
    public ContinueTree createContinue() {
        return new ContinueTreeImpl();
    }

    @Override
    public ReturnTree createReturn(ExpressionTree expression) {
        return new ReturnTreeImpl(expression);
    }

    @Override
    public ThrowTree createThrow(ExpressionTree expression) {
        return new ThrowTreeImpl(expression);
    }

    @Override
    public CatchTree createCatch(VariableTree parameter, BlockTree block) {
        return new CatchTreeImpl(parameter, block);
    }

    @Override
    public TryTree createTry(BlockTree tryBlock, List<? extends CatchTree> catches, BlockTree finallyBlock) {
        return new TryTreeImpl(tryBlock, catches, finallyBlock);
    }

    @Override
    public LiteralTree createStringLiteral(Token value) {
        return new LiteralTreeImpl(Tree.Kind.STRING_LITERAL, new TokenJCCAdapter(value));
    }

    @Override
    public BinaryTree createBinary(Tree.Kind kind, ExpressionTree left, ExpressionTree right, Token operator) {
        return new BinaryTreeImpl(kind, new TokenJCCAdapter(operator), left, right);
    }

    @Override
    public ForLoopTree createForLoop(List<VariableTree> initializer, ExpressionTree condition, List<ExpressionStatementTree> update, StatementTree statement) {
        return new ForLoopTreeImpl(initializer, condition, update, statement);
    }

    @Override
    public ArrayTypeTree createArrayType(Tree type) {
        return new ArrayTypeTreeImpl(type);
    }

    @Override
    public PrimitiveTypeTree createPrimitiveType(Token type, TypeKind kind) {
        return new PrimitiveTypeTreeImpl(new TokenJCCAdapter(type), kind);
    }

    @Override
    public BinaryTree createUnsignedRightShiftOperation(ExpressionTree left, ExpressionTree right, Token operator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParameterizedTypeTree createGenericType(Tree type, List<Tree> arguments) {
        return new ParameterizedTypeTreeImpl(type, arguments);
    }

    @Override
    public ErroneousTree createErroneousExpression() {
        return new ErroneousTreeImpl();
    }

    @Override
    public AnnotationTree createAnnotation(Token symbol, Name annotationType, List<? extends ExpressionTree> arguments) {
        return new AnnotationTreeImpl(new TokenJCCAdapter(symbol), createIdentifier(annotationType), arguments);
    }

    @Override
    public AnnotationTree createAnnotation(Name annotationType, List<? extends ExpressionTree> arguments) {
        return new AnnotationTreeImpl(createIdentifier((org.fundacionjala.oblivion.apex.ast.tree.Name)annotationType), arguments);
    }

    @Override
    public AnnotationTree createAnnotation(Token annotationTypeToken) {
        return new AnnotationTreeImpl(createIdentifier(annotationTypeToken), Collections.<ExpressionTree>emptyList());
    }

    @Override
    public BlockTree createBlock(Collection<StatementTree> statements, boolean isStatic, Token start, Token end) {
        return new BlockTreeImpl(statements, isStatic, new TokenJCCAdapter(start), new TokenJCCAdapter(end));
    }

    @Override
    public Tree createEmptyClass() {
        return new ClassTreeImpl(null, null, null, null, null, null, null, null, Collections.<Tree>emptyList());
    }

    @Override
    public ConstructorTree createConstructor(ModifiersTree modifier, IdentifierTree name, List<? extends VariableTree> parameters, Collection<StatementTree> statements, Token start, Token end) {
        return new ConstructorTreeImpl(modifier, name, parameters, createBlock(statements, false, start, end));
    }

    @Override
    public TriggerCompilationUnitTree createTriggerCompilationUnit(Tree tree) {
        return new TriggerCompilationUnitTreeImpl(tree);
    }

    @Override
    public TriggerDeclarationTree createTriggerDeclaration(Token token, Token token1, List<TriggerParameterTree> list, List<? extends Tree> list1) {
        return new TriggerDeclarationTreeImpl(null, new IdentifierTreeImpl(new TokenJCCAdapter(token)), new IdentifierTreeImpl(new TokenJCCAdapter(token1)), list, list1);
    }

    @Override
    public TriggerParameterTree createTriggerParameterTree(TriggerType tt, TriggerOperation to) {
        return new TriggerParameterTreeImpl(tt, to);
    }

    @Override
    public Tree createEmptyTrigger() {
        return new TriggerDeclarationTreeImpl(null, null, null, Collections.<TriggerParameterTree>emptyList(), Collections.<Tree>emptyList());
    }

    @Override
    public SOSLExpressionTree createSOSLExpression(SOSLFindExpressionTree slft, List<ExpressionTree> list) {
        return new SOSLExpressionTreeImpl(slft, list);
    }

    @Override
    public SOSLFindExpressionTree createSOSLFindExpression(LiteralTree lt) {
        return new SOSLFindExpressionTreeImpl(lt);
    }

    @Override
    public SOSLReturningExpressionTree createSOSLReturningExpression(List<SOSLObjectFieldExpressionTree> list) {
        return new SOSLReturningExpressionTreeImpl(list);
    }

    @Override
    public SOSLObjectFieldExpressionTree createSOSLObjectFieldExpression(IdentifierTree it, List<IdentifierTree> list, List<ExpressionTree> list1) {
        return new SOSLObjectFieldExpressionTreeImpl(it, list, list1);
    }

    @Override
    public SOSLInExpressionTree createSOSLInExpression(SOSLSearchGroupType slsgt) {
        return new SOSLInExpressionTreeImpl(slsgt);
    }

    @Override
    public SOSLUpdateExpressionTree createSOSLUpdateExpression(SOSLUpdateType soslut) {
        return new SOSLUpdateExpressionTreeImpl(soslut);
    }

    @Override
    public SOSLWithDivisionFilterExpressionTree createSOSLWithDivisionFilterExpression(LiteralTree lt) {
        return new SOSLWithDivisionFilterExpressionTreeImpl(lt);
    }

    @Override
    public SOSLDataCategoryExpressionTree createSOSLDataCategoryExpression(IdentifierTree it, SOSLDataCategoryOperator sldc, IdentifierTree it1) {
        return new SOSLDataCategoryExpressionTreeImpl(it, sldc, it1);
    }

    @Override
    public SOSLWithDataCategoryExpressionTree createSOSLWithDataCategoryExpression(ExpressionTree et) {
        return new SOSLWithDataCategoryExpressionTreeImpl(et);
    }

    @Override
    public SOSLWithNetworkExpressionTree createSOSLWithNetworkExpression(List<LiteralTree> list) {
        return new SOSLWithNetworkExpressionTreeImpl(list);
    }

    @Override
    public SOSLWithSnippetExpressionTree createSOSLWithSnippetExpression(LiteralTree lt) {
        return new SOSLWithSnippetExpressionTreeImpl(lt);
    }

    @Override
    public void setSemicolonToVariableTree(List<VariableTree> variables, Token semicolon) {
        if (variables.size() > 0) {
            ((VariableTreeImpl) variables.get(0)).setSemiColon(new TokenJCCAdapter(semicolon));
        }
    }

    private LinkedHashMap<Integer, org.fundacionjala.oblivion.apex.Token> convertMap(LinkedHashMap<Integer, Token> tokenMap) {
        LinkedHashMap<Integer, org.fundacionjala.oblivion.apex.Token> tokenAdapterMap = new LinkedHashMap<>();

        if (tokenMap != null) {
            for (Map.Entry<Integer, Token> token : tokenMap.entrySet()) {
                Integer key = token.getKey();
                Token value = token.getValue();

                tokenAdapterMap.put(key, new TokenJCCAdapter(value));
            }
        }

        return tokenAdapterMap;
    }

    private List<org.fundacionjala.oblivion.apex.Token> convertList(List<Token> tokenList) {
        List<org.fundacionjala.oblivion.apex.Token> tokenAdapterMap = new ArrayList<>();
        if (tokenList != null) {
            for (Token token : tokenList) {
                tokenAdapterMap.add(new TokenJCCAdapter(token));
            }
        }

        return tokenAdapterMap;
    }

    private static final class TokenJCCAdapter implements org.fundacionjala.oblivion.apex.Token {

        private final org.fundacionjala.oblivion.apex.grammar.jcclexer.Token original;

        public TokenJCCAdapter(Token original) {
            this.original = original;
        }

        @Override
        public int getId() {
            return original != null ? original.kind : -1;
        }

        @Override
        public String getImage() {
            return original != null ? original.image : "";
        }

        @Override
        public int getBeginLine() {
            return original != null ? original.beginLine : -1;
        }

        @Override
        public int getBeginColumn() {
            return original != null ? original.beginColumn : -1;
        }

        @Override
        public int getEndLine() {
            return original != null ? original.endLine : -1;
        }

        @Override
        public int getEndColumn() {
            return original != null ? original.endColumn : -1;
        }
    }
}
