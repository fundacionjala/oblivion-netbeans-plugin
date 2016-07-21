/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.Token;

/**
 * A tree node for a 'do' statement. For example:
 * <p> do
 *        statement
 *     while ( expression ); </p
 * @author Marcelo Garnica
 */
public class DoWhileLoopTreeImpl extends CompoundTree<Tree> implements DoWhileLoopTree {

    private final ExpressionTree condition;
    private final StatementTree statement;

    DoWhileLoopTreeImpl(ExpressionTree condition, StatementTree statement, Token startToken, Token endToken) {
        super(Kind.DO_WHILE_LOOP, null);
        setBlockStart(startToken);
        setBlockEnd(endToken);
        this.condition = condition;
        this.statement = statement;
        addMember(this.condition);
        addMember(this.statement);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitDoWhileLoop(this, d);
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
