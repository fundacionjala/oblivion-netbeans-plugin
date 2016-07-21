/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;

/**
 * Represents a tree node for a SOSL DATA CATEGORY specification. For example
 * <p>
 *    Geography AT Iceland
 * </p>
 * @author Pablo Romero
 */
public interface SOSLDataCategoryExpressionTree extends ExpressionTree{
    public IdentifierTree getGroupName();
    public SOSLDataCategoryOperator getOperator();
    public IdentifierTree getCategory();
}
