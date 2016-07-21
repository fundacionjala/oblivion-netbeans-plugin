/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.VariableTree;
import java.util.List;

/**
 * A Tree Node for a Property member. for example:
 * <p> Modifiers Type identifier 
 *     {
 *          accessors
 *     } </p>
 * @author Marcelo Garnica
 */
public interface PropertyTree extends VariableTree {
    List<? extends AccessorTree> getAccesors();
}
