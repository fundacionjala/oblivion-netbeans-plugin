/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.TreeVisitor;

/**
 * A tree node for a 'throw' statement. For example:
 * <p> throw expression; </p>
 * @author Marcelo Garnica
 */
public class ThrowTreeImpl extends BaseTree implements ThrowTree {
    private final ExpressionTree expression;

    ThrowTreeImpl(ExpressionTree expression) {
        super(Kind.THROW, null);
        this.expression = expression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitThrow(this, d);
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

}
