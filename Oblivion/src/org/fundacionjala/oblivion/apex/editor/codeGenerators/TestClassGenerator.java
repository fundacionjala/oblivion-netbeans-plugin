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

@NbBundle.Messages({"CodeGenerator_testClass=Test Class"})

/**
 * Generate code for salesforce's test class
 *
 * @author sergio_daza
 */
public class TestClassGenerator implements CodeGenerator {

    JTextComponent textComp;

    public TestClassGenerator(Lookup context) {
        textComp = context.lookup(JTextComponent.class);
    }

    @Override
    public String getDisplayName() {
        return Bundle.CodeGenerator_testClass();
    }

    @Override
    public void invoke() {
        try {
            Caret caret = textComp.getCaret();
            int dot = caret.getDot();
            String stringCode = "@isTest\n"
                    + "private class UnitTest1 {\n"
                    + "    static testMethod void testSomething(){\n"
                    + "    }\n"
                    + "}";
            textComp.getDocument().insertString(dot, stringCode, null);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
