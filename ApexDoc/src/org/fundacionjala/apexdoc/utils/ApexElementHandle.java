/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.apexdoc.utils;

import java.util.Set;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.filesystems.FileObject;

/**
 * class that implements the class ElementHandle 
 * 
 * @author sergio_daza
 */
public class ApexElementHandle implements ElementHandle{

    private static final String MIME_TYPE = "text/x-cls";
    private final String name;
    private final String description;
    private final ElementKind kind;
    
    public ApexElementHandle(String name, String description, ElementKind kind){
        this.name = name;
        this.description = description;
        this.kind = kind;
    }
    
    @Override
    public FileObject getFileObject() {
        return null;
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIn() {
        return description;
    }

    @Override
    public ElementKind getKind() {
        return kind;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return null;
    }

    @Override
    public boolean signatureEquals(ElementHandle eh) {
        return false;
    }

    @Override
    public OffsetRange getOffsetRange(ParserResult pr) {
        return OffsetRange.NONE;
    }
    
}
