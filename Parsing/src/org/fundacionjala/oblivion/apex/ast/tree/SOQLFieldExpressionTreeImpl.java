/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLFieldExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;

/**
 *
 * @author sergio_daza
 */
public class SOQLFieldExpressionTreeImpl extends BaseTree implements  SOQLFieldExpressionTree {
    
    private final Name name;
    private final Token operator;
    private final ExpressionTree value;
    
    SOQLFieldExpressionTreeImpl(Name name, Token operator, ExpressionTree value) {
        super(Tree.Kind.OTHER, null);
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public IdentifierTree getOperator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExpressionTree getValue() {
        return value;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this,d);
    }

}
