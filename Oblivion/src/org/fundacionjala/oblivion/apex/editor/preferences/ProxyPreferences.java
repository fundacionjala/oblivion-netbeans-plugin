/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * this class sets the formatting option in the temporary preferences to be used
 * in the preview panel before to be saved in the project preferences.
 *
 * @author Amir Aranibar
 */
public final class ProxyPreferences extends AbstractPreferences {

    private final Preferences[] delegates;

    public ProxyPreferences(Preferences... delegates) {
        super(null, "");
        this.delegates = delegates;
    }

    @Override
    protected void putSpi(String key, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String getSpi(String key) {
        for (Preferences p : delegates) {
            String value = p.get(key, null);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    protected void removeSpi(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        Set<String> keys = new HashSet<>();
        for (Preferences p : delegates) {
            keys.addAll(Arrays.asList(p.keys()));
        }
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected AbstractPreferences childSpi(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void syncSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void flushSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
