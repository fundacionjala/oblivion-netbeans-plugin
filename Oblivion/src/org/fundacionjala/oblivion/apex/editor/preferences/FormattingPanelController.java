/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.preferences;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.api.editor.settings.SimpleValueNames;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;
import org.netbeans.modules.options.editor.spi.PreviewProvider;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.text.CloneableEditorSupport;

/**
 * Manages the logic for any Apex formatting panel.
 *
 * @author Amir Aranibar
 */
public final class FormattingPanelController implements ActionListener, DocumentListener, PreviewProvider, PreferencesCustomizer, MouseListener {

    private static final Logger LOG = Logger.getLogger(FormattingPanelController.class.getName());
    public static final String RIGHT_MARGIN = SimpleValueNames.TEXT_LIMIT_WIDTH;

    private final String id;
    private final ApexFormattingPanel panel;
    private JEditorPane previewPanel;
    private final List<JComponent> components;
    private final Preferences preferences;
    private final Preferences previewPreferences;
    private final String previewText;

    FormattingPanelController(String id, ApexFormattingPanel panel, Preferences preferences, String previewText) {
        this.id = id;
        this.panel = panel;
        this.preferences = preferences;
        this.previewText = previewText != null ? previewText : NbBundle.getMessage(FormattingPanelController.class, "SAMPLE_Default");
        this.panel.load(preferences);
        this.components = panel.getComponentsToListen();
        Preferences forcedPrefs = new PreviewPreferences();
        this.previewPreferences = new ProxyPreferences(preferences, forcedPrefs);
        addListeners();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return panel.getName();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyChange();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        notifyChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        notifyChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        notifyChange();
    }

    @Override
    public JComponent getPreviewComponent() {
        if (previewPanel == null) {
            previewPanel = new JEditorPane();
            previewPanel.getAccessibleContext().setAccessibleName(NbBundle.getMessage(FormatOptions.class, "AN_Preview"));
            previewPanel.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(FormatOptions.class, "AD_Preview"));
            previewPanel.putClientProperty("HighlightsLayerIncludes", "^org\\.netbeans\\.modules\\.editor\\.lib2\\.highlighting\\.SyntaxHighlighting$");
            previewPanel.setEditorKit(CloneableEditorSupport.getEditorKit(MimeType.APEX_CLASS_MIME_TYPE));
            previewPanel.setEditable(false);
        }

        return previewPanel;
    }

    @Override
    public void refreshPreview() {
        JEditorPane editorPane = (JEditorPane) getPreviewComponent();
        try {
            int rm = previewPreferences.getInt(RIGHT_MARGIN, 80);
            editorPane.putClientProperty("TextLimitLine", rm);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }

        editorPane.setIgnoreRepaint(true);
        Document document = editorPane.getDocument();
        String previewTextFormatted;
        previewTextFormatted = panel.getPreviewText(preferences);

        try {
            if (document.getLength() > 0) {
                document.remove(0, document.getLength());
            }
            document.insertString(0, previewTextFormatted, null);
            editorPane.setText(document.getText(0, document.getLength()));
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }

        editorPane.setIgnoreRepaint(false);
        editorPane.scrollRectToVisible(new Rectangle(0, 0, 10, 10));
        editorPane.repaint(100);
    }

    /**
     * Adds a listener for each component with options to reformat the code.
     *
     */
    private void addListeners() {
        for (JComponent component : components) {
            addListener(component);
        }
    }

    /**
     * Notifies if a component has changed the selected option.
     *
     */
    private void notifyChange() {
        panel.store(preferences);
        refreshPreview();
    }

    /**
     * Adds a listener to a JComponent that contains formatting options.
     *
     * @param jComponent the component to add a listener
     */
    private void addListener(JComponent jComponent) {
        if (jComponent instanceof JTextField) {
            JTextField textField = (JTextField) jComponent;
            textField.addActionListener(this);
            textField.getDocument().addDocumentListener(this);
        } else if (jComponent instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) jComponent;
            checkBox.addActionListener(this);
        } else if (jComponent instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) jComponent;
            comboBox.addActionListener(this);
        } else if (jComponent instanceof JButton) {
            JButton button = (JButton) jComponent;
            button.addMouseListener(this);
            
        } 
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        notifyChange();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * This class is used by Netbeans to create and show a formatting panel in
     * the editor options.
     *
     */
    public static final class Factory implements PreferencesCustomizer.Factory {

        private final String id;
        private final Class<? extends ApexFormattingPanel> panelClass;
        private final String previewText;

        Factory(String id, Class<? extends ApexFormattingPanel> panelClass, String previewText) {
            this.id = id;
            this.panelClass = panelClass;
            this.previewText = previewText;
        }

        @Override
        public PreferencesCustomizer create(Preferences preferences) {
            FormattingPanelController formattingPanelController = null;
            try {
                formattingPanelController = new FormattingPanelController(id, panelClass.newInstance(), preferences, previewText);
            } catch (InstantiationException | IllegalAccessException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }

            return formattingPanelController;
        }

    }
}
