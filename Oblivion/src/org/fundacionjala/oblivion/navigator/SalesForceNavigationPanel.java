/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.navigator;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.fundacionjala.oblivion.apex.ast.tree.TreeNavigationUtils;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.util.Lookup;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.Line;
import org.openide.util.Exceptions;

/**
 * Class which define the navigation panel characteristic and functionality. This class extends form
 * ParserResultTask<ApexLanguageParser.ApexParserResult> to get Apex result and work with it.
 *
 * @author nelson_alcocer
 */
public class SalesForceNavigationPanel extends ParserResultTask<ApexLanguageParser.ApexParserResult> implements NavigatorPanel {

    private static SalesForceNavigationPanel panel;
    private static final int ERROR_PRIORITY = 100;
    private final JTree jTree;
    private CompilationUnitTree compilationTree;
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    private final JScrollPane jsp;
    private final DefaultTreeModel m_model = new DefaultTreeModel(root);
    private final NavBarMouseAdapter mouseAdapter;

    public SalesForceNavigationPanel() {
        jTree = new JTree(m_model);
        jTree.setRootVisible(false);
        jTree.setToggleClickCount(0);
        jTree.setCellRenderer(new TreeMemberCellRenderer());
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        jsp = new JScrollPane(jTree, v, h);
        mouseAdapter = new NavBarMouseAdapter();
    }

    @NavigatorPanel.Registration(mimeType = "text/x-cls", displayName = "Apex")
    public static SalesForceNavigationPanel getPanel() {
        if (panel == null) {
            panel = new SalesForceNavigationPanel();
        }
        return panel;
    }

