/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TypeCastTree;

/**
 * A tree node for a type cast expression. For example:
 * <p>  ( type ) expression </p>
 * @author Marcelo Garnica
 */
class TypeCastTreeImpl extends BaseTree implements TypeCastTree {
    private final ExpressionTree expression;
    private final Tree type;

    public TypeCastTreeImpl(ExpressionTree expression, Tree type) {
        super(Kind.TYPE_CAST, null);
        this.expression = expression;
        this.type = type;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitTypeCast(this, d);
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

}
