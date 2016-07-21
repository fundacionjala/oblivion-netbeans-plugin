/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.trigger;

import org.fundacionjala.oblivion.apex.editor.declarationFinder.ApexDeclarationFinder;
import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.editor.ApexStructureScanner;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.parsing.spi.Parser;

/**
 * Class to register the custom language.
 *
 * The base implementation of the class follows the reference
 * @see  https://platform.netbeans.org/tutorials/nbm-javacc-lexer.html
 *
 * @author Marcelo Garnica
 */
@LanguageRegistration(mimeType = MimeType.APEX_TRIGGER_MIME_TYPE)
public class ApexTriggerLanguage extends DefaultLanguageConfig {

    private static final String APEX_TRIGGER_FILE_EXTENSION = "trigger";
    private final ApexDeclarationFinder apexDeclarationFinder = new ApexDeclarationFinder(MimeType.APEX_TRIGGER_MIME_TYPE);

    /**
     * Gets the language defined with the custom tokens
     * @return An instance of the defined language
     */
    @Override
    public Language<ApexTokenId> getLexerLanguage() {
        return ApexTokenId.getLanguage(MimeType.APEX_TRIGGER_MIME_TYPE);
    }
     
    @Override
    public String getPreferredExtension() {
        return APEX_TRIGGER_FILE_EXTENSION;
    }
    
    /**
     * Gets the the file type name association
     * @return A String that contains the file extension
     */
    @Override
    public String getDisplayName() {
        return APEX_TRIGGER_FILE_EXTENSION;
    }
    
    @Override
    public Parser getParser() {
        return new ApexLanguageParser();
    }
    
    @Override
    public String getLineCommentPrefix() {
        return "//";
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
    public DeclarationFinder getDeclarationFinder() {
        return apexDeclarationFinder;
    }

}
