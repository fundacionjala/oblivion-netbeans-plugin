/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.sosl;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import java.util.List;

/**
 * Represents a tree node for a SOSL Object Field argument. For example
 * <p>
 *    Article (Id, Title WHERE id = '15')
 * </p>
 * @author Pablo Romero
 */
public interface SOSLObjectFieldExpressionTree extends ExpressionTree{
    public IdentifierTree getObjectTypeName();
    public List<IdentifierTree> getFieldList();
    public List<ExpressionTree> getoptionalStatements();
}
