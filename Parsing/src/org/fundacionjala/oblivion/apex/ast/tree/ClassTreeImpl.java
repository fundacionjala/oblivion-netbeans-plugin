/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TypeParameterTree;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.Token;


/**
 * Tree node for an Apex class or interface.
 * <p>
 * {@code modifiers class simpleName extends extendsClause
                                     implements implementsClause
          {
              members
          }
       }
 * </p>
 * @author Marcelo Garnica
 */
public class ClassTreeImpl extends CompoundTree<Tree> implements ClassTree {

    private static final List<? extends TypeParameterTree> EMPTY_TYPE_PARAMETERS = Collections.emptyList();

    private final ModifiersTree modifiers;
    private final List<Token> sharingRules;
    private final Tree type;
    private final Name name;
    private final List<? extends Tree> members;
    private final List<? extends Tree> implementsClause;
    private final Tree extendsClause;
    
    public ClassTreeImpl(ModifiersTree modifiers, List<Token> sharingRules, Tree type, Token nameToken, Token blockStart, Token blockEnd, List<? extends Tree> implementsClause, Tree extendsClause, List<? extends Tree> members) {
        super(Kind.CLASS, nameToken, new LinkedList<>(members), blockStart, blockEnd);
        this.sharingRules = sharingRules;
        this.type = type;
        this.modifiers = modifiers;
        this.name = nameToken != null ? TreeUtils.createNameFromString(nameToken.getImage()) : null;
        this.members = members;
        this.implementsClause = implementsClause;
        this.extendsClause = extendsClause;
        updateParentReference();
    }

    @Override
    public ModifiersTree getModifiers() {
        return modifiers;
    }

    @Override
    public Name getSimpleName() {
        return name;
    }

    @Override
    public List<? extends TypeParameterTree> getTypeParameters() {
        return EMPTY_TYPE_PARAMETERS;
    }

    @Override
    public Tree getExtendsClause() {
        return extendsClause;
    }

    @Override
    public List<? extends Tree> getImplementsClause() {
        return implementsClause;
    }

    @Override
    public List<? extends Tree> getMembers() {
        return members;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitClass(this, d);
        super.accept(tv, d);
        return null;
    }

    public List<Token> getSharingRules() {
        return sharingRules;
    }

    public Tree getType() {
        return type;
    }
}
