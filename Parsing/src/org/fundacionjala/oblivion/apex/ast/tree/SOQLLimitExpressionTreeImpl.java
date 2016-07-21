/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLLimitExpressionTree;


public class SOQLLimitExpressionTreeImpl extends CompoundTree<ExpressionTree> implements SOQLLimitExpressionTree {
    
    private final ExpressionTree limit;
    
    public SOQLLimitExpressionTreeImpl (ExpressionTree limit){
         super(Kind.OTHER, null);
         this.limit = limit;
         addMember(this.limit);
    }

    @Override
    public ExpressionTree getLimit() {
        return limit;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitOther(this,d);
    }
    
}
