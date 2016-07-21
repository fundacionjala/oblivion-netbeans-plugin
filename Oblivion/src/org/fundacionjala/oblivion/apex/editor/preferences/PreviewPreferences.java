/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

/**
 * This class represents the preferences to show a preview panel text in the
 * formatting panel.
 *
 * @author Amir Aranibar
 */
public class PreviewPreferences extends AbstractPreferences {

    private final Map<String, Object> map = new HashMap<>();

    public PreviewPreferences() {
        super(null, "");
    }

    @Override
    protected void putSpi(String key, String value) {
        map.put(key, value);
    }

    @Override
    protected String getSpi(String key) {
        return (String) map.get(key);
    }

    @Override
    protected void removeSpi(String key) {
        map.remove(key);
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        String array[] = new String[map.keySet().size()];
        return map.keySet().toArray(array);
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
