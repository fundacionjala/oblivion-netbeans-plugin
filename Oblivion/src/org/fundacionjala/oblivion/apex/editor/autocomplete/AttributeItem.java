/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import java.util.Set;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;

/**
 * class that to build proposal item for attributes
 * 
 * @author sergio_daza
 */
public class AttributeItem extends ProposalItem {

    public AttributeItem(String name, String type, int anchorOffset, Set<Modifier>  modifiers) {
        super(name, type, "", anchorOffset);
        super.modifiers = modifiers;
        super.elementKind = ElementKind.ATTRIBUTE;
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        formatter.appendText(super.name);
        return "<b>"+formatter.getText()+"</b>";
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
        final AttributeItem other = (AttributeItem) obj;
        return super.name.equals(other.getName())
                && super.type.equals(other.type)
                && super.icon.equals(other.icon);
    }

    @Override
    public String[] getParamListDelimiters() {
        return super.getParamListDelimiters();
    }

    @Override
    public String getName() {
        if(super.withReference) {
            return "this."+name;
        } else {
            return name;
        }
    }
    
}
