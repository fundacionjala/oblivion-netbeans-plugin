/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import com.sun.source.tree.Tree;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;

/**
 * This class stores the data to be used in the re-order process.
 *
 * @author Amir Aranibar
 */
public class ClassMember<T extends BaseTree> implements Comparable<ClassMember<?>> {

    private final int endPosition;
    protected final T classMemberTree;

    protected int commentStartPosition;
    protected String text;
    private Integer startPosition;
    private ClassTreeImpl parentClass;

    public ClassMember(T classMemberTree, int startPosition, int endPosition, int commentStartPosition, ClassTreeImpl parentClass) {
        this.classMemberTree = classMemberTree;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.parentClass = parentClass;
        this.commentStartPosition = commentStartPosition;
        text = null;
    }

    /**
     * Sets the class parent.
     *
     * @param parentClass the parent class.
     */
    public void setParentClass(ClassTreeImpl parentClass) {
        this.parentClass = parentClass;
    }

    /**
     * Sets the start position of the class member.
     *
     * @param startPosition the start position of the class member.
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Sets the class member as string.
     *
     * @param text the class member as string.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Removes the start position of the comment.
     */
    public void clearCommentStartPosition() {
        commentStartPosition = -1;
    }

    /**
     * Gets the parent class.
     *
     * @return the parent class.
     */
    public ClassTreeImpl getParentClass() {
        return parentClass;
    }

    /**
     * Gets the class member as string.
     *
     * @return the class member as string.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the start position of the class member.
     *
     * @return the start position of the class member.
     */
    public int getStartPosition() {
        return commentStartPosition < 0 ? startPosition : commentStartPosition;
    }

    /**
     * Gets the end position of the class member.
     *
     * @return the end position of the class member.
     */
    public int getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the kind of the class member.
     *
     * @return the kind of the class member.
     */
    public Tree.Kind getKind() {
        return classMemberTree.getKind();
    }

    /**
     * Gets the start position of the comment.
     *
     * @return the start position of the comment.
     */
    public int getCommentStartPosition() {
        return commentStartPosition;
    }

    /**
     * Gets the BaseTree of the class member.
     *
     * @return the BaseTree of the class member.
     */
    public BaseTree getType() {
        return classMemberTree;
    }

    @Override
    public int compareTo(ClassMember<?> treePosition) {
        return startPosition.compareTo(treePosition.startPosition);
    }

}
