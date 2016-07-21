/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.tab.testReport;

import java.io.File;
import org.fundacionjala.oblivion.apex.actions.RunRelatedUnitTestAction;
import org.fundacionjala.oblivion.gradle.AbstractGradleTask;
import org.fundacionjala.oblivion.gradle.GradleResultHandler;
import org.fundacionjala.oblivion.gradle.GradleTaskDescriptor;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.openide.filesystems.FileObject;
import static org.openide.filesystems.FileUtil.toFileObject;
import org.openide.util.NbBundle;

@NbBundle.Messages({
    "CTL_RunRelatedUnitTest=Run unit tests",
    "gradle.runRelatedUnitTest.description=Running unit tests",
    "gradle.runRelatedUnitTest.error=Couldn't run the unit tests, please try again."
})

/**
 * This class executes a test again.
 *
 * @author sergio_daza
 */
public class RunRelatedUnitTestReport extends RunRelatedUnitTestAction {

    private SalesforceProject salesforceProject = (SalesforceProject) ProjectUtils.getCurrentProject();
    private FileObject currentFile = null;

    public AbstractGradleTask buildTask(String projectPath, String filePath) {
        File testFilePath = new File(filePath);
        File testProjectPath = new File(projectPath);
        if (testFilePath.isFile() && testProjectPath.isDirectory()) {
            currentFile = toFileObject(testFilePath);
            salesforceProject = new SalesforceProject(toFileObject(testProjectPath));
        }
        AbstractGradleTask result = buildTask();
        return result;
    }

    @Override
    protected AbstractGradleTask buildTask() {
        String folder = salesforceProject.getProjectDirectory().getPath();
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
        TestResultTopComponent.addTestReport(salesforceProject.getProjectDirectory().getName(), currentFile.getPath(), destinationFolder.getPath(), TestResultItem.Status.running);
        return result;
    }

}
