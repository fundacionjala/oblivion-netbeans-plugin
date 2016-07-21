/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithDataCategoryExpressionTree;

/**
 * Represents a tree node for a SOSL WITH DATA CATEGORY clause. For example
 * <p>
 *    	 WITH DATA CATEGORY Geography ABOVE France
 * </p>
 * @author Pablo Romero
 */
public class SOSLWithDataCategoryExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLWithDataCategoryExpressionTree {

    private final ExpressionTree dataCategory;
    SOSLWithDataCategoryExpressionTreeImpl(ExpressionTree dataCategory) {
        super(Tree.Kind.OTHER, null);
        this.dataCategory = dataCategory;
        addMember(this.dataCategory);
    }

    @Override
    public ExpressionTree getDataCategory() {
        return this.dataCategory;
    }
}
