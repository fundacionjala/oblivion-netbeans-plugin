/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ExpressionTree;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represents an when you assign a value to a variable.
 *
 * @author Adrian Grajeda
 */
public class CompoundAssignmentTreeImpl extends CompoundTree<ExpressionTree> implements CompoundAssignmentTree {

    private final ExpressionTree variable;
    private final ExpressionTree expression;

    public CompoundAssignmentTreeImpl(Kind kind, Token token, ExpressionTree variable, ExpressionTree expression) {
        super(kind, token);
        this.variable = variable;
        this.expression = expression;

        children.add(variable);
        children.add(expression);
    }

    @Override
    public ExpressionTree getVariable() {
        return variable;
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }
}
