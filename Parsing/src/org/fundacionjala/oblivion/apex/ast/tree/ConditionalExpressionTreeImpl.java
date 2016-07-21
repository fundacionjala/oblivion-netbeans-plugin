/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ExpressionTree;
import java.util.LinkedList;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Represent expression of this kind:
 * <p>
 * {@code booleanExpression? TrueExpression : FalseExpression }
 * </p>
 * @author Adrian Grajeda
 */
public class ConditionalExpressionTreeImpl extends CompoundTree<ExpressionTree> implements ConditionalExpressionTree {

    private final ExpressionTree condition;
    private final ExpressionTree trueExpression;
    private final ExpressionTree falseExpression;
    
    ConditionalExpressionTreeImpl(Token token, 
                                  ExpressionTree condition, 
                                  ExpressionTree trueExpression,
                                  ExpressionTree falseExpression) {
        
        super(Kind.CONDITIONAL_EXPRESSION, token);
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
        
        addMember(condition);
        addMember(trueExpression);
        addMember(falseExpression);
        
    }

    ConditionalExpressionTreeImpl(ExpressionTree condition, ExpressionTree trueExpression, ExpressionTree falseExpression) {
        this(null, condition, trueExpression, falseExpression );
    }

    @Override
    public ExpressionTree getCondition() {
        return condition;
    }

    @Override
    public ExpressionTree getTrueExpression() {
        return trueExpression;
    }

    @Override
    public ExpressionTree getFalseExpression() {
        return falseExpression;
    }
}
