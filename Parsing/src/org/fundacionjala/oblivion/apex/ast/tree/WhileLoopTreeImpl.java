/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.WhileLoopTree;

/**
 * A tree node for a 'while' loop statement. For example:
 * <p>
 * while ( condition ) statement </p>
 *
 * @author Marcelo Garnica
 */
public class WhileLoopTreeImpl extends CompoundTree<Tree> implements WhileLoopTree {

    private final ExpressionTree condition;
    private final StatementTree statement;

    WhileLoopTreeImpl(ExpressionTree condition, StatementTree statement) {
        super(Kind.WHILE_LOOP, null);
        this.condition = condition;
        this.statement = statement;
        addMember(this.condition);
        addMember(this.statement);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitWhileLoop(this, d);
    }

    @Override
    public ExpressionTree getCondition() {
        return condition;
    }

    @Override
    public StatementTree getStatement() {
        return statement;
    }

}
