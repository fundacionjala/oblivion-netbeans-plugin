/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
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
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;
import org.fundacionjala.oblivion.apex.grammar.parser.ModifierSet;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeKind;
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
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryOperator;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLFindExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLInExpressionTree;

/**
 * Defines necessary methods to create tree nodes.
 *
 * @see http://docs.oracle.com/javase/8/docs/jdk/api/javac/tree/index.html?com/sun/source/tree/package-summary.html
 * @author Adrian Grajeda
 * @author Maria Garcia
 */
public interface TreeFactory {

    /**
     * Creates a tree node for CompilationUnit.
     * @param typeDeclaration
     * @return A new CompilationUnitTree object
     */
    CompilationUnitTree createCompilationUnit(Tree typeDeclaration);

    /**
     * Creates a tree node for an Interface.
     * @param modifier
     * @param name
     * @param members
     * @param extendsClause
     * @return A new ClassTree object
     */
    ClassTree createInterface(ModifiersTree modifier, Token name, List<? extends Tree> members,
        Tree extendsClause);

    /**
     * Creates a tree node for an Enum
     * @param name
     * @param body
     * @return A new ClassTree object
     */
    ClassTree createEnum(Token name, List<? extends ExpressionTree> body);

    /**
     * Creates a tree node for a class, interface or enum.
     * @param modifier The class access modifier
     * @param sharingRules
     * @param type
     * @param name The class identifier
     * @param blockStart
     * @param blockEnd
     * @param members The class members
     * @return A new ClassTree object
     */
    ClassTree createClass(ModifiersTree modifier, List<Token> sharingRules, Token type, Token name, Token blockStart, Token blockEnd, List<? extends Tree> members);

    /**
     * Creates a tree node for a class, interface or enum.
     * @param modifier The class access modifier
     * @param sharingRules
     * @param type
     * @param name The class identifier
     * @param blockStart
     * @param blockEnd
     * @param members The class members
     * @param implementsClause The list of types from which the class implements
     * @param extendsClause The type from which the class extends
     * @return A new ClassTree object
     */
    ClassTree createClass(ModifiersTree modifier, List<Token> sharingRules, Token type, Token name, Token blockStart, Token blockEnd, List<? extends Tree> members,
        List<? extends Tree> implementsClause, Tree extendsClause);

    /**
     * Creates a tree node for an access modifier.
     * @param modifier The id from ModifiersSet that identifies it
     * @return A new ModifiersTree object
     */
    ModifiersTree createModifier(LinkedHashMap<Integer, Token> modifier);

    /**
     * Creates a tree node for an access modifier.
     * @param modifier The id from ModifiersSet that identifies it
     * @param annotations
     * @return A new ModifiersTree object
     */
    ModifiersTree createModifier(LinkedHashMap<Integer, Token> modifier, List<? extends AnnotationTree> annotations);
    
    /**
     * Creates a tree node for a constructor.
     * @param modifier The method's access modifier
     * @param name The method identifier
     * @param parameters The constructor's parameters
     * @param statements The constructor's statements
     * @param start
     * @param end
     * @return A new MethodTree object
     */
    ConstructorTree createConstructor(ModifiersTree modifier, IdentifierTree name, List<? extends VariableTree> parameters, Collection<StatementTree> statements, Token start, Token end);

    /**
     * Creates a tree node for an access modifier.
     * @param modifier The id from ModifiersSet that identifies it
     * @param name The token that is relate to
     * @param modifiers The modifiers set that contains all access modifiers.
     * @return A new ModifiersTree object
     */
    ModifiersTree createModifier(int modifier, Token name, ModifierSet modifiers);

    /**
     * Creates a tree node for an identifier expression.
     * @param name
     * @return
     */
    IdentifierTree createIdentifier(Name name);
    IdentifierTree createIdentifier(Name name,Token modifier);
    IdentifierTree createIdentifier(Token identifier);
    IdentifierTree createIdentifier(List<IdentifierTree> identifiers);
    
     /**
     * Creates a tree node for a SOQL Select.
     * @param identifiersSelect
     * @param functions
     * @param querys
     * @return
     */
    SOQLExpressionSelectTree createSOQLExpressionSelect(List<IdentifierTree> identifiersSelect, List<SOQLSelectFunctionTree> functions, List<SOQLExpressionTree> querys);
    
