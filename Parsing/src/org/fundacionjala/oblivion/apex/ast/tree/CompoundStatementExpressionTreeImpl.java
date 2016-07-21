/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.CompoundStatementExpressionTree;

/**
 * A tree node for a 'CompoundStatementExpressionTree' statement. For example:
 * System.runAs(user) {
 *     System.debug(user);
 * }
 * @author sergio_daza
 */
class CompoundStatementExpressionTreeImpl extends CompoundTree<Tree> implements CompoundStatementExpressionTree {
    
    private final StatementTree parentStatement;
    private final List<StatementTree> childrenStatements;

    public CompoundStatementExpressionTreeImpl(StatementTree parentStatement, List<StatementTree> childrenStatements) {
        super(Tree.Kind.OTHER, null);
        this.parentStatement = parentStatement;
        this.childrenStatements = childrenStatements;
        addMember(this.parentStatement); 
        addMembers(this.childrenStatements); 
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitOther(this, d);
    }

    @Override
    public StatementTree getParentStatement() {
        return parentStatement;
    }

    @Override
    public List<StatementTree> getChildrenStatements() {
        return childrenStatements;
    }

}
