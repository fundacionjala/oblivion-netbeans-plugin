/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree.Kind;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.fundacionjala.oblivion.apex.ast.tree.TreeUtils;
import org.fundacionjala.oblivion.apex.utils.Icons;

/**
 * It represent a class from Apex parser to show on navigation panel. Holds necessary information for navigation panel
 * functionality
 *
 * @author nelson_alcocer
 */
class ClassMutableTreeNode extends ParserMutableTreeNode {

    private int line;
    private Kind kind;
    private String dataType;
    private boolean isPrivate = false;
    private static final String ICON_PATH = Icons.CLASS;

    public ClassMutableTreeNode(String showedName, ClassTree tree) {
        super(showedName);
        line = TreeUtils.getTokenFromIdentifierTree(tree).getBeginLine();
        kind = tree.getKind();
        dataType = "";
        setPrivate(tree.getModifiers().getFlags());
    }

    private void setPrivate(Set<Modifier> modifiers) {
        if (modifiers.contains(Modifier.PRIVATE)) {
            isPrivate = true;
        }
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public boolean getIsPrivate() {
        return isPrivate;
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }
}
