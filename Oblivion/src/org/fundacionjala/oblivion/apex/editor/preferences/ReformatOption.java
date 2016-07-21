/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

/**
 * Temporarily saves the option and position to be reformatted.
 *
 * @author Amir Aranibar
 */
public class ReformatOption implements Comparable<ReformatOption> {

    private PreferencesFormatOptions option;
    private Integer position;

    public ReformatOption(PreferencesFormatOptions option, Integer position) {
        this.option = option;
        this.position = position;
    }

    /**
     * Gets the option to reformat a position.
     *
     * @return the format option
     */
    public PreferencesFormatOptions getOption() {
        return option;
    }

    /**
     * Gets the position to be reformatted by an option.
     *
     * @return the position of a character
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets the option to reformat a position.
     *
     * @param option the format option
     */
    public void setOption(PreferencesFormatOptions option) {
        this.option = option;
    }

    /**
     * Sets the position to be reformatted by an option.
     *
     * @param postion the position of a character
     */
    public void setPosition(Integer postion) {
        this.position = postion;
    }

    @Override
    public int compareTo(ReformatOption other) {
        return position.compareTo(other.position);
    }
}
