/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLFindExpressionTree;

/**
 * Represents a tree node for a SOSL FIND clause. For example
 * <p>
 *    FIND 'mylogin@mycompany.com'
 * </p>
 * @author Pablo Romero
 */
public class SOSLFindExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLFindExpressionTree {

    private final LiteralTree searchQuery;
    SOSLFindExpressionTreeImpl(LiteralTree searchQuery) {
        super(Tree.Kind.OTHER, null);
        this.searchQuery = searchQuery;
        addMember(searchQuery);
    }

    @Override
    public LiteralTree getSearchQuery() {
        return this.searchQuery;
    }
}
