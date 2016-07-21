/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLGroupByExpressionTree;

/**
 *
 * @author sergio_daza
 */
class SOQLGroupByExpressionTreeImpl extends CompoundTree<Tree> implements SOQLGroupByExpressionTree {

    private final List<IdentifierTree> identifiers;
    private final List<MethodInvocationTree> methodInvocations;
    
    public SOQLGroupByExpressionTreeImpl(List<IdentifierTree> identifiers,List<MethodInvocationTree> methodInvocations) {
        super(Tree.Kind.OTHER, null);
        this.identifiers = identifiers;
        this.methodInvocations = methodInvocations;
        addMembers(identifiers);
        addMembers(methodInvocations);
    }

    @Override
    public List<IdentifierTree> getIdentifiers() {
        return this.identifiers;
    }

    @Override
    public List<MethodInvocationTree> getMethodInvocations() {
        return this.methodInvocations;
    }

}
