/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fundacionjala.oblivion.apex.actions.RunRelatedUnitTestAction;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.fundacionjala.oblivion.gradle.path.GradlePath;
import org.fundacionjala.oblivion.tab.testReport.TestResultItem;
import org.fundacionjala.oblivion.tab.testReport.TestResultTopComponent;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 * Create a Process based on a {@link AbstractGradleTask} and executes it in a separate process.
 * 
 * @author Adrian Grajeda
 */
public class GradleTaskExecutor implements Runnable, TaskListener, Cancellable {
    
    private static final RequestProcessor REQUEST_PROCESSOR = new RequestProcessor("interruptible tasks", 1, true);
    private static final Logger LOG = Logger.getLogger(GradleTaskExecutor.class.getName());
    private static final String EXECUTE_A_GRADLE_TASK = "Execute a gradle task";
    private static final String GRADLE_PATH = GradlePath.getGradlePath();
    protected final AbstractGradleTask gradleTask;
    protected ProgressHandle progressHandler;
    private GradleConnector gradleConnector;
    private RequestProcessor.Task processorTask = null;
    private final long startTime;
    private long endTime;

    /**
     * Short cut method to directly execute a {@link AbstractGradleTask}
     * @param gradleTask the task descriptor to be executed
     */
    public static void execute(AbstractGradleTask gradleTask) {
        new GradleTaskExecutor(gradleTask).execute();
    }
    
    /**
     * Creates a new instance of this class for the given {@link GradleTaskDescriptor}.
     * @param taskDescriptor the task descriptor 
     */
    public GradleTaskExecutor(AbstractGradleTask gradleTask) {
        this.gradleTask = gradleTask;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Create and launch a process to execute the gradle task
     */
    public void execute() {
        processorTask = REQUEST_PROCESSOR.create(this);
        processorTask.addTaskListener(this);
        processorTask.schedule(0);
    }

    /**
     * Starts the progress handler and executes the gradle task.
     */
    @Override
    public void run() {
        if (gradleTask != null && gradleTask.isValid()) {
            progressHandler = ProgressHandle.createHandle(gradleTask.getDescription(), this);
            progressHandler.start();            
            ProjectConnection connection = null;            
            try {
                connection = getGradleConnection();
                BuildLauncher buildLauncher = connection.newBuild();
                buildLauncher.forTasks(gradleTask.getName());
                String[] executionParameters = gradleTask.getExecutionParameters();
                buildLauncher.withArguments(executionParameters);
                LOG.info(EXECUTE_A_GRADLE_TASK);
                LOG.log(Level.INFO, "{0} {1} on folder: {2}", new Object[]{gradleTask.getName(), 
                                                                           Arrays.toString(executionParameters),
                                                                           gradleTask.getFolder()});
                GradleResultHandler resultHandler = gradleTask.getResultHandler();
                if (resultHandler != null) {
                    buildLauncher.setStandardError(resultHandler.getErrorOutputStream());
                    buildLauncher.setStandardOutput(resultHandler.getOutputStream());
                    buildLauncher.run(resultHandler);
                } else {
                    buildLauncher.run();
                }
            } catch(GradleConnectionException | IllegalStateException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Finishes the progress handler if it exists.
     * 
     * @param task A runnable task.
     */
    @Override
    public void taskFinished(Task task) {
        if (progressHandler != null) {
            this.endTime = System.currentTimeMillis();
            long durationTime = endTime - startTime;
            long second = (durationTime / 1000) % 60;
            long minute = (durationTime / (1000 * 60)) % 60;
            long hour = (durationTime / (1000 * 60 * 60)) % 24;
            String duration = String.format("Task Duration: %02d:%02d:%02d:%d", hour, minute, second, durationTime);
            LOG.log(Level.INFO, duration);
            progressHandler.finish();
            String nameProject = gradleTask.getParameter(RunRelatedUnitTestAction.PROJECT_NAME_PARAM);
            String pathFolder = gradleTask.getParameter(RunRelatedUnitTestAction.REPORT_DESTINATION_PARAM);
            String filePath = gradleTask.getParameter(RunRelatedUnitTestAction.FILE_PATH_PARAM);
            if (nameProject != null && pathFolder != null && filePath != null) {
                TestResultTopComponent.addTestReport(nameProject, filePath, pathFolder, TestResultItem.Status.successful);
            }
        }
    }

    /**
     * Cancels the process that is executing the gradle task.
     * 
     * @return false if the process has been finished.
     */
    @Override
    public boolean cancel() {
        if (processorTask != null) {
            return processorTask.cancel();
        }
        return false;
    }
    
    /**
     * Retrieves the gradle connection to the task folder.
     * 
     * @return a gradle connection to the task folder.
     */
    protected ProjectConnection getGradleConnection() {
        if (gradleConnector == null) {
            gradleConnector = GradleConnector.newConnector();
            File gradleHome = new File(GRADLE_PATH);
            LOG.log(Level.INFO, "Gradle Home directory");
            LOG.log(Level.INFO, gradleHome.getAbsolutePath());
            gradleConnector.useInstallation(gradleHome);
            gradleConnector.forProjectDirectory(new File(gradleTask.getFolder()));
        }
        return gradleConnector.connect();
    }
}
