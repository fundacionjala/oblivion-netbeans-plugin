/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

/**
 * This class returns a tag HTML that satisfy some conditions.
 * 
 * @author sergio_daza
 */
public class TagHTML {
    
    private static final String EMPTY_STRING = "";
    private static final String BLANK_SPACE = " ";
    private static final char BLANK_CHARACTER = ' ';
    private static final String LESS_THAN = "<";
    private static final String GREATER_THAN = ">";
    private static final String CLOSE_TAG = "</";
    private String value = EMPTY_STRING;
    private String textToSearch = EMPTY_STRING;
    private String testToSearchForClosing = EMPTY_STRING;
    private int numberOfCoincidencesForClosing = -1;
    private int numberOfCoincidences = -1;
    private int coincidenceOfClosing = 0;
    private int currentCoincidence = 0;
    private boolean finalized = false;
    private boolean isTag = false;
    private String innerTag = EMPTY_STRING;
    private int innerTagNumber = 0;
    private int numberOfInnerCoincidenceForOpen = 0;
    private int numberOfInnerCoincidence = 0;

    public TagHTML(String tagName, String className) {
        textToSearch = (LESS_THAN + tagName + className + GREATER_THAN).replace(BLANK_SPACE, EMPTY_STRING);
        testToSearchForClosing = (CLOSE_TAG + tagName).replace(BLANK_SPACE, EMPTY_STRING);
        numberOfCoincidences = textToSearch.length();
        numberOfCoincidencesForClosing = testToSearchForClosing.length();
        innerTag = (LESS_THAN + tagName).replace(BLANK_SPACE, EMPTY_STRING);
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
        if (!located()) {
            if (!isTag) {
                if (textToSearch.charAt(currentCoincidence) == character || character == BLANK_CHARACTER) {
                    if (character != BLANK_CHARACTER) {
                        currentCoincidence++;
                    }
                } else {
                    currentCoincidence = 0;
                }
                isTag = currentCoincidence == numberOfCoincidences;
            } else if (!finalized) {
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
        value += EMPTY_STRING + character;
        if (testToSearchForClosing.charAt(coincidenceOfClosing) == character || character == BLANK_CHARACTER) {
            if (character != BLANK_CHARACTER) {
                coincidenceOfClosing++;
            }
        } else {
            coincidenceOfClosing = 0;
        }
        if (coincidenceOfClosing == numberOfCoincidencesForClosing) {
            if (innerTagNumber == 0) {
                finalized = true;
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
    public String getValue() {
        return located() ? value.substring(0, value.length() - coincidenceOfClosing) : EMPTY_STRING;
    }

    /**
     * This method returns a Int with the found value.
     *
     * @return
     */
    public int getContainToInt() {
        return located() ? Integer.parseInt(value.substring(0, value.length() - coincidenceOfClosing)) : 0;
    }

    /**
     * This method returns true if finished the search with success.
     *
     * @return
     */
    public boolean located() {
        return isTag && finalized;
    }

}
