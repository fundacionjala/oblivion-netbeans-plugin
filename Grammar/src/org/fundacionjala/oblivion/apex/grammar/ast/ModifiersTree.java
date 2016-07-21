/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.ast;

import java.util.LinkedHashMap;
import org.fundacionjala.oblivion.apex.Token;

/**
 * class wrapper to of ModifiersTree with two new methos 
 * 
 * @author sergio_daza
 */
public interface ModifiersTree extends com.sun.source.tree.ModifiersTree{
    
    public LinkedHashMap<Integer, Token> getModifiers();
    public void addModifiers(ModifiersTree modifiers);
    
}
