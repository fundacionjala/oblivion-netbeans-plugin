/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.Token;


/**
 * Represents a literal expression in Apex language.
 * 
 * There are types of literal such as integers, doubles, etc.
 * 
 * @author Adrian Grajeda
 */
final class LiteralTreeImpl extends BaseTree implements LiteralTree {


    LiteralTreeImpl(Kind kind, Token token) {
        super(kind, token);
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitLiteral(this, d);
        return null;
    }

    @Override
    public Object getValue() {
        return getToken();
    }
}