    /**
     * Creates a tree node for a SOQL From.
     * @param arguments The list that contain all arguments
     * @return
     */
    SOQLExpressionFromTree createSOQLExpressionFrom(List<SOQLFromArgumentTree> arguments);
    
    /**
     * Creates a tree node for a SOQL From Argument.
     * @param identifier Identifier for specify field
     * *@param alias The alias that will be assigned to the identifier
     * @param alias
     * @return
     */
    SOQLFromArgumentTree createSOQLFromArgumentTree(IdentifierTree identifier, IdentifierTree alias);
    
    /**
     * Creates a tree node for a SOQL Where.
     * @param optionsStatementWhere 
     * @return
     */
    SOQLExpressionWhereTree createSOQLExpressionWhere(ExpressionTree optionsStatementWhere);
    
    /**
     * Creates a tree node for a SOQL OrderByExpression.
     * @param identifiers
     * @return
     */
    SOQLOrderByExpressionTree createSOQLOrderByExpression(List<IdentifierTree> identifiers);
    
    /**
     * Creates a tree node for a SOQL GroupByExpression.
     * @param identifier
     * @return 
     */
    SOQLGroupByExpressionTree createSOQLGroupByExpression(List<IdentifierTree> identifiers, List<MethodInvocationTree> methodInvocations);

    /**
     * Create a tree node for 'DMLOperator' statement
     * @param dmlOperationEnum
     * @return 
     */
    DMLOperationTree createDMLOperation(DMLOperationEnum dmlOperationEnum);
    
    /**
     * Create a tree node for 'DMLUpsert' statement
     * @param dmlOperationEnum
     * @param optionalExpression
     * @return 
     */
    DMLUpsertTree createDMLUpsert(DMLOperationEnum dmlOperationEnum, ExpressionTree optionalExpression);
    
    /**
     * Create a tree node for 'DMLMerge' statement
     * @param dmlOperationEnum
     * @param mergeRecordsExpression
     * @return 
     */
    DMLMergeTree createDMLMerge(DMLOperationEnum dmlOperationEnum, ExpressionTree mergeRecordsExpression);
    
    /**
     * Create a tree node for 'CompoundStatementExpressionTree' statement
     * @param parentStatement
     * @param childrenStatements
     * @return a CompoundStatementExpressionTree
     */
    CompoundStatementExpressionTree createCompoundStatementExpression(StatementTree parentStatement, List<StatementTree> childrenStatements);

    Tree createArrayType(Tree type);

    PrimitiveTypeTree createPrimitiveType(Token type, TypeKind kind);

    AccessorTree createAccessor(ModifiersTree modifier, IdentifierTree accessor, BlockTree body);
    
    AccessorTree createAccessor(ModifiersTree modifier, IdentifierTree accessor, Token endToken);

    /**
     * Creates a tree node for a property.
     * @param modifier
     * @param type
     * @param identifier
     * @param blockStart
     * @param blockEnd
     * @param accessors
     * @return
     */
    PropertyTree createProperty(ModifiersTree modifier, Tree type, Token identifier, Token blockStart, Token blockEnd, List<? extends AccessorTree> accessors);
    
    /**
     * 
     * @param name
     * @param field
     * @param alias
     * @return 
     */
    SOQLSelectFunctionTree createSOQLSelectFunction(Name name, Name field, Name alias);    

    /**
     * Creates a tree node for a variable declaration.
     * @param type The type of the variable
     * @param name The variable identifier
     * @param modifier The variable access modifier
     * @return A new VariableTree object
     */
    VariableTree createVariable(Tree type, Token name, ModifiersTree modifier);
    
    /**
     * Creates a a tree node for a variable declaration that has an initializer.
     * @param type
     * @param name
     * @param modifier
     * @param expression
     * @return A new VariableTree object
     */
    VariableTree createVariable(Tree type, Token name, ModifiersTree modifier, ExpressionTree expression);
    
    /**
     * Creates a tree node for a variable declaration.
     * @param name The variable identifier
     * @param operator
     * @param value names's value
     * @return A new VariableTree object
     */
    SOQLFieldExpressionTree createSOQLFieldExpression(Name name, Token operator, ExpressionTree value);

