/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLFromArgumentTree;



public class SOQLFromArgumentTreeImpl extends BaseTree implements SOQLFromArgumentTree {
    
    private final IdentifierTree argument;
    private final IdentifierTree alias;

    public SOQLFromArgumentTreeImpl(IdentifierTree argument, IdentifierTree alias) {
        super(Tree.Kind.OTHER, null);
        this.argument = argument;
        this.alias = alias;
    }    

    @Override
    public IdentifierTree getArgument() {
        return argument;
    }

    @Override
    public IdentifierTree getAlias() {
        return alias;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this,d);
    }
}
