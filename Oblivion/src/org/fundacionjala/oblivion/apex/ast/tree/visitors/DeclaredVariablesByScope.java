/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree.visitors;

import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;

/**
 * Represents and store all the variables that are declared in the class, you can look for a variable name if it is 
 * declared in the closest scope.
 * 
 * @author Adrian Grajeda
 */
public class DeclaredVariablesByScope {
    
    private final Map<Tree, List<VariableTree>> variablesByScope;

    public DeclaredVariablesByScope() {
        this.variablesByScope = new HashMap<>();
    }
    
    public boolean isVariableInLocalScope(Name variableName, Tree scope) {
        Tree localScope = findClosestScope(scope);
        return isVariablesDeclaredInScope(variableName, localScope);
    }

    private Tree findClosestScope(Tree scope) {
        if (scope == null) {
            return null;
        } else if (variablesByScope.containsKey(scope)) {
            return scope;
        } else {
            return findClosestScope(TreeNavigationUtils.getParentNode(scope));
        }
    }

    private boolean isVariablesDeclaredInScope(Name variableName, Tree localScope) {
        List<VariableTree> declaredVariables = variablesByScope.get(localScope);
        for(VariableTree current : declaredVariables) {
            if (variableName.equals(current)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    void addVariable(VariableTree variable) {
        Tree parent = TreeNavigationUtils.getParentNode(variable);
        if (parent == null) {
            throw new IllegalStateException("A variable must have a parent node");
        }
        getVariableListByScope(parent).add(variable);
    }
    
    List<VariableTree> getVariableListByScope(Tree scope) {
        if (!variablesByScope.containsKey(scope)) {
            variablesByScope.put(scope, new ArrayList<VariableTree>());
        }
        return variablesByScope.get(scope);
    }
    
    Set<Tree> getAllScopes() {
        return variablesByScope.keySet();
    }
}
