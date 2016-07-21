/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.util.Stack;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexLexerConstants;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.editor.indent.spi.Context;

/**
 * Utility class for Indentation and Formatting.
 *
 * @author Amir Aranibar
 */
public class ContextIndent {

    private final Context context;
    private final Document document;
    private final TokenHierarchy<Document> tokenHierarchy;
    private final int indentLevelSize;

    private TokenSequence<?> tokenSequence;

    /**
     * Constructor
     *
     * @param context the current Apex file.
     */
    public ContextIndent(Context context) {
        this.context = context;
        document = context.document();
        tokenHierarchy = TokenHierarchy.get(document);
        indentLevelSize = org.netbeans.modules.editor.indent.api.IndentUtils.indentLevelSize(document);
    }

    /**
     * Finds the current indentation level for a new line.
     *
     * @param offset the starting position of the first token.
     * @return the number of whites spaces to add in the new line.
     * @throws BadLocationException
     */
    public int findCurrentDepthToIndent(int offset) throws BadLocationException {
        return findCurrentDepth(offset, false);
    }

    /**
     * Finds the current indentation level for a specific line.
     *
     * @param offset the line number where starts a token in the sequence.
     * @return the number of whites spaces to add in the specific line.
     * @throws BadLocationException
     */
    public int findCurrentDepthToReformat(int offset) throws BadLocationException {
        return findCurrentDepth(offset, true);
    }

    /**
     * Finds the current indentation level.
     *
     * @param offset the starting position of the token sequence.
     * @param reformat defines if it will remove a level in the indentation when
     * the cursor is not in the document.
     * @return the number of whites spaces to add in a line.
     * @throws BadLocationException
     */
    private int findCurrentDepth(int offset, boolean reformat) throws BadLocationException {
        tokenSequence = tokenHierarchy.tokenSequence();
        tokenSequence.moveStart();
        Stack<Token<?>> indentLevel = new Stack<>();
        while (tokenSequence.moveNext() && tokenSequence.offset() <= offset) {
            Token<?> token = tokenSequence.offsetToken();
            updateIndentLevelByToken(indentLevel, token, offset, reformat);
        }
        return indentLevelSize * indentLevel.size();
    }

    /**
     * Adds or removes a level to the current indentation.
     *
     * @param indentLevel store the tokens that adds a indentation level
     * @param token the token that define if add or remove the indentation level
     * @param offset the starting position of the token sequence.
     * @param reformat defines if it will remove a level in the indentation when
     * the cursor is not in the document.
     * @throws BadLocationException
     */
    private void updateIndentLevelByToken(Stack<Token<?>> indentLevel, Token<?> token, int offset, boolean reformat) throws BadLocationException {
        int tokenId = token.id().ordinal();
        switch (tokenId) {
            case ApexLexerConstants.LBRACE_SEPARATOR:
                if (getNextTokenLineStartOffset() < offset) {
                    indentLevel.add(token);
                }
                break;
            case ApexLexerConstants.RBRACE_SEPARATOR:
                Token<?> previousToken = indentLevel.isEmpty() ? null : indentLevel.lastElement();
                if (previousToken != null && previousToken.id().ordinal() == ApexLexerConstants.LBRACE_SEPARATOR) {
                    int nextTokenLineStartOffset = getNextTokenLineStartOffset();
                    int cursorStartOffset = context.caretOffset();
                    int cursorLineStartOffset = context.lineStartOffset(cursorStartOffset);
                    if (reformat || nextTokenLineStartOffset <= cursorLineStartOffset) {
                        indentLevel.pop();
                    }
                }
                break;
        }
    }

    /**
     * Gets the starting position of the next token in the token sequence.
     *
     * @return the starting position of the next token.
     * @throws BadLocationException
     */
    private int getNextTokenLineStartOffset() throws BadLocationException {
        tokenSequence.moveNext();
        Token<?> nextToken = tokenSequence.offsetToken();
        int nextTokenOffset = nextToken.offset(tokenHierarchy);
        tokenSequence.movePrevious();
        return context.lineStartOffset(nextTokenOffset);
    }
}
