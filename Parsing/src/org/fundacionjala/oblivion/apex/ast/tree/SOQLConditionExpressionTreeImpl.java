/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLConditionExpressionTree;

public class SOQLConditionExpressionTreeImpl extends BaseTree implements SOQLConditionExpressionTree {
    private final ExpressionTree soqlConditionExpression;
    
    SOQLConditionExpressionTreeImpl(ExpressionTree soqlConditionExpression){   
        super(Tree.Kind.OTHER, null);
        this.soqlConditionExpression = soqlConditionExpression;        
    }

    @Override
    public ExpressionTree getSoqlConditionExpression() {
        return soqlConditionExpression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this,d);
    }
}
