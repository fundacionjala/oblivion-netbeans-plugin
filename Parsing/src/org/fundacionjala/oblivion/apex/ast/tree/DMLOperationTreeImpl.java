/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationEnum;
import org.fundacionjala.oblivion.apex.grammar.ast.DMLOperationTree;

/**
 * A tree node for a 'DMLOperationTree' statement. For example:
 * insert newAcct;
 * update myAcct;
 * delete doomedAccts;
 * undelete savedAccts;
 * @author sergio_daza
 */
public class DMLOperationTreeImpl extends BaseTree implements DMLOperationTree {

    private final DMLOperationEnum dmlOperationEnum;
    
    DMLOperationTreeImpl(DMLOperationEnum dmlOperationEnum) {
        super(Kind.RETURN, null);
        this.dmlOperationEnum = dmlOperationEnum;
    }
     
    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this, d);
    }

    @Override
    public DMLOperationEnum getDMLOperationEnum() {
        return dmlOperationEnum;
    }
    
}
