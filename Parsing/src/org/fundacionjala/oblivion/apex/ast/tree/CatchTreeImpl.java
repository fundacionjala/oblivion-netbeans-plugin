/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;

/**
 * A tree node for a 'catch' block in a 'try' statement. For example:
 * <p> catch ( parameter )
         block </p>
 * @author Marcelo Garnica
 */
class CatchTreeImpl extends CompoundTree<Tree> implements CatchTree {
    private final VariableTree parameter;
    private final BlockTree block;

    CatchTreeImpl(VariableTree parameter, BlockTree block) {
        super(Kind.CATCH, null);
        this.parameter = parameter;
        this.block = block;
        addMember(parameter);
        addMember(block);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitCatch(this, d);
    }

    @Override
    public VariableTree getParameter() {
        return parameter;
    }

    @Override
    public BlockTree getBlock() {
        return block;
    }

}
