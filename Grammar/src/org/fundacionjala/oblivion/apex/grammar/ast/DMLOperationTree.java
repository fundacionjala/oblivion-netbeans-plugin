/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.StatementTree;

/**
 * Defines necessary methods to create DML Operator Tree nodes (operators: insert, update, delete, undelete). For example:
 * insert newAcct;
 * update myAcct;
 * delete doomedAccts;
 * undelete savedAccts;
 * @author sergio_daza
 */
public interface DMLOperationTree extends StatementTree {

    public DMLOperationEnum getDMLOperationEnum();
}
