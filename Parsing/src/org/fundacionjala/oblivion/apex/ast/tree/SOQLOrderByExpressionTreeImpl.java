/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLOrderByExpressionTree;

/**
 * @author sergio_daza
 */
class SOQLOrderByExpressionTreeImpl extends CompoundTree<Tree> implements SOQLOrderByExpressionTree {

    private final List<IdentifierTree> identifiers;

    SOQLOrderByExpressionTreeImpl(List<IdentifierTree> identifiers) {
        super(Tree.Kind.OTHER, null);
        this.identifiers = identifiers;
        addMembers(this.identifiers);

    }

    @Override
    public List<IdentifierTree> getIdentifiers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
