/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import org.fundacionjala.oblivion.salesforce.project.nodes.AbstractFolderNode;
import org.fundacionjala.oblivion.salesforce.project.nodes.SalesforceDataNode;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Utilities;

/**
 * Utility class to get the current project and other useful information
 *
 * @author adrian_grajeda
 */
public class ProjectUtils {

    public static Lookup lookup;
    
    /**
     * Returns the current selected project
     *
     * @return the selected project
     */
    public static Project getCurrentProject() {
        Project result = getLookup().lookup(Project.class);
        if (result == null) {
            DataObject data = getLookup().lookup(DataObject.class);
            if (data != null) {
                FileObject primary = data.getPrimaryFile();
                result = FileOwnerQuery.getOwner(primary);
            }
        }
        releaseLookup();
        return result;
    }

    public static String getCurrentProjectPath() {
        return getCurrentProject().getProjectDirectory().getPath();
    }

    /**
     * Returns the selected file's path in the current project
     *
     * @return the select file's path in the project, or null if not found
     */
    public static String getCurrentFilePath() {
        FileObject currentFile = getCurrentFile();
        if (currentFile != null) {
            return currentFile.getPath();
        }
        return null;
    }
    
    /**
     * Returns the selected file in the current project
     *
     * @return the select file in the project, or null if not found
     */
    public static FileObject getCurrentFile() {
        DataObject data = getLookup().lookup(DataObject.class);
        if (data != null) {
            return data.getPrimaryFile();
        }
        return null;
    }
    
    /**
     * Returns the current file's path relative to the src folder.
     * 
     * @return the current file's path relative to the src folder, or null if there is no current file.
     */
    public static String getCurrentFilePathRelativeToSrcFolder() {
        return getPathRelativeToSrcFolder(getCurrentFile());
    }
    
    public static String getPathRelativeToSrcFolder(FileObject fileObject) {
        if (fileObject != null) {
            SalesforceProject salesforceProject = (SalesforceProject) getCurrentProject();
            FileObject sourceFolder = salesforceProject.getProjectDirectory().getFileObject(SalesforceProject.SOURCE_FOLDER);
            Path path = Paths.get(fileObject.getPath().replaceAll(sourceFolder.getPath(), ""));
            return path.toString().substring(1);
        }
        return null;
    }
    
    /**
     * Returns the selected Salesforce Data Node in the current project
     *
     * @return the select Salesforce Data Node in the project, or null if not found
     */
    public static Node getCurrentNode() {
        return getLookup().lookup(SalesforceDataNode.class);
    }
    
    public static AbstractFolderNode getCurrentFolderNode() {
        return getLookup().lookup(AbstractFolderNode.class);
    }
    
    /**
     * Opens a FileObject on the current Netbeans instance.
     * 
     * @param fileObject The FileObject to be opened.
     */
    public static void openFile(FileObject fileObject) {
        fileObject.getLookup().lookup(OpenCookie.class).open();
    }

    /**
     * @return the lookup
     */
    static Lookup getLookup() {
        if (lookup == null) {
            lookup = Utilities.actionsGlobalContext();
        }
        return lookup;
    }

    private static void releaseLookup() {
        lookup = null;
    }

    public static Token<? extends ApexTokenId> getApexTokenAt(Document document, int offset) {
        TokenHierarchy<Document> tokenHierarchy = TokenHierarchy.get(document);
        TokenSequence<?> ts = tokenHierarchy.tokenSequence();
        ts.move(offset);
        ts.moveNext();
        Token<? extends ApexTokenId> token = (Token<? extends ApexTokenId>) ts.token();
        return token;
    }
}
