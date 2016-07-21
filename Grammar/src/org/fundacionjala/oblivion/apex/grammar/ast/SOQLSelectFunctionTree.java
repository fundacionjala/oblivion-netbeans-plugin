/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.IdentifierTree;
import javax.lang.model.element.Name;

/**
 * A tree node for SOQLSelectFunction. For example:
 * List<sObject> list1 = [SELECT field1, count(field2) total FROM document];
 * List<sObject> list1 = [SELECT field1, count() FROM documents];
 * @author sergio_daza
 */
public interface SOQLSelectFunctionTree extends IdentifierTree{

    public Name getField();
    public Name getAlias();
}
