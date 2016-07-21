/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.samples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.fundacionjala.oblivion.gradle.DownloadProjectTask;
import org.fundacionjala.oblivion.gradle.GradleTaskExecutor;
import org.fundacionjala.oblivion.gradle.credentials.CredentialManager;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.fundacionjala.oblivion.ui.wizard.AbstractWizardIterator;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


// TODO define position attribute
@TemplateRegistration(folder = "Project/Salesforce", displayName = "#SalesforceProject_displayName", 
    description = "SalesforceProjectDescription.html", 
    iconBase = "org/fundacionjala/oblivion/salesforce/project/samples/SalesforceProject.png", 
    content = "SalesforceProjectProject.zip")
@Messages({"SalesforceProject_displayName=Salesforce Project",
           "gradle.init.downloadProject.error=Download the Salesforce code failed. Please verify your credentials and try again"})
public class SalesforceProjectWizardIterator extends AbstractWizardIterator {

    public SalesforceProjectWizardIterator() {
    }

    public static SalesforceProjectWizardIterator createIterator() {
        return new SalesforceProjectWizardIterator();
    }

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> buildPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardPanels = new ArrayList<>();
        wizardPanels.add(new SalesforceProjectWizardPanel());
        return wizardPanels;
    }

    @Override
    protected String[] createSteps() {
        return new String[]{
            NbBundle.getMessage(SalesforceProjectWizardIterator.class, "LBL_CreateProjectStep")
        };
    }

    @Override
    public Set<FileObject> instantiate() throws IOException {
        Set<FileObject> resultSet = new LinkedHashSet<>();
        File dirF = FileUtil.normalizeFile((File) wizard.getProperty("projdir"));
        dirF.mkdirs();

        FileObject template = Templates.getTemplate(wizard);
        FileObject dir = FileUtil.toFileObject(dirF);
        unZipFile(template.getInputStream(), dir);

        // Always open top dir as a project:
        resultSet.add(dir);
        // Look for nested projects to open as well:
        Enumeration<? extends FileObject> e = dir.getFolders(true);
        while (e.hasMoreElements()) {
            FileObject subfolder = e.nextElement();
            if (ProjectManager.getDefault().isProject(subfolder)) {
                resultSet.add(subfolder);
            }
        }

        File parent = dirF.getParentFile();
        if (parent != null && parent.exists()) {
            ProjectChooser.setProjectsFolder(parent);
        }
        
        postCreationStep(dirF);

        return resultSet;
    }
    
    private void postCreationStep(File projectPath) {
        FileObject projectFile = FileUtil.toFileObject(projectPath);
        String projectAbsolutePath = projectFile.getPath();
        SalesforceProjectWizardPanel panel = (SalesforceProjectWizardPanel) current();
        CredentialWrapper credential = panel.getCredential();
        CredentialManager.getDefaultStorage().save(projectAbsolutePath, credential);
        if (panel.isDownloadProjectChecked()) {
            DownloadProjectTask downloadTask = new DownloadProjectTask(projectAbsolutePath, 
                                                                       Bundle.gradle_init_downloadProject_error());
            GradleTaskExecutor.execute(downloadTask);
        }
    }

    @Override
    public void uninitialize(WizardDescriptor wiz) {
        this.wizard.putProperty("projdir", null);
        this.wizard.putProperty("name", null);
        this.wizard = null;
        panels = null;
    }

    private static void unZipFile(InputStream source, FileObject projectRoot) throws IOException {
        try {
            ZipInputStream str = new ZipInputStream(source);
            ZipEntry entry;
            while ((entry = str.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    FileUtil.createFolder(projectRoot, entry.getName());
                } else {
                    FileObject fo = FileUtil.createData(projectRoot, entry.getName());
                    if ("nbproject/project.xml".equals(entry.getName())) {
                        // Special handling for setting name of Ant-based projects; customize as needed:
                        filterProjectXML(fo, str, projectRoot.getName());
                    } else {
                        writeFile(str, fo);
                    }
                }
            }
        } finally {
            source.close();
        }
    }

    private static void writeFile(ZipInputStream str, FileObject fo) throws IOException {
        try (OutputStream out = fo.getOutputStream()) {
            FileUtil.copy(str, out);
        }
    }

    private static void filterProjectXML(FileObject fo, ZipInputStream str, String name) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileUtil.copy(str, baos);
            Document doc = XMLUtil.parse(new InputSource(new ByteArrayInputStream(baos.toByteArray())), false, false, null, null);
            NodeList nl = doc.getDocumentElement().getElementsByTagName("name");
            if (nl != null) {
                for (int i = 0; i < nl.getLength(); i++) {
                    Element el = (Element) nl.item(i);
                    if (el.getParentNode() != null && "data".equals(el.getParentNode().getNodeName())) {
                        NodeList nl2 = el.getChildNodes();
                        if (nl2.getLength() > 0) {
                            nl2.item(0).setNodeValue(name);
                        }
                        break;
                    }
                }
            }
            OutputStream out = fo.getOutputStream();
            try {
                XMLUtil.write(doc, out, "UTF-8");
            } finally {
                out.close();
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            writeFile(str, fo);
        }
    }
}
