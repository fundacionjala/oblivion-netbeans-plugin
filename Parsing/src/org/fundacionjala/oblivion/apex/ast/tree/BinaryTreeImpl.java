/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import java.util.Arrays;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represent any operation that follows this form.
 * <p>
 * {@code expresion operator expresion} </p>
 * <p>
 * i.e {@code 5 + randomInt()} </p>
 *
 * @author adrian_grajeda
 */
public class BinaryTreeImpl extends CompoundTree<ExpressionTree> implements BinaryTree {

    private final ExpressionTree left;
    private final ExpressionTree right;

    public BinaryTreeImpl(Kind kind, Token token, ExpressionTree left, ExpressionTree right) {
        super(kind, token, Arrays.asList(left, right), null, null);
        this.left = left;
        this.right = right;
    }

    BinaryTreeImpl(ExpressionTree left, ExpressionTree right, Token operator) {
        super(null, operator, Arrays.asList(left, right), null, null);
        this.left = left;
        this.right = right;
    }

    @Override
    public ExpressionTree getLeftOperand() {
        return left;
    }

    @Override
    public ExpressionTree getRightOperand() {
        return right;
    }
}
