/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import javax.lang.model.element.Name;

/**
 * Defines necessary methods to create SOQL Field Expression tree nodes.
 *
 * @author sergio_daza
 */
public interface SOQLFieldExpressionTree extends ExpressionTree  {
    public Name getName();
    
    public IdentifierTree getOperator();
    
    public ExpressionTree getValue();
}
