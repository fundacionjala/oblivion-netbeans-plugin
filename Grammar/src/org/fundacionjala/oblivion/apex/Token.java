/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex;

import java.io.Serializable;

/**
 * Describe a token with all the information to identify the word in the document.
 * 
 * @author Adrian Grajeda
 */
public interface Token extends Serializable {
    
    /**
     * Gets the token unique identifier.
     * 
     * @return the id
     */
    int getId();
    
    /**
     * Return the text value of this token.
     * 
     * @return the text value
     */
    String getImage();
    
    /**
     * Gets the number of the line where the tokens starts
     * 
     * @return the line number
     */
    int getBeginLine();
    
    /**
     * Gets the column index where the token starts.
     * 
     * @return the column index
     */
    int getBeginColumn();
    
    /**
     * Gets the number of line where the token ends.
     * 
     * @return the line number 
     */
    int getEndLine();
    
    /**
     * Gets the column index where the token ends.
     * 
     * @return the column index
     */
    int getEndColumn();
}
