/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class returns a list of tags HTML that satisfy some conditions.
 *
 * @author sergio_daza
 */
public class ListTagHTML {

    private String value = "";
    private final List<GenericPair<String, String>> listValue = new ArrayList<>();
    private String textToSearch = "";
    private String testToSearchForClosing = "";
    private int numberOfCoincidencesForClosing = -1;
    private int numberOfCoincidences = -1;
    private int coincidenceOfClosing = 0;
    private int currentCoincidence = 0;
    private boolean isTag = false;
    private static final String EMPTY_TEXT = "";
    private static final String BLANK_SPACE = " ";
    private static final char BLANK_CHARACTER = ' ';
    private static final String LESS_THAN = "<";
    private static final String CLOSE_TAG = "</";
    private static final char CHAR_GREATER_THAN = '>';
    private String innerTag = EMPTY_TEXT;
    private int innerTagNumber = 0;
    private int numberOfInnerCoincidenceForOpen = 0;
    private int numberOfInnerCoincidence = 0;
    private String tag = EMPTY_TEXT;
    private boolean tagClose = false;

    public ListTagHTML(String tagName, String className) {
        textToSearch = (LESS_THAN + tagName + className + EMPTY_TEXT).replace(BLANK_SPACE, EMPTY_TEXT);
        testToSearchForClosing = (CLOSE_TAG + tagName).replace(BLANK_SPACE, EMPTY_TEXT);
        numberOfCoincidences = textToSearch.length();
        numberOfCoincidencesForClosing = testToSearchForClosing.length();
        innerTag = (LESS_THAN + tagName).replace(BLANK_SPACE, EMPTY_TEXT);
        numberOfInnerCoincidenceForOpen = innerTag.length();
    }

    private void checkInnerTag(char character) {
        if (innerTag.charAt(numberOfInnerCoincidence) == character || character == BLANK_CHARACTER) {
            if (character != BLANK_CHARACTER) {
                numberOfInnerCoincidence++;
            }
        } else {
            numberOfInnerCoincidence = 0;
        }
        if (numberOfInnerCoincidence == numberOfInnerCoincidenceForOpen) {
            innerTagNumber++;
            numberOfInnerCoincidence = 0;
        }
    }

    /**
     * Verifies if the sequence of characters is the search tag.
     *
     * @param character
     */
    public void isTag(char character) {
        if (!isTag) {
            if (textToSearch.charAt(currentCoincidence) == character || character == BLANK_CHARACTER) {
                this.tag += character;
                if (character != BLANK_CHARACTER) {
                    currentCoincidence++;
                }
            } else {
                currentCoincidence = 0;
                this.tag = EMPTY_TEXT;
            }
            isTag = currentCoincidence == numberOfCoincidences;
        } else {
            if (!tagClose) {
                this.tag += character;
                if (character == CHAR_GREATER_THAN) {
                    tagClose = true;
                }
            } else {
                checkInnerTag(character);
                buildValue(character);
            }
        }
    }

    /**
     * It build the result value while the characters coincide.
     *
     * @param character
     */
    private void buildValue(char character) {
        value += EMPTY_TEXT + character;
        if (testToSearchForClosing.charAt(coincidenceOfClosing) == character || character == BLANK_CHARACTER) {
            if (character != BLANK_CHARACTER) {
                coincidenceOfClosing++;
            }
        } else {
            coincidenceOfClosing = 0;
        }
        if (coincidenceOfClosing == numberOfCoincidencesForClosing) {
            if (innerTagNumber == 0) {

                listValue.add(new GenericPair(tag, value.substring(0, value.length() - numberOfCoincidencesForClosing)));
                coincidenceOfClosing = 0;
                innerTagNumber = 0;
                numberOfInnerCoincidence = 0;
                currentCoincidence = 0;
                isTag = false;
                value = EMPTY_TEXT;
                tag = EMPTY_TEXT;
                tagClose = false;
            } else {
                innerTagNumber--;
                coincidenceOfClosing = 0;
            }
        }
    }

    /**
     * This method returns a String with the found value.
     *
     * @return
     */
    public List<GenericPair<String, String>> getValue() {
        return listValue;
    }

    /**
     * This method returns true if finished the search with success.
     *
     * @return
     */
    public boolean located() {
        return isTag;
    }

}
