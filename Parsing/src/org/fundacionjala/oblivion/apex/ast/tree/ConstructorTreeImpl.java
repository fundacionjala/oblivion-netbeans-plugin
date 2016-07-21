/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.VariableTree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.ConstructorTree;

/**
 * Represents an Apex constructor declaration.
 * <p>
 *    {@code modifiers name( parameters )
             {
                 body
             }
      }
 * </p>
 *
 * @author Marcelo Garnica
 */
public class ConstructorTreeImpl extends MethodTreeImpl implements ConstructorTree {

    ConstructorTreeImpl(ModifiersTree modifier, IdentifierTree nameIdentifier, List<? extends VariableTree> parameters, BlockTree body) {
        super(modifier, null, nameIdentifier, body, parameters);
    }
}
