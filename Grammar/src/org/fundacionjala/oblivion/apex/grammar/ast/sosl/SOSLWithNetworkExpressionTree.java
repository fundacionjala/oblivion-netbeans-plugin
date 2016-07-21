/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;
import java.util.List;

/**
 * Represents a tree node for a SOSL WITH NETWORK clause. For example
 * <p>
 *    	 WITH NETWORK IN ('NetworkId1', 'NetworkId2')
 * </p>
 * @author Pablo Romero
 */
public interface SOSLWithNetworkExpressionTree extends ExpressionTree{
    public List<LiteralTree> getNetworks();
}
