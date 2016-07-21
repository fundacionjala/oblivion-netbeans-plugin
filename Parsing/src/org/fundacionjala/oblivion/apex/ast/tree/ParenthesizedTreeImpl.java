/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.TreeVisitor;

/**
 * A tree node for a parenthesized expression. Note: parentheses not be preserved by the parser. For example:
 * <p> ( expression ) </p>
 * @author Marcelo Garnica
 */
public class ParenthesizedTreeImpl extends BaseTree implements ParenthesizedTree {
    private final ExpressionTree expression;

    public ParenthesizedTreeImpl(ExpressionTree expression) {
        super(Kind.PARENTHESIZED, null);
        this.expression = expression;
        if (this.expression instanceof BaseTree) {
            BaseTree baseTree = (BaseTree) this.expression;
            baseTree.setParent(this);
        }
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitParenthesized(this, d);
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }

}
