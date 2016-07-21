/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationEnum;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLUpsertTree;

/**
 * A tree node for a DMLUpsertTree statement. For example: 
 * upsert acctList; 
 * upsert sObjectList Account.Fields.MyExternalId__c;
 * @author sergio_daza
 */
public class DMLUpsertTreeImpl extends CompoundTree<Tree>  implements DMLUpsertTree {

    private final DMLOperationEnum dmlOperationEnum;
    public final ExpressionTree optionalExpression;

    DMLUpsertTreeImpl(DMLOperationEnum dmlOperationEnum, ExpressionTree optionalExpression) {
        super(Kind.RETURN, null);
        this.dmlOperationEnum = dmlOperationEnum;
        this.optionalExpression = optionalExpression;
    }

    @Override
    public ExpressionTree getOptionalExpression() {
        return optionalExpression;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitOther(this, d);
    }

    @Override
    public DMLOperationEnum getDMLOperationEnum() {
        return dmlOperationEnum;
    }

}
