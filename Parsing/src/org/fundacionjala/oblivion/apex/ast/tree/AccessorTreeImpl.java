/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ModifiersTree;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.ast.AccessorTree;

/**
 * A tree node for the accessor of a Property member.
 *
 * @author Marcelo Garnica
 */
class AccessorTreeImpl extends MethodTreeImpl implements AccessorTree {

    AccessorTreeImpl(ModifiersTree modifier, IdentifierTree nameIdentifier, BlockTree body) {
        super(modifier, null, nameIdentifier, body);
    }

    public AccessorTreeImpl(ModifiersTree modifier, IdentifierTree nameIdentifier, Token startToken, Token endToken) {
        super(modifier, null, nameIdentifier, null);
        this.setBlockStart(startToken);
        this.setBlockEnd(endToken);
    }
}
