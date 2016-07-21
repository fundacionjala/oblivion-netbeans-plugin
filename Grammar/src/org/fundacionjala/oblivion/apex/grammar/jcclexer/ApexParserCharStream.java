/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.jcclexer;

import java.io.Reader;

/**
 * Represents the javacc default ChartStream implementation to be used in the parser 
 */
public class ApexParserCharStream extends JavaCharStream implements CharStream
{
    private static final int TAB_SIZE = 1;
    
    public ApexParserCharStream(Reader dstream) {
        super(dstream);
        setTabSize(TAB_SIZE);
    } 
}

