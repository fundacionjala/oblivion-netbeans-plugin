/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor.indexSearcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.api.IndexSearcher;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;

/**
 * Searches for Types and Symbols in a Salesforce project.
 * @author Marcelo Garnica
 */
public class SalesforceIndexSearcher implements IndexSearcher {
    
    private static final String BUILD_FOLDER = "build";
    private static final String TEMP_FOLDER = "temp";
    private static final String INVALID_FOLDER_PREFIX = ".";
    private static final Set<String> INVALID_FOLDERS = new HashSet<>(Arrays.asList(BUILD_FOLDER, TEMP_FOLDER));
    private static final Set<String> INVALID_FILE_TYPES = new HashSet<>(Arrays.asList("data"));
    
    /**
     * Searches for Types that match a given search term on a Salesforce project.
     * @param project Salesforce project on which the types will be searched.
     * @param searchTerm Search Term that will be searched.
     * @param kind Kind of search
     * @param helper A default helper
     * @return 
     */
    @Override
    public Set<? extends Descriptor> getTypes(Project project, String searchTerm, QuerySupport.Kind kind, Helper helper) {
        Set<Descriptor> types = new HashSet<>();
        if (project == null) {
            project = ProjectUtils.getCurrentProject();
        }
        if (project != null) {
            FileObject projectDirectory = project.getProjectDirectory();
            Enumeration<? extends FileObject> projectFolders = projectDirectory.getFolders(false);
            searchTerm = searchTerm.toLowerCase();
            while (projectFolders.hasMoreElements()) {
                FileObject projectFolder = projectFolders.nextElement();
                if (!projectFolder.getName().startsWith(INVALID_FOLDER_PREFIX) && !INVALID_FOLDERS.contains(projectFolder.getName().toLowerCase())) {
                    types.addAll(getTypes(projectFolder, searchTerm, project));
                }
            }
        }
        return types;
    }

    /**
     * Searches for Symbols that match a given search term on a Salesforce project.
     * @param project Salesforce project on which the symbols will be searched.
     * @param searchTerm Search Term that will be searched.
     * @param kind Kind of search
     * @param helper A default helper
     * @return 
     */
    @Override
    public Set<? extends Descriptor> getSymbols(Project prjct, String string, QuerySupport.Kind kind, Helper helper) {
        return Collections.emptySet();
    }
    
    /**
     * Searches for Types that match a given search term on a given folder from a project.
     * @param folder The folder on which the types will be searched.
     * @param searchTerm Search Term that will be searched.
     * @param project The folder associated project.
     * @return 
     */
    private Set<? extends Descriptor> getTypes(FileObject folder, String searchTerm, Project project) {
        Set<Descriptor> types = new HashSet<>();
        Enumeration<? extends FileObject> dataFiles = folder.getData(true);
        while (dataFiles.hasMoreElements()) {
            FileObject dataFile = dataFiles.nextElement();
            if (!INVALID_FILE_TYPES.contains(dataFile.getExt().toLowerCase()) && dataFile.getName().toLowerCase().contains(searchTerm)) {
                types.add(new SalesforceTypeDescriptor(dataFile, project));
            }
        }
        return types;
    }
}
