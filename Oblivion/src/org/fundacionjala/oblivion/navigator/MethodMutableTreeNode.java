/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.fundacionjala.oblivion.apex.ast.tree.TreeUtils;
import org.fundacionjala.oblivion.apex.utils.Icons;

/**
 * It represent a method from Apex parser to show on navigation panel. Holds necessary information for navigation panel
 * functionality
 *
 * @author nelson_alcocer
 */
public class MethodMutableTreeNode extends ParserMutableTreeNode {

    private int line;
    private Tree.Kind kind;
    private String dataType;
    private boolean isPrivate = false;
    private String iconPath;
    private static final String CONSTRUCTOR_PATH = Icons.CONSTRUCTOR;
    private static final String PRIVATE_METHOD_PATH = Icons.PRIVATE_METHOD;
    private static final String METHOD_PATH = Icons.METHOD;

    public MethodMutableTreeNode(String showedName, MethodTree tree) {
        super(showedName);
        line = TreeUtils.getTokenFromIdentifierTree(tree).getBeginLine();
        kind = tree.getKind();
        Tree returnType = tree.getReturnType();
        setPrivate(tree.getModifiers().getFlags());
        dataType = returnType == null ? "" : createDataType(returnType);
        setImagePath();
    }

    private String createDataType(Tree returnType) {
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
        if (dataType.isEmpty()) {
            iconPath = CONSTRUCTOR_PATH;
        } else if (isPrivate) {
            iconPath = PRIVATE_METHOD_PATH;
        } else {
            iconPath = METHOD_PATH;
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
