/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionSelectTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLSelectFunctionTree;

public class SOQLExpressionSelectTreeImpl  extends CompoundTree<Tree> implements SOQLExpressionSelectTree {
    
    private final List<IdentifierTree> identifiers;
    private final List<SOQLSelectFunctionTree> functions;
    private final List<SOQLExpressionTree> querys;
    
    SOQLExpressionSelectTreeImpl(List<IdentifierTree> identifiers,List<SOQLSelectFunctionTree> functions, List<SOQLExpressionTree> querys) {
        super(Kind.OTHER, null);
        this.identifiers = identifiers;
        this.functions = functions;
        this.querys = querys;
        addMembers(this.identifiers);
        addMembers(this.querys);
    }

    @Override
    public List<IdentifierTree> getIdentifiers() {
        return identifiers;
    }
    
    @Override
    public List<SOQLExpressionTree> getNestedQuerys() {
        return querys;
    }

    @Override
    public List<SOQLSelectFunctionTree> getFunctions() {
        return functions;
    }

}
