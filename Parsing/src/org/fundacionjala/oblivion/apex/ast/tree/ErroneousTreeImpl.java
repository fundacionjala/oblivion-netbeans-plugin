/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 * A tree node to stand in for a malformed expression.
 * @author Marcelo Garnica
 */
class ErroneousTreeImpl extends CompoundTree<Tree> implements ErroneousTree {
    
    private final List<? extends Tree> errorTrees;

    ErroneousTreeImpl() {
        super(Kind.ERRONEOUS, null);
        errorTrees = new ArrayList<>();
    }

    @Override
    public List<? extends Tree> getErrorTrees() {
        return errorTrees;
    }

}
