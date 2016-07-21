/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.jcclexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Interface that defines the behavior for both the lexer and parser.
 * 
 * @author Maria Garcia
 */
public abstract class ApexCharStream {
    static boolean staticFlag;
    
    abstract char BeginToken() throws IOException;

    abstract String GetImage();

    public abstract char[] GetSuffix(int len);

    abstract void ReInit(Reader stream, int i, int i0);

    abstract void ReInit(InputStream stream, String encoding, int i, int i0) throws UnsupportedEncodingException;

    abstract void backup(int i);

    abstract int getBeginColumn();

    abstract int getBeginLine();

    abstract int getEndColumn();

    abstract int getEndLine();

    abstract char readChar() throws IOException; 
}
