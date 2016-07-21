/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.MethodTree;

/**
 * Represents an Apex constructor declaration.
 * <p>
 *    {@code modifiers name( parameters )
             {
                 body
             }
      }
 * </p>
 * @author Marcelo Garnica
 */
public interface ConstructorTree extends MethodTree {

}
