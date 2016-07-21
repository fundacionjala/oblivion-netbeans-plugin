/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;

/**
 * class that to build proposal item for methods
 *
 * @author sergio_daza
 */
public class MethodItem extends ProposalItem {

    public List<? extends VariableTree> variablesMethod;

    public MethodItem(String name, String type, int anchorOffset, Set<Modifier>  modifiers, ElementKind elementKind) {
        super(name, type, "", anchorOffset);
        variablesMethod = new ArrayList<>();
        super.modifiers = modifiers;
        super.elementKind = elementKind;
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        formatter.appendText(name + "(" + getParameters() + ")");
        return formatter.getText();
    }

    @Override
    public String getCustomInsertTemplate() {
        StringBuilder sb = new StringBuilder();
        sb.append(getInsertPrefix());
        String[] delimiters = getParamListDelimiters();
        assert delimiters.length == 2;
        if(this.variablesMethod.size() == 0) {
            sb.append(delimiters[0]);
            sb.append(delimiters[1]);
            sb.append("${cursor}");
        } else {
            sb.append(delimiters[0]);
            sb.append("${cursor}");
            sb.append(delimiters[1]);
        }
        return sb.toString();
    }

    @Override
    public ElementKind getKind() {
        return super.elementKind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodItem other = (MethodItem) obj;
        return super.name.equals(other.getName())
                && super.type.equals(other.type)
                && super.icon.equals(other.icon)
                && this.variablesMethod.equals(other.variablesMethod);
    }

    @Override
    public String[] getParamListDelimiters() {
        return new String[]{"(", ")"};
    }
    
    /**
     * This method return a string that contain text with parameters (type and name) to show between parentheses. For example: 
     * "String a, Integer b, ..."
     *
     * @return a String
     */
    private String getParameters() {
        StringBuilder sb = new StringBuilder();
        for (VariableTree variable : variablesMethod) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            final String typeVariable = variable.getType().toString();
            final String nameVariable = variable.getName().toString();
            sb.append(typeVariable);
            sb.append(" ");
            sb.append(nameVariable);
        }
        return sb.toString();
    }
    
    public boolean isMethod(String methodName, int parameters) {
        return super.name.equalsIgnoreCase(methodName) && this.variablesMethod.size() == parameters;
    }
   
}
