/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import com.sun.source.tree.ExpressionTree;

/**
 *
 * @author sergio_daza
 */
public enum DMLOperationEnum {
        insert(null),
        update(null),
        delete(null),
        undelete(null),
        merge(null),
        upsert(null);
        private ExpressionTree expression;
        
        DMLOperationEnum(){
            this.expression = null;
        }
        
        DMLOperationEnum(ExpressionTree expression){
            this.expression = expression;
        }
        
        public ExpressionTree getExpression() {
            return expression;
        }
        
        public DMLOperationEnum setExpression(ExpressionTree expression) {
            this.expression = expression;
            return this;
        }
}
