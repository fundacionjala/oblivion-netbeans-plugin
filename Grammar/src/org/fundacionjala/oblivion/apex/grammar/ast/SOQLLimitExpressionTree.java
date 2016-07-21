/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;

/**
 * Defines necessary methods to create SOQL Limit Expression tree nodes.
 *
 * @author nelson_alcocer
 */
public interface SOQLLimitExpressionTree extends ExpressionTree {
    public ExpressionTree getLimit();
}
