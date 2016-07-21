/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.declarationFinder;

import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.netbeans.modules.csl.api.OffsetRange;
import org.openide.text.NbDocument;

/**
 * Utils for the declaration finder
 *
 * @author sergio_daza
 */
public class DeclarationFinderUtils {

    /**
     * This method returns the range of compoundTree
     *
     * @param compoundTree
     * @return static OffsetRange getRange(CompoundTree x
     */
    public static OffsetRange getRange(CompoundTree compoundTree, String name, StyledDocument styledDocument) {
        if (compoundTree.getToken() != null) {
            return getRange(compoundTree.getToken(), name, styledDocument);
        }
        return OffsetRange.NONE;
    }

    public static OffsetRange getRange(Token token, String name, StyledDocument styledDocument) {
        int offsetEnd = NbDocument.findLineOffset((StyledDocument) styledDocument, token.getEndLine() - 1) + token.getEndColumn() - 1;
        int offsetStart = offsetEnd - name.length();
        return new OffsetRange(offsetStart, offsetEnd);
    }
}
