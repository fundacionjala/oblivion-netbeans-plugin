/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import org.openide.util.Exceptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.fundacionjala.oblivion.apex.actions.RunRelatedUnitTestAction.DESTINATION_DEFAULT_VALUE;
import org.fundacionjala.oblivion.gradle.GradleTaskExecutor;
import org.fundacionjala.oblivion.tab.testReport.TestResultItem.Status;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.Line;
import org.openide.util.NbBundle;

@NbBundle.Messages({
    "TestReport_CaptionTest=Test",
    "TestReport_CaptionPassed=Passed",
    "TestReport_CaptionFailed=Failed",
    "TestReport_CaptionResult=Result",
    "TestReport_OptionRunAgain=Run again",
    "TestReport_OptionOpenReport=Open Report",
    "TestReport_OptionRemoveTest=Remove test",
    "TestReport_OptionRemoveAllTest=Remove all test",
    "TestReport_OptionGoToSource=Go to source",
    "TestReport_ErrorFailedToDeleteFile=Failed to delete file: "
})

/**
 * This class is for build the panel that it will load on the tab from the
 * JTabbedPane of the window of Salesforce Test Result.
 *
 * @author sergio_daza
 */
public final class PanelOfTestReport implements ActionListener {

    private static final Logger LOG = Logger.getLogger(PanelOfTestReport.class.getName());
    private static final String ICON_REMOVE_ALL = "/org/fundacionjala/oblivion/tab/testReport/icons/removeAll.png";
    private static final String ICON_REMOVE = "/org/fundacionjala/oblivion/tab/testReport/icons/remove.png";
    private static final String ICON_PLAY = "/org/fundacionjala/oblivion/tab/testReport/icons/play.png";
    private static final String ICON_URL = "/org/fundacionjala/oblivion/tab/testReport/icons/url.png";
    private static final String CLASS_FOLDER_PATH = "/src/classes/";
    private static final String TEST_FOLDER_PATH = "/src/test/";
    private static final String FILE_EXTENSION = ".cls";
    private static final String EMPTY_TEXT = "";
    private final SortedMap testResulList = new TreeMap();
    private final SortedMap tableRows = new TreeMap();
    private final String pathProject;

