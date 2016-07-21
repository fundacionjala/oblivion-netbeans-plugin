/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import javax.lang.model.element.Name;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParserConstants;
import org.fundacionjala.oblivion.apex.Token;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link TreeUtils} class.
 *
 * @author Maria Garcia
 */
public class TreeUtilsTest {

    @Test
    public void createNameFromBlankString() {
        Name blankName = TreeUtils.createNameFromString("");
        assertEquals("", blankName.toString());
    }

    @Test
    public void createNameFromValidString() {
        String identifier = "Class.someAtributte";
        Name name = TreeUtils.createNameFromString(identifier);
        assertEquals(identifier, name.toString());
    }

    @Test
    public void createNameFromIdentifiersListWithSimpleNames() {
        List<ExpressionTree> identifiers = new ArrayList<>();
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "name1"));
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "name2"));

        Name name = TreeUtils.createName(identifiers);
        assertEquals("name1.name2", name.toString());
    }

    @Test
    public void createNameFromIdentifiersListWithCharactersInNames() {
        List<ExpressionTree> identifiers = new ArrayList<>();
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "$name_1"));
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "$name_2"));

        Name name = TreeUtils.createName(identifiers);
        assertEquals("$name_1.$name_2", name.toString());
    }

    @Test
    public void createNameFromIdentifiersListWithDotsInNames() {
        List<ExpressionTree> identifiers = new ArrayList<>();
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "name.1"));
        identifiers.add(createIdentifierTree(ApexParserConstants.IDENTIFIER, "name.2"));

        Name name = TreeUtils.createName(identifiers);
        assertEquals("name.1.name.2", name.toString());
    }

    @Test
    public void updateParentReference() {
        String word = "Class";
        Name name = TreeUtils.createNameFromString(word);
        IdentifierTree identifier = new IdentifierTreeImpl((org.fundacionjala.oblivion.apex.ast.tree.Name)name);

        word = "other";
        name = TreeUtils.createNameFromString(word);
        IdentifierTreeImpl other = new IdentifierTreeImpl((org.fundacionjala.oblivion.apex.ast.tree.Name)name);

        TreeUtils.updateParentReference(identifier, Collections.singletonList(other));

        assertEquals(identifier, other.getParent());
    }

    @Test
    public void updateParentWithNullReference() {
        IdentifierTree identifier = null;

        String word = "other";
        Name name = TreeUtils.createNameFromString(word);
        IdentifierTreeImpl other = new IdentifierTreeImpl((org.fundacionjala.oblivion.apex.ast.tree.Name)name);

        TreeUtils.updateParentReference(identifier, Collections.singletonList(other));

        assertEquals(identifier, other.getParent());
    }

    public IdentifierTree createIdentifierTree(final int id, final String value) {
        Token token = new Token() {

            @Override
            public int getId() {
                return id;
            }

            @Override
            public String getImage() {
                return value;
            }

            @Override
            public int getBeginLine() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getBeginColumn() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getEndLine() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getEndColumn() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        return new IdentifierTreeImpl(token);
    }
}
