/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.spi.DefaultCompletionProposal;
import org.openide.util.ImageUtilities;

/**
 * Class that contain information to show
 *
 * @author sergio_daza
 */
public class ProposalItem extends DefaultCompletionProposal {

    public final String name;
    public final String type;
    public final String icon;
    public Set<Modifier> modifiers;
    public boolean withReference = true;

    public ProposalItem(String name, String type, String icon, int anchorOffset) {
        this.anchorOffset = anchorOffset;
        this.name = name;
        this.type = type;
        this.icon = icon;
        modifiers = new HashSet<Modifier>();
    }

    @Override
    public void setAnchorOffset(int offset) {
        this.anchorOffset = offset;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ElementKind getKind() {
        return super.getKind();
    }

    @Override
    public String getRhsHtml(HtmlFormatter formatter) {
        if (type != null) {
            formatter.appendHtml(type.replaceAll("<", "&lt;"));
            return formatter.getText();
        } else {
            return null;
        }
    }

    @Override
    public ImageIcon getIcon() {
        ImageIcon imageIcon = null;
        if (!"".equals(icon.trim())) {
            imageIcon = new ImageIcon(ImageUtilities.loadImage(icon));
        }
        return imageIcon;
    }

    @Override
    public ElementHandle getElement() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 47 * hash + (this.getKind() != null ? this.getKind().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String cls = getClass().getName();
        cls = cls.substring(cls.lastIndexOf('.') + 1);
        return cls + "(" + getKind() + "): " + getName();
    }
    
    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }
   
    public boolean isModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public boolean isConstructor() {
        return elementKind.equals(ElementKind.CONSTRUCTOR);
    }

    public String getType() {
        return type;
    }
    
}
