/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLWithSnippetExpressionTree;

/**
 * Represents a tree node for a SOSL WITH SNIPPET clause. For example
 * <p>
 *    	 WITH SNIPPET (target_length=120)
 * </p>
 * @author Pablo Romero
 */
public class SOSLWithSnippetExpressionTreeImpl  extends CompoundTree<Tree> implements SOSLWithSnippetExpressionTree {

    private final LiteralTree targetLength;
    SOSLWithSnippetExpressionTreeImpl(LiteralTree targetLength) {
        super(Tree.Kind.OTHER, null);
        this.targetLength = targetLength;
        addMember(this.targetLength);
    }

    @Override
    public LiteralTree getTargetLength() {
        return this.targetLength;
    }
}
