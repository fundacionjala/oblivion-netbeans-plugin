/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor.indexSearcher;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProjectFactory;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.IndexSearcher;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;

/**
 * Descriptor for Salesforce types, used to display results by the IndexSearcher.
 * @author Marcelo Garnica
 */
class SalesforceTypeDescriptor extends IndexSearcher.Descriptor {
    
    private static final ImageIcon TYPE_ICON = new ImageIcon(ImageUtilities.loadImage("org/fundacionjala/oblivion/apex/resources/apexDevIcon16.png"));
    
    private final FileObject child;
    private final Project project;

    public SalesforceTypeDescriptor(FileObject child, Project project) {
        this.child = child;
        this.project = project;
    }

    @Override
    public ElementHandle getElement() {
        return new SalesforceElementHandle(child);
    }

    @Override
    public String getSimpleName() {
        return child.getName();
    }

    @Override
    public String getOuterName() {
        return child.getName();
    }

    @Override
    public String getTypeName() {
        return child.getNameExt();
    }

    @Override
    public String getContextName() {
        return null;
    }

    @Override
    public Icon getIcon() {
        return TYPE_ICON;
    }

    @Override
    public String getProjectName() {
        return project.getProjectDirectory().getName();
    }

    @Override
    public Icon getProjectIcon() {
        return SalesforceProjectFactory.PROJECT_IMAGE_ICON;
    }

    @Override
    public FileObject getFileObject() {
        return child;
    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public void open() {
        ProjectUtils.openFile(child);
    }
}
