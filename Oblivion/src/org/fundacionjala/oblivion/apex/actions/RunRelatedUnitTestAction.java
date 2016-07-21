/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.actions;

import java.io.File;
import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleResultHandler;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.gradle.actions.AbstractGradleAction;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.tab.testReport.TestResultItem;
import org.fundacionjala.oblivion.tab.testReport.TestResultTopComponent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Will execute the task to run the unit tests that are related to an apex class.
 * @author Marcelo Garnica
 */

@ActionID(
    category = "Salesforce",
    id = "org.fundacionjala.oblivion.apex.actions.RunRelatedUnitTestAction"
)
@ActionRegistration(
    displayName = "#CTL_RunRelatedUnitTest", key = RunRelatedUnitTestAction.SHORTCUT
)
@ActionReferences({
    @ActionReference(path = MimeType.APEX_CLASS_ACTIONS_EDITOR_POPUP, position = 161, separatorAfter = 175),
    @ActionReference(path = "Shortcuts", name = RunRelatedUnitTestAction.SHORTCUT)
})
@NbBundle.Messages({"CTL_RunRelatedUnitTest=Run unit tests",
    "gradle.runRelatedUnitTest.description=Running unit tests",
    "gradle.runRelatedUnitTest.error=Couldn't run the unit tests, please try again."})
public class RunRelatedUnitTestAction extends AbstractGradleAction {
    
    public static final String SHORTCUT = "O-U";
    public static final String DESTINATION_DEFAULT_VALUE = "/temp/reports/";
    protected static final String COMMAND = "runTest";
    protected static final String CLASS_FILES_PARAMETER = "files";
    protected static final String TEST_CLASS_FILES_PARAMETER = "tests";
    protected static final String MAPPING_PARAMETER = "refreshMapping";
    protected static final String MAPPING_DEFAULT_VALUE = "true";
    public static final String REPORT_DESTINATION_PARAM = "destination";
    public static final String PROJECT_NAME_PARAM = "projectName";
    public static final String FILE_PATH_PARAM = "filePath";
    protected static final String TEST_FILE_SUFFIX = "Test";

    @Override
    protected AbstractGradleTask buildTask() {
        SalesforceProject salesforceProject = (SalesforceProject) ProjectUtils.getCurrentProject();
        String folder = salesforceProject.getProjectDirectory().getPath();
        FileObject currentFile = ProjectUtils.getCurrentFile();
        boolean isTestClass = currentFile.getName().contains(TEST_FILE_SUFFIX);
        File destinationFolder = new File(folder + DESTINATION_DEFAULT_VALUE + currentFile.getName());
        GradleTaskDescriptor result = new GradleTaskDescriptor(COMMAND, folder, Bundle.gradle_runRelatedUnitTest_description());
        result.setResultHandler(new GradleResultHandler(Bundle.gradle_runRelatedUnitTest_error()));
        if (isTestClass) {
            result.addParameterEntry(TEST_CLASS_FILES_PARAMETER, currentFile.getNameExt());
        } else {
            result.addParameterEntry(CLASS_FILES_PARAMETER, currentFile.getNameExt());
            result.addParameterEntry(MAPPING_PARAMETER, MAPPING_DEFAULT_VALUE);
        }
        result.addParameterEntry(REPORT_DESTINATION_PARAM, destinationFolder.getPath());
        result.addCredentialParameter(salesforceProject.getCredential());
        result.addParameterEntry(PROJECT_NAME_PARAM, salesforceProject.getProjectDirectory().getName());
        result.addParameterEntry(FILE_PATH_PARAM, currentFile.getPath());
        TestResultTopComponent.addTestReport(salesforceProject.getProjectDirectory().getName(),currentFile.getPath(), destinationFolder.getPath(), TestResultItem.Status.running);
        return result;
    }

    @Override
    public String getName() {
        return Bundle.CTL_RunRelatedUnitTest();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }
    
}
