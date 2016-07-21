/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;

/**
 * class that to build proposal item for JavaDoc
 * 
 * @author sergio_daza
 */
class JavaDocItem extends ProposalItem {

    public JavaDocItem(String name) {
        super(name, "", "", 1);
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
        return ElementKind.OTHER;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JavaDocItem other = (JavaDocItem) obj;
        return super.name.equals(other.getName());
    }

    @Override
    public String[] getParamListDelimiters() {
        return super.getParamListDelimiters();
    }
    
}
