/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.tools;

/**
 * Class that manages the tags of metadata file
 *
 * @author sergio_daza
 */
public class TagOfMetadata {

    private final String name;
    private final String value;
    private boolean isValid;

    public TagOfMetadata(String name, String value) {
        this.isValid = true;
        this.name = name.trim();
        this.value = value.trim();
        if ("".equals(name.trim()) || "".equals(value.trim())) {
            isValid = false;
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid() {
        return isValid;
    }

}
