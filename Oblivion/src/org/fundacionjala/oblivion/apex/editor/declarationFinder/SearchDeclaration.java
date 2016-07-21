/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.declarationFinder;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.ArrayAccessTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.BinaryTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.BlockTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundAssignmentTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.fundacionjala.oblivion.apex.ast.tree.ConditionalExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.DMLMergeTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.DMLOperationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.DoWhileLoopTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.EnhancedForLoopTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ExpressionStatementTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ForLoopTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.IdentifierTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.IfTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.InstanceOfTreeImp;
import org.fundacionjala.oblivion.apex.ast.tree.MemberSelectTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodInvocationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.NewClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ParenthesizedTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.PropertyTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ReturnTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLConditionExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLExpressionWhereTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLFieldExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLLimitExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.SOQLOffsetExpressionTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ThrowTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.TriggerDeclarationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.TryTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.UnaryTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.WhileLoopTreeImpl;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationEnum;
import org.netbeans.modules.csl.api.OffsetRange;

/**
 * This class runs the compilationUnit on searching the declarations for the class, method and variables.
 *
 * @author sergio_daza
 */
public class SearchDeclaration {

    private final FoundTree foundTree;
    private final String name;
    private final int offset;
    private final StyledDocument styledDocument;
    private final CompilationUnitTree unit;
    private String className;
    private TypeOfIdentifier type;

    public SearchDeclaration(String mimeType, String name, final int offset, final StyledDocument styledDocument, CompilationUnitTree unit) {
        this.className = "";
        this.type = TypeOfIdentifier.UNKNOWN;
        this.foundTree = new FoundTree(name, mimeType);
        this.name = name;
        this.offset = offset;
        this.styledDocument = styledDocument;
        this.unit = unit;
    }

    public FoundTree getFoundTree() {
        return foundTree;
    }
    
    public boolean SearchIdentifier() {
        boolean result = false;
        List<? extends Tree> typeDecls = unit.getTypeDecls();
        for (Tree type : typeDecls) {
            if (type instanceof ClassTreeImpl) {
                result |= IsInClass((ClassTreeImpl) type);
            } else if (type instanceof TriggerDeclarationTreeImpl) {
                result |= IsInTrigger((TriggerDeclarationTreeImpl) type);
            }
        }
        return result;
    }

    private boolean IsInTrigger(TriggerDeclarationTreeImpl triggger) {
        boolean result = false;
        List<? extends Tree> members = triggger.getMembers();
        for (Tree tree : members) {
            if (tree instanceof ClassTreeImpl) {
                result |= IsInClass((ClassTreeImpl) tree);
            } else {
            result |= SearchInExpression((ExpressionTree) tree, null);
        }
        }
        return result;
    }

    private boolean IsInClass(ClassTreeImpl classTree) {
        boolean result = false;
        this.type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        if(classTree.getSimpleName() != null) {
            this.className = classTree.getSimpleName().toString();
            if (classTree.getSimpleName().toString().equalsIgnoreCase(name)) {
                foundTree.classes.add(classTree);
                BaseTree baseTree = (BaseTree) classTree;
                if (baseTree.getToken() != null) {
                    if (inRange(getRange(baseTree.getToken()))) {
                        return true;
                    }
                }
            } else {
                Tree extendsClause = classTree.getExtendsClause();
                if (extendsClause instanceof IdentifierTreeImpl) {
                    IdentifierTreeImpl identifier = (IdentifierTreeImpl) extendsClause;
                    this.type = TypeOfIdentifier.EXTENDS_CLASS_VARIABLE_NAME;
                    foundTree.extendsIdentifier = identifier;
                    result |= IsIdentifier(identifier, classTree);
                }
                List<? extends Tree> implementsClause = classTree.getImplementsClause();
                for (Tree tree : implementsClause) {
                    if (tree instanceof IdentifierTreeImpl) {
                        result |= isType((IdentifierTreeImpl) tree);
                    }
                }
            }
            result |= SearchInClassChildren(classTree.getChildren(), classTree);
        }
        return result;
    }

