/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.fundacionjala.oblivion.apex.editor.format.ContextIndent;
import org.fundacionjala.oblivion.apex.editor.format.DocumentFormatter;
import org.fundacionjala.oblivion.apex.editor.format.WhiteSpacesRemover;
import org.netbeans.modules.csl.api.Formatter;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.editor.indent.spi.Context;

/**
 * This class is used by Netbeans to start the reformat and indent process.
 *
 * @author Amir Aranibar
 */
public class ApexFormatter implements Formatter {

    private static final Logger LOG = Logger.getLogger(ApexFormatter.class.getName());
    private static final int MINIMUM_VALUE = 0;

    @Override
    public void reformat(Context context, ParserResult parserResult) {
        if (parserResult == null) {
            return;
        }

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(context.document(), context.startOffset());
        DocumentFormatter documentFormatter = new DocumentFormatter(context, parserResult);
        documentFormatter.reformat();

        try {
            int startOffset = context.startOffset();
            int end = context.endOffset();
            int lineOffset = context.lineStartOffset(end);

            while (lineOffset >= startOffset) {
                whiteSpacesRemover.removeWhiteSpaces(lineOffset);
                if (whiteSpacesRemover.isBlankLine(lineOffset)) {
                    whiteSpacesRemover.removeBlankLines(lineOffset);
                } else {
                    documentFormatter.reformatLine(lineOffset);
                }

                if (lineOffset <= MINIMUM_VALUE) {
                    break;
                }

                lineOffset = context.lineStartOffset(lineOffset - 1);
            }
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    @Override
    public void reindent(Context context) {
        ContextIndent contextIndent = new ContextIndent(context);

        try {
            int indentLevel = contextIndent.findCurrentDepthToIndent(context.startOffset());
            context.modifyIndent(context.startOffset(), indentLevel);
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    @Override
    public boolean needsParserResult() {
        return true;
    }

    @Override
    public int indentSize() {
        return 0;
    }

    @Override
    public int hangingIndentSize() {
        return 0;
    }
}
