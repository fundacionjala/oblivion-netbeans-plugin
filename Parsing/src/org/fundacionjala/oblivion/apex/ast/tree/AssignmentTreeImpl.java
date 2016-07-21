/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import org.fundacionjala.oblivion.apex.Token;

/**
 *
 * @author Adrian Grajeda
 */
class AssignmentTreeImpl extends CompoundTree<Tree> implements AssignmentTree {

    private VariableTreeImpl variable;
    private ExpressionTree value;

    AssignmentTreeImpl(Token token) {
        super(Tree.Kind.ASSIGNMENT, token);
    }

    AssignmentTreeImpl(VariableTree variable, ExpressionTree value) {
        super(Tree.Kind.ASSIGNMENT, null);
        this.variable = (VariableTreeImpl) variable;

        this.value = value;

        children.add(this.value);
        children.add(this.variable);
    }

    @Override
    public ExpressionTree getVariable() {
        return variable;
    }

    @Override
    public ExpressionTree getExpression() {
        return value;
    }
}
