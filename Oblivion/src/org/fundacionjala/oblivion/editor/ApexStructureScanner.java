/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;

/**
 * Given a parse tree, scan its structure and produce a flat list of
 * structure items suitable for display in a navigator / outline / structure view
 * @author Marcelo Garnica
 */
public class ApexStructureScanner implements StructureScanner {
    
    public static final String CODE_FOLDS = "codeblocks";
    public static final Configuration SORTING_AND_FILTERING_CONFIGURATION = new Configuration(false, false);
    private final Map<String, List<OffsetRange>> foldsMap;

    public ApexStructureScanner() {
        foldsMap = new HashMap<>();
    }

    /**
     * Compute a list of structure items from the parse tree.
     */
    @Override
    public List<? extends StructureItem> scan(ParserResult pr) {
        return Collections.emptyList();
    }

    /**
     * Compute a list of foldable regions, named "codeblocks", "comments", "imports", "initial-comment".
     */
    @Override
    public Map<String, List<OffsetRange>> folds(ParserResult pr) {
        ApexParserResult apexParserResult = (ApexParserResult)pr;
        StyledDocument document = (StyledDocument) apexParserResult.getSnapshot().getSource().getDocument(true);
        List<OffsetRange> folds = new ArrayList<>();
        FoldTreeVisitor foldVisitor = new FoldTreeVisitor(document);
        apexParserResult.getCompilationUnit().accept(foldVisitor, folds);
        foldsMap.put(CODE_FOLDS, folds);
        return foldsMap;
    }

    /**
     * Return configuration information for this language. Used 
     * to provide default sorting and filtering options.
     */
    @Override
    public Configuration getConfiguration() {
        return SORTING_AND_FILTERING_CONFIGURATION;
    }

}
