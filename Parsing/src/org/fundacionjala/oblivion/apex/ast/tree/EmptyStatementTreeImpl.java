/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represent an skip statement, i.e. ";"
 * 
 * @author Adrian Grajeda
 */
class EmptyStatementTreeImpl extends BaseTree implements EmptyStatementTree {

    EmptyStatementTreeImpl(Token token) {
        super(Kind.EMPTY_STATEMENT, token);
    }

    EmptyStatementTreeImpl() {
        super(Kind.EMPTY_STATEMENT, null);
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitEmptyStatement(this, d);
    }
}
