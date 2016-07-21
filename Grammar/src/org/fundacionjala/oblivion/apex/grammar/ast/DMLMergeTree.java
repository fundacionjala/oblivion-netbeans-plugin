/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;

/**
 * Defines necessary methods to create DML Merge Tree nodes. For example:
 * merge masterAcct mergeAcct;
 * @author sergio_daza
 */
public interface DMLMergeTree extends DMLOperationTree {

    public ExpressionTree getMergeRecordsExpression();
}