    public PanelOfTestReport(String pathProject) {
        this.pathProject = pathProject;
        initComponents();
        table.setDefaultRenderer(javax.swing.JLabel.class, new Renderer());
        table.getColumnModel().getColumn(1).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(75);
        table.getColumnModel().getColumn(2).setMinWidth(70);
        table.getColumnModel().getColumn(3).setMaxWidth(200);
        table.getColumnModel().getColumn(3).setMinWidth(180);
        table.repaint();
        load();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        buttonRunAgain.setToolTipText(Bundle.TestReport_OptionRunAgain());
        buttonRunAgain.setEnabled(false);
        buttonOpenBrowser.setToolTipText(Bundle.TestReport_OptionOpenReport());
        buttonOpenBrowser.setEnabled(false);
        buttonRemoveTest.setToolTipText(Bundle.TestReport_OptionRemoveTest());
        buttonRemoveTest.setEnabled(false);
        buttonRemoveAll.setToolTipText(Bundle.TestReport_OptionRemoveAllTest());
        buttonRunAgain.setIcon(new javax.swing.ImageIcon(getClass().getResource(ICON_PLAY)));
        org.openide.awt.Mnemonics.setLocalizedText(buttonRemoveTest, EMPTY_TEXT);
        buttonRunAgain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonRunAgain.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                buttonReloadActionPerformed(actionEvent);
            }
        });

        buttonOpenBrowser.setIcon(new javax.swing.ImageIcon(getClass().getResource(ICON_URL)));
        org.openide.awt.Mnemonics.setLocalizedText(buttonOpenBrowser, EMPTY_TEXT);
        buttonOpenBrowser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonOpenBrowser.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                buttonOpenBrowserActionPerformed(actionEvent);
            }
        });

        buttonRemoveTest.setIcon(new javax.swing.ImageIcon(getClass().getResource(ICON_REMOVE)));
        org.openide.awt.Mnemonics.setLocalizedText(buttonRemoveTest, EMPTY_TEXT);
        buttonRemoveTest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonRemoveTest.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                buttonremoveTestActionPerformed(actionEvent);
            }
        });

        buttonRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource(ICON_REMOVE_ALL)));
        org.openide.awt.Mnemonics.setLocalizedText(buttonRemoveAll, EMPTY_TEXT);
        buttonRemoveAll.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                buttonremoveAllActionPerformed(actionEvent);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(buttonRemoveAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonRemoveTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonOpenBrowser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(buttonRunAgain))
                        .addGap(0, 622, Short.MAX_VALUE))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)))
        );
        panelLayout.setVerticalGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonRunAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonOpenBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(buttonRemoveTest, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRemoveAll)
                        .addContainerGap(128, Short.MAX_VALUE))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addGap(0, 0, 0)))
        );

        table.setModel(new ModelTestReport());

        jScrollPane1.setViewportView(table);

        table.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                enableButtons(true);
            }
        });

        table.addKeyListener(new java.awt.event.KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                enableButtons(true);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                javax.swing.JTable jTable = (javax.swing.JTable) me.getSource();
                Point p = me.getPoint();
                int row = jTable.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    if (table.getSelectedColumn() == 3) {
                        int[] selectedRows = table.getSelectedRows();
                        openReport(selectedRows);
                    } else {
                        goToFile(table.getSelectedRows());
                    }
                }
            }
        });

        menuItemRunAgain.addActionListener(this);
        menuItemOpenReport.addActionListener(this);
        menuItemGoToFile.addActionListener(this);
        menuItemRemoveTest.addActionListener(this);
        menuItemRemoveAllTest.addActionListener(this);
        table.setComponentPopupMenu(jPopupMenu);

    }

    private final javax.swing.JTable table = new javax.swing.JTable();
    private final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    private final javax.swing.JPopupMenu jPopupMenu = new javax.swing.JPopupMenu();
    private final javax.swing.JMenuItem menuItemRunAgain = jPopupMenu.add(new javax.swing.JMenuItem(Bundle.TestReport_OptionRunAgain()));
    private final javax.swing.JMenuItem menuItemOpenReport = jPopupMenu.add(new javax.swing.JMenuItem(Bundle.TestReport_OptionOpenReport()));
    private final javax.swing.JMenuItem menuItemGoToFile = jPopupMenu.add(new javax.swing.JMenuItem(Bundle.TestReport_OptionGoToSource()));
    private final javax.swing.JMenuItem menuItemRemoveTest = jPopupMenu.add(new javax.swing.JMenuItem(Bundle.TestReport_OptionRemoveTest()));
    private final javax.swing.JMenuItem menuItemRemoveAllTest = jPopupMenu.add(new javax.swing.JMenuItem(Bundle.TestReport_OptionRemoveAllTest()));
    private final javax.swing.JPanel panel = new javax.swing.JPanel();
    private final javax.swing.JButton buttonRunAgain = new javax.swing.JButton();
    private final javax.swing.JButton buttonOpenBrowser = new javax.swing.JButton();
    private final javax.swing.JButton buttonRemoveTest = new javax.swing.JButton();
    private final javax.swing.JButton buttonRemoveAll = new javax.swing.JButton();

    /**
     * This button executes the action of open browser.
     *
     * @param actionEvent
     */
    private void buttonOpenBrowserActionPerformed(java.awt.event.ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();
        openReport(selectedRows);
    }

    /**
     * This button executes the action of remove all tests.
     *
     * @param actionEvent
     */
    private void buttonremoveAllActionPerformed(ActionEvent actionEvent) {
        removeAllTest();
    }

    /**
     * This button executes the action of remove one test or all tests selected
     *
     * @param actionEvent
     */
    private void buttonremoveTestActionPerformed(ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();
        removeTest(selectedRows);
    }

    /**
     * This method execute the test again
     * 
     * @param actionEvent 
     */
    private void buttonReloadActionPerformed(ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();
        reload(selectedRows);

    }

    /**
     * This method is for load the tests it was executed on before the current
     * session.
     */
    public void load() {
        String pathFolderResult = pathProject + DESTINATION_DEFAULT_VALUE;
        File dir = new File(pathFolderResult);
        if (dir.isDirectory()) {
            File[] folders = dir.listFiles();
            for (File folder : folders) {
                if (folder.isDirectory()) {
                    addTestReport(TestResultTopComponent.buildItem(getPathFile(folder.getName(), pathProject), folder.getPath(), Status.successful));
                }
            }
            showTable();
        }
    }

    /**
     * Enable the buttons when one or more test is selected.
     *
     * @param value
     */
    private void enableButtons(boolean value) {
        buttonRunAgain.setEnabled(value);
        buttonOpenBrowser.setEnabled(value);
        buttonRemoveTest.setEnabled(value);
    }

    /**
     * This method return the panel on the it will show the tests.
     *
     * @return
     */
    public javax.swing.JPanel getPanel() {
        return panel;
    }

    /**
     * This method add a new item in test result list
     *
     * @param nameProject
     * @param testResutlItem
     */
    public void addTestReport(TestResultItem item) {
        testResulList.put(item.getTestName(), item);
    }

    /**
     * This method shows the tests on the table.
     */
    public void showTable() {
        try {
            tableRows.clear();
            enableButtons(false);
            int countRows = 0;
            javax.swing.table.DefaultTableModel modelTable = clear();
            for (Iterator iterator = testResulList.keySet().iterator(); iterator.hasNext();) {
                String test = (String) iterator.next();
                tableRows.put(countRows, test);
                TestResultItem testResultItems = (TestResultItem) testResulList.get(test);
                Object[] newTag = new Object[4];
                newTag[0] = testResultItems.getTestName();
                newTag[1] = testResultItems.getSuccessToString();
                newTag[2] = testResultItems.getErrorsToString();
                newTag[3] = new javax.swing.JLabel(testResultItems.getStatus().name());
                countRows++;
                modelTable.addRow(newTag);
            }
            table.setModel(modelTable);
            table.setDefaultRenderer(javax.swing.JLabel.class, new Renderer());
            table.repaint();
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.INFO, e.toString());
        }
    }

    /**
     * It remove the table rows
     *
     * @return a DefaultTableModel empty.
     */
    private javax.swing.table.DefaultTableModel clear() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        return model;
    }

    /**
     * This method open the url on the browser.
     *
     * @param url
     */
    private void openUrl(URL url) {
        try {
            URI uri = url.toURI();
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(uri);
            }
        } catch (URISyntaxException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method executes the test again.
     *
     * @param selectedRows
     */
    private void reload(int[] selectedRows) {
        for (int selectedRow : selectedRows) {
            String nameTest = (String) tableRows.get(selectedRow);
            TestResultItem item = (TestResultItem) testResulList.get(nameTest);
            if (item.getStatus() != TestResultItem.Status.running) {
                GradleTaskExecutor.execute(TestResultTopComponent.run.buildTask(item.getProjectPath(), item.getFilePath()));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();
        javax.swing.JMenuItem menu = (javax.swing.JMenuItem) e.getSource();
        if (menu == menuItemRemoveAllTest) {
            removeAllTest();
        } else if (selectedRows.length > 0) {
            if (menu == menuItemGoToFile) {
                goToFile(selectedRows);
            } else if (menu == menuItemOpenReport) {
                openReport(selectedRows);
            } else if (menu == menuItemRunAgain) {
                reload(selectedRows);
            } else if (menu == menuItemRemoveTest) {
                removeTest(selectedRows);
            }
        }
    }

    /**
     * This method opens one o more urls selected.
     *
     * @param selectedRows
     * @see #openUrl(java.net.URL)
     */
    private void openReport(int[] selectedRows) {
        try {
            for (int selectedRow : selectedRows) {
                TestResultItem tesResultItem = (TestResultItem) testResulList.get((String) tableRows.get(selectedRow));
                if (!tesResultItem.getUrl().isEmpty()) {
                    openUrl(new URL(tesResultItem.getUrl()));
                }
            }
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method remove all test of table, it including its folders on disk.
     *
     * @see #removeFolder(java.io.File)
     */
    private void removeAllTest() {
        for (int i = 0; i < tableRows.size(); i++) {
            try {
                String testName = (String) tableRows.get(i);
                TestResultItem item = (TestResultItem) testResulList.get(testName);
                File file = new File(item.getTestFolderPath());
                removeFolder(file);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        testResulList.clear();
        showTable();
    }

    /**
     * This method removes one or more tests selected, it including its folders
     * on disk.
     *
     * @param selectedRows
     * @see #removeFolder(java.io.File)
     */
    private void removeTest(int[] selectedRows) {
        for (int selectedRow : selectedRows) {
            try {
                String testName = (String) tableRows.get(selectedRow);
                TestResultItem item = (TestResultItem) testResulList.get(testName);
                File file = new File(item.getTestFolderPath());
                removeFolder(file);
                testResulList.remove(testName);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        showTable();
    }

    /**
     * It deletes the folder on disk.
     *
     * @param folder
     * @throws IOException
     */
    void removeFolder(File folder) throws IOException {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                removeFolder(file);
            }
        }
        if (!folder.delete()) {
            throw new FileNotFoundException(Bundle.TestReport_ErrorFailedToDeleteFile() + folder);
        }
    }

    /**
     * This method go to a file, line and column the error on the editor.
     */
    private void goToFile(int[] idSelectedRows) {
        for (int idSelectedRow : idSelectedRows) {
            String testName = (String) tableRows.get(idSelectedRow);
            if (!testName.isEmpty()) {
                try {
                    TestResultItem testResullItem = (TestResultItem) testResulList.get(testName);
                    File file = new File(testResullItem.getFilePath());
                    if (file.isFile()) {
                        DataObject document = DataObject.find(FileUtil.toFileObject(file));
                        EditorCookie editorCookie = document.getLookup().lookup(EditorCookie.class);
                        editorCookie.open();
                        editorCookie.getLineSet().getCurrent(0).show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT, 0);
                    }
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    /**
     * It returns the path it can be from the classes folder or the test folder.
     *
     * @param test
     * @param folder
     * @return
     */
    private String getPathFile(String test, String folder) {
        String result = EMPTY_TEXT;
        if (new File(folder + CLASS_FOLDER_PATH + test + FILE_EXTENSION).isFile()) {
            result = folder + CLASS_FOLDER_PATH + test + FILE_EXTENSION;
        } else if (new File(folder + TEST_FOLDER_PATH + test + FILE_EXTENSION).isFile()) {
            result = folder + TEST_FOLDER_PATH + test + FILE_EXTENSION;
        }
        return result;
    }

}
