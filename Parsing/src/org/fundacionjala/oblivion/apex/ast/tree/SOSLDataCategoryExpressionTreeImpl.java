/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLDataCategoryOperator;

/**
 * Represents a tree node for a SOSL DATA CATEGORY specification. For example
 * <p>
 *    Geography AT Iceland
 * </p>
 * @author Pablo Romero
 */
public class SOSLDataCategoryExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLDataCategoryExpressionTree {

    private final IdentifierTree groupName;
    private final SOSLDataCategoryOperator operator;
    private final IdentifierTree category;
    SOSLDataCategoryExpressionTreeImpl(IdentifierTree groupName, SOSLDataCategoryOperator operator, IdentifierTree category) {
        super(Tree.Kind.OTHER, null);
        this.groupName = groupName;
        this.operator = operator;
        this.category = category;
        addMember(groupName);
        addMember(category);
    }

    @Override
    public IdentifierTree getGroupName() {
        return this.groupName;
    }

    @Override
    public SOSLDataCategoryOperator getOperator() {
        return this.operator;
    }

    @Override
    public IdentifierTree getCategory() {
        return this.category;
    }
}
