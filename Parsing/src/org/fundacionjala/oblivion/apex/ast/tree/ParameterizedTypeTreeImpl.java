/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.HashSet;
import java.util.List;

/**
 * A tree node for a type expression involving type parameters. For example:
 * <p>
 * type < typeArguments > </p>
 *
 * @author Marcelo Garnica
 */
public class ParameterizedTypeTreeImpl extends CompoundTree<Tree> implements ParameterizedTypeTree {

    private final Tree type;
    private final List<Tree> typeArguments;
    private static final String PARAMETERIZED_TYPE_TEMPLATE = "%s<%s>";

    ParameterizedTypeTreeImpl(Tree type, List<Tree> typeArguments) {
        super(Kind.PARAMETERIZED_TYPE, null, new HashSet<>(typeArguments), null, null);
        this.type = type;
        this.typeArguments = typeArguments;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitParameterizedType(this, d);
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public List<? extends Tree> getTypeArguments() {
        return typeArguments;
    }

    @Override
    public String toString() {
        IdentifierTree type = (IdentifierTree) this.type;
        String parameterizedType = type.getName().toString();
        String arguments = "";

        for (Tree typeArgument : getTypeArguments()) {
            if (arguments.isEmpty()) {
                arguments = typeArgument.toString();
            } else {
                arguments = String.join(", ", arguments, typeArgument.toString());
            }
        }
        return String.format(PARAMETERIZED_TYPE_TEMPLATE, parameterizedType, arguments);
    }
}
