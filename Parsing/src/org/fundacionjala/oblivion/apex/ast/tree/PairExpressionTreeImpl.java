/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.PairExpressionTree;

/**
 * A tree node for the Map initializer expression. For example:
 * <p> Key => Value </p>
 * @author Marcelo Garnica
 */
public class PairExpressionTreeImpl extends CompoundTree<Tree> implements PairExpressionTree {
    private final ExpressionTree key;
    private final ExpressionTree value;

    public PairExpressionTreeImpl(ExpressionTree key, ExpressionTree value) {
        super(null, null);
        this.key = key;
        this.value = value;
        addMember(this.key);
        addMember(this.value);
        updateParentReference();
    }
    
    @Override
    public ExpressionTree getKey() {
        return key;
    }
    
    @Override
    public ExpressionTree getValue() {
        return value;
    }

}
