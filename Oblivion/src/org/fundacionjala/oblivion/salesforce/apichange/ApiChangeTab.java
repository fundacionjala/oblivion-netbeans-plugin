/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.apichange;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.salesforce.apichange.panels.PanelManagerOfMetadata;
import org.openide.awt.UndoRedo;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;

@MultiViewElement.Registration(displayName = "#LBL_METADATA_VISUAL",
    iconBase = "org/fundacionjala/oblivion/salesforce/resources/SalesforceProject_Icon.png",
    mimeType = {MimeType.APEX_CLASS_MIME_TYPE, MimeType.APEX_TRIGGER_MIME_TYPE, MimeType.XHTML_MIME_TYPE},
    persistenceType = TopComponent.PERSISTENCE_ALWAYS,
    preferredID = "MetadataVisual",
    position = 8000)

@Messages({
    "LBL_METADATA_VISUAL=Metadata"
})

/**
 * Class that implement new tab in window editor for metadata file
 *
 * @author sergio_daza
 */
public final class ApiChangeTab extends JPanel implements MultiViewElement {

    private final DataObject dataObject;
    private final PanelManagerOfMetadata panelManagerOfMetadata;

    public ApiChangeTab(Lookup lkp) {
        dataObject = lkp.lookup(DataObject.class);
        panelManagerOfMetadata = new PanelManagerOfMetadata();
    }

    @Override
    public JComponent getVisualRepresentation() {
        return panelManagerOfMetadata.getPanelForm();
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return panelManagerOfMetadata.getToolBar();
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return dataObject.getLookup();
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback mvec) {
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

}
