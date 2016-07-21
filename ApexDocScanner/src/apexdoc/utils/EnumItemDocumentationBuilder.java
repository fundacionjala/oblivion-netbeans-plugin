/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

import java.util.List;

/**
 *
 * @author sergio_daza
 */
public class EnumItemDocumentationBuilder extends DocumentationBuilder {

    public EnumItemDocumentationBuilder(String api, String htmlCode, String className, String classUrl) {
        super(api, htmlCode, className, classUrl);
        this.type = "enumItem";
        this.returnType = className.replaceAll("Enum", EMPTY_STRING).trim();
    }

    @Override
    protected void recoverInformation() {
        List<GenericPair<String, String>> getSamp = getElements(htmlCode, "samp", "class=\"codeph apex_code\"");
        if(!getSamp.isEmpty()) {
            this.tittle = ScannerUtils.removeTags(getSamp.get(0).second);
            List<GenericPair<String, String>> getSpan = getElements(htmlCode, "td", "class=\"entry\" ");
            if(!getSpan.isEmpty()) {
                this.description = ScannerUtils.removeTags(getSpan.get(1).second);
            }
        }
    }

    
    @Override
    protected void scannerSignature(int index) {
        
    }

    @Override
    protected String getName(String text) {
        return text;
    }

    @Override
    public boolean isValid() {
        return !this.tittle.isEmpty();
    }

    private List<GenericPair<String, String>> getElements(String htmlCode, String tagName, String conditionText) {
        ListTagHTML listNamespaces = new ListTagHTML(tagName, conditionText);
        for (int i = 0; i < htmlCode.length(); i++) {
            char charAt = htmlCode.charAt(i);
            listNamespaces.isTag(charAt);
        }
        return listNamespaces.getValue();
    }
}
