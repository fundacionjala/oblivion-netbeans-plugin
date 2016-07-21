/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import java.util.List;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.fundacionjala.oblivion.apex.ast.tree.TreeUtils;
import org.fundacionjala.oblivion.apex.ast.tree.visitors.ApexTreeVisitorAdapter;
import org.fundacionjala.oblivion.apex.editor.preferences.PreferencesFormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.ReformatOption;
import org.openide.text.NbDocument;

/**
 * Sets the option and position to reformat in a document.
 *
 * @author Amir Aranibar
 */
class ReformatTreeVisitor extends ApexTreeVisitorAdapter<Void, List<ReformatOption>> {

    private final StyledDocument document;
    private final int startOffset;
    private final int endOffset;

    ReformatTreeVisitor(Document document, int startOffset, int endOffset) {
        this.document = (StyledDocument) document;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    @Override
    public Void visitMethod(MethodTree methodTree, List<ReformatOption> optionsToReformat) {
        BlockTree body = methodTree.getBody();
        
        if (body != null) {
            addLeftBraceToList(optionsToReformat, body, PreferencesFormatOptions.BRACES_IN_METHOD_DECLARATION);
            addRightBraceToList(optionsToReformat, (CompoundTree) body, PreferencesFormatOptions.AFTER_METHOD_DECLARATION);
        } else {
            addRightBraceToList(optionsToReformat, ((CompoundTree) methodTree), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }


        return null;
    }

    @Override
    public Void visitClass(ClassTree classTree, List<ReformatOption> optionsToReformat) {
        if (classTree instanceof CompoundTree) {
            addOptionToList(optionsToReformat, ((CompoundTree) classTree).getBlockStart(), PreferencesFormatOptions.BRACES_IN_CLASS_DECLARATION);
            addOptionToList(optionsToReformat, ((CompoundTree) classTree).getBlockEnd(), PreferencesFormatOptions.AFTER_CLASS_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitVariable(VariableTree variableTree, List<ReformatOption> optionsToReformat) {
        if (variableTree instanceof CompoundTree) {
            addOptionToList(optionsToReformat, ((CompoundTree) variableTree).getBlockStart(), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
            addOptionToList(optionsToReformat, ((CompoundTree) variableTree).getBlockEnd(), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitTry(TryTree tryTree, List<ReformatOption> optionsToReformat) {
        addLeftBraceToList(optionsToReformat, tryTree.getBlock(), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
        BlockTree finalBlock = tryTree.getFinallyBlock();
        List<? extends CatchTree> catches = tryTree.getCatches();

        if (finalBlock instanceof CompoundTree) {
            addLeftBraceToList(optionsToReformat, finalBlock, PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
            addRightBraceToList(optionsToReformat, (CompoundTree) finalBlock, PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        } else if (!catches.isEmpty()) {
            BlockTree catchBlock = catches.get(catches.size() - 1).getBlock();
            addRightBraceToList(optionsToReformat, (CompoundTree) catchBlock, PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        } else {
            addRightBraceToList(optionsToReformat, (CompoundTree) tryTree.getBlock(), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitCatch(CatchTree catchTree, List<ReformatOption> optionsToReformat) {
        addLeftBraceToList(optionsToReformat, catchTree.getBlock(), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);

        return null;
    }

    @Override
    public Void visitIf(IfTree ifTree, List<ReformatOption> optionsToReformat) {
        StatementTree thenStatement = ifTree.getThenStatement();
        if (thenStatement instanceof BlockTree) {
            addLeftBraceToList(optionsToReformat, ((BlockTree) thenStatement), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
        }

        StatementTree elseStatement = ifTree.getElseStatement();
        if (elseStatement instanceof EmptyStatementTree && thenStatement instanceof CompoundTree) {
            addRightBraceToList(optionsToReformat, ((CompoundTree) thenStatement), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        } else if (elseStatement instanceof BlockTree) {
            addLeftBraceToList(optionsToReformat, ((BlockTree) elseStatement), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitWhileLoop(WhileLoopTree whileLoopTree, List<ReformatOption> optionsToReformat) {
        StatementTree statement = whileLoopTree.getStatement();
        if (statement instanceof BlockTree) {
            addLeftBraceToList(optionsToReformat, ((BlockTree) statement), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
            addRightBraceToList(optionsToReformat, ((CompoundTree) statement), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitForLoop(ForLoopTree forLoopTree, List<ReformatOption> optionsToReformat) {
        StatementTree statement = forLoopTree.getStatement();
        if (statement instanceof BlockTree) {
            addLeftBraceToList(optionsToReformat, ((BlockTree) statement), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
            addRightBraceToList(optionsToReformat, ((CompoundTree) statement), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }

        return null;
    }

    @Override
    public Void visitDoWhileLoop(DoWhileLoopTree doWhileLoopTree, List<ReformatOption> optionsToReformat) {
        StatementTree statement = doWhileLoopTree.getStatement();
        if (statement instanceof BlockTree) {
            addLeftBraceToList(optionsToReformat, ((BlockTree) statement), PreferencesFormatOptions.BRACES_IN_OTHER_DECLARATION);
            addRightBraceToList(optionsToReformat, ((CompoundTree) doWhileLoopTree), PreferencesFormatOptions.AFTER_OTHER_DECLARATION);
        }

        return null;
    }

    /**
     * Adds the option to reformat a right brace token.
     *
     * @param optionsToReformat list of options and positions to reformat
     * @param body a block of code that contains the left brace token
     * @param optionToReformat the option to reformat the character
     */
    private void addRightBraceToList(List<ReformatOption> optionsToReformat, CompoundTree<?> body, final PreferencesFormatOptions optionToReformat) {
        Token rightBraceToken = TreeUtils.getLastToken(body);
        addOptionToList(optionsToReformat, rightBraceToken, optionToReformat);
    }

    /**
     * Adds the option to reformat a left brace token.
     *
     * @param optionsToReformat list of options and positions to reformat
     * @param body a block of code that contains the left brace token
     * @param optionToReformat the option to reformat the character
     */
    private void addLeftBraceToList(List<ReformatOption> optionsToReformat, BlockTree body, final PreferencesFormatOptions optionToReformat) {
        Token leftBraceToken = TreeUtils.getFirstToken(body);
        addOptionToList(optionsToReformat, leftBraceToken, optionToReformat);
    }

    /**
     * Adds the option to reformat from the position of a token.
     *
     * @param optionsToReformat list of options and positions to reformat
     * @param token the token to get the position
     * @param optionToReformat the option to reformat the character
     */
    private void addOptionToList(List<ReformatOption> optionsToReformat, Token token, final PreferencesFormatOptions optionToReformat) {
        if (token != null && !token.getImage().isEmpty()) {
            final int offset = NbDocument.findLineOffset(document, token.getBeginLine() - 1) + token.getBeginColumn() - 1;
            if (offset >= startOffset && offset <= endOffset) {
                optionsToReformat.add(new ReformatOption(optionToReformat, offset));
            }
        }
    }
}
