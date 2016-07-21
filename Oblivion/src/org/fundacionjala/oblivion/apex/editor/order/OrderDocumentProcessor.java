/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.netbeans.modules.csl.spi.GsfUtilities;
import org.netbeans.modules.parsing.api.Source;
import org.openide.filesystems.FileObject;

/**
 * Process a document to provide the objects required by the order process.
 *
 * @author Amir Aranibar
 */
public class OrderDocumentProcessor implements IOrderDocumentProcessor {

    private ApexParserResult parserResult;

    @Override
    public ApexParserResult getParserResult() {
        return parserResult;
    }

    @Override
    public void processDocument(Document document) {
        FileObject fileObject = GsfUtilities.findFileObject(document);
        setParserResult(fileObject);
    }

    /**
     * Parses the FileObject and sets the result in the parserResult field.
     *
     * @param fileObject the file object to be parsed.
     */
    private void setParserResult(FileObject fileObject) {
        if (fileObject != null) {
            Source model = Source.create(fileObject);

            if (model != null) {
                ApexLanguageParser apexLanguageParser = new ApexLanguageParser();
                apexLanguageParser.parse(model.createSnapshot(), null, null);
                parserResult = (ApexParserResult) apexLanguageParser.getResult(null);
            }
        }
    }
}