    /**
     * Creates a tree node for an assignment expression.
     * @param variable The variable that contains the expression
     * @param value The value that is assigned to the variable
     * @return A new AssignmentTree object
     */
    AssignmentTree createAssignment(VariableTree variable, ExpressionTree value);

    LiteralTree createStringLiteral(Token value);

    /**
     * Creates a tree node for a integer literal expression.
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    LiteralTree createIntegerLiteral(Token value);

    /**
     * Creates a tree node for a double literal expression.
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    LiteralTree createDoubleLiteral(Token value);

    /**
     * Creates a tree node for a boolean literal expression.
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    LiteralTree createBooleanLiteral(Token value);

    /**
     * Creates a tree node for a null literal expression.
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    LiteralTree createNullLiteral(Token value);

    /**
     * Creates a tree node for a long literal expression.
     * @param value The token that contains the literal value
     * @return A new LiteralTree object
     */
    LiteralTree createLongLiteral(Token value);
    
    ArrayAccessTree createArrayAccess(ExpressionTree expression, ExpressionTree index);

    /**
     * Creates a tree node for a member access expression.
     * Ex. expression.identifier
     * @param identifier
     * @param expression
     * @return A new MemberSelectTree object
     */
    MemberSelectTree createMemberSelect(Token identifier, ExpressionTree expression);

    /**
     * Creates a tree node for a method invocation.
     * @param methodSelect
     * @param arguments
     * @return A new MethodInvocationTree object
     */
    MethodInvocationTree createMethodInvocation(ExpressionTree methodSelect, List<? extends ExpressionTree> arguments);

    /**
     * Creates a tree node for a unary expression
     * @param kind
     * @param expression
     * @param operator
     * @return
     */
    UnaryTree createUnary(Tree.Kind kind, ExpressionTree expression, Token operator);

    BinaryTree createBinary(Tree.Kind kind, ExpressionTree left, ExpressionTree right, Token operator);

    /**
     * Creates a tree node for a compound assignment operator.
     * @param variable
     * @param assignment
     * @param operator
     * @return
     */
    CompoundAssignmentTree createCompoundAssignment(ExpressionTree variable, ExpressionTree assignment, Token operator);

    /**
     * Creates a tree node for a conditional expression.
     * @param condition The condition to be evaluate
     * @param trueExpression The expression when the condition is true
     * @param falseExpression The expression when the condition is false
     * @return A new ConditionalExpressionTree object
     */
    ConditionalExpressionTree createConditional(ExpressionTree condition, ExpressionTree trueExpression,
        ExpressionTree falseExpression);

    /**
     * Creates a tree node for a prefix increment expression
     * @param expression
     * @param operator
     * @return A new UnaryTree object
     */
    UnaryTree createPrefixIncrement(ExpressionTree expression, Token operator);

    /**
     * Creates a tree node for a prefix decrement expression
     * @param expression
     * @param operator
     * @return
     */
    UnaryTree createPrefixDecrement(ExpressionTree expression, Token operator);

    /**
     * Creates a tree node for a postfix increment expression
     * @param expression
     * @param operator
     * @return
     */
    UnaryTree createPostfixIncrement(ExpressionTree expression, Token operator);

    /**
     * Creates a tree node for a postfix decrement expression
     * @param expression
     * @param operator
     * @return
     */
    UnaryTree createPostfixDecrement(ExpressionTree expression, Token operator);

    /**
     * Creates a tree node for an "instanceof" expression.
     * @param expression
     * @param type
     * @return A new InstanceOfTree object
     */
    InstanceOfTree createInstanceOf(ExpressionTree expression, Tree type);

    /**
     * Creates a tree node for a parenthesized expression.
     * @param exp
     * @return A new ParenthesizedTree object
     */
    ParenthesizedTree createParenthesized(ExpressionTree exp);

    /**
     * Creates a tree node for a type cast expression.
     * @param expression
     * @param type
     * @return A new TypeCastTree object
     */
    TypeCastTree createTypeCast(ExpressionTree expression, Tree type);
    
    /**
     * Creates a tree node for a block
     * @param statements
     * @param isStatic
     * @param start
     * @param end
     * @return A new BlockTree object
     */
    BlockTree createBlock(Collection<StatementTree> statements, boolean isStatic, Token start, Token end);
    
