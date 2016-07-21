/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;
import java.util.Objects;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.Token;

import static org.fundacionjala.oblivion.apex.ast.tree.TreeUtils.createNameFromString;

/**
 * Implements the {@link VariableTree} for Apex language.
 *
 * @author Adrian Grajeda
 */
public class VariableTreeImpl extends BaseTree implements VariableTree, ExpressionTree {

    private final Tree type;
    private final ModifiersTree modifiers;
    private final Name name;

    private ExpressionTree nameExpression;
    private ExpressionTree initializer;
    private Token semicolon;

    VariableTreeImpl(Tree type, Token token, ModifiersTree modifiers) {
        super(Kind.VARIABLE, token);
        this.type = type;
        this.modifiers = modifiers;
        name = createNameFromString(token.getImage());
        semicolon = null;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitVariable(this, d);
        return null;
    }

    @Override
    public ModifiersTree getModifiers() {
        return modifiers;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public ExpressionTree getNameExpression() {
        return nameExpression;
    }

    public void setNameExpression(ExpressionTree nameExpression) {
        this.nameExpression = nameExpression;
    }

    @Override
    public ExpressionTree getInitializer() {
        return initializer;
    }

    public Token getSemicolon() {
        return semicolon;
    }

    public void setInitializer(ExpressionTree initializer) {
        this.initializer = initializer;
    }

    public void setSemiColon(Token semicolon) {
        this.semicolon = semicolon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.modifiers);
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableTreeImpl other = (VariableTreeImpl) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(super.kind, other.kind)) {
            return false;
        }
        if (!Objects.equals(this.modifiers, other.modifiers)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
