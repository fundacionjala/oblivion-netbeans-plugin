/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;

/**
 * Defines the methods to get the objects to be used by the order process.
 *
 * @author Amir Aranibar
 */
public interface IOrderDocumentProcessor {

    /**
     * Process the document to get the required objects.
     *
     * @param document the document to be processed.
     */
    void processDocument(Document document);

    /**
     * Gets the parser result.
     *
     * @return the parser result.
     */
    ApexParserResult getParserResult();
}