    /**
     * Creates a tree node for a block with a single statement.
     * @param statement
     * @return A new BlockTree object
     */
    BlockTree createBlock(StatementTree statement);
    
    /**
     * Creates a tree node for a block with a no statements.
     * @return A new BlockTree object
     */
    BlockTree createEmptyBlock();

    /**
     * Creates a tree node for a statement based on a variable declaration.
     * @param variableDeclaration
     * @return A new StatementTree object
     */
    StatementTree createStatement(VariableTree variableDeclaration);

    /**
     * Creates a tree node for a statement based on a class declaration.
     * @param nestedClass
     * @return A new StatementTree object
     */
    StatementTree createStatement(ClassTree nestedClass);

    /**
     * Creates a tree node for a statement based on a block.
     * @param block
     * @return A new StatementTree object
     */
    StatementTree createStatement(BlockTree block);

    /**
     * Creates a tree node for a static statement based on a block statement.
     * @param block
     * @return
     */
    StatementTree createStaticStatement(StatementTree block);
    
    /**
     * Creates a tree node for a method that has parameters.
     * @param modifier The method's access modifier
     * @param type The method return type
     * @param name The method identifier
     * @param body The content of the method
     * @param parameters The list of formal parameters
     * @return A new MethodTree object
     */
    MethodTree createMethod(ModifiersTree modifier, Tree type, ExpressionTree name, BlockTree body,
            List<? extends VariableTree> parameters);
    
    MethodTree createMethod(ModifiersTree modifier, Tree type, ExpressionTree identifier, 
            List<? extends VariableTree> parameters, Token endToken);

    /**
     *
     * @param identifiers
     * @return
     */
    Name createName(List<ExpressionTree> identifiers);

    /**
     * Creates a tree node for an expression to create a new instance of an array.
     * @param blockStart
     * @param blockEnd
     * @param type
     * @param dimension
     * @return
     */
    NewArrayTree createNewArray(Token blockStart, Token blockEnd, Tree type, ExpressionTree dimension);

    /**
     * Creates a tree node for an expression to create a new instance of an array.
     * @param blockStart
     * @param type
     * @param blockEnd
     * @param initializer
     * @return
     */
    NewArrayTree createNewArray(Token blockStart, Token blockEnd, Tree type, List<? extends ExpressionTree> initializer);

    /**
     * Creates a tree node to declare a new instance of a class.
     * @param type
     * @param blockStart
     * @param blockEnd
     * @param arguments
     * @return
     */
    NewClassTree createNewClass(ExpressionTree type, Token blockStart, Token blockEnd, List<? extends ExpressionTree> arguments);
    
    /**
     * Creates a tree node to declare a new instance of a Collection type.
     * @param type
     * @param blockStart
     * @param blockEnd
     * @param typeArguments 
     * @param arguments
     * @return
     */
    NewClassTree createNewCollection(Token type, Token blockStart, Token blockEnd, List<? extends Tree> typeArguments, List<? extends ExpressionTree> arguments);

    
    PairExpressionTree createPair(ExpressionTree key, ExpressionTree value);
    
    /**
     * Creates a tree node for an empty statement.
     * @return a new EmptyStatementTree object
     */
    EmptyStatementTree createEmptyStatement();

    /**
     * Creates a tree node for an expression statement.
     * @param expression
     * @return
     */
    ExpressionStatementTree createExpressionStatement(ExpressionTree expression);

    /**
     * Creates a tree node for an 'if' statement
     * @param condition
     * @param thenStatement
     * @return
     */
    IfTree createIf(ExpressionTree condition, StatementTree thenStatement);

    /**
     * Creates a tree node for an 'if' statement that has also an 'else' statement.
     * @param condition
     * @param thenStatement
     * @param elseStatement
     * @return
     */
    IfTree createIf(ExpressionTree condition, StatementTree thenStatement, StatementTree elseStatement);
    
    /**
     * Creates a tree node for an expression SOQL.
     * @param selectSOQLExpression
     * @param fromSOQLExpression
     * @param optionalStatements
     * @return
     */
    SOQLExpressionTree createSOQLExpression(SOQLExpressionSelectTree selectSOQLExpression, SOQLExpressionFromTree fromSOQLExpression, List <ExpressionTree> optionalStatements);
    
