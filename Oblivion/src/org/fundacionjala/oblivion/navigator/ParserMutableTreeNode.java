/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import com.sun.source.tree.Tree.Kind;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Base Implementation of a {@link DefaultMutableTreeNode} that hold the common attributes to show relevant information on navigation panel.
 *
 * @author nelson_alcocer
 */
public abstract class ParserMutableTreeNode extends DefaultMutableTreeNode {

    public ParserMutableTreeNode(Object showedName) {
        super(showedName, true);
    }

    public abstract int getLine();

    public abstract Kind getKind();

    public abstract String getDataType();

    public abstract boolean getIsPrivate();

    public abstract String getIconPath();
}
