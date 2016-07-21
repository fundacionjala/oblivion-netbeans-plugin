/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;

/**
 * Represents a tree node for a SOSL WITH DIVISION FILTER clause. For example
 * <p>
 *    	 WITH DIVISION = 'Global'
 * </p>
 * @author Pablo Romero
 */
public interface SOSLWithDivisionFilterExpressionTree extends ExpressionTree{
    public LiteralTree getDivisionField();
}
