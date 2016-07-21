/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

/**
 * Because sometimes it's good to have a Pair.
 *
 * @author sergio_daza
 */
public class GenericPair<T, S> {

    T first;
    S second;

    public GenericPair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    
    public void setFirst(T value) {
        first = value;
    }
    
    public void setSecond(S value) {
        second = value;
    }
    
    public boolean isNull() {
        return first == null && second == null;
    }
}
