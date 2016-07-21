/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Base implementation of a {@link StatementTree}
 *
 * @author Adrian Grajeda
 * @param <T>
 */
public abstract class CompoundTree<T extends Tree> extends BaseTree implements StatementTree, ExpressionTree, Serializable {

    protected final Set<T> children;
    
    protected Collection<T> members;

    private Token blockStart;

    private Token blockEnd;

    CompoundTree(Kind kind, Token token, Collection<T> members, Token blockStart, Token blockEnd) {
        super(kind, token);
        if (members != null) {
            children = new HashSet<>(members);
        } else {
            children = new HashSet<>();
        }
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
        this.members = members;
    }

    CompoundTree(Kind kind, Token token) {
        super(kind, token);
        children = new HashSet<>();
        this.blockStart = null;
        this.blockEnd = null;
    }
    
    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        for (T current : children) {
            if (current != null) {
                current.accept(tv, d);
            }
        }
        return null;
    }

    public void addMembers(Collection<? extends T> members) {
        children.addAll(members);
    }

    public void addMember(T member) {
        children.add(member);
    }

    public Collection<T> getChildren() {
        return children;
    }

    protected void updateParentReference() {
        TreeUtils.updateParentReference(this, children);
    }

    protected void updateParentReference(List<? extends Tree> members) {
        TreeUtils.updateParentReference(this, members);
    }

    /**
     * Gets the first {@link Token} for this Tree
     *
     * @return the first token
     */
    public Token getBlockStart() {
        return blockStart;
    }

    /**
     * Gets the last {@link Token} for this Tree
     *
     * @return The last Token
     */
    public Token getBlockEnd() {
        return blockEnd;
    }

    /**
     * Sets the first {@link Token} for this Tree
     *
     * @param firstToken
     */
    public void setBlockStart(Token firstToken) {
        this.blockStart = firstToken;
    }

    /**
     * Sets the last {@link Token} for this Tree
     * @param lastToken
     */
    public void setBlockEnd(Token lastToken) {
        this.blockEnd = lastToken;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 43 * hash + children.toString().hashCode();
        return hash;
    }
    
}
