/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.TreeVisitor;
import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represents and identifier in the Apex language.
 *
 * @author Adrian Grajeda
 */
public class IdentifierTreeImpl extends BaseTree implements IdentifierTree {

    private final Name name;
    private List<? extends ExpressionTree> identifiers = new ArrayList<>();
    private Token reference;
    

    public IdentifierTreeImpl(Token token) {
        super(Kind.IDENTIFIER, token);
        name = TreeUtils.createNameFromString(token.getImage());
        reference = null;
    }

    public IdentifierTreeImpl(Name name, Token modifier) {
        this(name);
        this.reference=modifier;
    }
    public IdentifierTreeImpl(Name name) {
        super(Kind.IDENTIFIER, null);
        this.name = name;
        this.identifiers = name.getIdentifierList();
        reference = null;
    }

    public IdentifierTreeImpl(List<IdentifierTree> identifiers) {
        super(Kind.IDENTIFIER, TreeUtils.getTokenFromIdentifierTree(identifiers.get(0)));
        name = TreeUtils.createName(identifiers);
        this.identifiers = identifiers;
        reference = null;
    }
    
    public List<? extends ExpressionTree> getIdentifiers(){
        return identifiers;
    }
    
    public Token getReference(){
        return reference;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitIdentifier(this, d);
        return null;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return getName().toString();
    }
}
