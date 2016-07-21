/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import java.util.List;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.TreeUtils;
import org.fundacionjala.oblivion.apex.ast.tree.visitors.ApexTreeVisitorAdapter;
import org.netbeans.modules.csl.api.OffsetRange;
import org.openide.text.NbDocument;

/**
 * Visitor that populates a list of foldable regions.
 */
class FoldTreeVisitor extends ApexTreeVisitorAdapter<Void, List<OffsetRange>> {
    private final StyledDocument document;

    FoldTreeVisitor(StyledDocument document) {
        this.document = document;
    }

    @Override
    public Void visitMethod(MethodTree mt, List<OffsetRange> folds) {
        BlockTree body = mt.getBody();
        if (body != null) {
            Token firstToken = TreeUtils.getFirstToken(body);
            Token lastToken = TreeUtils.getLastToken(body);
            if (!firstToken.getImage().isEmpty() && !lastToken.getImage().isEmpty()) {
                int startPos = NbDocument.findLineOffset(document, firstToken.getBeginLine() - 1) + firstToken.getBeginColumn() - 1;
                int endPos = NbDocument.findLineOffset(document, lastToken.getEndLine() - 1) + lastToken.getEndColumn();
                OffsetRange range = new OffsetRange(startPos, endPos);
                folds.add(range);
            }
        }
        return null;
    }

}
