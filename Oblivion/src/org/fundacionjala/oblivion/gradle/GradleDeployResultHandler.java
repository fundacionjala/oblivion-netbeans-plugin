/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.gradle.tooling.GradleConnectionException;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode;
import org.fundacionjala.oblivion.apex.errors.ServerErrors;
import org.fundacionjala.oblivion.messages.MessagesUtil;
import org.fundacionjala.oblivion.salesforce.project.SalesforceProject;
import org.fundacionjala.oblivion.tab.salesforceErrors.SalesforceErrorsTopComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 * Class that handles the result of a Deploy to Salesforce task.
 * @author Marcelo Garnica
 */
public class GradleDeployResultHandler extends GradleResultHandler {

    public static final String DEPLOY_ERROR_LAYER = "DEPLOY_ERROR_LAYER";
    private static final Logger LOG = Logger.getLogger(GradleDeployResultHandler.class.getName());
    private static final String ERROR_FILENAME = "fileName";
    private static final String ERROR_PROBLEM = "problem";
    private static final String ERROR_LINE = "line";
    private static final String ERROR_COLUMN = "column";
    private static final String FILENAME_SEPARATOR = "/";
    static final String DEPLOY_ERROR_MESSAGE_FORMAT = "%s%nAt line %d, column %d";
    public SalesforceProject salesforceProject = null;

    private Map<String, DocumentErrorsWrapper> documents;

    public GradleDeployResultHandler(FileObject sourceFile, String errorMessage) {
        super(errorMessage);
        documents = new HashMap<>();
        try {
            if (sourceFile.isFolder()) {
                putMultipleDocumentsErrors(sourceFile);
            } else {
                documents.put(sourceFile.getNameExt(), new DocumentErrorsWrapper(sourceFile));
            }
        } catch (DataObjectNotFoundException ex){
            Exceptions.printStackTrace(ex);
        }
    }

    public GradleDeployResultHandler(SalesforceProject project, FileObject sourceFile, String errorMessage) {
        super(errorMessage);
        this.salesforceProject = project;
        documents = new HashMap<>();
        try {
            if (sourceFile.isFolder()) {
                putMultipleDocumentsErrors(sourceFile);
            } else {
                documents.put(sourceFile.getNameExt(), new DocumentErrorsWrapper(sourceFile));
            }
        } catch (DataObjectNotFoundException ex){
            Exceptions.printStackTrace(ex);
        }
    }
    
    Map<String, DocumentErrorsWrapper> getDocuments() {
        return documents;
    }

    private void putMultipleDocumentsErrors(FileObject sourceFile) throws DataObjectNotFoundException{
        DataFolder currentFolder = DataFolder.findFolder(sourceFile);
        Enumeration<DataObject> children = currentFolder.children(true);
        while (children.hasMoreElements()) {
            DataObject nextElement = children.nextElement();
            FileObject primaryFile = nextElement.getPrimaryFile();
            if (primaryFile != null && primaryFile.isData()) {
                documents.put(primaryFile.getNameExt(), new DocumentErrorsWrapper(primaryFile));
            }
        }
    }

    @Override
    public void onComplete(Object t) {
        super.onComplete(t);
        for (DocumentErrorsWrapper documentError : documents.values()) {
            StyledDocument tempDocument = documentError.getDocument();
            if (tempDocument != null) {
                ServerErrors.getInstance().removeErrors(documentError.documentFileObject);
                HintsController.setErrors(tempDocument, DEPLOY_ERROR_LAYER, new ArrayList<ErrorDescription>());
                documentError.updateIcon();
            }
        }
    }

