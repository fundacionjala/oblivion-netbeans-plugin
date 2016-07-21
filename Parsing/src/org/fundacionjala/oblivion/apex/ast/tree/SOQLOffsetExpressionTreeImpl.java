/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLOffsetExpressionTree;


public class SOQLOffsetExpressionTreeImpl extends CompoundTree<ExpressionTree> implements SOQLOffsetExpressionTree {

    private final ExpressionTree offset;
    
    public SOQLOffsetExpressionTreeImpl (ExpressionTree offset){
         super(Kind.OTHER, null);
         this.offset = offset;
         addMember(this.offset);
    }
    
    @Override
    public ExpressionTree getOffset() {
        return offset;
    }
    
    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitOther(this,d);
    }
    
}
