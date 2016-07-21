/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex;

import org.fundacionjala.oblivion.apex.editor.declarationFinder.ApexDeclarationFinder;
import org.fundacionjala.oblivion.apex.editor.ApexFormatter;
import org.fundacionjala.oblivion.apex.editor.autocomplete.AutoCompleteHandler;
import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.editor.ApexStructureScanner;
import org.fundacionjala.oblivion.editor.indexSearcher.SalesforceIndexSearcher;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.Formatter;
import org.netbeans.modules.csl.api.IndexSearcher;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.Parser;

/**
 * Class to register the custom language.
 *
 * The base implementation of the class follows the reference
 * @see  https://platform.netbeans.org/tutorials/nbm-javacc-lexer.html
 *
 * @author maria_garcia
 */
@LanguageRegistration(mimeType = MimeType.APEX_CLASS_MIME_TYPE)
public class ApexLanguage extends DefaultLanguageConfig {

    public static final String APEX_FILE_EXTENSION = "cls";
    public static final String LANGUAGE_NAME = "Apex";
    private final ApexDeclarationFinder apexDeclarationFinder = new ApexDeclarationFinder(MimeType.APEX_CLASS_MIME_TYPE);

    /**
     * Gets the language defined with the custom tokens
     * @return An instance of the defined language
     */
    @Override
    public Language<ApexTokenId> getLexerLanguage() {
        return ApexTokenId.getLanguage(MimeType.APEX_CLASS_MIME_TYPE);
    }

    @Override
    public String getPreferredExtension() {
        return APEX_FILE_EXTENSION;
    }
    
    /**
     * Gets the the file type name association
     * @return A String that contains the file extension
     */
    @Override
    public String getDisplayName() {
        return LANGUAGE_NAME;
    }

    @Override
    public Parser getParser() {
        return new ApexLanguageParser();
    }
    
    @Override
    public boolean hasStructureScanner() {
        return true;
    }
    
    @Override
    public StructureScanner getStructureScanner() {
        return new ApexStructureScanner();
    }
    
    @Override
    public CodeCompletionHandler getCompletionHandler() {
        return new AutoCompleteHandler();
    }
    
    @Override
    public String getLineCommentPrefix() {
        return "//";
    }
    
    @Override
    public IndexSearcher getIndexSearcher() {
        return new SalesforceIndexSearcher();
    }

    @Override
    public Formatter getFormatter() {
        return new ApexFormatter();
    }

    @Override
    public boolean hasFormatter() {
        return true;
    }
    
    @Override
    public DeclarationFinder getDeclarationFinder() {
        return apexDeclarationFinder;
    }
}
