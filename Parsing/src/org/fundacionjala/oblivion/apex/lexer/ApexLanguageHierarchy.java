/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.lexer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexLexerConstants;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.openide.util.Exceptions;

/**
 * Defines a set of token ids and categories for the language.
 *
 * The base implementation of the class follows the reference
 *
 * @see https://platform.netbeans.org/tutorials/nbm-javacc-lexer.html
 *
 * @author maria_garcia
 */
public class ApexLanguageHierarchy extends LanguageHierarchy<ApexTokenId> {

    /**
     * Token category that represents an Operator
     */
    public static final String OPERATOR = "operator";
    /**
     * Token category that represents a literal
     */
    public static final String LITERAL = "literal";
    /**
     * Token category that represents a keyword
     */
    public static final String KEYWORD = "keyword";
    /**
     * Token category that represents a comment
     */
    public static final String COMMENT = "comment";
    /**
     * Token category that represents a white space
     */
    public static final String WHITESPACE = "whitespace";

    private static List<ApexTokenId> tokens;
    private static Map<Integer, ApexTokenId> idToToken;
    public static final String APEX_MIME_TYPE = "text/x-cls";
    public static final String APEX_TRIGGER_MIME_TYPE = "text/x-trigger";
    private String mimeType;
    
    public ApexLanguageHierarchy(String mimeType){
        this.mimeType = mimeType;
    }
    
    /**
     * Inits the list and the map of tokens
     */
    private static void init() throws IllegalArgumentException {
        Map<String, String> specialTokens = new HashMap<>();
        specialTokens.put("EOF", WHITESPACE);        
        List<String> invalidTokenNames = new ArrayList<>();
        invalidTokenNames.add("DEFAULT");
        invalidTokenNames.add("IN_FORMAL_COMMENT");
        invalidTokenNames.add("IN_MULTI_LINE_COMMENT");
        invalidTokenNames.add("tokenImage");
        idToToken = new HashMap<>();
        tokens = new ArrayList<>();
        ApexTokenId apexToken;
        String tokenName;
        String tokenCategory;
        int tokenId;
        Field[] fields = ApexLexerConstants.class.getDeclaredFields();
        for (Field field : fields) {
            tokenName = field.getName();
            if (!invalidTokenNames.contains(tokenName)) {
                tokenCategory = specialTokens.containsKey(tokenName) ? specialTokens.get(tokenName) : getTokenCategory(tokenName);
                try {
                    tokenId = field.getInt(null);
                    apexToken =  new ApexTokenId(tokenName, tokenCategory, tokenId);
                    idToToken.put(tokenId, apexToken);
                    tokens.add(apexToken);
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    /**
     * Retrieves the token corresponding to an id passed as a parameter
     *
     * @param id The id of the token
     * @return The ApexTokenId that corresponds to the id
     */
    public static synchronized ApexTokenId getToken(int id) throws IllegalArgumentException {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    /**
     * Gets the token category of a given token name.
     * 
     * @param tokenName The token name.
     * @return the token category.
     */
    private static String getTokenCategory(String tokenName) {
        String tokenCategory = tokenName;
        if (tokenName.contains("_")) {
            tokenCategory = tokenName.substring(tokenName.lastIndexOf("_") + 1);
        }
        return tokenCategory.toLowerCase();
    }

    /**
     * Provide a collection of token ids that comprise the language.
     *
     * @return A collection of token objects
     */
    @Override
    protected synchronized Collection<ApexTokenId> createTokenIds() throws IllegalArgumentException {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    /**
     * Create lexer prepared for returning tokens from subsequent calls
     *
     * @param info non-null lexer restart info containing the information necessary for lexer restarting.
     * @return An ApexLexer
     */
    @Override
    protected synchronized Lexer<ApexTokenId> createLexer(LexerRestartInfo<ApexTokenId> info) {
        return new ApexLexer(info);
    }

    /**
     * Gets the mime type of the language constructed from this language hierarchy.
     *
     * @return A String that contains the simple text type
     */
    @Override
    protected String mimeType() {
        return mimeType;
    }
    
}
