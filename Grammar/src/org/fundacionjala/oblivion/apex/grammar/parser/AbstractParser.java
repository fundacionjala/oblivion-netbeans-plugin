/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.runtime.ParserException;
import org.fundacionjala.oblivion.apex.grammar.ast.TreeFactory;


import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;
import org.fundacionjala.oblivion.apex.grammar.parser.exceptions.ContextParseException;

import static org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants.EOF;
import static org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants.RBRACE_SEPARATOR;
import static org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants.RPAREN_SEPARATOR;
import static org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants.SEMICOLON_SEPARATOR;

/**
 * Base class for {@link ApexParser} that have the methods to handle the errors, etc.
 *
 * @author Adrian Grajeda
 */
public abstract class AbstractParser {

    protected TreeFactory factory;
    protected static final List<Integer> RECOVERY_TOKENS = Arrays.asList(
        new Integer[]{EOF, SEMICOLON_SEPARATOR, RBRACE_SEPARATOR, RPAREN_SEPARATOR});

    protected static final int NO_MODIFIER = -1;

    protected List<ParseException> syntaxErrors = new ArrayList<>();

    public void setTreeFactory(TreeFactory tree) {
        this.factory = tree;
    }
            
    public synchronized List<ParseException> getSyntaxErrors() {
        return Collections.unmodifiableList(syntaxErrors);
    }

    /**
     * Recovers from a {@link ParserException} by setting the parser in a group of tokens that can be parsed again.
     *
     * @param ex the error
     * @param recoveryPoint position to move the parser
     */
    protected void recover(ParseException ex, int recoveryPoint) {
        syntaxErrors.add(ex);
        Token token;
        do {
            token = getNextToken();
        } while (token.kind != EOF && token.kind != recoveryPoint);
    }

    /**
     * Recover from a {@link ContextParseException} by setting the parser in a group of tokens that can be parser again
     *
     * @param ce the error
     */
    public void contextErrorRecover(ParseException ce) {
        syntaxErrors.add(ce);
        Token token;
        do {
            token = getNextToken();
        } while (!RECOVERY_TOKENS.contains(token.kind));
    }

    /**
     * Recover from a {@link ContextParseException} by setting the parser in the next token that can be parser again
     *
     * @param ce the error
     */
    public void contextErrorRecoverToNextToken(ParseException ce) {
        syntaxErrors.add(ce);
        getNextToken();
    }

    /**
     * Return the next token to be consumed by the parser.
     *
     * @return the {@link Token}
     */
    protected abstract Token getNextToken();

}
