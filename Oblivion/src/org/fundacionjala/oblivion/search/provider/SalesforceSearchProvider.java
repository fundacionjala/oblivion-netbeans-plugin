/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.search.provider;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

public class SalesforceSearchProvider implements SearchProvider {

    /**
     * Shows search results on search bar.
     */
    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {
        List<FileObject> foundFiles;
        Project project = ProjectUtils.getCurrentProject();
        if (project != null) {
            FileObject projectDirectory = project.getProjectDirectory();
            String fileName = request.getText();
            foundFiles = searchFile(projectDirectory, fileName);
            for (FileObject foundFile : foundFiles) {
                String foundFileName = foundFile.getNameExt();
                response.addResult(new SearchResult(foundFile), foundFileName);
            }
        }
    }

    /**
     * Search a fileObject by name.
     *
     * @return a List<FileObject> with found FileObjects.
     */
    List<FileObject> searchFile(FileObject fileObject, String fileName) {
        List<FileObject> result = new ArrayList<>();
        FileObject[] children = fileObject.getChildren();
        if (children.length > 0) {
            for (FileObject child : children) {
                result.addAll(searchFile(child, fileName));
            }
        } else {
            if (!fileObject.isFolder() && fileObject.getName().toLowerCase().contains(fileName.toLowerCase())) {
                result.add(fileObject);
            }
        }
        return result;
    }

    /**
     * Class Runnable which will open the selected file on editor.
     */
    private static class SearchResult implements Runnable {

        private final FileObject foundFile;

        public SearchResult(FileObject foundFile) {
            this.foundFile = foundFile;
        }

        /**
         * Opens a fileObject in a new label on editor.
         */
        @Override
        public void run() {
            ProjectUtils.openFile(foundFile);
        }
    }

}
