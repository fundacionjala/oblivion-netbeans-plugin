/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.lexer;

import java.util.Objects;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.Language;

/**
 * Class that contains the necessary information of a token.
 *
 * The base implementation of the class follows the reference
 *
 * @see https://platform.netbeans.org/tutorials/nbm-javacc-lexer.html
 *
 * @author maria_garcia
 */
public class ApexTokenId implements TokenId {

    private final int id;
    private final String name;
    private final String primaryCategory;

    ApexTokenId(String name, String primaryCategory, int id) {
        this.id = id;
        this.name = name;
        this.primaryCategory = primaryCategory;
    }

    /**
     * Get integer identification of this ApexTokenId.
     *
     * @return The numeric identification
     */
    @Override
    public int ordinal() {
        return id;
    }

    /**
     * Get name of this ApexTokenId
     *
     * @return A unique name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Get name of primary token category into which this token belongs.
     *
     * @return The name of the primary token category into which this token belongs
     */
    @Override
    public String primaryCategory() {
        return primaryCategory;
    }

    public static Language<ApexTokenId> getLanguage(String mimeType) {
        return new ApexLanguageHierarchy(mimeType).language();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApexTokenId other = (ApexTokenId) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
