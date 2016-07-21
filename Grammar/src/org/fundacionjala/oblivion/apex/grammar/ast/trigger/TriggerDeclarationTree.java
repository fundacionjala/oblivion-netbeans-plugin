/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.trigger;

import com.sun.source.tree.Tree;
import java.util.List;

/**
 * Represents a tree node for a trigger declaration. For Example
 * <p>
 *      Trigger TriggerObject on Account(after insert){}
 * </p>
 * @author Pablo Romero
 */
public interface TriggerDeclarationTree extends Tree {
    public List<? extends Tree> getMembers();
    public List<TriggerParameterTree> getParameter();
}
