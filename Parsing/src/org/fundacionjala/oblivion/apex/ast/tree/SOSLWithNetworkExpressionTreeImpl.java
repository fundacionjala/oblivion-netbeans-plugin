/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithNetworkExpressionTree;

/**
 * Represents a tree node for a SOSL WITH NETWORK clause. For example
 * <p>
 *    	 WITH NETWORK IN ('NetworkId1', 'NetworkId2')
 * </p>
 * @author Pablo Romero
 */
public class SOSLWithNetworkExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLWithNetworkExpressionTree {

    private final List<LiteralTree> networks;
    SOSLWithNetworkExpressionTreeImpl(List<LiteralTree> networks) {
        super(Tree.Kind.OTHER, null);
        this.networks = networks;
        addMembers(this.networks);
    }

    @Override
    public List<LiteralTree> getNetworks() {
        return this.networks;
    }
}
