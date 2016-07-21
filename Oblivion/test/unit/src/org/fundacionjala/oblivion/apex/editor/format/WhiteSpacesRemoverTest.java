/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.format;

import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.editor.AbstractTestFormat;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 *
 * @author Amir Aranibar
 */
public class WhiteSpacesRemoverTest extends AbstractTestFormat {

    private final static String RESOURCE_FOLDER = "resources/%s";
    private final int startOffset = 0;

    @Test
    public void testRemoveWhiteSpaces() throws Exception {
        String expectedValue = "TestClass {\n";
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        String contentWithoutWhiteSpaces = content.replaceAll("\\s", "");
        Document document = buildDocument(content);
        int position = 2;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeWhiteSpaces(position);

        String result = document.getText(0, document.getLength());
        String resultWithoutWhiteSpaces = result.replaceAll("\\s", "");

        assertThat(content, not(result));
        assertTrue(result.contains(expectedValue));
        assertTrue(content.length() > result.length());
        assertEquals(contentWithoutWhiteSpaces, resultWithoutWhiteSpaces);
    }

    @Test
    public void testDoNotRemoveWhiteSpaces() throws Exception {
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        Document document = buildDocument(content);
        int position = 60;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeWhiteSpaces(position);

        String result = document.getText(0, document.getLength());

        assertEquals(content, result);
    }

    @Test
    public void testRemoveWhiteSpacesBetweenCharacters() throws Exception {
        String expectedValue = "private static final Integer";
        String content = getTextFromFile("ClassWithWhiteSpacesBetweenCharacters.cls");
        String contentWithoutWhiteSpaces = content.replaceAll("\\s", "");
        Document document = buildDocument(content);
        int position = 30;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeWhiteSpaces(position);

        String result = document.getText(0, document.getLength());
        String resultWithoutWhiteSpaces = result.replaceAll("\\s", "");

        assertThat(content, not(result));
        assertTrue(result.contains(expectedValue));
        assertTrue(content.length() > result.length());
        assertEquals(contentWithoutWhiteSpaces, resultWithoutWhiteSpaces);
    }

    @Test
    public void testDoNotRemoveWhiteSpacesBetweenCharacters() throws Exception {
        String content = getTextFromFile("ClassWithWhiteSpacesBetweenCharacters.cls");
        Document document = buildDocument(content);
        int position = 1;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeWhiteSpaces(position);

        String result = document.getText(0, document.getLength());

        assertEquals(content, result);
    }

    @Test
    public void testRemoveBlankLines() throws Exception {
        String expectedValue = "MY_INT;\n\n}";
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        Document document = buildDocument(content);
        int position = 96;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeBlankLines(position);

        String result = document.getText(0, document.getLength());

        assertThat(content, not(result));
        assertTrue(result.contains(expectedValue));
        assertTrue(content.length() > result.length());
    }

    @Test
    public void testDoNotRemoveBlankLines() throws Exception {
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        Document document = buildDocument(content);
        int position = 95;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeBlankLines(position);

        String result = document.getText(0, document.getLength());

        assertEquals(content, result);
    }

    @Test
    public void testIsBlankLine() throws Exception {
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        Document document = buildDocument(content);
        int position = 96;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeBlankLines(position);

        boolean result = whiteSpacesRemover.isBlankLine(position);

        assertTrue(result);
    }

    @Test
    public void testIsNotBlankLine() throws Exception {
        String content = getTextFromFile("ClassWithWhiteSpaces.cls");
        Document document = buildDocument(content);
        int position = 0;

        WhiteSpacesRemover whiteSpacesRemover = new WhiteSpacesRemover(document, startOffset);
        whiteSpacesRemover.removeBlankLines(position);

        boolean result = whiteSpacesRemover.isBlankLine(position);

        assertFalse(result);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
