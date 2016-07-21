/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.ui.wizard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;

/**
 * Represents the UI component of a wizard panel.
 * @author Marcelo Garnica
 */
public abstract class AbstractVisualPanel extends JPanel {

    protected abstract void addDocumentListener(DocumentListener documentListener);

    protected abstract void addChangeListener(ChangeListener changeListener);

    protected abstract boolean isValid(WizardDescriptor wizardDescriptor);

    public void initProperties(WizardDescriptor wizardDescriptor) {
        
    }
    
    /**
     * This method checks if a word contain characteres invalid.
     * 
     * @param input is the text introduced in the form
     * @return a boolean, true if not contain characters invalid 
     */
    public boolean ifValidName(String input) {
        Pattern pattern = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
}