    private boolean IsInVariableDeclaration(VariableTreeImpl variable, Tree parent) {
        boolean result = false;
        if (variable.getName().toString().equalsIgnoreCase(name)) {
            foundTree.declarations.add(variable);
            BaseTree baseTree = (BaseTree) variable;
            if (baseTree.getToken() != null) {
                if (inRange(getRange(baseTree.getToken()))) {
                    return true;
                }
            }
        } else {
            Tree variableType = variable.getType();
            if (variableType instanceof IdentifierTreeImpl) {
                result |= isType((IdentifierTreeImpl) variableType);
            }
            ExpressionTree initializer = variable.getInitializer();
            if (initializer != null) {
                result |= SearchInExpression(initializer, parent);
            }
        }
        return result;
    }

    private boolean IsInPropertyDeclaration(PropertyTreeImpl property, Tree parent) {
        boolean result = false;
        if (property.getName().toString().equalsIgnoreCase(name)) {
            foundTree.declarations.add(property);
            BaseTree baseTree = (BaseTree) property;
            if (baseTree.getToken() != null) {
                if (inRange(getRange(baseTree.getToken()))) {
                    return true;
                }
            }
        } else {
            Tree type = property.getType();
            if (type instanceof IdentifierTreeImpl) {
                result |= isType((IdentifierTreeImpl) type);
            }
        }
        return result;
    }

    private boolean IsInNewClass(NewClassTreeImpl newClassTreeImpl, Tree parent) {
        boolean result = false;
        ExpressionTree expression = newClassTreeImpl.getIdentifier();
        if (expression instanceof IdentifierTreeImpl) {
            result |= isType((IdentifierTreeImpl) expression);
        }
        List<? extends ExpressionTree> arguments = newClassTreeImpl.getArguments();
        for (ExpressionTree argument : arguments) {
            result |= SearchInExpression(argument, parent);
        }
        return result;
    }

    private boolean IsInMemberSelect(MemberSelectTreeImpl memberSelectTreeImpl, Tree parent) {
        Token token = memberSelectTreeImpl.getToken();
        if (token != null) {
            if (inRange(getRange(token))) {
                return true;
            }
        }
        return SearchInClassChildren(memberSelectTreeImpl.getChildren(), parent);
    }

    private boolean IsMethodTree(MethodTreeImpl method) {
        if (!foundTree.methods.contains(method)) {
            foundTree.methods.add(method);
        }
        if (method.getName().toString().equalsIgnoreCase(name)) {
            BaseTree baseTree = (BaseTree) method;
            if (baseTree.getToken() != null) {
                if (inRange(getRange(baseTree.getToken()))) {
                    return true;
                }
            }
        } else {
            List<? extends VariableTree> parameters = method.getParameters();
            for (VariableTree variable : parameters) {
                if (IsInVariableDeclaration((VariableTreeImpl) variable, method)) {
                    return true;
                }
            }
        }
        this.type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        return SearchInMethodBlock((BlockTreeImpl) method.getBody(), method);
    }

    private boolean IsInCompoundAssignment(CompoundAssignmentTreeImpl compoundAssignment, Tree parent) {
        boolean result = false;
        ExpressionTree variable = compoundAssignment.getVariable();
        result |= SearchInExpression(variable, parent);
        if (!result) {
            result |= SearchInExpression(compoundAssignment.getExpression(), parent);
        }
        return result;
    }

    private boolean IsInMethodInvocation(MethodInvocationTreeImpl methodInvocation, Tree parent) {
        boolean result = false;
        List<? extends ExpressionTree> arguments = methodInvocation.getArguments();
        type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        for (ExpressionTree tree : arguments) {
            result |= SearchInExpression(tree, parent);
        }
        Collection<ExpressionTree> children = methodInvocation.getChildren();
        this.type = TypeOfIdentifier.LOCAL_METHOD_INVOCATION;
        boolean resultBranch = SearchInClassChildren(children, parent);
        if (resultBranch) {
            foundTree.methodInvocation = methodInvocation;
        }
        type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        result |= resultBranch;
        return result;
    }

