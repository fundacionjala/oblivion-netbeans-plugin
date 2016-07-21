/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import java.util.ArrayList;
import java.util.List;

/**
 * This class grouped all class members collected by the visitor.
 *
 * @author Amir Aranibar
 */
public class ClassMemberGrouper {

    private final List<ClassMemberClass> classes;
    private final List<ClassMember<?>> members;

    public ClassMemberGrouper() {
        classes = new ArrayList<>();
        members = new ArrayList<>();
    }

    /**
     * Adds a class to the member list and class list.
     *
     * @param classTree the class to be added.
     */
    public void addClass(ClassMemberClass classTree) {
        classes.add(classTree);
        members.add(classTree);
    }

    /**
     * Adds a member to the members list.
     *
     * @param member the member to be added.
     */
    public void addMember(ClassMember<?> memberTree) {
        members.add(memberTree);
    }

    /**
     * Gets the class list.
     *
     * @return the class list.
     */
    public List<ClassMemberClass> getClasses() {
        return classes;
    }

    /**
     * Gets the member list.
     *
     * @return the member list.
     */
    public List<ClassMember<?>> getAllMembers() {
        return members;
    }
}
