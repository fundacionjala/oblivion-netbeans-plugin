/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains constants values and utility methods for the different salesforce MimeTypes
 * 
 * @author Marcelo Garnica
 */
public class MimeType {

    public static final String APEX_CLASS_MIME_TYPE = "text/x-cls";
    public static final String APEX_CLASS_ACTIONS_PATH = "Loaders/text/x-cls/Actions";
    public static final String APEX_CLASS_ACTIONS_EDITOR_POPUP = "Editors/text/x-cls/Popup";
    
    public static final String APEX_TRIGGER_MIME_TYPE = "text/x-trigger";
    public static final String APEX_TRIGGER_ACTIONS_PATH = "Loaders/text/x-trigger/Actions";
    public static final String APEX_TRIGGER_ACTIONS_EDITOR_POPUP = "Editors/text/x-trigger/Popup";
    
    public static final String XHTML_MIME_TYPE = "text/x-salesforce+xhtml";
    public static final String XHTML_ACTIONS_PATH = "Loaders/text/x-salesforce+xhtml/Actions";
    public static final String XHTML_ACTIONS_EDITOR_POPUP = "Editors/text/x-salesforce+xhtml/Popup";
    
    public static final String XML_MIME_TYPE = "text/x-salesforce+xml";
    public static final String XML_ACTIONS_PATH = "Loaders/text/x-salesforce+xml/Actions";
    public static final String XML_ACTIONS_EDITOR_POPUP = "Editors/text/x-salesforce+xml/Popup";
    
    private static Set<String> mimeTypes;

    public static Set<String> getMimeTypes() {
        if (mimeTypes == null) {
            mimeTypes = new HashSet<>();
            mimeTypes.add(APEX_CLASS_MIME_TYPE);
            mimeTypes.add(APEX_TRIGGER_MIME_TYPE);
            mimeTypes.add(XHTML_MIME_TYPE);
            mimeTypes.add(XML_MIME_TYPE);
            mimeTypes = Collections.unmodifiableSet(mimeTypes);
        }
        return mimeTypes;
    }
    
}
