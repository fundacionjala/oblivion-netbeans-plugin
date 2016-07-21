/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor;

import java.net.URISyntaxException;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.editor.order.DummyDocumentProcessor;
import org.fundacionjala.oblivion.apex.editor.order.DummyPreferencesLoader;
import org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier;
import org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.junit.Test;
import org.netbeans.modules.csl.spi.ParserResult;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link ApexMembersOrganizer} class.
 *
 * @author Amir Aranibar
 */
public class ApexMembersOrganizerTest extends AbstractTestFormat {

    private final static String RESOURCE_FOLDER = "order/resources/%s";
    private final static int START_POSITION_FIRST_ELEMENT = 41;

    @Test
    public void testOrganizeMembersByVisibilityAndAlphabetically() throws BadLocationException, URISyntaxException {
        ParserResult parserResult = getParserResult("OrganizeMembers_1.cls");
        ClassMemberType[] classMemberTypes = ClassMemberType.values();
        AccessModifier[] accessModifiers = AccessModifier.values();
        DummyPreferencesLoader preferencesLoader = new DummyPreferencesLoader(classMemberTypes, accessModifiers, true, true);
        DummyDocumentProcessor documentProcessor = new DummyDocumentProcessor((ApexLanguageParser.ApexParserResult) parserResult);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setDocument(getDocument(parserResult));

        ApexMembersOrganizer membersOrganizer = new ApexMembersOrganizer(preferencesLoader, documentProcessor);
        membersOrganizer.actionPerformed(null, editorPane);

        String expectedOrder = String.format("public Integer a = 1;\n    public Integer b = 2;");
        String actualOrder = editorPane.getDocument().getText(START_POSITION_FIRST_ELEMENT, 47);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testOrganizeMembersFieldWithPublicModifierFirst() throws BadLocationException, URISyntaxException {
        ParserResult parserResult = getParserResult("OrganizeMembers_2.cls");
        ClassMemberType[] classMemberTypes = ClassMemberType.values();
        classMemberTypes[0] = ClassMemberType.FIELD;
        classMemberTypes[3] = ClassMemberType.STATIC_FIELD;
        AccessModifier[] accessModifiers = AccessModifier.values();
        DummyPreferencesLoader preferencesLoader = new DummyPreferencesLoader(classMemberTypes, accessModifiers, true, true);
        DummyDocumentProcessor documentProcessor = new DummyDocumentProcessor((ApexLanguageParser.ApexParserResult) parserResult);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setDocument(getDocument(parserResult));

        ApexMembersOrganizer membersOrganizer = new ApexMembersOrganizer(preferencesLoader, documentProcessor);
        membersOrganizer.actionPerformed(null, editorPane);

        String expectedOrder = String.format("public Integer a = 1;");
        String actualOrder = editorPane.getDocument().getText(START_POSITION_FIRST_ELEMENT, 21);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testOrganizeMembersMethodWithProtectedModifierFirst() throws BadLocationException, URISyntaxException {
        ParserResult parserResult = getParserResult("OrganizeMembers_3.cls");
        ClassMemberType[] classMemberTypes = ClassMemberType.values();
        classMemberTypes[0] = ClassMemberType.METHOD;
        classMemberTypes[6] = ClassMemberType.STATIC_FIELD;
        AccessModifier[] accessModifiers = AccessModifier.values();
        accessModifiers[1] = AccessModifier.PROTECTED;
        accessModifiers[3] = AccessModifier.PUBLIC;
        DummyPreferencesLoader preferencesLoader = new DummyPreferencesLoader(classMemberTypes, accessModifiers, true, true);
        DummyDocumentProcessor documentProcessor = new DummyDocumentProcessor((ApexLanguageParser.ApexParserResult) parserResult);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setDocument(getDocument(parserResult));

        ApexMembersOrganizer membersOrganizer = new ApexMembersOrganizer(preferencesLoader, documentProcessor);
        membersOrganizer.actionPerformed(null, editorPane);

        String expectedOrder = String.format("protected void c(){}");
        String actualOrder = editorPane.getDocument().getText(START_POSITION_FIRST_ELEMENT, 20);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testOrganizeMembersClassWithPrivateModifierFirst() throws BadLocationException, URISyntaxException {
        ParserResult parserResult = getParserResult("OrganizeMembers_4.cls");
        ClassMemberType[] classMemberTypes = ClassMemberType.values();
        classMemberTypes[0] = ClassMemberType.CLASS;
        classMemberTypes[8] = ClassMemberType.STATIC_FIELD;
        AccessModifier[] accessModifiers = AccessModifier.values();
        accessModifiers[0] = AccessModifier.PRIVATE;
        accessModifiers[1] = AccessModifier.PUBLIC;
        DummyPreferencesLoader preferencesLoader = new DummyPreferencesLoader(classMemberTypes, accessModifiers, true, true);
        DummyDocumentProcessor documentProcessor = new DummyDocumentProcessor((ApexLanguageParser.ApexParserResult) parserResult);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setDocument(getDocument(parserResult));

        ApexMembersOrganizer membersOrganizer = new ApexMembersOrganizer(preferencesLoader, documentProcessor);
        membersOrganizer.actionPerformed(null, editorPane);

        String expectedOrder = String.format("private class C{}");
        String actualOrder = editorPane.getDocument().getText(START_POSITION_FIRST_ELEMENT, 17);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testOrganizeMembersConstructorWithDefaultModifierFirst() throws BadLocationException, URISyntaxException {
        ParserResult parserResult = getParserResult("OrganizeMembers_5.cls");
        ClassMemberType[] classMemberTypes = ClassMemberType.values();
        classMemberTypes[0] = ClassMemberType.CONSTRUCTOR;
        classMemberTypes[5] = ClassMemberType.STATIC_FIELD;
        AccessModifier[] accessModifiers = AccessModifier.values();
        accessModifiers[0] = AccessModifier.DEFAULT;
        accessModifiers[3] = AccessModifier.PUBLIC;
        DummyPreferencesLoader preferencesLoader = new DummyPreferencesLoader(classMemberTypes, accessModifiers, true, true);
        DummyDocumentProcessor documentProcessor = new DummyDocumentProcessor((ApexLanguageParser.ApexParserResult) parserResult);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setDocument(getDocument(parserResult));

        ApexMembersOrganizer membersOrganizer = new ApexMembersOrganizer(preferencesLoader, documentProcessor);
        membersOrganizer.actionPerformed(null, editorPane);

        String expectedOrder = String.format("VariablesClassMembers(){}");
        String actualOrder = editorPane.getDocument().getText(START_POSITION_FIRST_ELEMENT, 25);

        assertEquals(expectedOrder, actualOrder);
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }

    private Document getDocument(ParserResult parserResult) {
        return parserResult.getSnapshot().getSource().getDocument(true);
    }
}
