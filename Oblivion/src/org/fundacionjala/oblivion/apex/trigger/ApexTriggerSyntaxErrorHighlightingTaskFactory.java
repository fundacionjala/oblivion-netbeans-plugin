/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.trigger;

import java.util.Collection;
import java.util.Collections;
import org.fundacionjala.oblivion.apex.parser.ApexSyntaxErrorHighlightingTask;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.TaskFactory;

/**
 * Factory class that registers the error highlight task for the trigger file type.
 * 
 * @author Marcelo Garnica
 */
@MimeRegistration(mimeType=MimeType.APEX_TRIGGER_MIME_TYPE, service=TaskFactory.class)
public class ApexTriggerSyntaxErrorHighlightingTaskFactory extends TaskFactory {
    
    /**
     * Creates a new Highlighting task Scheduler for the given snapshot.
     * 
     * @param snapshot
     * @return 
     */
    @Override
    public Collection<ApexSyntaxErrorHighlightingTask> create(Snapshot snapshot) {
        return Collections.<ApexSyntaxErrorHighlightingTask>singleton(new ApexSyntaxErrorHighlightingTask());
    }
}
