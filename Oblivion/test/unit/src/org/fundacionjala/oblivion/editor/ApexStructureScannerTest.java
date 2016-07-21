/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor;

import org.fundacionjala.oblivion.editor.ApexStructureScanner;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.swing.text.BadLocationException;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.junit.Test;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 *
 * @author Marcelo Garnica
 */
public class ApexStructureScannerTest extends AbstractTestGrammar {
    
    private static final String RESOURCE_FOLDER = "Resources/%s";

    @Test
    public void testFolds() throws FileNotFoundException, URISyntaxException, BadLocationException {
        ParserResult result = getParserResult("ClassToFold.cls");
        ApexStructureScanner apexStructureScanner = new ApexStructureScanner();
        Map<String, List<OffsetRange>> foldsMap = apexStructureScanner.folds(result);
        assertFalse(foldsMap.isEmpty());
        List<OffsetRange> foldList = foldsMap.get(ApexStructureScanner.CODE_FOLDS);
        assertFalse(foldList.isEmpty());
        assertEquals(3, foldList.size());
    }    

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
