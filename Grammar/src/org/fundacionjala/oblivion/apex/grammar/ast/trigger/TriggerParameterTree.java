/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.trigger;

import com.sun.source.tree.Tree;

/**
 * Represents a tree node for a trigger parameter. For example
 *  <p>
 *      after insert
 * </p>
 * @author Pablo Romero
 */
public interface TriggerParameterTree extends Tree {
    public TriggerType getTriggerType();
    public TriggerOperation getTriggerOperation();
}
