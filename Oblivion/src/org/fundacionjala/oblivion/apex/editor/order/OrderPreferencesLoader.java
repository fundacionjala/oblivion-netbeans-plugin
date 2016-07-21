/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import java.util.prefs.Preferences;
import javax.swing.text.Document;
import org.apache.commons.lang.ArrayUtils;
import org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier;
import org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType;
import org.fundacionjala.oblivion.apex.editor.preferences.FormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.IFormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.PreferencesFormatOptions;
import org.netbeans.modules.editor.indent.spi.CodeStylePreferences;

/**
 * Loads all ordering preferences defined in the formating options.
 *
 * @author Amir Aranibar
 */
public class OrderPreferencesLoader implements IOrderPreferencesLoader {

    private static final String COMMA_SEPARATOR = ",";

    private final IFormatOptions formatOptions;

    private ClassMemberType[] membersSortOrder;
    private AccessModifier[] sortMembersByVisibility;
    private boolean orderAlphabetically;
    private boolean sortByVisibility;

    public OrderPreferencesLoader() {
        this.formatOptions = FormatOptions.getInstance();
    }

    @Override
    public void loadPreferences(Document document) {
        Preferences preferences = CodeStylePreferences.get(document).getPreferences();
        String membersSortOrderPreferences = formatOptions.getOptionValue(preferences, PreferencesFormatOptions.MEMBERS_SORT_ORDER.name());
        String[] members = membersSortOrderPreferences.split(COMMA_SEPARATOR);
        membersSortOrder = new ClassMemberType[members.length];

        for (int index = 0; index < members.length; index++) {
            membersSortOrder[index] = ClassMemberType.getEnum(members[index]);
        }

        String orderAlphabeticallyPreferences = formatOptions.getOptionValue(preferences, PreferencesFormatOptions.CHECK_SORT_MEMBERS_IN_GROUPS_ALPHABETICALLY.name());
        orderAlphabetically = Boolean.parseBoolean(orderAlphabeticallyPreferences);

        String sortByVisibilityPreferences = formatOptions.getOptionValue(preferences, PreferencesFormatOptions.SORT_MEMBERS_BY_VISIBILITY.name());
        sortByVisibility = Boolean.parseBoolean(sortByVisibilityPreferences);

        if (sortByVisibility) {
            String sortMembersByVisibilityPreferences = formatOptions.getOptionValue(preferences, PreferencesFormatOptions.MEMBERS_VISIBILITY.name());
            String[] accessModifiers = sortMembersByVisibilityPreferences.split(COMMA_SEPARATOR);
            sortMembersByVisibility = new AccessModifier[accessModifiers.length];
            for (int index = 0; index < accessModifiers.length; index++) {
                sortMembersByVisibility[index] = AccessModifier.getEnum(accessModifiers[index]);
            }
        } else {
            sortMembersByVisibility = new AccessModifier[]{AccessModifier.DEFAULT};
        }

        ArrayUtils.reverse(membersSortOrder);
        ArrayUtils.reverse(sortMembersByVisibility);
    }

    @Override
    public ClassMemberType[] getSortedMembers() {
        return membersSortOrder;
    }

    @Override
    public AccessModifier[] getSortedModifiers() {
        return sortMembersByVisibility;
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
