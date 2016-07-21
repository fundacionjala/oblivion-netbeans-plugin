/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represents an Apex method declaration.
 * <p>
 *    {@code modifiers type name( parameters )
          {
              body
          }
      }
 * </p>
 *
 * @author Marcelo Garnica
 */
public class MethodTreeImpl extends CompoundTree<Tree> implements MethodTree {

    private static final List<? extends ExpressionTree> EMPTY_TROWS_LIST = Collections.emptyList();

    private final ModifiersTree modifier;
    private final Tree type;
    private final Name name;
    private final BlockTree body;
    private final List<? extends VariableTree> parameters;

    MethodTreeImpl(ModifiersTree modifier, Tree type, ExpressionTree nameIdentifier, BlockTree body) {
        this(modifier, type, nameIdentifier, body, new ArrayList<VariableTree>());
    }

    MethodTreeImpl(ModifiersTree modifier, Tree type, ExpressionTree nameIdentifier, BlockTree body, List<? extends VariableTree> parameters) {
        super(Kind.METHOD, TreeUtils.getTokenFromIdentifierTree(nameIdentifier));
        this.modifier = modifier;
        this.type = type;
        this.body = body;
        this.name = TreeUtils.createNameFromExpressionTree(nameIdentifier);
        this.parameters = parameters;
        addMembers(this.parameters);
        addMember(this.body);
        updateParentReference();
    }

    MethodTreeImpl(ModifiersTree modifier, Tree type, ExpressionTree nameIdentifier, List<? extends VariableTree> parameters, Token startToken, Token endToken) {
        super(Kind.METHOD, TreeUtils.getTokenFromIdentifierTree(nameIdentifier));
        this.modifier = modifier;
        this.type = type;
        this.body = null;
        this.name = TreeUtils.createNameFromExpressionTree(nameIdentifier);
        this.parameters = parameters;
        addMembers(this.parameters);
        this.setBlockStart(startToken);
        this.setBlockEnd(endToken);
        updateParentReference();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitMethod(this, d);
        for (VariableTree variable : parameters) {
            variable.accept(tv, d);
        }
        return super.accept(tv, d);
    }

    @Override
    public ModifiersTree getModifiers() {
        return modifier;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Tree getReturnType() {
        return type;
    }

    @Override
    public List<? extends TypeParameterTree> getTypeParameters() {
        return Collections.emptyList();
    }

    @Override
    public List<? extends VariableTree> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    @Override
    public VariableTree getReceiverParameter() {
        return null;
    }

    @Override
    public List<? extends ExpressionTree> getThrows() {
        return EMPTY_TROWS_LIST;
    }

    @Override
    public BlockTree getBody() {
        return body;
    }

    @Override
    public Tree getDefaultValue() {
        return null;
    }
}
