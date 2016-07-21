/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;

/**
 * Defines necessary methods to create DML Upsert Tree nodes. For example:
 * upsert acctList; 
 * upsert sObjectList Account.Fields.MyExternalId__c;
 * @author sergio_daza
 */
public interface DMLUpsertTree extends DMLOperationTree {

    public ExpressionTree getOptionalExpression();
}
