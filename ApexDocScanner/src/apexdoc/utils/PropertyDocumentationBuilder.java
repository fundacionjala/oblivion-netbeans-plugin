/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

/**
 *
 * @author sergio_daza
 */
public class PropertyDocumentationBuilder extends DocumentationBuilder {

    public PropertyDocumentationBuilder(String api, String htmlCode, String className, String classUrl) {
        super(api, htmlCode, className, classUrl);
        this.type = "property";
    }

    @Override
    protected void scannerSignature(int index) {
        this.signature = this.signature.replaceAll(this.tittle, EMPTY_STRING);
        scannerNewSignature(index);
    }
    
    private void scannerNewSignature(int index) {
        
        String word = EMPTY_STRING;
        for (int i = index; i < signature.length(); i++) {
            index++;
            char charAt = signature.charAt(i);
            if (charAt != BLANK_CHARACTER && charAt != '{') {
                word += charAt;
            } else {
                break;
            }
        }
        if (!word.isEmpty()) {
            if (MODIFIER_LIST.contains(word)) {
                this.modifiers.add(word);
            } else if (this.returnType.isEmpty()) {
                this.returnType = this.returnType + word.trim();
                
            }
        }
        if (index < signature.length()) {
            scannerSignature(index);
        }
    }

    @Override
    protected String getName(String text) {
        return text.trim();
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
