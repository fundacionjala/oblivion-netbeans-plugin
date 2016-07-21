/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.editor.AbstractTestFormat;
import org.fundacionjala.oblivion.apex.editor.preferences.PreferencesFormatOptions;
import org.fundacionjala.oblivion.apex.editor.preferences.BracesValueOptions;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Amir Aranibar
 */
public class BracesFormatterTest extends AbstractTestFormat {

    private final static String RESOURCE_FOLDER = "resources/%s";

    @Test
    public void testReformatLeftBraceNewLine() throws BadLocationException, IOException {
        String expectedValue = "class TestClass\n{\n";
        String optionValue = BracesValueOptions.NEW_LINE;
        int position = 23;

        testReformatBraces(expectedValue, optionValue, position);
    }

    @Test
    public void testReformatLeftBraceSameLine() throws BadLocationException, IOException {
        String expectedValue = "class TestClass {\n";
        String optionValue = BracesValueOptions.SAME_LINE;
        int position = 23;

        testReformatBraces(expectedValue, optionValue, position);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private void testReformatBraces(String expectedValue, String optionValue, int position) throws BadLocationException, IOException {
        String content = getTextFromFile("ClassWithLeftBracesInANewLine.cls");
        String contentWithoutNewLines = content.replaceAll("\\s+", " ");
        StyledDocument document = buildDocument(content);
        DummyFormatOptions formatOptions = new DummyFormatOptions();
        formatOptions.setOptionValue(null, null, optionValue);

        BracesFormatter bracesFormatter = new BracesFormatter(document, null, formatOptions);
        bracesFormatter.reformatLeftBraces(PreferencesFormatOptions.BRACES_IN_CLASS_DECLARATION, position);

        String result = document.getText(0, document.getLength());
        String resultWithoutNewLines = result.replaceAll("\\s+", " ");

        assertThat(content, not(result));
        Assert.assertTrue(result.contains(expectedValue));
        assertEquals(contentWithoutNewLines, resultWithoutNewLines);
    }
}
