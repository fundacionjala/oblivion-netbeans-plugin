/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.Token;

import static org.fundacionjala.oblivion.apex.ast.tree.TreeUtils.createNameFromString;

/**
 * Represents a member reference expression. <br/>
 *  
 * i.e. 
 * {@code 
 *    person.name
 * }
 * 
 * 
 * <p>Where person is an instance of a class Person and name it an attribute</p> 
 * 
 * @author Adrian Grajeda
 */
public class MemberSelectTreeImpl extends CompoundTree<ExpressionTree> implements MemberSelectTree {
    
    private final ExpressionTree expresion;
    
    private final Name name;

    MemberSelectTreeImpl(Token token, ExpressionTree expression) {
        super(Kind.MEMBER_SELECT, token);
        this.expresion = expression;
        children.add(this.expresion);
        name = createNameFromString(token.getImage());
    }

    @Override
    public ExpressionTree getExpression() {
        return expresion;
    }

    @Override
    public Name getIdentifier() {
        return name;
    }
}