    private boolean IsInBinary(BinaryTreeImpl binaryTree, Tree parent) {
        boolean result = false;
        this.type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        result |= SearchInExpression(binaryTree.getLeftOperand(), parent);
        result |= SearchInExpression(binaryTree.getRightOperand(), parent);
        return result;
    }

    private boolean IsInReturn(ReturnTreeImpl returnTree, Tree parent) {
        return SearchInExpression(returnTree.getExpression(), parent);
    }

    private boolean IsInUnary(UnaryTreeImpl unaryTree, Tree parent) {
        return SearchInExpression(unaryTree.getExpression(), parent);
    }

    private boolean IsInParenthesized(ParenthesizedTreeImpl parenthesized, Tree parent) {
        return SearchInExpression(parenthesized.getExpression(), parent);
    }

    private boolean IsInBlock(BlockTreeImpl blockTree, Tree parent) {
        boolean result = false;
        if (blockTree != null) {
            List<? extends StatementTree> statements = blockTree.getStatements();
            for (StatementTree statement : statements) {
                result |= SearchOnBodyBlock(statement, blockTree);
            }
        }
        return result;
    }

    private boolean IsInIf(IfTreeImpl ifTree, Tree parent) {
        boolean result = false;
        type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        result |= SearchInExpression(ifTree.getCondition(), parent);
        result |= SearchOnBodyBlock(ifTree.getThenStatement(), parent);
        result |= SearchOnBodyBlock(ifTree.getElseStatement(), parent);
        return result;
    }

    private boolean IsInConditionalExpression(ConditionalExpressionTreeImpl conditionalExpression, Tree parent) {
        boolean result = false;
        type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        result |= SearchInExpression(conditionalExpression.getCondition(), parent);
        result |= SearchInExpression(conditionalExpression.getTrueExpression(), parent);
        result |= SearchInExpression(conditionalExpression.getFalseExpression(), parent);
        return result;
    }

    private boolean IsInForLoop(ForLoopTreeImpl forLoop, Tree parent) {
        boolean result = false;
        result |= SearchInExpression(forLoop.getCondition(), parent);
        result |= SearchOnBodyBlock(forLoop.getStatement(), parent);
        List<? extends StatementTree> initializer = forLoop.getInitializer();
        for (StatementTree variable : initializer) {
            SearchOnBodyBlock(variable, parent);
        }
        return result;
    }

    private boolean IsInEnhancedForLoop(EnhancedForLoopTreeImpl enhancedForLoop, Tree parent) {
        boolean result = false;
        result |= IsInVariableDeclaration((VariableTreeImpl) enhancedForLoop.getVariable(), parent);
        result |= SearchInExpression(enhancedForLoop.getExpression(), parent);
        result |= SearchOnBodyBlock(enhancedForLoop.getStatement(), parent);
        return result;
    }

    private boolean IsInWhileLoop(WhileLoopTreeImpl whileLoop, Tree parent) {
        boolean result = false;
        result |= SearchInExpression(whileLoop.getCondition(), parent);
        result |= SearchOnBodyBlock(whileLoop.getStatement(), parent);
        return result;
    }

    private boolean IsInDoWhileLoop(DoWhileLoopTreeImpl doWhileLoop, Tree parent) {
        boolean result = false;
        result |= SearchInExpression(doWhileLoop.getCondition(), parent);
        result |= SearchOnBodyBlock(doWhileLoop.getStatement(), parent);
        return result;
    }

