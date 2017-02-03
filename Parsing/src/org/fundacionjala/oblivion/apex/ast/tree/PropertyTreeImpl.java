/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.ast.AccessorTree;
import org.fundacionjala.oblivion.apex.grammar.ast.PropertyTree;

/**
 * A Tree Node for a Property member. for example:
 * <p>
 * Modifiers Type identifier { accessors } </p>
 *
 * @author Marcelo Garnica
 */
public class PropertyTreeImpl extends CompoundTree<AccessorTree> implements PropertyTree {

    private final List<? extends AccessorTree> accesors;
    private final ModifiersTree modifiers;
    private final Tree type;
    private final Name name;

    PropertyTreeImpl(ModifiersTree modifiers, Tree type, Token identifier, List<? extends AccessorTree> accessors, Token blockStart, Token blockEnd) {
        super(Kind.OTHER, identifier, new ArrayList<AccessorTree>(accessors), blockStart, blockEnd);
        this.accesors = accessors;
        this.modifiers = modifiers;
        this.name = TreeUtils.createNameFromString(identifier.getImage());
        this.type = type;
    }

    @Override
    public List<? extends AccessorTree> getAccesors() {
        return accesors;
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
    public ExpressionTree getNameExpression() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public ExpressionTree getInitializer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitVariable(this, d);
        return super.accept(tv, d);
    }
}
