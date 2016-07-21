/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import java.util.List;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLExpressionFromTree;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLFromArgumentTree;

public class SOQLExpressionFromTreeImpl  extends CompoundTree<Tree> implements SOQLExpressionFromTree {

    private final List<SOQLFromArgumentTree> arguments;
    SOQLExpressionFromTreeImpl(List<SOQLFromArgumentTree> arguments) {
        super(Tree.Kind.OTHER, null);
        this.arguments = arguments;
        addMembers(arguments);
        
    }
    
    @Override
    public Name getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