    private boolean IsInDMLOperation(DMLOperationTreeImpl dmlOperation, Tree parent) {
        boolean result = false;
        DMLOperationEnum dmlOperationEnum = dmlOperation.getDMLOperationEnum();
        result |= SearchInExpression(dmlOperationEnum.getExpression(), parent);
        return result;
    }

    private boolean IsInDMLMerge(DMLMergeTreeImpl dmlMerge, Tree parent) {
        boolean result = false;
        DMLOperationEnum dmlOperationEnum = dmlMerge.getDMLOperationEnum();
        result |= SearchInExpression(dmlOperationEnum.getExpression(), parent);
        result |= SearchInExpression(dmlMerge.getMergeRecordsExpression(), parent);
        return result;
    }

    private boolean IsInTry(TryTreeImpl tryTree, Tree parent) {
        boolean result = false;
        result |= IsInBlock((BlockTreeImpl) tryTree.getFinallyBlock(), parent);
        result |= IsInBlock((BlockTreeImpl) tryTree.getBlock(), parent);
        List<? extends CatchTree> catches = tryTree.getCatches();
        for (CatchTree catchTree : catches) {
            VariableTree parameter = catchTree.getParameter();
            result |= IsInVariableDeclaration((VariableTreeImpl) parameter, parent);
            BlockTree block = catchTree.getBlock();
            result |= IsInBlock((BlockTreeImpl) block, parent);
        }
        return result;
    }

    private boolean IsInThrow(ThrowTreeImpl throwTree, Tree parent) {
        boolean result = false;
        result |= SearchInExpression(throwTree.getExpression(), parent);
        return result;
    }

    private boolean IsInArrayAccess(ArrayAccessTreeImpl arrayAccessTreeImpl, Tree parent) {
        boolean result = false;
        this.type = TypeOfIdentifier.LOCAL_ARRAY_VARIABLE_NAME;
        result |= SearchInExpression(arrayAccessTreeImpl.getExpression(), parent);
        this.type = TypeOfIdentifier.LOCAL_VARIABLE_NAME;
        result |= SearchInExpression(arrayAccessTreeImpl.getIndex(), parent);
        return result;
    }

    private boolean IsInSOQLExpression(SOQLExpressionTreeImpl expression, Tree parent) {
        boolean result = false;
        List<ExpressionTree> optionalStatements = expression.getOptionalStatements();
        for (ExpressionTree expressionTree : optionalStatements) {
            if (expressionTree instanceof SOQLExpressionWhereTreeImpl) {
                SOQLExpressionWhereTreeImpl where = (SOQLExpressionWhereTreeImpl) expressionTree;
                ExpressionTree optionsStatementWhere = where.getOptionsStatementWhere();
                if (optionsStatementWhere instanceof SOQLConditionExpressionTreeImpl) {
                    SOQLConditionExpressionTreeImpl options = (SOQLConditionExpressionTreeImpl) optionsStatementWhere;
                    ExpressionTree soqlConditionExpression = options.getSoqlConditionExpression();
                    if (soqlConditionExpression instanceof SOQLFieldExpressionTreeImpl) {
                        SOQLFieldExpressionTreeImpl field = (SOQLFieldExpressionTreeImpl) soqlConditionExpression;
                        result |= SearchInExpression(field.getValue(), parent);
                    }
                }
            } else if (expressionTree instanceof SOQLLimitExpressionTreeImpl) {
                SOQLLimitExpressionTreeImpl limit = (SOQLLimitExpressionTreeImpl) expressionTree;
                result |= SearchInExpression(limit.getLimit(), parent);
            } else if (expressionTree instanceof SOQLOffsetExpressionTreeImpl) {
                SOQLOffsetExpressionTreeImpl offset = (SOQLOffsetExpressionTreeImpl) expressionTree;
                result |= SearchInExpression(offset.getOffset(), parent);
            }
        }
        return result;
    }

