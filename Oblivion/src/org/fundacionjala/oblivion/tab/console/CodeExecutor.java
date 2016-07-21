/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.tab.console;

import java.io.File;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.gradle.credentials.CredentialWrapper;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
@NbBundle.Messages({"Executecode_executeCode=Execute code.",
    "Executecode_executeOfConsoleCode=executing console code",
    "ExecuteCode_errorMessage=There was a problem while executing the console code, please, fix the  errors on it and try again."

})

/**
 * This class to handle the execution of code from the console tab in netbeans. 
 * 
 * @author sergio_daza
 */
public class CodeExecutor extends AbstractGradleAction  {

    private String pathTempFile;
    private CredentialWrapper credential;
    private String pathDirectory;
    private static final String EXECUTE = "execute";
    private static final String INPUT = "input";
    
    public AbstractGradleTask buildTask(ConsoleFileHandler tempFile,CredentialWrapper credential) {
        this.pathTempFile = tempFile.getPathTempFile();
        this.pathDirectory = tempFile.getPatdDir();
        this.credential = credential;
        return buildTask();
    }
    
    @Override
    protected AbstractGradleTask buildTask() {
        FileObject currentFile = FileUtil.toFileObject(new File(pathTempFile));
        GradleTaskDescriptor result = new GradleTaskDescriptor(EXECUTE, pathDirectory,Bundle.Executecode_executeOfConsoleCode());
        result.setResultHandler(new GradleExecutionResultHandler(Bundle.ExecuteCode_errorMessage()));
        result.addCredentialParameter(credential);
        result.addParameterEntry(INPUT, pathTempFile);
        return result;
    }

    @Override
    public String getName() {
        return Bundle.Executecode_executeCode();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

}
