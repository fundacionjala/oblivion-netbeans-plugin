/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.ui.wizard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Represents a wizard descriptor panel, stores the information of a wizard panel.
 * @author Marcelo Garnica
 */
public abstract class AbstractWizardDescriptor implements WizardDescriptor.Panel<WizardDescriptor>, 
                                                          WizardDescriptor.FinishablePanel<WizardDescriptor>, 
                                                          DocumentListener, ChangeListener {
    
    protected AbstractVisualPanel component;
    protected WizardDescriptor wizardDescriptor;
    
    private final Set<ChangeListener> listeners = new HashSet<>(1);    
    
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public AbstractVisualPanel getComponent() {
        if (component == null) {
            component = createVisualComponent();
            component.addDocumentListener(this);
            component.addChangeListener(this);
        }
        return component;
    }
    
    protected abstract AbstractVisualPanel createVisualComponent();

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        return component.isValid(wizardDescriptor);
    }
    
    @Override
    public boolean isFinishPanel() {
        return isValid();
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    
    @Override
    public void readSettings(WizardDescriptor wiz) {
        wizardDescriptor = wiz;
        getComponent().initProperties(wizardDescriptor);
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        change();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        change();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        change();
    }
    
    public final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
           it.next().stateChanged(ev);
        }
   }

    private void change() {
        if (isValid()) {
            fireChangeEvent();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        change();
    }
}
