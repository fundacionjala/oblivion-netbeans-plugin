/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.salesforceErrors;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import org.fundacionjala.oblivion.messages.MessagesUtil;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.openide.awt.ActionReferences;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.Line;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.fundacionjala.oblivion.tab.salesforceErrors//SalesforceErrors//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SalesforceErrorsTopComponent",
        iconBase = "org/fundacionjala/oblivion/tab/salesforceErrors/salesforce_errors.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent")
@ActionReferences({@ActionReference(path = "Menu/Window/Salesforce"), @ActionReference(path = "Shortcuts", name = "C-S-E")})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SalesforceErrorsAction",
        preferredID = "SalesforceErrorsTopComponent"
)
@NbBundle.Messages({
    "CTL_SalesforceErrorsAction=Salesforce Errors",
    "CTL_SalesforceErrorsTopComponent=Salesforce - Errors",
    "HINT_SalesforceErrorsTopComponent=Shows Salesforce errors",
    "SalesforceErrors_GoToSource=Go to source",
    "SalesforceErrors_CollapseAll=Collapse all",
    "SalesforceErrors_ExpandAll=Expand all",
    "SalesforceErrors_RemoveError=Remove error",
    "SalesforceErrors_RemoveProject=Remove project",
    "SalesforceErrors_RemoveAllErrors=Remove all errors",
    "SalesforceErrors_CopyToClipboard=Copy to clipboard",
    "SalesforceErrors_WordProjects=projects",
    "SalesforceErrors_WordErrors=errors",
    "SalesforceErrors_WordDescription=Description",
    "SalesforceErrors_WordPath=Path",
    "SalesforceErrors_WordLocation=Location",
    "SalesforceErrors_WordClear=Clear",
    "SalesforceErrors_ShowError=Show complete error"
})

/**
 * This class  shows the errors that returns the server If exist error on  
 * action save on server
 * 
 * @author sergio_daza
 */
public final class SalesforceErrorsTopComponent extends TopComponent implements ActionListener {
    
    private static final Logger LOG = Logger.getLogger(SalesforceErrorsTopComponent.class.getName());
    private static final SortedMap errorsList = new TreeMap();
    private static final SortedMap tableRows = new TreeMap();
    private static final SortedMap showErrors = new TreeMap();
    private final JPopupMenu jPopupMenu;
    private final JMenuItem menuItemGoToSource;
    private final JMenuItem menuItemCollapseAll;
    private final JMenuItem menuItemExpandAll;
    private final JMenuItem menuItemRemove;
    private final JMenuItem menuItemRemoveProject;
    private final JMenuItem menuItemRemoveAll;
    private final JMenuItem menuItemCopyToClipboard;
    private final JMenuItem menuItemShowError;
    private static int totalErrors = 0;
    private static final String BLANK_SPACE = " ";
    
