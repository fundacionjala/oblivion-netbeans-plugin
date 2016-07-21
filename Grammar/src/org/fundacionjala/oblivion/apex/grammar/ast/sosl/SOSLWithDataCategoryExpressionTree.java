/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;

/**
 * Represents a tree node for a SOSL WITH DATA CATEGORY clause. For example
 * <p>
 *    	 WITH DATA CATEGORY Geography ABOVE France
 * </p>
 * @author Pablo Romero
 */
public interface SOSLWithDataCategoryExpressionTree extends ExpressionTree{
    public ExpressionTree getDataCategory();
}