    private boolean SearchOnBodyBlock(StatementTree statement, Tree parent) {
        if (statement instanceof ExpressionStatementTreeImpl) {
            return SearchInStatement((ExpressionStatementTreeImpl) statement, parent);
        } else if (statement instanceof VariableTreeImpl) {
            return IsInVariableDeclaration((VariableTreeImpl) statement, parent);
        } else if (statement instanceof ReturnTreeImpl) {
            return IsInReturn((ReturnTreeImpl) statement, parent);
        } else if (statement instanceof BlockTreeImpl) {
            return IsInBlock((BlockTreeImpl) statement, parent);
        } else if (statement instanceof IfTreeImpl) {
            return IsInIf((IfTreeImpl) statement, parent);
        } else if (statement instanceof ForLoopTreeImpl) {
            return IsInForLoop((ForLoopTreeImpl) statement, parent);
        } else if (statement instanceof WhileLoopTreeImpl) {
            return IsInWhileLoop((WhileLoopTreeImpl) statement, parent);
        } else if (statement instanceof DoWhileLoopTreeImpl) {
            return IsInDoWhileLoop((DoWhileLoopTreeImpl) statement, parent);
        } else if (statement instanceof DMLOperationTreeImpl) {
            return IsInDMLOperation((DMLOperationTreeImpl) statement, parent);
        } else if (statement instanceof DMLMergeTreeImpl) {
            return IsInDMLMerge((DMLMergeTreeImpl) statement, parent);
        } else if (statement instanceof TryTreeImpl) {
            return IsInTry((TryTreeImpl) statement, parent);
        } else if (statement instanceof ThrowTreeImpl) {
            return IsInThrow((ThrowTreeImpl) statement, parent);
        } else if (statement instanceof ArrayAccessTreeImpl) {
            return IsInArrayAccess((ArrayAccessTreeImpl) statement, parent);
        } else if (statement instanceof EnhancedForLoopTreeImpl) {
            return IsInEnhancedForLoop((EnhancedForLoopTreeImpl) statement, parent);
        }
        return false;
    }

    private boolean SearchInStatement(ExpressionStatementTreeImpl expressionStatement, Tree parent) {
        return SearchInClassChildren(expressionStatement.getChildren(), parent);
    }

    private boolean SearchInClassChildren(Collection<? extends Tree> children, Tree parent) {
        boolean result = false;
        Iterator itr = children.iterator();
        List<ClassTreeImpl> classes = new ArrayList<>();
        while (itr.hasNext()) {
            Object element = itr.next();
            if (element instanceof ClassTreeImpl) {
                classes.add((ClassTreeImpl) element);
            } else if (element instanceof ExpressionTree) {
                result |= SearchInExpression((ExpressionTree) element, parent);
            }
        }
        for (ClassTreeImpl cl : classes) {
            result |= IsInClass(cl);
        }
        return result;
    }

