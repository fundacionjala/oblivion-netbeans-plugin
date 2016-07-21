/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithDivisionFilterExpressionTree;

/**
 * Represents a tree node for a SOSL WITH DIVISION FILTER clause. For example
 * <p>
 *    	 WITH DIVISION = 'Global'
 * </p>
 * @author Pablo Romero
 */
public class SOSLWithDivisionFilterExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLWithDivisionFilterExpressionTree {

    private final LiteralTree divisionField;
    SOSLWithDivisionFilterExpressionTreeImpl(LiteralTree divisionField) {
        super(Tree.Kind.OTHER, null);
        this.divisionField = divisionField;
        addMember(this.divisionField);
    }

    @Override
    public LiteralTree getDivisionField() {
        return this.divisionField;
    }
}
