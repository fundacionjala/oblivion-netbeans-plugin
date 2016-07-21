/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import javax.swing.text.Document;
import org.apache.commons.lang.ArrayUtils;
import org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier;
import org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType;

/**
 *
 * @author Amir Aranibar
 */
public class DummyPreferencesLoader implements IOrderPreferencesLoader {

    private final ClassMemberType[] classMemberTypes;
    private final AccessModifier[] accessModifiers;
    private final boolean orderAlphabetically;
    private final boolean sortByVisibility;

    public DummyPreferencesLoader(ClassMemberType[] classMemberTypes, AccessModifier[] accessModifiers, boolean sortByVisibility, boolean orderAlphabetically) {
        ArrayUtils.reverse(classMemberTypes);
        ArrayUtils.reverse(accessModifiers);

        this.classMemberTypes = classMemberTypes;
        this.accessModifiers = accessModifiers;
        this.sortByVisibility = sortByVisibility;
        this.orderAlphabetically = orderAlphabetically;
    }

    @Override
    public ClassMemberType[] getSortedMembers() {
        return classMemberTypes;
    }

    @Override
    public AccessModifier[] getSortedModifiers() {
        return accessModifiers;
    }

    @Override
    public void loadPreferences(Document document) {
    }

    @Override
    public boolean orderAlphabetically() {
        return orderAlphabetically;
    }

    @Override
    public boolean sortByVisibility() {
        return sortByVisibility;
    }

}
