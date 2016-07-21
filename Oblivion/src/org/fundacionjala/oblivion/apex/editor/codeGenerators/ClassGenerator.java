/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.codeGenerators;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import org.netbeans.spi.editor.codegen.CodeGenerator;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

@NbBundle.Messages({"CodeGenerator_class=Class"})

/**
 * Generate code for salesforce's class
 *
 * @author sergio_daza
 */
public class ClassGenerator implements CodeGenerator {

    JTextComponent textComp;

    ClassGenerator(Lookup context) {
        textComp = context.lookup(JTextComponent.class);
    }

    @Override
    public String getDisplayName() {
        return Bundle.CodeGenerator_class();
    }

    @Override
    public void invoke() {
        try {
            Caret caret = textComp.getCaret();
            int dot = caret.getDot();
            String nameClass = "newClass";
            String selectedText = (String) textComp.getSelectedText();
            if (selectedText != null) {
                nameClass = selectedText;
            }
            String stringCode = "public with sharing class " + nameClass + " {"
                    + "\n\n"
                    + "}";
            if (selectedText != null) {
                textComp.replaceSelection(stringCode);
            } else {
                textComp.getDocument().insertString(dot, stringCode, null);
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
} 