    @Override
    public void onFailure(GradleConnectionException gradleConnectionException) {
        LOG.log(Level.INFO, outputStream.toString());
        LOG.log(Level.INFO, errorOutputStream.toString());
        Throwable realException = getRealException(gradleConnectionException);
        boolean createdErrors = createErrors(realException);
        if (createdErrors) {
            for (DocumentErrorsWrapper documentError : documents.values()) {
                ServerErrors.getInstance().addErrors(documentError.documentFileObject, documentError.getErrors());
                StyledDocument document = documentError.getDocument();
                List<ErrorDescription> errors = documentError.getErrors();
                if (document != null && errors != null) {
                    HintsController.setErrors(document, DEPLOY_ERROR_LAYER, errors);
                }
                documentError.updateIcon();
            }
        } else {
            SalesforceErrorsTopComponent.addNofitfy(salesforceProject,getDisplayableErrorMessage(realException));
            MessagesUtil.showError(getDisplayableErrorMessage(realException));
        }
    }
    
    /**
     * Creates the error messages that will be dipslayed on various documents based on the message of a given deploy
     * exception.
     * @param deployException - The deploy exception which contains the errors to be displayed.
     * @return Whether the errors could be created from the deploy exception.
     */
    private boolean createErrors(Throwable deployException) {
        Boolean createdErrors = true;
        String exceptionMessage = getExceptionMessage(deployException);
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject parseResult = (JSONObject) jsonParser.parse(exceptionMessage);
            JSONArray errors = (JSONArray) parseResult.get("errors");
            String fileName;
            String problem;
            int line;
            int column;
            for (Object error : errors) {
                JSONObject jsonError = (JSONObject) error;
                fileName = (String)jsonError.get(ERROR_FILENAME);
                problem = (String)jsonError.get(ERROR_PROBLEM);
                line = ((Long)jsonError.get(ERROR_LINE)).intValue();
                if (line < 1) {
                    line = 1;
                }
                column = ((Long)jsonError.get(ERROR_COLUMN)).intValue();
                if (column < 1) {
                    column = 1;
                }
                SalesforceErrorsTopComponent.addNofitfy(salesforceProject,problem, fileName,line, column);
                createError(fileName, problem, line, column);
            }
        } catch (ParseException | BadLocationException ex) {
            createdErrors = false;
        }
        return createdErrors;
    }

    /**
     * Creates the error message that will be displayed on a document.
     * @param fileName - The document's file name.
     * @param errorDescription - The error description.
     * @param line - On which line the error will be displayed.
     * @param column - On which column the error will be displayed.
     * @throws BadLocationException
     */
    private void createError(String fileName, String errorDescription, int line, int column) throws BadLocationException {
        fileName = fileName.substring(fileName.lastIndexOf(FILENAME_SEPARATOR) + 1);
        StyledDocument document = documents.get(fileName).getDocument();
        int errorPosition = NbDocument.findLineOffset(document, line - 1) + column - 1;
        documents.get(fileName).getErrors().add(ErrorDescriptionFactory.createErrorDescription(
            Severity.ERROR,
            String.format(DEPLOY_ERROR_MESSAGE_FORMAT, errorDescription, line, column),
            document,
            document.createPosition(errorPosition),
            document.createPosition(errorPosition)
        ));
    }

    /**
     * Innerclass which wraps the related errors, node and document of a FileObject.
     */
    private class DocumentErrorsWrapper {
        private final StyledDocument document;
        private final List<ErrorDescription> errors;
        private final FileObject documentFileObject;
        private final Node node;

        public DocumentErrorsWrapper(FileObject documentFileObject) throws DataObjectNotFoundException {
            this.documentFileObject = documentFileObject;
            this.document = (StyledDocument) Source.create(documentFileObject).getDocument(true);
            this.errors = new ArrayList<>();
            this.node = DataObject.find(documentFileObject).getNodeDelegate();
        }

        public StyledDocument getDocument() {
            return document;
        }

        public List<ErrorDescription> getErrors() {
            return errors;
        }

        public FileObject getDocumentFileObject() {
            return documentFileObject;
        }

        public SalesforceDataNode getSalesforceNode() {
            return node instanceof SalesforceDataNode ? (SalesforceDataNode) node : null;
        }

        /**
         * Sets the flag for server errors of the node which will trigger the icon change.
         */
        private void updateIcon() {
            if (node instanceof SalesforceDataNode) {
                SalesforceDataNode currentNode = (SalesforceDataNode)node;
                currentNode.setHasServerErrors(!errors.isEmpty());
            }
        }
    }
}
