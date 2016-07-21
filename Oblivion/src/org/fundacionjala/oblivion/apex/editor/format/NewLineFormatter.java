/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * reformat the right brace adding newlines in a given document.
 *
 * @author Amir Aranibar
 */
class NewLineFormatter {

    private static final Logger LOG = Logger.getLogger(NewLineFormatter.class.getName());
    private static final char NEWLINE_CHARACTER = '\n';
    private static final int TEXT_LENGTH = 1;
    private static final char WHITESPACE_CHARACTER = ' ';

    private final Document document;

    NewLineFormatter(Document document) {
        this.document = document;
    }
    
    /**
     * Reformat the code after the right brace position.
     * 
     * @param position right brace position
     */
    void reformatAfterRightBrace(int position) {
        try {
            if (!hasNewLine(position)) {
                document.insertString(position + 1, String.valueOf(NEWLINE_CHARACTER), null);
            }
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }
    }
    
    /**
     * Checks if there is a new line after a specific position
     * 
     * @param offset the position to start to check.
     * @return true if the line is empty otherwise false.
     * @throws BadLocationException 
     */
    private boolean hasNewLine(int offset) throws BadLocationException {
        int position = offset + 1;
        boolean hasNewline = false;
        char character = getCharacter(position);
        while (position <= document.getLength()) {
            if (character != NEWLINE_CHARACTER && character != WHITESPACE_CHARACTER) {
                break;
            } else if (character == NEWLINE_CHARACTER) {
                hasNewline = true;
                break;
            }

            position++;
            character = getCharacter(position);
        }

        return hasNewline;
    }

    /**
     * Gets a character from a specific position.
     *
     * @param position the position of the character to be returned
     * @return the found character
     * @throws BadLocationException
     */
    private char getCharacter(int position) throws BadLocationException {
        return document.getText(position, TEXT_LENGTH).charAt(0);
    }

}
