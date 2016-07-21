/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import java.util.List;
/**
 * Defines necessary methods to create SOQL Expression Select tree nodes.
 *
 * @author sergio_daza
 */
public interface SOQLExpressionSelectTree extends ExpressionTree {
    public List<IdentifierTree> getIdentifiers();
    
    public List<SOQLSelectFunctionTree> getFunctions();
    
    public List<SOQLExpressionTree> getNestedQuerys();
}