    /**
     * Creates a tree node for an expression SOQL.
     * @param SoqlConditionExpression
     * @return
     */
    SOQLConditionExpressionTree createSOQLConditionExpression(ExpressionTree SoqlConditionExpression);
    
    /**
     * Creates a tree node for an expression SOQL SetExpressionValues '(' VALUE (',' VALUE)* ')' .
     * @param values 
     * @return
     */
    SOQLSetExpressionValuesTree createSOQLSetExpressionValues(List <ExpressionTree> values );
    
    /**
     * Creates a tree node for an SOQL LIMIT expression.
     * @param limit
     * @return
     */
    SOQLLimitExpressionTree createSOQLLimitExpressionTree(ExpressionTree limit);
    
    /**
     * Creates a tree node for an SOQL OFFSET expression.
     * @param offset
     * @return
     */
    SOQLOffsetExpressionTree createSOQLOffsetExpressionTree(ExpressionTree offset);

    /**
     * Creates a tree node for a 'while' loop statement.
     * @param condition
     * @param statement
     * @return
     */
    WhileLoopTree createWhileLoop(ExpressionTree condition, StatementTree statement);

    /**
     * Creates a tree node for a 'do' statement
     * @param condition
     * @param statement
     * @param startToken
     * @param endToken
     * @return
     */
    DoWhileLoopTree createDoWhileLoop(ExpressionTree condition, StatementTree statement, Token startToken, Token endToken);

    /**
     * Creates a tree node for a basic 'for' loop statement.
     * @param initializer
     * @param condition
     * @param update
     * @param statement
     * @return
     */
    ForLoopTree createForLoop(List<VariableTree> initializer, ExpressionTree condition,
         List<ExpressionStatementTree> update, StatementTree statement);

    /**
     * Creates a tree node for a 'foreach' loop.
     * @param variable
     * @param expression
     * @param statement
     * @return
     */
    EnhancedForLoopTree createEnhancedForLoop(VariableTree variable, ExpressionTree expression, StatementTree statement);

    /**
     * Creates a tree node for a 'break' statement.
     * @return
     */
    BreakTree createBreak();

    /**
     * Creates a tree node for a 'continue' statement.
     * @return
     */
    ContinueTree createContinue();

    /**
     * Creates a tree node for 'return' statement.
     * @param expression
     * @return
     */
    ReturnTree createReturn(ExpressionTree expression);

    /**
     * Creates a tree node for 'throw' statement.
     * @param expression
     * @return
     */
    ThrowTree createThrow(ExpressionTree expression);

    /**
     * Creates a tree node for a 'catch' block.
     * @param parameter
     * @param block
     * @return
     */
    CatchTree createCatch(VariableTree parameter, BlockTree block);

    /**
     * Creates a tree node for a 'try' statement with a finally block.
     * @param tryBlock
     * @param catches
     * @param finallyBlock
     * @return
     */
    TryTree createTry(BlockTree tryBlock, List<? extends CatchTree> catches, BlockTree finallyBlock);

    BinaryTree createDivideOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createLeftShiftOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createMinusOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createPlusOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createRemainderOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createRightShiftOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createMultiplyOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createLessThanEqualOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createLessThanOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createGreaterThanEqualOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createGreaterThanOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createAndOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createConditionalAndOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createConditionalOROperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createEqualOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createNotEqualOperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createOROperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createXOROperation(ExpressionTree left, ExpressionTree right, Token operator);

    BinaryTree createUnsignedRightShiftOperation(ExpressionTree left, ExpressionTree right, Token operator);

    Tree createType(Token token, TypeKind kind);
    
    ParameterizedTypeTree createGenericType(Tree type, List<Tree> arguments);
    
    /**
     * Creates a tree node to stand in for a malformed expression.
     * @return An erroneous expression tree.
     */
    ErroneousTree createErroneousExpression();

    /**
     * Creates an Annotation tree node include the at symbol
     * @param symbol
     * @param annotationType
     * @param arguments
     * @return 
     */
    AnnotationTree createAnnotation(Token symbol, Name annotationType, List<? extends ExpressionTree> arguments);
    
    /**
     * Creates an Annotation tree node.
     * @param annotationType
     * @param arguments
     * @return 
     */
    AnnotationTree createAnnotation(Name annotationType, List<? extends ExpressionTree> arguments);
    
