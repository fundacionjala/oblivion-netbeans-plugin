/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MethodInvocationTree;
import java.util.List;

/**
 * Defines necessary methods to create SOQL GroupByExpression tree nodes.
 * 
 * @author sergio_daza
 */
public interface SOQLGroupByExpressionTree extends ExpressionTree {
    
    public List<IdentifierTree> getIdentifiers();
    public List<MethodInvocationTree> getMethodInvocations();

}
