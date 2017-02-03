/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree.visitors;

import com.sun.source.tree.PackageTree;
import com.sun.source.tree.VariableTree;

/**
 * Simple visitor that collects all declared variables by context
 * 
 * 
 * @author Adrian Grajeda
 */
public class DeclaredVariablesVisitor extends ApexTreeVisitorAdapter<DeclaredVariablesByScope, Void> {

    private final DeclaredVariablesByScope scope;

    public DeclaredVariablesVisitor() {
        scope = new DeclaredVariablesByScope();
    }
    
    @Override
    public DeclaredVariablesByScope visitVariable(VariableTree vt, Void p) {
        scope.addVariable(vt);
        return scope;
    }

    public DeclaredVariablesByScope getScope() {
        return scope;
    }
}