    public SalesforceErrorsTopComponent() {
        initComponents();
        setName(Bundle.CTL_SalesforceErrorsTopComponent());
        setToolTipText(Bundle.HINT_SalesforceErrorsTopComponent());
        jPopupMenu = new JPopupMenu();
        menuItemGoToSource = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_GoToSource()));
        menuItemGoToSource.addActionListener(this);
        menuItemCollapseAll = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_CollapseAll()));
        menuItemCollapseAll.addActionListener(this);
        menuItemExpandAll = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_ExpandAll()));
        menuItemExpandAll.addActionListener(this);
        menuItemRemove = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_RemoveError()));
        menuItemRemove.addActionListener(this);
        menuItemRemoveProject = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_RemoveProject()));
        menuItemRemoveProject.addActionListener(this);
        menuItemRemoveAll = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_RemoveAllErrors()));
        menuItemRemoveAll.addActionListener(this);
        menuItemCopyToClipboard = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_CopyToClipboard()));
        menuItemCopyToClipboard.addActionListener(this);
        menuItemShowError = jPopupMenu.add(new JMenuItem(Bundle.SalesforceErrors_ShowError()));
        menuItemShowError.addActionListener(this);
        tableOfNotifications.setComponentPopupMenu(jPopupMenu);
        tableOfNotifications.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    if (!goToSource(tableOfNotifications.getSelectedRow())) {
                        showTable();
                    }
                }
            }
        });
        tableOfNotifications.setDefaultRenderer(JLabel.class,  new RenderErrors());
        tableOfNotifications.repaint();
    }

    /**
     * This method static adds  a error on a jtable.
     * 
     * @param project
     * @param description
     * @param path
     * @param line
     * @param column
     */
    public static void addNofitfy(SalesforceProject project, String description, String path, int line, int column) {
        if(project != null) {
            String projectName = project.getProjectDirectory().getName();
            NotificationItem item = new NotificationItem(projectName, project.getProjectDirectory().getPath(), description, path, line, column);
            if (errorsList.containsKey(projectName)) {
                SortedMap itemError = (SortedMap) errorsList.get(projectName);
                itemError.put(item.pathFile(), item);
            } else {
                SortedMap itemError = new TreeMap();
                itemError.put(item.pathFile(), item);
                errorsList.put(projectName, itemError);
            }
            showTable();
            showStatistics();
        }
    }

    public static void addNofitfy(SalesforceProject project, String description) {
        if(project == null) {
            addNofitfy((SalesforceProject)ProjectUtils.getCurrentProject(), description, "", 0, 0);
        }
        else {
            addNofitfy(project, description, "", 0, 0);
        }
    }
    
    /**
     * Shows the project and errors quantity
     */
    private static  void showStatistics() {
        labelStatistics.setText(errorsList.size() + " " + Bundle.SalesforceErrors_WordProjects() +", " + totalErrors + " " + Bundle.SalesforceErrors_WordErrors());
    }
    
    /**
     * Shows the errors in the table, an error on each line
     */
    private static void showTable() {
        try {
            DefaultTableModel modelo = new ErrorTableModel();
            totalErrors = 0;
            tableRows.clear();
            int countRow = 0;
            for (Iterator iterator = errorsList.keySet().iterator(); iterator.hasNext();) {
                String projectName = (String) iterator.next();
                SortedMap notificationItem = (SortedMap) errorsList.get(projectName);
                modelo.addRow(addNotificationTitle(projectName, notificationItem.size()));
                tableRows.put(countRow, new ErrorRow(ErrorRow.TypeRow.PROJECT_NAME, new NotificationItem(projectName, "", "", "", 0, 0)));
                countRow++;
                if (showErrorsOfProject(projectName)) {
                    for (Iterator iteratorOfNotifications = notificationItem.keySet().iterator(); iteratorOfNotifications.hasNext();) {
                        String path = (String) iteratorOfNotifications.next();
                        NotificationItem item = (NotificationItem) notificationItem.get(path);
                        modelo.addRow(addNotificationOnTable(item));
                        tableRows.put(countRow, new ErrorRow(ErrorRow.TypeRow.ERROR, item));
                        countRow++;
                    }
                }
            }
            tableOfNotifications.setModel(modelo);
            tableOfNotifications.repaint();
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Verify if it will shows the  errors of a specific project.(collapse or expand)
     * 
     * @param nameProject
     * @return 
     */
    private static boolean showErrorsOfProject(String nameProject) {
        if (showErrors.get(nameProject) == null) {
            showErrors.put(nameProject, false);
            return false;
        } else {
            return (boolean) showErrors.get(nameProject);
        }
    }
    
    /**
     * Adds a new error line in the table.
     * 
     * @param item 
     */
    private static Object[] addNotificationOnTable(NotificationItem item) {
        Object[] newTag = new Object[3];
        JLabel label = new JLabel(new String("      " + item.description));
        label.setToolTipText(label.getText());
        newTag[0] = label;
        newTag[1] = item.path;
        newTag[2] = new JLabel(item.location());
        return newTag;
    }

    /**
     * Adds a new title line in the table with the project name.
     * 
     * @param title
     * @param quantity 
     */
    private static Object[] addNotificationTitle(String title, int quantity) {
        totalErrors += quantity;
        Object[] newTag = new Object[3];
        newTag[0] = new JLabel(new String(" " + title + " (" + quantity + " " + Bundle.SalesforceErrors_WordErrors() + ")"));
        newTag[1] = "";
        newTag[2] = new JLabel("");
        return newTag;
    }

    /**
     * This method run the option from contextual menu.
     * 
     * @param actionEvent 
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem menu = (JMenuItem) actionEvent.getSource();
        if (menu == menuItemCollapseAll) {
            for (Iterator iterator = showErrors.keySet().iterator(); iterator.hasNext();) {
                String projectName = (String) iterator.next();
                showErrors.put(projectName, false);
            }
        } else if (menu == menuItemExpandAll) {
            for (Iterator iterator = showErrors.keySet().iterator(); iterator.hasNext();) {
                String projectName = (String) iterator.next();
                showErrors.put(projectName, true);
            }
        } else if (menu == menuItemRemoveAll) {
            clear();
        }
        int[] idSelectedRows = tableOfNotifications.getSelectedRows();
        for (int idSelectedRow : idSelectedRows) {
            if (menu == menuItemRemoveProject) {
                ErrorRow tableRow = (ErrorRow) tableRows.get(idSelectedRow);
                if (tableRow != null && tableRow.typeRow == ErrorRow.TypeRow.PROJECT_NAME) {
                    removeProject(tableRow.nameProject());
                }
            } else if (menu == menuItemRemove) {
                ErrorRow tableRow = (ErrorRow) tableRows.get(idSelectedRow);
                if (tableRow.typeRow == ErrorRow.TypeRow.ERROR) {
                    removeError(tableRow.nameProject(), tableRow.pathFile());
                }
            } else if (menu == menuItemCopyToClipboard) {
                ErrorRow tableRow = (ErrorRow) tableRows.get(idSelectedRow);
                if (tableRow.typeRow == ErrorRow.TypeRow.ERROR) {
                    CopyToClipboard(tableRow.description());
                }
            } else if (menu == menuItemGoToSource) {
                goToSource(idSelectedRow);
            } else if (menu == menuItemShowError) {
                ErrorRow errorRow = (ErrorRow) tableRows.get(idSelectedRow);
                MessagesUtil.showError(errorRow.description());
            }
        }
        showTable();
        showStatistics();
    }
    
    /**
     * This method remove all elements of table.
     */
    private void clear() {
        errorsList.clear();
        tableRows.clear();
        showErrors.clear();
        totalErrors = 0;
        tableOfNotifications.setModel(new ErrorTableModel());
        tableOfNotifications.repaint();
        pathLabel.setText(BLANK_SPACE);
    }
    
    /**
     * This method go to a  file, line and column the error on the editor.
     */
    private boolean goToSource(int idSelectedRow) {
        String valueAt = (String) tableOfNotifications.getModel().getValueAt(idSelectedRow, 1);
        ErrorRow tableRow = (ErrorRow) tableRows.get(idSelectedRow);
        if (!valueAt.trim().isEmpty()) {
            try {
                File file = new File(tableRow.pathFile());
                if (file.isFile()) {
                    DataObject document = DataObject.find(FileUtil.toFileObject(file));
                    EditorCookie editorCookie = document.getLookup().lookup(EditorCookie.class);
                    editorCookie.open();
                    editorCookie.getLineSet().getCurrent(tableRow.lineError() - 1).show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FRONT, tableRow.columnError() - 1);
                }
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            return true;
        } else {
            showErrors.put(tableRow.nameProject(), showErrorsOfProject(tableRow.nameProject()) == false);
            return false;
        }
    }
    
    /**
     * This method enables or disables the options according to the context and
     * it current selection.
     */
    private void showjPopupMenu() {
        int selectedRow = tableOfNotifications.getSelectedRow();
        menuItemGoToSource.setEnabled(false);
        menuItemRemove.setEnabled(false);
        menuItemCopyToClipboard.setEnabled(false);
        menuItemRemoveProject.setEnabled(true);
        menuItemShowError.setEnabled(false);
        if (selectedRow > 0) {
            String path = (String) tableOfNotifications.getModel().getValueAt(selectedRow, 1);
            if (!path.trim().isEmpty()) {
                menuItemGoToSource.setEnabled(true);
                menuItemRemove.setEnabled(true);
                menuItemCopyToClipboard.setEnabled(true);
                menuItemShowError.setEnabled(true);
                menuItemRemoveProject.setEnabled(false);
            } else {
                pathLabel.setText(BLANK_SPACE);
            }
        }
    }
    
    /**
     * This method removed a specific line in the table.
     * 
     * @param nameProject
     * @param pathError 
     */
    private static void removeError(String nameProject, String pathError) {
        if (errorsList.containsKey(nameProject)) {
            SortedMap errorItem = (SortedMap) errorsList.get(nameProject);
            errorItem.remove(pathError);
        }
    }
    
    /**
     * This method removed a specific line in the table 
     * and repaints the tableOfNotifications.
     * 
     * @param nameProject
     * @param pathError 
     * @see #removeError(String, String) 
     */
    public static void deleteError(String nameProject, String pathError) {
        removeError(nameProject,pathError);
        showTable();
        showStatistics();
    }
    
    /**
     * This method removed a specific project in the table including its errors lines.
     * 
     * @param nameProject 
     */
    private static void removeProject(String nameProject) {
        if (!errorsList.isEmpty()) {
            errorsList.remove(nameProject);
            showErrors.remove(nameProject);
        }
    }
    
    /**
     * This method removed a specific project in the table including its errors lines 
     * and  repaints the tableOfNotifications.
     * 
     * @param nameProject 
     * @see #removeProject(String)
     */
    public static void deleteProject(String nameProject) {
        removeProject(nameProject);
        showTable();
        showStatistics();
    }
    
    /**
     * This method clear the lines on the table and it resets initial values.
     */
    private void showPath() {
        if (tableOfNotifications.getSelectedRow() > 0) {
            String path = (String) tableOfNotifications.getModel().getValueAt(tableOfNotifications.getSelectedRow(), 1);
            if (!path.trim().isEmpty()) {
                ErrorRow tableRow = (ErrorRow) tableRows.get(tableOfNotifications.getSelectedRow());
                pathLabel.setText(tableRow.pathFile());
            }
        } else {
            pathLabel.setText(BLANK_SPACE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelStatistics = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableOfNotifications = new javax.swing.JTable();
        btnClear = new java.awt.Button();
        pathLabel = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        org.openide.awt.Mnemonics.setLocalizedText(labelStatistics, org.openide.util.NbBundle.getMessage(SalesforceErrorsTopComponent.class, "SalesforceErrorsTopComponent.labelStatistics.text_1")); // NOI18N

        jScrollPane2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                jScrollPane2AncestorMoved(evt);
            }
        });

        tableOfNotifications.setBackground(new java.awt.Color(254, 254, 254));
        tableOfNotifications.setModel(new ErrorTableModel());
        tableOfNotifications.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableOfNotificationsMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOfNotificationsMouseClicked(evt);
            }
        });
        tableOfNotifications.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableOfNotificationsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableOfNotificationsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableOfNotifications);

        btnClear.setActionCommand(org.openide.util.NbBundle.getMessage(SalesforceErrorsTopComponent.class, "SalesforceErrorsTopComponent.btnClear.actionCommand")); // NOI18N
        btnClear.setLabel(Bundle.SalesforceErrors_WordClear());
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(pathLabel, org.openide.util.NbBundle.getMessage(SalesforceErrorsTopComponent.class, "SalesforceErrorsTopComponent.pathLabel.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelStatistics)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 837, Short.MAX_VALUE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pathLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelStatistics))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pathLabel))
        );

        btnClear.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SalesforceErrorsTopComponent.class, "SalesforceErrorsTopComponent.btnClear.AccessibleContext.accessibleName")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableOfNotificationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOfNotificationsMouseClicked
        showPath();
    }//GEN-LAST:event_tableOfNotificationsMouseClicked

    
    private void jScrollPane2AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jScrollPane2AncestorMoved
    }//GEN-LAST:event_jScrollPane2AncestorMoved

    private void tableOfNotificationsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableOfNotificationsKeyReleased
        showjPopupMenu();
        showPath();
    }//GEN-LAST:event_tableOfNotificationsKeyReleased

    private void tableOfNotificationsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableOfNotificationsKeyPressed
        int selectedRow = tableOfNotifications.getSelectedRow();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            goToSource(selectedRow);
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (selectedRow >= 0) {
                ErrorRow tableRow = (ErrorRow) tableRows.get(selectedRow);
                String valueAt = (String) tableOfNotifications.getModel().getValueAt(selectedRow, 1);
                if (!"".equals(valueAt.trim())) {
                    removeError(tableRow.nameProject(), tableRow.pathFile());
                } else {
                    removeProject(tableRow.nameProject());
                }
            }
        }
    }//GEN-LAST:event_tableOfNotificationsKeyPressed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
        showStatistics();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tableOfNotificationsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOfNotificationsMousePressed
        showjPopupMenu();
    }//GEN-LAST:event_tableOfNotificationsMousePressed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnClear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JLabel labelStatistics;
    private javax.swing.JLabel pathLabel;
    private static javax.swing.JTable tableOfNotifications;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private void CopyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection (text);
        Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        clpbrd.setContents (stringSelection, null);
    }
}
