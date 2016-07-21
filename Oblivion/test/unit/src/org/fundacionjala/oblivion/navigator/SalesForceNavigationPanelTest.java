/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import org.fundacionjala.oblivion.navigator.ParserMutableTreeNode;
import org.fundacionjala.oblivion.navigator.SalesForceNavigationPanel;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;
import org.fundacionjala.oblivion.apex.grammar.AbstractTestGrammar;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.junit.Test;
import org.netbeans.modules.csl.spi.ParserResult;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link SalesForceNavigationPanel} class.
 * 
 * @author nelson_alcocer
 */
public class SalesForceNavigationPanelTest extends AbstractTestGrammar {

    private final static String RESOURCE_FOLDER = "../apex/grammar/resources/navigationpanel/%s";

    private JTree getJTree(SalesForceNavigationPanel panel) {    
        JScrollPane scrollPane = (JScrollPane) panel.getComponent();
        JViewport viewport = scrollPane.getViewport();
        return (JTree) viewport.getView();
    }
    
    @Test
    public void testRunClassWithTwoMembers() throws FileNotFoundException, ParseException, URISyntaxException {
        ParserResult parser = getParserResult("ClassWithTwoMembers.cls");
        SalesForceNavigationPanel panel = new SalesForceNavigationPanel();
        panel.run((ApexLanguageParser.ApexParserResult) parser, null);
        JTree jTree = getJTree(panel);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        assertEquals(root.getChildCount(), 1);
        assertEquals(root.getChildAt(0).getChildCount(), 2);
    }
    
    @Test
    public void testRunClassWithManyMembers() throws FileNotFoundException, ParseException, URISyntaxException {
        ParserResult parser = getParserResult("ClassWithManyMembers.cls");
        SalesForceNavigationPanel panel = new SalesForceNavigationPanel();
        panel.run((ApexLanguageParser.ApexParserResult) parser, null);
        JTree jTree = getJTree(panel);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        assertEquals(1, root.getChildCount());
        assertEquals(12, root.getChildAt(0).getChildCount());
    }
    
    @Test
    public void testRunClassWithThreeLevels() throws FileNotFoundException, ParseException, URISyntaxException {
        ParserResult parser = getParserResult("ClassWithThreeLevels.cls");
        SalesForceNavigationPanel panel = new SalesForceNavigationPanel();
        panel.run((ApexLanguageParser.ApexParserResult) parser, null);
        JTree jTree = getJTree(panel);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        assertEquals(1, root.getChildCount());
        DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(0);
        assertEquals(1, child.getChildCount());
        child = (DefaultMutableTreeNode) child.getChildAt(0);
        assertEquals(1, child.getChildCount());
    }
    
    @Test
    public void testRunCheckJTreeNodesArrange() throws FileNotFoundException, ParseException, URISyntaxException {
        ParserResult parser = getParserResult("testArrange.cls");
        SalesForceNavigationPanel panel = new SalesForceNavigationPanel();
        panel.run((ApexLanguageParser.ApexParserResult) parser, null);
        JTree jTree = getJTree(panel);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        assertEquals(1, root.getChildCount());
        DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(0);
        int childCount = child.getChildCount();
        assertEquals(18, childCount);
        int lastLine = 0;
        for(int i = 0; i < 18; i++) {
            ParserMutableTreeNode node = (ParserMutableTreeNode) child.getChildAt(i);
            int currentLine = node.getLine();
            boolean isHigher = (currentLine > lastLine);
            assertEquals(true, isHigher);
            lastLine = currentLine;
        }
    }

    @Override
    protected String getResourceFolder() {
        return RESOURCE_FOLDER;
    }
}
