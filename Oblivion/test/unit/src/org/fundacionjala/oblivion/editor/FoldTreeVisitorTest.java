/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor;

import org.fundacionjala.oblivion.editor.FoldTreeVisitor;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.junit.Test;
import org.netbeans.modules.csl.api.OffsetRange;

import static org.junit.Assert.*;

/**
 *
 * @author Marcelo Garnica
 */
public class FoldTreeVisitorTest extends AbstractTestGrammar {
    
    private static final String RESOURCE_FOLDER = "Resources/%s";

    @Test
    public void testFolds() throws FileNotFoundException, URISyntaxException, BadLocationException {       
        ApexLanguageParser.ApexParserResult apexParserResult = (ApexLanguageParser.ApexParserResult)getParserResult("ClassToFold.cls");
        StyledDocument document = (StyledDocument) apexParserResult.getSnapshot().getSource().getDocument(true);
        List<OffsetRange> folds = new ArrayList<>();
        FoldTreeVisitor foldVisitor = new FoldTreeVisitor(document);
        apexParserResult.getCompilationUnit().accept(foldVisitor, folds);
        assertFalse(folds.isEmpty());
        assertEquals(3, folds.size());
    }    

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
