/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import org.openide.util.NbBundle;

@NbBundle.Messages({
    "TestReport_notFound=notFound"
})

/**
 * This class verifies if a sequence of characters is the substring that correspond with the tag.
 * 
 * @author sergio_daza
 */
public class TagHTML {

    private String value = "";
    private String textToSearch = "";
    private String testToSearchForClosing = "";
    private int numberOfCoincidencesForClosing = -1;
    private int numberOfCoincidences = -1;
    private int coincidenceOfClosing = 0;
    private int currentCoincidence = 0;
    private boolean finalized = false;
    private boolean isTag = false;
    private static final String EMPTY_TEXT = "";
    private static final String BLANK_SPACE = " ";
    private static final char BLANK_CHARACTER = ' ';

    TagHTML(String tagName, String className) {
        textToSearch = ("<" + tagName + "class=\"" + className + "\">").replace(BLANK_SPACE, EMPTY_TEXT);
        testToSearchForClosing = ("</" + tagName).replace(BLANK_SPACE, EMPTY_TEXT);
        numberOfCoincidences = textToSearch.length();
        numberOfCoincidencesForClosing = testToSearchForClosing.length();
    }

    /**
     * Verifies if the sequence of characters is the  search tag.
     * 
     * @param character 
     */
    public void isTag(char character) {
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
            buildValue(character);
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
        finalized = coincidenceOfClosing == numberOfCoincidencesForClosing;
    }

    /**
     * This method returns a String with the found value.
     * 
     * @return 
     */
    public String getValue() {
        return located() ? value.substring(0, value.length() - coincidenceOfClosing) : Bundle.TestReport_notFound();
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
