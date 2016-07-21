/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.fundacionjala.oblivion.apex.ast.tree.TreeUtils;
import org.fundacionjala.oblivion.apex.utils.Icons;

/**
 * It represent a variable from Apex parser to show on navigation panel. Holds necessary information for navigation
 * panel functionality
 *
 * @author nelson_alcocer
 */
public class VariableMutableTreeNode extends ParserMutableTreeNode {

    private int line;
    private Tree.Kind kind;
    private String dataType;
    private boolean isPrivate = false;
    private String iconPath;
    private static final String PRIVATE_VARIABLE_PATH = Icons.PRIVATE_VARIABLE;
    private static final String VARIABLE_PATH = Icons.VARIABLE;

    public VariableMutableTreeNode(String showedName, VariableTree tree) {
        super(showedName);
        line = TreeUtils.getTokenFromIdentifierTree(tree).getBeginLine();
        kind = tree.getKind();
        dataType = createType(tree);
        ModifiersTree modifiers = tree.getModifiers();
        if (modifiers != null) {
            setPrivate(modifiers.getFlags());
        }
        setImagePath();
    }

    private String createType(VariableTree tree) {
        Tree returnType = tree.getType();

        if (returnType instanceof ParameterizedTypeTree || returnType instanceof ArrayTypeTree) {
            return returnType.toString();
        } else {
            return TreeUtils.getTokenFromIdentifierTree(returnType).getImage();
        }
    }

    private void setPrivate(Set<Modifier> modifiers) {
        if (modifiers.contains(Modifier.PRIVATE)) {
            isPrivate = true;
        }
    }

    private void setImagePath() {
        if (isPrivate) {
            iconPath = PRIVATE_VARIABLE_PATH;
        } else {
            iconPath = VARIABLE_PATH;
        }
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public Tree.Kind getKind() {
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
        return iconPath;
    }
}
