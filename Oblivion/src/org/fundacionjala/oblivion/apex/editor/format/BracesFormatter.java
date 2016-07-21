/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.editor.preferences.PreferencesFormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.IFormatOptions;

import static org.fundacionjala.oblivion.apex.editor.preferences.BracesValueOptions.*;

/**
 * Based in the format options, reformat all left brace characters of a given
 * document.
 *
 * @author Amir Aranibar
 */
class BracesFormatter {

    private static final Logger LOG = Logger.getLogger(BracesFormatter.class.getName());
    private static final int TEXT_LENGTH = 1;
    private static final char NEWLINE_CHARACTER = '\n';
    private static final char WHITESPACE_CHARACTER = ' ';

    private final StyledDocument document;
    private final IFormatOptions formatOptions;
    private final Preferences preferences;

    BracesFormatter(Document document, Preferences preferences, IFormatOptions formatOptions) {
        this.document = (StyledDocument) document;
        this.preferences = preferences;
        this.formatOptions = formatOptions;
    }

    /**
     * Reformats a left brace character from a specific position in the document.
     *
     * @param option the options to reformat
     * @param offset the position of the left brace
     */
    public void reformatLeftBraces(PreferencesFormatOptions option, int offset) {
        reformatLeftBraceByOptionValue(formatOptions.getOptionValue(preferences, option.name()), offset);
    }

    /**
     * Defines which character will be added before the left brace according to the option.
     *
     * @param option the options to reformat
     * @param offset the position of the left brace
     */
    private void reformatLeftBraceByOptionValue(String option, int offset) {
        switch (option) {
            case SAME_LINE:
                reformatLeftBrace(offset, String.valueOf(WHITESPACE_CHARACTER));
                break;
            case NEW_LINE:
                reformatLeftBrace(offset, String.valueOf(NEWLINE_CHARACTER));
                break;
        }
    }

    /**
     * Reformats a left brace character removing unnecessary characters around it.
     * 
     * @param offset the position of the left brace
     * @param stringToAdd the string to be added before the left brace
     */
    private void reformatLeftBrace(int offset, String stringToAdd) {
        try {
            int nextOffset = getNextNonBlankCharacterPosition(offset);
            removeCharactersExceptFirstAndLast(offset, nextOffset);
            document.insertString(offset + 1, String.valueOf(NEWLINE_CHARACTER), null);

            int previousOffset = getPreviousNonBlankCharacterPosition(offset);
            removeCharactersExceptFirstAndLast(previousOffset, offset);
            document.insertString(previousOffset + 1, stringToAdd, null);
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    /**
     * Gets the position of the non blank character previous to the left brace.
     * 
     * @param offset the position of the left brace
     * @return the position of the non blank character previous to the left brace
     * @throws BadLocationException 
     */
    private int getPreviousNonBlankCharacterPosition(int offset) throws BadLocationException {
        int position = offset - 1;
        char character = getCharacter(position);
        while (position > 0) {
            if (character != NEWLINE_CHARACTER && character != WHITESPACE_CHARACTER) {
                break;
            }
            position--;
            character = getCharacter(position);
        }

        return position;
    }

    /**
     * Gets the position of the non blank character next to the left brace.
     * 
     * @param offset the position of the left brace
     * @return the position of the non blank character next to the left brace
     * @throws BadLocationException 
     */
    private int getNextNonBlankCharacterPosition(int offset) throws BadLocationException {
        int position = offset + 1;
        char character = getCharacter(position);
        while (position < document.getLength()) {
            if (character != NEWLINE_CHARACTER && character != WHITESPACE_CHARACTER) {
                break;
            }
            position++;
            character = getCharacter(position);
        }
        return position;
    }

    /**
     * Removes all characters in a determined range excluding the first and last character.
     * 
     * @param startOffset the starting position of the range to be removed
     * @param endOffset the ending position of the range to be removed
     * @throws BadLocationException 
     */
    private void removeCharactersExceptFirstAndLast(int startOffset, int endOffset) throws BadLocationException {
        int length = endOffset - startOffset;

        if ((length) > 1) {
            document.remove(startOffset + 1, length - 1);
        }
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
