/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.tools;

/**
 * Class that return tag's name root for each file type
 *
 * @author sergio_daza
 */
public class MetadataUtil {

    public static final String APEX_CLASS_ROOT_TAG = "ApexClass";
    public static final String APEX_TRIGGER_ROOT_TAG = "ApexTrigger";
    public static final String VISUALFORCE_COMPONENT = "Component";
    public static final String VISUALFORCE_PAGE = "ApexPage";
    public static final String STATIC_RESOURCE = "StaticResource";

    /**
     * Get root tag name
     *
     * @param extension String with file's extension
     * @return a String with root tag name
     */
    public static String getRootTagName(String extension) {
        String rootTagName = "";
        if ("cls".equals(extension)) {
            rootTagName = APEX_CLASS_ROOT_TAG;
        }
        if ("trigger".equals(extension)) {
            rootTagName = APEX_TRIGGER_ROOT_TAG;
        }
        if ("page".equals(extension)) {
            rootTagName = VISUALFORCE_PAGE;
        }
        if ("component".equals(extension)) {
            rootTagName = VISUALFORCE_COMPONENT;
        }
        if ("resource".equals(extension)) {
            rootTagName = STATIC_RESOURCE;
        }
        return rootTagName;
    }

}
