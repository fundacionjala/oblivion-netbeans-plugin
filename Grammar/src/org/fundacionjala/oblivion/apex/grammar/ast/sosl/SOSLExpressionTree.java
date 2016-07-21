/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import java.util.List;

/**
 * Represents a tree node for a SOSL search query. for example
 * <p>
 *    FIND 'John Smith'
 *    RETURNING Account
 *    LIMIT 10
 * </p>
 * @author Pablo Romero
 */
public interface SOSLExpressionTree extends ExpressionTree{
    public SOSLFindExpressionTree getFindExpression();
    public List<ExpressionTree> getOptionalStatements();
}