    /**
     * Called when parser is finished. Get the relevant information from relevant Tree nodes to show on navigation
     * panel.
     *
     * @param apexResult The result of parsing
     * @param se
     */
    @Override
    public void run(ApexLanguageParser.ApexParserResult apexResult, SchedulerEvent se) {
        compilationTree = apexResult.getCompilationUnit();
        try {
            mouseAdapter.setDocument(apexResult.getSnapshot().getSource().getFileObject());
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        root.removeAllChildren();
        root = buildTreeStructure(compilationTree, root);
        m_model.nodeStructureChanged(root);
        expandAll(jTree);
    }

    /**
     * Expand all elements of a JTree.
     *
     * @param tree JTree to expand elements.
     */
    private void expandAll(JTree tree) {
        int row = 0;

        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }

    /**
     * Build Tree structure to show on navigation panel with respective hierarchy. This method add class children to
     * nodes.
     *
     * @param tree Tree node from extract child nodes.
     * @param node Node where add found child nodes.
     * @return DefaultMutableTreeNode which have added child class nodes.
     */
    private DefaultMutableTreeNode buildTreeStructure(Tree tree, DefaultMutableTreeNode node) {
        List<ClassTree> foundClasses = TreeNavigationUtils.findAllClasses(tree);

        if (tree instanceof CompilationUnitTree) {
            tree = null;
        }

        for (ClassTree foundClass : foundClasses) {
            if (TreeNavigationUtils.getParentNode(foundClass) == tree && foundClass.getSimpleName() != null) {
                String name = foundClass.getSimpleName().toString();
                DefaultMutableTreeNode childNode = new ClassMutableTreeNode(name, foundClass);
                childNode = addVariables(foundClass, childNode);
                childNode = addMethods(foundClass, childNode);
                childNode = buildTreeStructure(foundClass, childNode);
                node.add(childNode);
            }
        }

        arrangeNode(node);
        return node;
    }

    /**
     * Add methods children to a given JTree node. This method add method children to nodes.
     *
     * @param tree Tree node from extract child nodes.
     * @param node Node where add found child nodes.
     * @return DefaultMutableTreeNode which have added child method nodes.
     */
    private DefaultMutableTreeNode addMethods(ClassTree tree, DefaultMutableTreeNode node) {
        List<MethodTree> foundMethods = TreeNavigationUtils.findAllMethods(tree);

        for (MethodTree foundMethod : foundMethods) {
            if (tree.equals(TreeNavigationUtils.getParentNode(foundMethod)) || tree instanceof MethodTree) {
                String name = "";
                if (foundMethod.getName() != null) {
                    name = foundMethod.getName().toString();
                    DefaultMutableTreeNode childNode = new MethodMutableTreeNode(name, foundMethod);
                    node.add(childNode);
                }
            }
        }

        arrangeNode(node);
        return node;
    }

    /**
     * Add variable children to a given JTree node. This method add variable children to nodes.
     *
     * @param tree Tree node from extract child nodes.
     * @param node Node where add found child nodes.
     * @return DefaultMutableTreeNode which have added child variable nodes.
     */
    private DefaultMutableTreeNode addVariables(Tree tree, DefaultMutableTreeNode node) {
        List<VariableTree> foundVariables = TreeNavigationUtils.findAllVariableTree(tree);

        for (VariableTree foundVariable : foundVariables) {
            Tree parentTree = TreeNavigationUtils.getParentNode(foundVariable);
            if (tree.equals(parentTree)) {
                String name = foundVariable.getName().toString();
                DefaultMutableTreeNode childNode = new VariableMutableTreeNode(name, foundVariable);
                node.add(childNode);
            }
        }

        arrangeNode(node);
        return node;
    }

    /**
     * Start arrangingNode function. Arrange a node's children by line.
     *
     * @param node Node's children will be arranged.
     */
    void arrangeNode(DefaultMutableTreeNode node) {
        int count = node.getChildCount();
        arrangingNode(node, count);
    }

    /**
     * Recursive arranging node's children.
     *
     * @param node Node's children will be arranged.
     * @param count Number of node's children from get the child with the lowest line.
     */
    private void arrangingNode(DefaultMutableTreeNode node, int count) {
        int line = 0;
        DefaultMutableTreeNode fistByLine = null;

        for (int i = 0; i < count; i++) {
            ParserMutableTreeNode child = (ParserMutableTreeNode) node.getChildAt(i);
            if (line == 0 || line > child.getLine()) {
                line = child.getLine();
                fistByLine = child;
            }
        }

        if (fistByLine != null) {
            node.remove(fistByLine);
            node.add(fistByLine);
            count--;
            if (count >= 0) {
                arrangingNode(node, count);
            }
        }
    }

    /**
     * A priority. Less number wins
     *
     * @return int An integer with low priority
     */
    @Override
    public int getPriority() {
        return ERROR_PRIORITY;
    }

    /**
     * Returns an implementation of Scheduler. Reschedules all tasks when current document is changed (file opened,
     * closed, editor tab switched) and when text in the current document is changed.
     *
     * @return
     */
    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    /**
     * Called by infrastructure when the task was interrupted by the infrastructure.
     */
    @Override
    public void cancel() {
    }

    @Override
    public String getDisplayHint() {
        return "Members view";
    }

    @Override
    public String getDisplayName() {
        return "Members";
    }

    @Override
    public JComponent getComponent() {
        return jsp;
    }

    @Override
    public void panelActivated(Lookup context) {
        jTree.addMouseListener(mouseAdapter);
    }

    @Override
    public void panelDeactivated() {
        jTree.removeMouseListener(mouseAdapter);
    }

    @Override
    public Lookup getLookup() {
        return null;
    }

    /**
     * Class which define MouseAdapter to show line of selected element on navigation panel.
     *
     * @author nelson_alcocer
     */
    private class NavBarMouseAdapter extends MouseAdapter {

        private DataObject document;

        @Override
        public void mousePressed(MouseEvent e) {
            int selRow = jTree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
            if (selRow != -1) {
                if (e.getClickCount() == 2) {
                    ParserMutableTreeNode a = (ParserMutableTreeNode) selPath.getLastPathComponent();
                    goToLine(a.getLine());
                }
            }
        }

        public void setDocument(FileObject document) throws DataObjectNotFoundException {
            this.document = DataObject.find(document);
        }

        /**
         * Take the editor cursor to selected member line.
         */
        private void goToLine(int selPath) {
            EditorCookie ec = document.getLookup().lookup(EditorCookie.class);
            ec.getLineSet().getCurrent(selPath - 1).show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.NONE);
        }
    }
}
