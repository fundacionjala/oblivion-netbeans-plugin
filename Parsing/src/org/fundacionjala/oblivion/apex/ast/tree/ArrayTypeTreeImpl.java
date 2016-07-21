/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;

/**
 * A tree node for an array type. For example:
 * <p>
 * type [] </p>
 *
 * @author Marcelo Garnica
 */
public class ArrayTypeTreeImpl extends BaseTree implements ArrayTypeTree {

    private final Tree type;
    private static final String ARRAY_TYPE_TEMPLATE = "%s[]";

    public ArrayTypeTreeImpl(Tree type) {
        super(Kind.ARRAY_TYPE, null);
        this.type = type;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitArrayType(this, d);
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format(ARRAY_TYPE_TEMPLATE, type.toString());
    }
}
