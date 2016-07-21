/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionFromTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionSelectTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionTree;

public class SOQLExpressionTreeImpl extends CompoundTree<Tree> implements SOQLExpressionTree {

    private final SOQLExpressionSelectTree selectSOQLExpression;
    private final SOQLExpressionFromTree fromSOQLExpression;
    private final List<ExpressionTree> optionalStatments;

    SOQLExpressionTreeImpl(SOQLExpressionSelectTree selectSOQLExpression, SOQLExpressionFromTree fromSOQLExpression, List<ExpressionTree> optionalStatments) {
        super(Tree.Kind.OTHER, null);
        this.selectSOQLExpression = selectSOQLExpression;
        this.fromSOQLExpression = fromSOQLExpression;
        this.optionalStatments = optionalStatments;
        addMember(this.selectSOQLExpression);
        addMember(this.fromSOQLExpression);
        addMembers(this.optionalStatments);

    }

    @Override
    public SOQLExpressionSelectTree getSelesctStatement() {
        return selectSOQLExpression;
    }

    @Override
    public SOQLExpressionFromTree getFromStatement() {
        return fromSOQLExpression;
    }

    @Override
    public List<ExpressionTree> getOptionalStatements() {
        return optionalStatments;
    }
}
