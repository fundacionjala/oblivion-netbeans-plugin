/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.editor.codeGenerators;

import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.codegen.CodeGenerator;
import org.openide.util.Lookup;

/**
 * This class implements CodeGenerator.Factory 
 * 
 * @author sergio_daza
 */
@MimeRegistration(mimeType = MimeType.APEX_CLASS_MIME_TYPE, service = CodeGenerator.Factory.class)
    public class Factory implements CodeGenerator.Factory {

        @Override
        public List<? extends CodeGenerator> create(Lookup context) {
            List<CodeGenerator> codeGenerators = new ArrayList<>();
            codeGenerators.add(new ClassGenerator(context));
            codeGenerators.add(new TestClassGenerator(context));
            return codeGenerators;
        }
  
    }
