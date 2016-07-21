/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import java.util.Collections;

/**
 * Represent a single statement expression.
 *
 * @author Adrian Grajeda
 */
public class ExpressionStatementTreeImpl extends CompoundTree<ExpressionTree> implements ExpressionStatementTree {

    ExpressionStatementTreeImpl(ExpressionTree expression) {
        super(Kind.EXPRESSION_STATEMENT, null, Collections.singletonList(expression), null, null);
    }

    @Override
    public ExpressionTree getExpression() {
        return children.iterator().next();
    }
}
