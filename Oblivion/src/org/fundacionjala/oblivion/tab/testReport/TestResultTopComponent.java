/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

@ConvertAsProperties(
        dtd = "-//org.fundacionjala.oblivion.tab.testReport//TestResult//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TestResultTopComponent",
        iconBase = "org/fundacionjala/oblivion/tab/testReport/icons/test.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "org.fundacionjala.oblivion.tab.testReport.TestResultTopComponent")
@ActionReferences({@ActionReference(path = "Menu/Window/Salesforce" , position = 1000), @ActionReference(path = "Shortcuts", name = "C-S-R")})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TestResultAction",
        preferredID = "TestResultTopComponent"
)
@Messages({
    "CTL_TestResultAction=Salesforce Test Results",
    "CTL_TestResultTopComponent=Salesforce - Test Results",
    "HINT_TestResultTopComponent=This is a TestResult "
})

/**
 * Top component which displays test result report.
 */
public final class TestResultTopComponent extends TopComponent {
    
    private static final Logger LOG = Logger.getLogger(TestResultTopComponent.class.getName());
    private static final RecoverInformation recoverInformation = new RecoverInformation();
    private static final Map<String,PanelOfTestReport> projects = new HashMap<>();
    private static final Map<String,String> projectsAndPath = new HashMap<>();
    private static final String SUCCESS_TAG_NAME = "success";
    private static final String FAILED_TAG_NAME = "failed";
    private static final String TAG_TD = "td";
    private static final String SUCCESS_CLASS_NAME = "success center-text";
    private static final String FAILED_CLASS_NAME = "danger center-text";
    private static final String RESULT_FILE = "/index.html";
    private static boolean activePanel = false;
    public static final RunRelatedUnitTestReport run = new RunRelatedUnitTestReport();
    
    
    public TestResultTopComponent() {
        initComponents();
        setName(Bundle.CTL_TestResultTopComponent());
        setToolTipText(Bundle.HINT_TestResultTopComponent());
    }
    
    /**It close  tab when it press the button close
     * This method adds a project when the user create or opens other project,
     * also if it starts Netbeans with projects in use.
     * 
     * @param nameProject
     * @param pathFolder 
     */
    public static void addProject(String nameProject, String pathFolder) {
        if (!projectsAndPath.containsKey(nameProject.trim())&&!nameProject.isEmpty()&&!pathFolder.isEmpty()) {
            projectsAndPath.put(nameProject, pathFolder);
        }
    }

    /**
     * This method adds a new line in the table of tests.
     * 
     * @param nameProject
     * @param filePath
     * @param testName
     * @param status 
     */
    public static void addTestReport(String nameProject, String filePath, String testName, TestResultItem.Status status) {
        if (activePanel) {
            TestResultItem item = buildItem(filePath, testName, status);
            checkPanel(nameProject, item.getProjectPath());
            projects.get(nameProject).addTestReport(item);
            projects.get(nameProject).showTable();
        }
    }
    
    /**
     * This method returns a TestResultItem according its state.
     * 
     * @param filePath
     * @param testName
     * @param status
     * @return 
     */
    public static TestResultItem buildItem( String filePath, String testName, TestResultItem.Status status) {
        int errors = 0;
        int success = 0;
        if (TestResultItem.Status.running != status) {
            Map<String, TagHTML> datas = new HashMap<>();
            datas.put(SUCCESS_TAG_NAME, new TagHTML(TAG_TD, SUCCESS_CLASS_NAME));
            datas.put(FAILED_TAG_NAME, new TagHTML(TAG_TD, FAILED_CLASS_NAME));
            Map<String, TagHTML> testResult = recoverInformation.testResult(testName + RESULT_FILE, datas);
            TagHTML tagSuccess = testResult.get(SUCCESS_TAG_NAME);
            TagHTML tagFailed = testResult.get(FAILED_TAG_NAME);
            success = tagSuccess.getContainToInt();
            errors = tagFailed.getContainToInt();
        }
        return new TestResultItem(testName, filePath, status, errors, success);
    }
    
    /**
     * This method verifies if exist a tab for the project, if not it creates a new tab. 
     * 
     * @param nameProject
     * @param pathProject 
     */
    private static void checkPanel(String nameProject, String pathProject) {
        if (activePanel && !nameProject.isEmpty()) {
            int indexOfTab = jTabbedPane1.indexOfTab(nameProject);
            if (indexOfTab == -1) {
                projects.put(nameProject, new PanelOfTestReport(pathProject));
                jTabbedPane1.addTab(nameProject, projects.get(nameProject).getPanel());
                indexOfTab = jTabbedPane1.indexOfTab(nameProject);
                jTabbedPane1.setTabComponentAt(indexOfTab, new TabWithCloseButton(jTabbedPane1));
            }
            jTabbedPane1.repaint();
            try {
                jTabbedPane1.setSelectedIndex(indexOfTab);
            } catch (ArrayIndexOutOfBoundsException e) {
                LOG.log(Level.INFO, e.toString());
            }
        }
    }

    /**
     * It close tab when it press the button close.
     * 
     * @param index 
     */
    public static void closeTab(int index) {
        if (activePanel) {
            projects.remove(jTabbedPane1.getTitleAt(index));
            projectsAndPath.remove(jTabbedPane1.getTitleAt(index));
            jTabbedPane1.remove(index);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleParent(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        projects.clear();
        jTabbedPane1.removeAll();
        jTabbedPane1.repaint();
        activePanel = true;
        for (String key : projectsAndPath.keySet()) {
            checkPanel(key, projectsAndPath.get(key));
        }
    }

    @Override
    public void componentClosed() {
        projects.clear();
        jTabbedPane1.removeAll();
        activePanel = false;
        try {
            super.finalize();
        } catch (Throwable ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
}
