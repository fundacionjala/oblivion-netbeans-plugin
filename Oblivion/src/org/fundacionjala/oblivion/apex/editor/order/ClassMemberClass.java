/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;

/**
 * This class represents a class member type class and stores the data to be
 * used in the re-order process.
 *
 * @author Amir Aranibar
 */
public class ClassMemberClass extends ClassMember<ClassTreeImpl> {

    private final int blockStartPosition;
    private final List<ClassMember<?>> members;

    ClassMemberClass(ClassTreeImpl classTree, int startPosition, int endPosition, int commentStartPosition, int blockStartPosition, ClassTreeImpl parentClassTree) {
        super(classTree, startPosition, endPosition, commentStartPosition, parentClassTree);
        this.blockStartPosition = blockStartPosition > 0 ? ++blockStartPosition : blockStartPosition;
        members = new ArrayList<>();
    }

    /**
     * Adds a class member to the children list.
     *
     * @param treePosition the class member to be added.
     */
    public void addMember(ClassMember<?> classMember) {
        members.add(classMember);
    }

    /**
     * Gets the children list.
     *
     * @return the children list.
     */
    public List<ClassMember<?>> getMembers() {
        return members;
    }

    /**
     * Gets the start position of the code block.
     *
     * @return the start position of the code block.
     */
    public int getBlockStartPosition() {
        return blockStartPosition;
    }
}
