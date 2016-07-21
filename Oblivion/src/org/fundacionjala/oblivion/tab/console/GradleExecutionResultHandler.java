/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.console;

import org.fundacionjala.oblivion.gradle.GradleResultHandler;
import org.fundacionjala.oblivion.tab.output.OutputHandler;
import org.gradle.tooling.GradleConnectionException;
import org.openide.util.NbBundle;
@NbBundle.Messages({"CodeExecuted_finishedExecution=Finished execution",
    "CodeExecuted_executingSalesforceCode=Execute Salesforce code",
    "CodeExecuted_successfulExecution=Finished"
})

/**
 * Handles the result of a gradle execution and  shows in the console's status.
 * 
 * @author sergio_daza
 */
public class GradleExecutionResultHandler extends GradleResultHandler {

    public GradleExecutionResultHandler(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void onComplete(Object t) {
        OutputHandler.setStatus(Bundle.CodeExecuted_finishedExecution());
        OutputHandler.notify(Bundle.CodeExecuted_executingSalesforceCode(),Bundle.CodeExecuted_successfulExecution());
        ConsoleTopComponent.showResult(outputStream.toString());
        
    }

    @Override
    public void onFailure(GradleConnectionException gradleConnectionException) {
        Throwable realException = getRealException(gradleConnectionException);
        OutputHandler.setStatus(Bundle.CodeExecuted_finishedExecution());
        OutputHandler.notify(Bundle.CodeExecuted_executingSalesforceCode(),Bundle.ExecuteCode_errorMessage());
        ConsoleTopComponent.showResult(getDisplayableErrorMessage(realException));
    }

}
