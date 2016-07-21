/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;

/**
 * A tree node for the Map initializer expression. For example:
 * <p> Key => Value </p>
 * @author Marcelo Garnica
 */
public interface PairExpressionTree extends ExpressionTree {
    ExpressionTree getKey();
    
    ExpressionTree getValue();
}
