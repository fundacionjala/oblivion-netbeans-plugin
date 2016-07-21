/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.finalModifier;

import com.sun.source.tree.VariableTree;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.swing.text.BadLocationException;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Test rules for final modifier within the method.
 *
 * @author sergio_daza
 */
public class TestFinalModifierWithinTheMethod extends AbstractTestGrammar {

    private static final String RESOURCE_FOLDER = "resources/%s";

    @Test
    public void testIsFinalModifier() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ApexParserResult parserResult = (ApexParserResult)getParserResult("IsFinalModifier.cls");
        List<VariableTree> findAllVariableTree = TreeNavigationUtils.findVariableTreeByName("myList",parserResult.getCompilationUnit());
        assertTrue(findAllVariableTree.get(0).getModifiers().getFlags().contains(Modifier.FINAL));
    }
    
    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
    
}