    private boolean SearchInExpression(ExpressionTree expression, Tree parent) {
        boolean result = false;
        if (expression instanceof MethodTreeImpl) {
            result |= IsMethodTree((MethodTreeImpl) expression);
        } else if (expression instanceof VariableTreeImpl) {
            result |= IsInVariableDeclaration((VariableTreeImpl) expression, parent);
        } else if (expression instanceof CompoundAssignmentTreeImpl) {
            result |= IsInCompoundAssignment((CompoundAssignmentTreeImpl) expression, parent);
        } else if (expression instanceof PropertyTreeImpl) {
            result |= IsInPropertyDeclaration((PropertyTreeImpl) expression, parent);
        } else if (expression instanceof MethodInvocationTreeImpl) {
            result |= IsInMethodInvocation((MethodInvocationTreeImpl) expression, parent);
        } else if (expression instanceof IdentifierTreeImpl) {
            result |= IsIdentifier((IdentifierTreeImpl) expression, parent);
        } else if (expression instanceof MemberSelectTreeImpl) {
            result |= IsInMemberSelect((MemberSelectTreeImpl) expression, parent);
        } else if (expression instanceof NewClassTreeImpl) {
            result |= IsInNewClass((NewClassTreeImpl) expression, parent);
        } else if (expression instanceof BinaryTreeImpl) {
            result |= IsInBinary((BinaryTreeImpl) expression, parent);
        } else if (expression instanceof UnaryTreeImpl) {
            result |= IsInUnary((UnaryTreeImpl) expression, parent);
        } else if (expression instanceof ParenthesizedTreeImpl) {
            result |= IsInParenthesized((ParenthesizedTreeImpl) expression, parent);
        } else if (expression instanceof ArrayAccessTreeImpl) {
            result |= IsInArrayAccess((ArrayAccessTreeImpl) expression, parent);
        } else if (expression instanceof ConditionalExpressionTreeImpl) {
            result |= IsInConditionalExpression((ConditionalExpressionTreeImpl) expression, parent);
        } else if (expression instanceof SOQLExpressionTreeImpl) {
            result |= IsInSOQLExpression((SOQLExpressionTreeImpl) expression, parent);
        } else if (expression instanceof InstanceOfTreeImp) {
            result |= IsInInstanceOf((InstanceOfTreeImp) expression, parent);
        } else if (expression instanceof BlockTreeImpl) {
            result |= IsInBlock((BlockTreeImpl) expression, parent);
        }
        return result;
    }

    private boolean IsInInstanceOf(InstanceOfTreeImp instanceOf, Tree parent) {
        boolean result = false;
        result |= SearchInExpression(instanceOf.getExpression(), parent);
        result |= isType((IdentifierTreeImpl) instanceOf.getType());
        return result;
    }

    private boolean SearchInMethodBlock(BlockTreeImpl body, Tree parent) {
        boolean result = false;
        if (body != null) {
            List<? extends StatementTree> statements = body.getStatements();
            for (StatementTree statement : statements) {
                result |= SearchOnBodyBlock(statement, body);
            }
        }
        return result;
    }

    private boolean IsIdentifier(IdentifierTreeImpl identifier, Tree parent) {
        List<? extends ExpressionTree> identifiers = identifier.getIdentifiers();
        int size = identifiers.size();
        if (size == 0) {
            Token token = identifier.getToken();
            if (token != null) {
                if (inRange(getRange(token))) {
                    foundTree.saveTreeIdentifier(identifier, parent, this.type, className);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                ExpressionTree expression = identifiers.get(i);
                if (expression.toString().equalsIgnoreCase(name)) {
                    BaseTree baseTree = (BaseTree) expression;
                    if (baseTree.getToken() != null) {
                        if (inRange(getRange(baseTree.getToken()))) {
                            if (size == 1) {
                                foundTree.saveTreeIdentifier(identifier, parent, this.type, className);
                                return true;
                            } else {
                                foundTree.saveTreeIdentifier(identifier, parent, TypeOfIdentifier.EXTERNAL_VARIABLE_NAME, className);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isType(IdentifierTreeImpl identifier) {
        List<? extends ExpressionTree> identifiers = identifier.getIdentifiers();
        int size = identifiers.size();
        for (ExpressionTree expression : identifiers) {
            if (expression.toString().equalsIgnoreCase(name)) {
                BaseTree baseTree = (BaseTree) expression;
                if (baseTree.getToken() != null) {
                    if (inRange(getRange(baseTree.getToken()))) {
                        foundTree.saveTreeIdentifier(identifier, null, TypeOfIdentifier.TYPE_VARIABLE_NAME, className);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean inRange(OffsetRange range) {
        return (range.getStart() <= offset && offset <= range.getEnd());
    }

    private OffsetRange getRange(CompoundTree compoundTree) {
        return DeclarationFinderUtils.getRange(compoundTree, name, styledDocument);
    }

    private OffsetRange getRange(Token token) {
        return DeclarationFinderUtils.getRange(token, name, styledDocument);
    }

    }
