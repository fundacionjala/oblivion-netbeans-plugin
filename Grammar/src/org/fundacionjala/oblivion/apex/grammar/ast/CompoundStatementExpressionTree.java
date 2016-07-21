/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.StatementTree;
import java.util.List;

/**
 * Defines necessary methods to create a compound statement expression From tree nodes.
 *
 * @author sergio_daza
 */
public interface CompoundStatementExpressionTree extends StatementTree {

    public StatementTree  getParentStatement();
    public List<StatementTree>  getChildrenStatements();
}
