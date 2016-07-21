/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import java.util.Collections;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 *
 * @author Adrian Grajeda
 */
public class MethodInvocationTreeImpl extends CompoundTree<ExpressionTree> implements MethodInvocationTree {

    private static final List<? extends Tree> EMPTY_TYPE_ARGUMENTS = Collections.emptyList();

    private final ExpressionTree expression;
    private final List<? extends ExpressionTree> arguments;

    public MethodInvocationTreeImpl(Token token, ExpressionTree expression, List<? extends ExpressionTree> arguments) {
        super(Kind.METHOD_INVOCATION, token);
        this.expression = expression;
        this.arguments = arguments;
        children.add(this.expression);
    }

    @Override
    public List<? extends Tree> getTypeArguments() {
        return EMPTY_TYPE_ARGUMENTS;
    }

    @Override
    public ExpressionTree getMethodSelect() {
        return expression;
    }

    @Override
    public List<? extends ExpressionTree> getArguments() {
        return arguments;
    }
}
