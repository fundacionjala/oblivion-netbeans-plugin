/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLObjectFieldExpressionTree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLReturningExpressionTree;

/**
 * Represents a tree node for a SOSL RETURNING clause. For example
 * <p>
 *    RETURNING Contact(FirstName, LastName)
 * </p>
 * @author Pablo Romero
 */
public class SOSLReturningExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLReturningExpressionTree {

    private final List<SOSLObjectFieldExpressionTree> objectList;
    SOSLReturningExpressionTreeImpl(List<SOSLObjectFieldExpressionTree> objectList){
        super(Tree.Kind.OTHER, null);
        this.objectList = objectList;
        addMembers(objectList);
    }

    @Override
    public List<SOSLObjectFieldExpressionTree> getObjectList() {
        return this.objectList;
    }
}
