/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;

/**
 * A tree node for an 'instanceof' expression. For example:
 * <p> expression instanceof type </p>
 * @author Marcelo Garnica
 */
public class InstanceOfTreeImp extends BaseTree implements InstanceOfTree {
    private final ExpressionTree expression;
    private final Tree type;

    public InstanceOfTreeImp(ExpressionTree expression, Tree type) {
        super(Kind.INSTANCE_OF, null);
        this.expression = expression;
        this.type = type;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitInstanceOf(this, d);
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

    @Override
    public Tree getType() {
        return type;
    }

}
