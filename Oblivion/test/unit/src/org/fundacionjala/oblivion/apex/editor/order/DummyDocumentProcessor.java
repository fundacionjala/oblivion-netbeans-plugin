/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;

/**
 *
 * @author Amir Aranibar
 */
public class DummyDocumentProcessor implements IOrderDocumentProcessor {

    private final ApexParserResult parserResult;

    public DummyDocumentProcessor(ApexParserResult parserResult) {
        this.parserResult = parserResult;
    }

    @Override
    public ApexParserResult getParserResult() {
        return parserResult;
    }

    @Override
    public void processDocument(Document document) {
    }
}
