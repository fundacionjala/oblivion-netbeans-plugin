/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.Tree;
import java.io.Serializable;
import java.util.Objects;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Base Implementation of a {@link Tree} that hold the common attributes and logic to accept visitors.
 *
 * @author Adrian Grajeda
 */
public abstract class BaseTree implements Serializable, Tree {

    protected final Kind kind;

    protected final Token token;

    protected Tree parent;

    /**
     * Creates a new instance of Tree (a lead node in the Apex AST) that has a {@link Kind} and the token that
     * represents {@link Token}.
     *
     * @param kind the kind of this tree
     * @param token the text representation
     */
    BaseTree(Kind kind, Token token) {
        this.kind = kind;
        this.token = token;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    /**
     * Gets the {@link Token} for this Tree
     *
     * @return
     */
    public Token getToken() {
        return token;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.kind);
        hash = 41 * hash + Objects.hashCode(this.token);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseTree other = (BaseTree) obj;
        if (this.kind != other.kind) {
            return false;
        }
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return Objects.equals(this.parent, other.parent);
    }

}
