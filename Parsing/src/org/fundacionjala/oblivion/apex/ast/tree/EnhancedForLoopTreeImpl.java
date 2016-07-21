/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;

/**
 * A tree node for an "enhanced" 'for' loop statement. For example:
 * <p> for ( variable : expression )
 *         statement </p>
 * @author Marcelo Garnica
 */
public class EnhancedForLoopTreeImpl extends CompoundTree<Tree> implements EnhancedForLoopTree {
    private final VariableTree variable;
    private final ExpressionTree expression;
    private final StatementTree statement;

    EnhancedForLoopTreeImpl(VariableTree variable, ExpressionTree expression, StatementTree statement) {
        super(Kind.ENHANCED_FOR_LOOP, null);
        this.variable = variable;
        this.expression = expression;
        this.statement = statement;
        addMember(this.variable);
        addMember(this.expression);
        addMember(this.statement);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitEnhancedForLoop(this, d);
    }

    @Override
    public VariableTree getVariable() {
        return variable;
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

    @Override
    public StatementTree getStatement() {
        return statement;
    }

}
