/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import java.util.HashSet;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLFindExpressionTree;

/**
 * Represents a tree node for a SOSL search query. for example
 * <p>
 *    FIND 'John Smith'
 *    RETURNING Account
 *    LIMIT 10
 * </p>
 * @author Pablo Romero
 */
public class SOSLExpressionTreeImpl extends CompoundTree<Tree> implements SOSLExpressionTree{
    
    private final SOSLFindExpressionTree findSOSLExpression;
    private final List<ExpressionTree> optionalStatements;
    
    SOSLExpressionTreeImpl(SOSLFindExpressionTree findSOSLExpression, List<ExpressionTree> optionalStatements) {
        super(Tree.Kind.OTHER, null);
        this.findSOSLExpression = findSOSLExpression;
        this.optionalStatements = optionalStatements;
        addMember(this.findSOSLExpression);
        addMembers(this.optionalStatements);
    }

    @Override
    public SOSLFindExpressionTree getFindExpression() {
        return this.findSOSLExpression;
    }

    @Override
    public List<ExpressionTree> getOptionalStatements() {
        return this.optionalStatements;
    }
}
