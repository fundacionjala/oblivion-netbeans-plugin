/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BreakTree;
import com.sun.source.tree.TreeVisitor;
import javax.lang.model.element.Name;

/**
 * A tree node for a 'break' statement. For example:
 * <p> break; </p>
 * @author Marcelo Garnica
 */
class BreakTreeImpl extends BaseTree implements BreakTree {
    private static final String DEFAULT_EMPTY_LABEL = "";

    BreakTreeImpl() {
        super(Kind.BREAK, null);
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitBreak(this, d);
    }

    @Override
    public Name getLabel() {
        return TreeUtils.createNameFromString(DEFAULT_EMPTY_LABEL);
    }

}
