/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import java.util.List;
import org.fundacionjala.oblivion.apex.grammar.ast.sosl.SOSLObjectFieldExpressionTree;

/**
 * Represents a tree node for a SOSL Object Field element. For example
 * <p>
 *    Article (Id, Title WHERE id = '15')
 * </p>
 * @author Pablo Romero
 */
public class SOSLObjectFieldExpressionTreeImpl extends CompoundTree<Tree> implements SOSLObjectFieldExpressionTree{

    private final IdentifierTree objectTypeName;
    private final List<IdentifierTree> fieldList;
    private final List<ExpressionTree> optionalStatements;
    
    SOSLObjectFieldExpressionTreeImpl(IdentifierTree objectTypeName, List<IdentifierTree> fieldList, List<ExpressionTree> optionalStatements){
        super(Tree.Kind.OTHER, null);
        this.objectTypeName = objectTypeName;
        this.fieldList = fieldList;
        this.optionalStatements = optionalStatements;
        addMember(this.objectTypeName);
        addMembers(this.fieldList);
        addMembers(this.optionalStatements);
    }

    @Override
    public IdentifierTree getObjectTypeName() {
        return this.objectTypeName;
    }

    @Override
    public List<IdentifierTree> getFieldList() {
        return this.fieldList;
    }

    @Override
    public List<ExpressionTree> getoptionalStatements() {
        return this.optionalStatements;
    }
}
