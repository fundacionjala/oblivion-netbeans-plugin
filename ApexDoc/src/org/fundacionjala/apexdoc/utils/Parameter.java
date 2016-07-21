/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.apexdoc.utils;

/**
 * tuple that contain the information of parameter
 * 
 * @author sergio_daza
 */
public class Parameter {

    public String type;
    public String variable;

    public Parameter(String type, String variable) {
        this.type = type;
        this.variable = variable;
    }
    
}
