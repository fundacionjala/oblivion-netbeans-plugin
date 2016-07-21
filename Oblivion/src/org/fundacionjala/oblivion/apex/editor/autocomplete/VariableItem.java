/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import com.sun.source.tree.Tree;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;

/**
 * class that to build proposal item for variables
 *
 * @author sergio_daza
 */
public class VariableItem extends ProposalItem {

    public Tree parent;
    public VariableItem(String name, String type, int anchorOffset, Tree parent) {
        super(name, type, "", anchorOffset);
        super.elementKind = ElementKind.VARIABLE;
        this.parent = parent;
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        formatter.appendText(super.name);
        return formatter.getText();
    }

    @Override
    public String getCustomInsertTemplate() {
        return super.getCustomInsertTemplate();
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
        final VariableItem other = (VariableItem) obj;
        return super.name.equals(other.getName())
                && super.type.equals(other.type)
                && super.icon.equals(other.icon);
    }

    @Override
    public String[] getParamListDelimiters() {
        return super.getParamListDelimiters();
    }
}
