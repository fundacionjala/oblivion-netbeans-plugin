/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor;

import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import org.apache.commons.io.IOUtils;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;

/**
 *
 * @author Amir Aranibar
 */
public abstract class AbstractTestFormat extends AbstractTestGrammar {

    protected StyledDocument buildDocument(String text) throws BadLocationException {
        StyledDocument document = new DefaultStyledDocument();
        if (document.getLength() > 0) {
            document.remove(0, document.getLength());
        }

        document.insertString(0, text, null);
        return document;
    }

    protected String getTextFromFile(String fileName) throws IOException {
        String filePath = String.format(getResourceFolder(), fileName);
        return IOUtils.toString(getClass().getResourceAsStream(filePath), "UTF-8");
    }
}
