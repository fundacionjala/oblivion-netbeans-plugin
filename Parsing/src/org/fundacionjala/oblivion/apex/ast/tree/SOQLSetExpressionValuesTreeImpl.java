/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLSetExpressionValuesTree;

/**
 *
 * @author sergio_daza
 */
class SOQLSetExpressionValuesTreeImpl extends BaseTree implements SOQLSetExpressionValuesTree {
    private final List<ExpressionTree> values;
    SOQLSetExpressionValuesTreeImpl(List<ExpressionTree> values) {
        super(Tree.Kind.OTHER, null);
        this.values = values;
    }

    @Override
    public List<ExpressionTree> getValues() {
        return values;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this,d);
    }


}
