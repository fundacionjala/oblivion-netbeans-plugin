/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange.panels;

import org.fundacionjala.oblivion.salesforce.apichange.tools.MetadataUtil;
import org.fundacionjala.oblivion.salesforce.apichange.tools.MetadataFileManager;
import org.fundacionjala.oblivion.salesforce.apichange.tools.MetadataManager;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.openide.filesystems.FileUtil;

/**
 * Class that manages toolbar and form
 *
 * @author sergio_daza
 */
public class PanelManagerOfMetadata {

    private final Form panelForm;
    private final Toolbar toolBar;
    private static final String SALESFORCE_METADATA_EXTENSION = "-meta.xml";

    public PanelManagerOfMetadata() {
        String pathMetaData = ProjectUtils.getCurrentFilePath() + SALESFORCE_METADATA_EXTENSION;
        MetadataFileManager metadataFileManager = new MetadataFileManager(pathMetaData);
        MetadataManager metadataManager = new MetadataManager(pathMetaData, MetadataUtil.getRootTagName(FileUtil.getExtension(ProjectUtils.getCurrentFilePath())));
        panelForm = new Form(pathMetaData, metadataManager);
        toolBar = new Toolbar(pathMetaData, metadataFileManager);
        panelForm.setToolbar(toolBar);
        toolBar.setForm(panelForm);
        panelForm.refreshForm();
        toolBar.refresh();
    }

    /**
     * @return a Form to show in editor's window 
     */
    public Form getPanelForm() {
        return panelForm;
    }

    /**
     * @return a Toolbar to show in editor's window
     */
    public Toolbar getToolBar() {
        return toolBar;
    }
}
