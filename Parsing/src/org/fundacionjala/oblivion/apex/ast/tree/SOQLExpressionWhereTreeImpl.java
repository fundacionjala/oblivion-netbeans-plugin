/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionWhereTree;

public class SOQLExpressionWhereTreeImpl extends CompoundTree<Tree> implements SOQLExpressionWhereTree {

    private final ExpressionTree optionsStatementWhere;

    SOQLExpressionWhereTreeImpl(ExpressionTree optionsStatementWhere) {
        super(Tree.Kind.OTHER, null);
        this.optionsStatementWhere = optionsStatementWhere;
        addMember(this.optionsStatementWhere);
    }

    @Override
    public Name getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ExpressionTree getOptionsStatementWhere() {
        return this.optionsStatementWhere;
    }

}
