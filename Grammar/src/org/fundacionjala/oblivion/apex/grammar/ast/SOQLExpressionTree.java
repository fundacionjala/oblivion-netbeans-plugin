/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import java.util.List;

/**
 * Defines necessary methods to create SOQL Expression tree nodes.
 *
 * @author sergio_daza
 */
public interface SOQLExpressionTree extends ExpressionTree {
    public SOQLExpressionSelectTree getSelesctStatement();
    
    public SOQLExpressionFromTree getFromStatement();
    
    public List<ExpressionTree> getOptionalStatements();
}
