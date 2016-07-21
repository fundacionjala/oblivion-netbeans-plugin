/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast.trigger;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import java.util.List;

/**
 * Represents the abstract syntax tree for the trigger compilation units. 
 * @author Pablo Romero
 */
public interface TriggerCompilationUnitTree extends CompilationUnitTree {
    public List<Tree> getTriggerDeclarations();
}
