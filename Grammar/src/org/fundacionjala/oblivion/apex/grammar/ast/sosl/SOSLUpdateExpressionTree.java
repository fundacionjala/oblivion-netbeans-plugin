/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;

/**
 * Represents a tree node for a SOSL UPDATE clause. For example
 * <p>
 *    	UPDATE TRACKING
 * </p>
 * @author Pablo Romero
 */
public interface SOSLUpdateExpressionTree extends ExpressionTree{
    public SOSLUpdateType getUpdateType();
}

