/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.TreeVisitor;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerOperation;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerParameterTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerType;

/**
 * Represents a tree node for a trigger parameter. For example
 *  <p>
 *      after insert
 * </p>
 * @author Pablo Romero
 */
public class TriggerParameterTreeImpl extends BaseTree implements TriggerParameterTree{
    private  final TriggerType type;
    private final TriggerOperation operation;

    public TriggerParameterTreeImpl(TriggerType type, TriggerOperation operation) {
        super(Kind.OTHER, null);
        this.type = type;
        this.operation = operation;
    }
    
    public TriggerType getType(){
        return this.type;
    }
    
    public TriggerOperation getOperation(){
        return this.operation;
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        return tv.visitOther(this, d);
    }

    @Override
    public TriggerType getTriggerType() {
        return this.type;
    }

    @Override
    public TriggerOperation getTriggerOperation() {
        return this.operation;
    }
}
