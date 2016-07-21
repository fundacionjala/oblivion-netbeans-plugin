/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

/**
 * This class is for build the documentation for a method.
 *
 * @author sergio_daza
 */
public final class MethodDocumentionBuilder extends DocumentationBuilder {

    public MethodDocumentionBuilder(String api, String htmlCode, String className, String classUrl) {
        super(api, htmlCode, className, classUrl);
    }

    @Override
    protected void scannerSignature(int index) {
        String word = EMPTY_STRING;
        if (!signature.contains("(")) {
            this.errorsOnParameters = true;
            return;
        }
        for (int i = index; i < signature.length(); i++) {
            index++;
            char charAt = signature.charAt(i);
            if (charAt != BLANK_CHARACTER && charAt != '(') {
                word += charAt;
            } else if (charAt == '(') {
                this.parameters = signature.substring(index - 1, signature.length());
                break;
            } else {
                break;
            }
        }
        if (!word.isEmpty()) {
            if (MODIFIER_LIST.contains(word)) {
                this.modifiers.add(word);
            } else if (this.returnType.isEmpty()) {
                this.returnType = word.trim();
                this.isConstructor = this.returnType.equals(this.tittle);
                this.type = "method";
                if (this.isConstructor) {
                    this.type = "constructor";
                }
            }
        }
        if (index < signature.length()) {
            scannerSignature(index);
        }
    }

    @Override
    protected String getName(String text) {
        String result = EMPTY_STRING;
        for (int i = 0; i < text.length(); i++) {
            char charAt = text.charAt(i);
            if (charAt == '(') {
                break;
            }
            result += charAt;
        }
        return result;
    }

    @Override
    public boolean isValid() {
        return (!this.tittle.isEmpty()
                && !this.returnType.isEmpty()
                && !this.errorsOnParameters)
                || this.isConstructor;
    }

}
