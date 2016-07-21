/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;
import java.util.List;

/**
 * A tree node for a basic 'for' loop statement. For example:
 * <p> for ( initializer ; condition ; update )
 *         statement </p>
 * @author Marcelo Garnica
 */
public class ForLoopTreeImpl extends CompoundTree<Tree> implements ForLoopTree {
    private final List<VariableTree> initializers;
    private final ExpressionTree condition;
    private final List<ExpressionStatementTree> update;
    private final StatementTree statement;

    ForLoopTreeImpl(List<VariableTree> initializers, ExpressionTree condition, List<ExpressionStatementTree> update, StatementTree statement) {
        super(Kind.FOR_LOOP, null);
        this.initializers = initializers;
        this.condition = condition;
        this.update = update;
        this.statement = statement;
        addMembers(this.initializers);
        addMembers(this.update);
        addMember(this.condition);
        addMember(this.statement);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitForLoop(this, d);
    }

    @Override
    public List<? extends StatementTree> getInitializer() {
        return initializers;
    }

    @Override
    public ExpressionTree getCondition() {
        return condition;
    }

    @Override
    public List<? extends ExpressionStatementTree> getUpdate() {
        return update;
    }

    @Override
    public StatementTree getStatement() {
        return statement;
    }

}
