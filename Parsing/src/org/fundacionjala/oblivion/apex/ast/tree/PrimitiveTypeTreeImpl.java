/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.TreeVisitor;
import javax.lang.model.type.TypeKind;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represents a primitive type of a method variable or something similar.
 * 
 * @see  http://docs.oracle.com/javase/8/docs/api/javax/lang/model/type/TypeKind.html
 * @author Adrian Grajeda
 */
class PrimitiveTypeTreeImpl extends BaseTree implements PrimitiveTypeTree, ExpressionTree {
    
    private final TypeKind primitiveTypeKind;

    PrimitiveTypeTreeImpl(Token token, TypeKind kind) {
        super(Kind.PRIMITIVE_TYPE, token);
        primitiveTypeKind = kind;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitPrimitiveType(this, d);
        return null;
    }

    @Override
    public TypeKind getPrimitiveTypeKind() {
        return primitiveTypeKind;
    }
}
