/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.parser;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode;
import org.fundacionjala.oblivion.apex.errors.ServerErrors;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.Token;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;

import static org.fundacionjala.oblivion.gradle.GradleDeployResultHandler.DEPLOY_ERROR_LAYER;

/**
 * SchedulerTask that process result of parsing.
 *
 * @author Maria Garcia
 */
public class ApexSyntaxErrorHighlightingTask extends ParserResultTask<ApexParserResult> {

    private static final String APEX_ERROR_LAYER = "APEX_ERROR_LAYER";

    private static final int ERROR_PRIORITY = 100;

    /**
     * Called when parser is finished.
     *
     * @param result The result of parsing
     * @param event
     */
    @Override
    public void run(ApexParserResult apexResult, SchedulerEvent event) {
        try {
            List<? extends ParseException> syntaxErrors = apexResult.getApexParser().getSyntaxErrors();
            StyledDocument document = (StyledDocument) apexResult.getSnapshot().getSource().getDocument(false);
            List<ErrorDescription> errors = new ArrayList<>();
            for (ParseException syntaxError : syntaxErrors) {
                ErrorDescription errorDescription = createError(document, syntaxError);
                errors.add(errorDescription);
            }
            SalesforceDataNode currentNode = (SalesforceDataNode)ProjectUtils.getCurrentNode();
            List<ErrorDescription> serverErrors = ServerErrors.getInstance().getErrors(apexResult.getSnapshot().getSource().getFileObject());
            if (currentNode != null) {
                currentNode.setHasLocalErrors(!errors.isEmpty());
                currentNode.setHasServerErrors(!serverErrors.isEmpty());
            }
            HintsController.setErrors(document, APEX_ERROR_LAYER, errors);
            HintsController.setErrors(document, DEPLOY_ERROR_LAYER, serverErrors);
        } catch (BadLocationException | org.netbeans.modules.parsing.spi.ParseException error) {
            Exceptions.printStackTrace(error);
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
     * closed, editor tab switched) and when text in the current document is changed
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

    /**
     * Creates a error descriptor based on the ParseException
     * @param document the source code document
     * @param syntaxError the apex parser error
     * @return
     * @throws BadLocationException
     */
    private ErrorDescription createError(StyledDocument document, ParseException syntaxError) throws BadLocationException {
        Token token = syntaxError.currentToken;
        int start = NbDocument.findLineOffset(document, token.beginLine - 1) + token.beginColumn - 1;
        int end = NbDocument.findLineOffset(document, token.endLine - 1) + token.endColumn;
        return ErrorDescriptionFactory.createErrorDescription(
            Severity.ERROR,
            syntaxError.getMessage(),
            document,
            document.createPosition(start),
            document.createPosition(end)
        );
    }
}
