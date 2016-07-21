/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;

/**
 * Helper method to work with AST tree
 *
 * @author adrian_grajeda
 */
public final class TreeUtils {

    private static final String DOT_SEPARATOR = ".";

    /**
     * Creates a immutable instance of {@link Name} class.
     *
     * @param value the text value to create the Name
     * @return the name
     */
    public static Name createNameFromString(final String value, final List<? extends ExpressionTree> names) {
        return new Name(value,names);
    }
    
    public static Name createNameFromString(final String value) {
        return new Name(value);
    }

    static Name createNameFromExpressionTree(ExpressionTree identifier) {
        if (identifier instanceof IdentifierTree) {
            return (Name) ((IdentifierTree) identifier).getName();
        }
        return null;
    }

    public static Name createName(List<? extends ExpressionTree> names) {
        IdentifierTree identifier;
        List<CharSequence> identifierNameList = new ArrayList<>();
        for (ExpressionTree current : names) {
            if (current instanceof ErroneousTree) {
                break;
            }
            identifier = ((IdentifierTree) current);
            identifierNameList.add(identifier.getName());
        }
        return createNameFromString(String.join(DOT_SEPARATOR, identifierNameList),names);
    }

    public final static void updateParentReference(Tree parent, Collection<? extends Tree> children) {
        for (Tree current : children) {
            if (current instanceof BaseTree) {
                ((BaseTree) current).setParent(parent);
            }
        }
    }

    /**
     * Retrieves The First Token that appears on the entry to a base tree node scope. The first token should have been
     * filled when the tree node was created.
     *
     * @param tree The tree node to get the first token from
     * @return the first token.
     */
    public static Token getFirstToken(Tree tree) {
        if (tree instanceof CompoundTree) {
            return ((CompoundTree) tree).getBlockStart();
        }
        return null;
    }

    /**
     * Retrieves The Last Token that appears on the exit of a base tree node scope. The last token should have been
     * filled when the tree node was created.
     *
     * @param tree The tree node to get the last token from
     * @return the last token.
     */
    public static Token getLastToken(Tree tree) {
        if (tree instanceof CompoundTree) {
            return ((CompoundTree) tree).getBlockEnd();
        }
        return null;
    }
    
    /**
     * Retrieves The Token assigned to base tree node scope.
     * The last token should have been filled when the tree node was created.
     * @param tree The tree node to get the last token from
     * @return the last token.
     */
    public static Token getTokenFromIdentifierTree(Tree tree) {
        if (tree instanceof BaseTree) {
            return ((BaseTree) tree).getToken();
        }
        return null;
    }
}