    /**
     * Creates an Annotation tree node.
     * @param annotationTypeToken
     * @return 
     */
    AnnotationTree createAnnotation(Token annotationTypeToken);

    /**
     * Creates a class with no members.
     * @return an empty class.
     */
    Tree createEmptyClass();
    
    /**
     * Creates a tree node for a TriggerCompilationUnit.
     * @param triggerDeclaration
     * @return A new TriggerCompilationUnitTree object
     */
    TriggerCompilationUnitTree createTriggerCompilationUnit(Tree triggerDeclaration);
    
    /**
     * Creates a tree node for a Trigger.
     * @param name
     * @param object
     * @param parameters
     * @param members
     * @return A new TriggerDeclarationTree object
     */
    TriggerDeclarationTree createTriggerDeclaration(Token name, Token object,List<TriggerParameterTree> parameters, List<? extends Tree> members);
    
    /**
     * Creates a tree node for a TriggerParameterTree.
     * @param type
     * @param operation
     * @return A new TriggerParameterTree object
     */
    TriggerParameterTree createTriggerParameterTree(TriggerType type, TriggerOperation operation);
    
     /**
     * Creates a trigger with no members.
     * @return an empty trigger.
     */
    Tree createEmptyTrigger();
    
    /**
     * Creates a tree node for an SOSL expression.
     * @param findSOQLExpression
     * @param optionalStatements
     * @return a new SOSLExpressionTree
     */
    SOSLExpressionTree createSOSLExpression(SOSLFindExpressionTree findSOQLExpression, List <ExpressionTree> optionalStatements);
    
     /**
     * Creates a tree node for an SOSL find expression.
     * @param findQueryExpression
     * @return a new SOSLFindExpressionTree
     */
    SOSLFindExpressionTree createSOSLFindExpression(LiteralTree findQueryExpression);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param objectList
     * @return a new SOSLReturningExpressionTree
     */
    SOSLReturningExpressionTree createSOSLReturningExpression(List<SOSLObjectFieldExpressionTree> objectList);
    
    /*
     * Creates a tree node for a SOSLObjectFieldExpressionTree.  
     * @param objectTypeName
     * @param fieldList
     * @param optionalStatements
     * @return a new SOSLObjectFieldExpressionTree
     */
    SOSLObjectFieldExpressionTree createSOSLObjectFieldExpression(IdentifierTree objectTypeName, List<IdentifierTree> fieldList, List<ExpressionTree> optionalStatements);
    
     /**
     * Creates a tree node for an SOSL Returning expression.
     * @param searchGroup
     * @return a new SOSLInExpressionTree
     */
    SOSLInExpressionTree createSOSLInExpression(SOSLSearchGroupType searchGroup);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param updateType
     * @return a new SOSLUpdateExpressionTree
     */
    SOSLUpdateExpressionTree createSOSLUpdateExpression(SOSLUpdateType updateType);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param divisionField
     * @return a new SOSLWithDivisionFilterExpressionTree
     */
    SOSLWithDivisionFilterExpressionTree createSOSLWithDivisionFilterExpression(LiteralTree divisionField);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param groupName
     * @param operator
     * @param category
     * @return a new SOSLWithDivisionFilterExpressionTree
     */
    SOSLDataCategoryExpressionTree createSOSLDataCategoryExpression(IdentifierTree groupName, SOSLDataCategoryOperator operator, IdentifierTree category);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param dataCategory
     * @return a new SOSLWithDataCategoryExpressionTree
     */
    SOSLWithDataCategoryExpressionTree createSOSLWithDataCategoryExpression(ExpressionTree dataCategory);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param networks
     * @return a new SOSLWithNetworkExpressionTree
     */
    SOSLWithNetworkExpressionTree createSOSLWithNetworkExpression(List<LiteralTree> networks);
    
    /**
     * Creates a tree node for an SOSL Returning expression.
     * @param targetLength
     * @return a new SOSLWithSnippetExpressionTree
     */
    SOSLWithSnippetExpressionTree createSOSLWithSnippetExpression(LiteralTree targetLength);
    
    /**
     * Sets the semicolon token in the VariableTree list.
     * 
     * @param variables the list of VariableTree
     * @param semicolon the semicolon token
     */
    void setSemicolonToVariableTree(List<VariableTree> variables, Token semicolon);
}
