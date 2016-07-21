/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;

/**
 *
 * @author Marcelo Garnica
 */
public class IfTreeImpl extends CompoundTree<Tree> implements IfTree {

    private final ExpressionTree condition;
    private final StatementTree thenStatement;
    private final StatementTree elseStatement;

    IfTreeImpl(ExpressionTree condition, StatementTree thenStatement, StatementTree elseStatement) {
        super(Tree.Kind.IF, null);
        this.condition = condition;
        this.thenStatement = thenStatement;
        if(this.thenStatement instanceof BaseTree){
            BaseTree baseTree = (BaseTree) this.thenStatement;
            baseTree.setParent(this);
        }
        this.elseStatement = elseStatement;
        if(this.elseStatement instanceof BaseTree){
            BaseTree baseTree = (BaseTree) this.elseStatement;
            baseTree.setParent(this);
        }
        addMember(this.thenStatement);
        addMember(this.elseStatement);
    }

    @Override
    public ExpressionTree getCondition() {
        return condition;
    }

    @Override
    public StatementTree getThenStatement() {
        return thenStatement;
    }

    @Override
    public StatementTree getElseStatement() {
        return elseStatement;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitIf(this, d);
        return super.accept(tv, d);
    }
}
