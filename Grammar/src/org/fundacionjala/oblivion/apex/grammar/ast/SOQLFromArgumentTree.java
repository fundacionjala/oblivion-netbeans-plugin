/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;

/**
 * Defines necessary methods to create SOQL From Argument tree nodes.
 *
 * @author nelson_alcocer
 */
public interface SOQLFromArgumentTree extends ExpressionTree {
    public IdentifierTree getArgument();

    public IdentifierTree getAlias();
}
