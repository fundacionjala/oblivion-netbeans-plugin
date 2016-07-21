/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.TreeVisitor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represent any block of code delimited by "{} and one o more statement inside.
 * 
 * @author Adrian Grajeda
 */
public class BlockTreeImpl extends CompoundTree<StatementTree> implements BlockTree {

    private final boolean isStatic;
    
    BlockTreeImpl(Collection<StatementTree> members, boolean isStatic, Token start, Token end) {
        super(Kind.BLOCK, null, members, start, end);
        this.isStatic = isStatic;
        updateParentReference();
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public List<? extends StatementTree> getStatements() {
        return Collections.unmodifiableList(new ArrayList<>(members));
    }
    
    
    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitBlock(this, d);
        
        return super.accept(tv, d);
    }
}
