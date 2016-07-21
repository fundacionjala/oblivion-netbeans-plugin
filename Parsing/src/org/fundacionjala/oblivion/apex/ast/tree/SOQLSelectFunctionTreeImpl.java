/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.grammar.ast.SOQLSelectFunctionTree;

/**
 * A tree node for SOQLSelectFunction expression. For example:
 * List<sObject> list1 = [SELECT field1, count(field2) total FROM document];
 * List<sObject> list1 = [SELECT field1, count() FROM documents];
 * @author sergio_daza
 */
class SOQLSelectFunctionTreeImpl extends CompoundTree<Tree> implements SOQLSelectFunctionTree {
    
    private final Name name;
    private final Name field;
    private final Name alias;
    
    public SOQLSelectFunctionTreeImpl(Name name, Name field, Name alias) {
        super(Tree.Kind.OTHER, null);
        this.name = name;
        this.field = field;
        this.alias = alias;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Name getAlias() {
        return alias;
    }

    @Override
    public Name getField() {
        return field;
    }
   
}
