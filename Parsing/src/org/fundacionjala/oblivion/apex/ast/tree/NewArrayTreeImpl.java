/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.Collections;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * A tree node for an expression to create a new instance of an array. For example:
 * <p>
 *   new type [dimension]
 *
 *   new type [] { initializers }
 * </p>
 * @author Marcelo Garnica
 */
public class NewArrayTreeImpl extends CompoundTree<Tree> implements NewArrayTree {
    private static final List<? extends AnnotationTree> EMPTY_ANNOTATIONS = Collections.emptyList();
    private static final List<? extends List<? extends AnnotationTree>> EMPTY_DIM_ANNOTATIONS = Collections.emptyList();
    private final Tree type;
    private final List<ExpressionTree> dimensions;
    private final List<? extends ExpressionTree> initializers;
    

    NewArrayTreeImpl(Token blockStart, Token blockEnd, Tree type, ExpressionTree dimension) {
        super(Kind.NEW_ARRAY, null, null, blockStart, blockEnd);
        this.type = type;
        this.dimensions = Collections.singletonList(dimension);
        this.initializers = Collections.emptyList();
        init();
    }

    NewArrayTreeImpl(Token blockStart, Token blockEnd, Tree type, List<? extends ExpressionTree> initializers) {
        super(Kind.NEW_ARRAY, null, null, blockStart, blockEnd);
        this.type = type;
        this.initializers = initializers;
        this.dimensions = Collections.emptyList();
        init();
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitNewArray(this, d);
    }

    @Override
    public Tree getType() {
        return type;
    }

    @Override
    public List<? extends ExpressionTree> getDimensions() {
        return dimensions;
    }

    @Override
    public List<? extends ExpressionTree> getInitializers() {
        return initializers;
    }

    @Override
    public List<? extends AnnotationTree> getAnnotations() {
        return EMPTY_ANNOTATIONS;
    }

    @Override
    public List<? extends List<? extends AnnotationTree>> getDimAnnotations() {
        return EMPTY_DIM_ANNOTATIONS;
    }

    private void init() {
        addMember(type);
        addMembers(dimensions);
        addMembers(initializers);
        updateParentReference();
    }

}
