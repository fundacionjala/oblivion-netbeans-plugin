/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Removes unnecessary white spaces from a given document.
 * 
 * @author Amir Aranibar
 */
public class WhiteSpacesRemover {

    private static final int TEXT_LENGTH = 1;
    private static final int MINIMUM_VALUE = 0;
    private static final int INITIAL_VALUE = -1;
    private static final char NEWLINE_CHARACTER = '\n';
    private static final char WHITESPACE_CHARACTER = ' ';

    private final Document document;
    private final int startOffset;

    public WhiteSpacesRemover(Document document, int startOffset) {
        this.document = document;
        this.startOffset = startOffset;
    }

    /**
     * Removes white spaces from a line with characters.
     *
     * @param position the current character position.
     * @return true if the line is empty otherwise false.
     * @throws BadLocationException
     */
    public void removeWhiteSpaces(int position) throws BadLocationException {
        char character = getCharacter(position);
        if (character == NEWLINE_CHARACTER) {
            return;
        }
        if (!Character.isWhitespace(character)) {
            removeWhiteSpacesBetweenCharacters(position);
            return;
        }
        int nextPosition = position + 1;
        if (character == WHITESPACE_CHARACTER && nextPosition <= document.getLength()) {
            removeWhiteSpaces(nextPosition, position, 1);
        }
    }

    /**
     * Removes more than two successive blank lines.
     *
     * @param position the starting position to validate the characters.
     * @throws BadLocationException
     */
    public void removeBlankLines(int position) throws BadLocationException {
        int nextPosition = position + 1;
        int previousPosition = position - 1;
        if (nextPosition <= document.getLength() && previousPosition > startOffset) {
            char nextCharacter = getCharacter(nextPosition);
            char previousCharacter = getCharacter(previousPosition);
            if (NEWLINE_CHARACTER == nextCharacter && NEWLINE_CHARACTER == previousCharacter) {
                document.remove(position, TEXT_LENGTH);
            }
        }
    }

    /**
     * Checks if a specific line is empty.
     *
     * @param lineOffset the current line be checked.
     * @return true if the line is empty otherwise false.
     */
    public boolean isBlankLine(int lineOffset) throws BadLocationException {
        char character = getCharacter(lineOffset);
        return character == NEWLINE_CHARACTER;
    }

    /**
     * Removes white spaces from a line with characters.
     *
     * @param position the current character position.
     * @param startPosition the starting position to remove white spaces.
     * @param lengthToRemove the number of characters to remove.
     * @return true if the line is empty otherwise false.
     * @throws BadLocationException
     */
    private void removeWhiteSpaces(int position, int startPosition, int lengthToRemove) throws BadLocationException {
        char character = getCharacter(position);
        if (!Character.isWhitespace(character)) {
            removeWhiteSpacesBetweenCharacters(position);
            return;
        }
        int nextPosition = position + 1;
        if (character == WHITESPACE_CHARACTER && nextPosition <= document.getLength()) {
            removeWhiteSpaces(nextPosition, startPosition, lengthToRemove + 1);
        } else {
            document.remove(startPosition, lengthToRemove);
        }
    }

    /**
     * Removes more than one white space between characters in a line.
     *
     * @param position the starting position to validate the characters.
     * @throws BadLocationException
     */
    private void removeWhiteSpacesBetweenCharacters(int position) throws BadLocationException {
        position++;
        if (position <= document.getLength()) {
            int startPosition = INITIAL_VALUE;
            int lengthToRemove = INITIAL_VALUE;
            char character = getCharacter(position);
            while (position <= document.getLength()) {
                if (character == WHITESPACE_CHARACTER) {
                    lengthToRemove++;
                    if (lengthToRemove == MINIMUM_VALUE) {
                        startPosition = position;
                    }
                } else if (startPosition >= MINIMUM_VALUE && lengthToRemove > MINIMUM_VALUE) {
                    if (character == NEWLINE_CHARACTER) {
                        lengthToRemove++;
                    }
                    document.remove(startPosition, lengthToRemove);
                    position = startPosition - 1;
                    lengthToRemove = INITIAL_VALUE;
                } else if (lengthToRemove >= MINIMUM_VALUE) {
                    lengthToRemove = INITIAL_VALUE;
                }
                if (character == NEWLINE_CHARACTER) {
                    break;
                }
                position++;
                character = getCharacter(position);
            }
        }
    }
    
    /**
     * Obtains the character of a specific position in the document.
     *
     * @param position the position of a character in the document.
     * @return the character of a specific position.
     * @throws BadLocationException
     */
    private char getCharacter(int position) throws BadLocationException {
        return document.getText(position, TEXT_LENGTH).charAt(0);
    }
}
