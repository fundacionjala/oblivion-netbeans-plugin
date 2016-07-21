/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.UnaryTree;
import org.fundacionjala.oblivion.apex.Token;

/**
 * A tree node for postfix and unary expressions. Use getKind to determine the kind of operator. For example:
 *  <p> operator expression </p>
 *  <p> expression operator </p>
 * @author Marcelo Garnica
 */
public class UnaryTreeImpl extends BaseTree implements UnaryTree {
    
    private final ExpressionTree expression;
    

    UnaryTreeImpl(Kind kind, ExpressionTree expression, Token operator) {
        super(kind, operator);
        this.expression = expression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitUnary(this, d);
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

}
