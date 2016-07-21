/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.navigator.SalesForceNavigationPanel;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.TaskFactory;

/**
 * 
 * @author Maria Garcia
 */
@MimeRegistration(mimeType=MimeType.APEX_CLASS_MIME_TYPE, service=TaskFactory.class)
public class ApexSyntaxErrorHighlightingTaskFactory extends TaskFactory {

    /**
     * Creates a new Highlighting task Scheduler for the given snapshot.
     * 
     * @param snapshot
     * @return 
     */
    @Override
    public Collection<ParserResultTask> create(Snapshot snapshot) {
        List<ParserResultTask> tasks = new ArrayList<>();
        tasks.add(new ApexSyntaxErrorHighlightingTask());
        tasks.add(SalesForceNavigationPanel.getPanel());
        return Collections.unmodifiableList(tasks);
    }
}
