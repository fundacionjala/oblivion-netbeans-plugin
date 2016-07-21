/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;

/**
 * Represents an array access expression
 * <p> expression [ index ] </p>
 * @author Marcelo Garnica
 */
public class ArrayAccessTreeImpl extends BaseTree implements ArrayAccessTree {
    private final ExpressionTree expression;
    private final ExpressionTree index;

    public ArrayAccessTreeImpl(ExpressionTree expression, ExpressionTree index) {
        super(Kind.ARRAY_ACCESS, null);
        this.expression = expression;
        this.index = index;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitArrayAccess(this, d);
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

    @Override
    public ExpressionTree getIndex() {
        return index;
    }

}
