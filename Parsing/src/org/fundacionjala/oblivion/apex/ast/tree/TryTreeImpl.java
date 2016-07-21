/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import java.util.List;

/**
 * A tree node for a 'try' statement. For example:
 * <p>
 * try block catches finally finallyBlock </p>
 *
 * @author Marcelo Garnica
 */
public class TryTreeImpl extends CompoundTree<Tree> implements TryTree {

    private final BlockTree tryBlock;
    private final List<? extends CatchTree> catches;
    private final BlockTree finallyBlock;

    TryTreeImpl(BlockTree tryBlock, List<? extends CatchTree> catches, BlockTree finallyBlock) {
        super(Kind.TRY, null);
        this.tryBlock = tryBlock;
        this.catches = catches;
        this.finallyBlock = finallyBlock;
        addMember(this.tryBlock);
        addMembers(catches);
        addMember(this.finallyBlock);
        updateParentReference();
    }
        
    @Override
    public BlockTree getBlock() {
        return tryBlock;
    }

    @Override
    public List<? extends CatchTree> getCatches() {
        return catches;
    }

    @Override
    public BlockTree getFinallyBlock() {
        return finallyBlock;
    }

    @Override
    public List<? extends Tree> getResources() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitTry(this, d);
        return super.accept(tv, d);
    }

}
