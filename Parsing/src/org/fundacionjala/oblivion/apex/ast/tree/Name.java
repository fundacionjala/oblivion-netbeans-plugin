/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the name.
 * 
 * @author sergio_daza
 */
public class Name implements javax.lang.model.element.Name{
    
    private CharSequence delegate;
    final List<? extends ExpressionTree> names;
    
    public Name(String value){
        this.delegate = value;
        names = new ArrayList<>();
    }
    
    public Name(String value,List<? extends ExpressionTree> names){
        this.delegate = value;
        this.names = names;
    }
    
    @Override
    public boolean contentEquals(CharSequence cs) {
        return delegate.equals(cs);
    }

    @Override
    public int length() {
        return delegate.length();
    }

    @Override
    public char charAt(int index) {
        return delegate.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return delegate.subSequence(start, end);
    }
    
    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj);
    }

    @Override
    public int hashCode() {
        return delegate.toString().hashCode();
    }

    public List<? extends ExpressionTree> getIdentifierList() {
        return names;
    }

}
