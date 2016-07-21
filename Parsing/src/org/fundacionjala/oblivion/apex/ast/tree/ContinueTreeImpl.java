/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.TreeVisitor;
import javax.lang.model.element.Name;

/**
 * A tree node for a 'continue' statement. For example:
 * <p> continue; </p>
 * @author Marcelo Garnica
 */
class ContinueTreeImpl extends BaseTree implements ContinueTree {
    private static final String DEFAULT_EMPTY_LABEL = "";

    ContinueTreeImpl() {
        super(Kind.CONTINUE, null);
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitContinue(this, d);
    }

    @Override
    public Name getLabel() {
        return TreeUtils.createNameFromString(DEFAULT_EMPTY_LABEL);
    }

}
