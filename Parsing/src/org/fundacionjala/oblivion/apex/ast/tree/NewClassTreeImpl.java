/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * A tree node to declare a new instance of a class. For example:
 * <p>
 * new identifier ( )
 *
 * new identifier ( arguments )
 *
 * new identifier typeArguments ( arguments )
 *
 * new identifier typeArguments { arguments }
 * </p>
 *
 * @author Marcelo Garnica
 */
public class NewClassTreeImpl extends CompoundTree<Tree> implements NewClassTree {

    private final ExpressionTree type;
    private final List<? extends Tree> typeArguments;
    private final List<? extends ExpressionTree> arguments;

    NewClassTreeImpl(ExpressionTree type, Token blockStart, Token blockEnd, List<? extends ExpressionTree> arguments) {
        super(Kind.NEW_CLASS, null, new HashSet<Tree>(arguments), blockStart, blockEnd);
        this.type = type;
        this.typeArguments = Collections.emptyList();
        this.arguments = arguments;
    }

    NewClassTreeImpl(ExpressionTree type, Token blockStart, Token blockEnd, List<? extends Tree> typeArguments, List<? extends ExpressionTree> arguments) {
        super(Kind.NEW_CLASS, null, null, blockStart, blockEnd);
        this.type = type;
        this.typeArguments = typeArguments;
        this.arguments = arguments;
        addMember(this.type);
        addMembers(this.typeArguments);
        addMembers(this.arguments);
        updateParentReference();
    }

    @Override
    public ExpressionTree getEnclosingExpression() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends Tree> getTypeArguments() {
        return typeArguments;
    }

    @Override
    public ExpressionTree getIdentifier() {
        return type;
    }

    @Override
    public List<? extends ExpressionTree> getArguments() {
        return arguments;
    }

    @Override
    public ClassTree getClassBody() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
