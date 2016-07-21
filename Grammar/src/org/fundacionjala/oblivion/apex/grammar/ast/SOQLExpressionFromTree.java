/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import javax.lang.model.element.Name;
/**
 * Defines necessary methods to create SOQL Expression From tree nodes.
 *
 * @author sergio_daza
 */
public interface SOQLExpressionFromTree extends ExpressionTree {
    public Name getName();
}
