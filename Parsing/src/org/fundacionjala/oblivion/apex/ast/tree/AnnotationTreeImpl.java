/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * A tree node for an annotation. For example:
 * <p>   @annotationType
 *       @annotationType ( arguments ) </p>
 * @author Marcelo Garnica
 */
public class AnnotationTreeImpl extends CompoundTree<Tree> implements AnnotationTree {
    private final Token symbol;
    private final Tree annotationType;
    private final List<? extends ExpressionTree> arguments;

    public AnnotationTreeImpl(Tree annotationType, List<? extends ExpressionTree> arguments) {
        super(Kind.ANNOTATION, null);
        this.symbol = null;
        this.annotationType = annotationType;
        this.arguments = arguments;
    }
    
    public AnnotationTreeImpl(Token symbol, Tree annotationType, List<? extends ExpressionTree> arguments) {
        super(Kind.ANNOTATION, null);
        this.symbol = symbol;
        this.annotationType = annotationType;
        this.arguments = arguments;
    }
    
    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        super.accept(tv, d);
        return tv.visitAnnotation(this, d);
    }

    @Override
    public Tree getAnnotationType() {
        return annotationType;
    }

    @Override
    public List<? extends ExpressionTree> getArguments() {
        return arguments;
    }

    public Token getSymbol() {
        return symbol;
    }
}
