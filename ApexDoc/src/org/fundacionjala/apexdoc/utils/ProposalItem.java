/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.apexdoc.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import org.json.simple.JSONArray;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.spi.DefaultCompletionProposal;

/**
 * class that contain information of proposal
 *
 * @author sergio_daza
 */
public class ProposalItem extends DefaultCompletionProposal {

    public final String name;
    public final String type;
    public final String description;
    public String parameters;
    public Set<Modifier> modifiers;
    private ElementKind elementeKind;
    private ProposalType proposalType;
    private static final Map<String,Modifier> modifiersMap = new HashMap<String,Modifier>();
    private static final Map<String,ElementKind> elementeKindMap = new HashMap<String,ElementKind>();
    private static final Map<String,ProposalType> proposalTypeMap = new HashMap<String,ProposalType>();
    
    static {
        modifiersMap.put("public", Modifier.PUBLIC);
        modifiersMap.put("protected", Modifier.PROTECTED);
        modifiersMap.put("private", Modifier.PRIVATE);
        modifiersMap.put("static", Modifier.STATIC);
        elementeKindMap.put("class", ElementKind.CLASS);
        elementeKindMap.put("constructor", ElementKind.CONSTRUCTOR);
        elementeKindMap.put("method", ElementKind.METHOD);
        elementeKindMap.put("property", ElementKind.PROPERTY);
        elementeKindMap.put("enum", ElementKind.CLASS);
        elementeKindMap.put("enumItem", ElementKind.VARIABLE);
        elementeKindMap.put("interface", ElementKind.INTERFACE);
        proposalTypeMap.put("interface", ProposalType.INTERFACE);
        proposalTypeMap.put("class", ProposalType.CLASS);
        proposalTypeMap.put("method", ProposalType.METHOD_PUBLIC);
        proposalTypeMap.put("constructor", ProposalType.CONSTRUCTOR);
        proposalTypeMap.put("property", ProposalType.PROPERTY);
        proposalTypeMap.put("enumItem", ProposalType.ENUM);
        proposalTypeMap.put("enumItem", ProposalType.ENUM_ITEM);
    }

    public ProposalItem(String typeProposal, String name, String type, String description, int anchorOffset, String parameters, JSONArray modifiers) {
        this(typeProposal, name, type, description, anchorOffset);
        for (Object modifier : modifiers) {
           String nameModifier = (String)modifier;
           if(nameModifier.equals("static")) {
               proposalType = ProposalType.METHOD_STATIC;
           }
           this.modifiers.add(this.modifiersMap.get(nameModifier));
        }
        this.parameters = parameters;
    }
    
    public ProposalItem(String typeProposal, String name, String type, String description, int anchorOffset) {
        this.anchorOffset = anchorOffset;
        this.elementeKind = elementeKindMap.get(typeProposal);
        this.name = name.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        this.proposalType = proposalTypeMap.get(typeProposal);
        this.type = type;
        
//        if(typeProposal.equals("constructor")) {
//            this.type = "";
//        } else {
//            this.type = type;
//            this.proposalType = ProposalType.METHOD_PUBLIC;
//        }
        this.description = description;
        modifiers = new HashSet<Modifier>();
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        if (this.elementeKind.equals(ElementKind.METHOD) || this.elementeKind.equals(ElementKind.CONSTRUCTOR)) {
            formatter.appendText(name + "(" + getParameters() + ")");
            return formatter.getText();
        } else {
            formatter.appendText(name);
            return formatter.getText();
        }
    }

    @Override
    public ElementKind getKind() {
        return elementeKind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProposalItem other = (ProposalItem) obj;
        return name.equals(other.getName())
                && type.equals(other.type)
                && description.equals(other.description);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRhsHtml(HtmlFormatter formatter) {
        if (type != null) {
            formatter.appendHtml(type);
            return formatter.getText();
        } else {
            return null;
        }
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }

    @Override
    public ElementHandle getElement() {
        if (!this.description.isEmpty()) {
            return new ApexElementHandle(this.name, this.description, this.elementKind);
        }
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

    @Override
    public String getCustomInsertTemplate() {
        if (this.elementeKind.equals(ElementKind.METHOD) || this.elementeKind.equals(ElementKind.CONSTRUCTOR)) {
            StringBuilder sb = new StringBuilder();
            sb.append(getInsertPrefix());
            String[] delimiters = getParamListDelimiters();
            assert delimiters.length == 2;
            if (this.parameters.isEmpty()) {
                sb.append(delimiters[0]);
                sb.append(delimiters[1]);
                sb.append("${cursor}");
            } else {
                sb.append(delimiters[0]);
                sb.append("${cursor}");
                sb.append(delimiters[1]);
            }
            return sb.toString();
        } else {
            return super.getCustomInsertTemplate();
        }

    }

    @Override
    public String[] getParamListDelimiters() {
        return new String[]{"(", ")"};
    }

    private String getParameters() {
        return this.parameters;
    }
    
    public ProposalType getProposalType() {
        return proposalType;
    }

}
