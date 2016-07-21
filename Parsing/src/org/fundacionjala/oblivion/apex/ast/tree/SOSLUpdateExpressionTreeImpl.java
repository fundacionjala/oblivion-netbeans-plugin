/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLUpdateExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLUpdateType;

/**
 * Represents a tree node for a SOSL UPDATE clause. For example
 * <p>
 *    	UPDATE TRACKING
 * </p>
 * @author Pablo Romero
 */
public class SOSLUpdateExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLUpdateExpressionTree {

    private final SOSLUpdateType updateType;
    SOSLUpdateExpressionTreeImpl(SOSLUpdateType updateType) {
        super(Tree.Kind.OTHER, null);
        this.updateType = updateType;
    }

    @Override
    public SOSLUpdateType getUpdateType() {
        return this.updateType;
    }
}
