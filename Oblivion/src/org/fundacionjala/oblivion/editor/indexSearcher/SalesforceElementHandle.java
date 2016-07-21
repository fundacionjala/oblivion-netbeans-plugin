/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor.indexSearcher;

import java.util.Collections;
import java.util.Set;
import org.fundacionjala.oblivion.apex.ApexLanguage;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.filesystems.FileObject;

/**
 * Salesforce element handle used by Netbeans to display elements.
 * @author Marcelo Garnica
 */
class SalesforceElementHandle implements ElementHandle {
    private final FileObject elementFileObject;

    SalesforceElementHandle(FileObject elementFileObject) {
        this.elementFileObject = elementFileObject;
    }

    @Override
    public FileObject getFileObject() {
        return elementFileObject;
    }

    @Override
    public String getMimeType() {
        return elementFileObject.getMIMEType();
    }

    @Override
    public String getName() {
        return elementFileObject.getName();
    }

    @Override
    public String getIn() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return elementFileObject.getExt().equalsIgnoreCase(ApexLanguage.APEX_FILE_EXTENSION) ? ElementKind.CLASS : ElementKind.OTHER;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.singleton(Modifier.PUBLIC);
    }

    @Override
    public boolean signatureEquals(ElementHandle eh) {
        return eh != null ? elementFileObject.equals(eh.getFileObject()) : false;
    }

    @Override
    public OffsetRange getOffsetRange(ParserResult pr) {
        return new OffsetRange(0, 0);
    }
}
