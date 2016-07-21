/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.TreeVisitor;

/**
 * Represents a return expression in Apex language
 * 
 * @author Adrian Grajeda
 */
public class ReturnTreeImpl extends BaseTree implements ReturnTree {

    private final ExpressionTree expression;
    
    ReturnTreeImpl(ExpressionTree expression) {
        super(Kind.RETURN, null);
        this.expression = expression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitReturn(this, d);
        return null;
    }

    @Override
    public ExpressionTree getExpression() {
        return expression;
    }
    
}
