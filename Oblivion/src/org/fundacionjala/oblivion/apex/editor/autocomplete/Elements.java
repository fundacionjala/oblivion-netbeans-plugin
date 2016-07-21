/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;

/**
 * this class contain the elements for one class
 * 
 * @author sergio_daza
 */
public class Elements {
    public String extendsClassName = "";
    public List<VariableTreeImpl> variables = new ArrayList<>();
    public List<VariableTreeImpl> attributes = new ArrayList<>();
    public List<AttributeItem> item_attributes = new ArrayList<>();
    public List<PropertyItem> item_properties = new ArrayList<>();
    public List<MethodItem> item_methods = new ArrayList<>();
}
