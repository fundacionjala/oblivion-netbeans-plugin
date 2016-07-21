/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.editor.preferences.FormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.ReformatOption;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.editor.indent.spi.CodeStylePreferences;
import org.netbeans.modules.editor.indent.spi.Context;

/**
 * Reformats and indents a specific Apex document
 *
 * @author Amir Aranibar
 */
public class DocumentFormatter {

    private final Context context;
    private final ApexParserResult parserResult;
    private final ContextIndent contextIndent;
    private final BracesFormatter bracesFormatter;
    private final NewLineFormatter newLineFormatter;
    private final ReformatTreeVisitor reformatTreeVisitor;
    private final List<ReformatOption> optionsToReformat;

    /**
     * Constructor
     *
     * @param context the current Apex file.
     * @param parserResult contains the tokens sequence of the Apex file
     */
    public DocumentFormatter(Context context, ParserResult parserResult) {
        this.context = context;
        Document document = context.document();
        this.parserResult = (ApexParserResult) parserResult;
        contextIndent = new ContextIndent(context);
        Preferences preferences = CodeStylePreferences.get(document).getPreferences();
        bracesFormatter = new BracesFormatter(document, preferences, FormatOptions.getInstance());
        newLineFormatter = new NewLineFormatter(document);
        reformatTreeVisitor = new ReformatTreeVisitor(document,context.startOffset(), context.endOffset());
        optionsToReformat = new ArrayList<>();
        visitParserResult();
    }

    /**
     * Reformats the Apex file using the defined format options.
     *
     */
    public void reformat() {
        for (ReformatOption option : optionsToReformat) {
            switch (option.getOption()) {
                case BRACES_IN_CLASS_DECLARATION:
                case BRACES_IN_METHOD_DECLARATION:
                case BRACES_IN_OTHER_DECLARATION:
                    bracesFormatter.reformatLeftBraces(option.getOption(), option.getPosition());
                    break;
                case AFTER_CLASS_DECLARATION:
                case AFTER_METHOD_DECLARATION:
                case AFTER_OTHER_DECLARATION:
                    newLineFormatter.reformatAfterRightBrace(option.getPosition());
                    break;
            }
        }
    }

    /**
     * Reformats a specific line.
     *
     * @param lineOffset the line number where starts a token in the sequence.
     * @throws BadLocationException
     */
    public void reformatLine(int lineOffset) throws BadLocationException {
        indentLine(lineOffset);
    }

    /**
     * Visits the parser result to define the options and characters to
     * reformat.
     *
     */
    private void visitParserResult() {
        parserResult.getCompilationUnit().accept(reformatTreeVisitor, optionsToReformat);
        Collections.sort(optionsToReformat, Collections.reverseOrder());
    }

    /**
     * Indents a specific line.
     *
     * @param lineOffset the line number where starts a token in the sequence.
     * @throws BadLocationException
     */
    private void indentLine(int lineOffset) throws BadLocationException {
        int indentLevel = contextIndent.findCurrentDepthToReformat(lineOffset);
        context.modifyIndent(lineOffset, indentLevel);
    }
}
