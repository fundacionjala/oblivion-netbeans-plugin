/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLInExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLSearchGroupType;

/**
 * Represents a tree node for a SOSL IN  clause. For example
 * <p>
 *    IN NAME FIELDS
 * </p>
 * @author Pablo Romero
 */
public class SOSLInExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLInExpressionTree {

    private final SOSLSearchGroupType searchGroup;
    SOSLInExpressionTreeImpl(SOSLSearchGroupType searchGroup) {
        super(Tree.Kind.OTHER, null);
        this.searchGroup = searchGroup;
    }

    @Override
    public SOSLSearchGroupType getSearchGroup() {
        return this.searchGroup;
    }
}
