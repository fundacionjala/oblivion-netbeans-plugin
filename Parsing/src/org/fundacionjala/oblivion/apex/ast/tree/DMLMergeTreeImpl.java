/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLMergeTree;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationEnum;


/**
 * A tree node for a 'DMLMergeTree' statement. For example:
 * merge masterAcct mergeAcct;
 * @author sergio_daza
 */
public class DMLMergeTreeImpl extends CompoundTree<Tree>  implements DMLMergeTree {
    
    private final DMLOperationEnum dmlOperationEnum;
    public final ExpressionTree mergeRecordsExpression;

    DMLMergeTreeImpl(DMLOperationEnum dmlOperationEnum, ExpressionTree mergeRecordsExpression) {
        super(Kind.RETURN, null);
        this.dmlOperationEnum = dmlOperationEnum;
        this.mergeRecordsExpression = mergeRecordsExpression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitOther(this, d);
    }

    @Override
    public ExpressionTree getMergeRecordsExpression() {
        return mergeRecordsExpression;
    }

    @Override
    public DMLOperationEnum getDMLOperationEnum() {
        return dmlOperationEnum;
    }
    
}
