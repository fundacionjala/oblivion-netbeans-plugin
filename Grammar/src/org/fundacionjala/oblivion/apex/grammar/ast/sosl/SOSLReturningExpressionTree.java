/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import java.util.List;

/**
 * Represents a tree node for a SOSL RETURNING clause. For example
 * <p>
 *    RETURNING Contact(FirstName, LastName)
 * </p>
 * @author Pablo Romero
 */
public interface SOSLReturningExpressionTree extends ExpressionTree{
    public List<SOSLObjectFieldExpressionTree> getObjectList();
}
