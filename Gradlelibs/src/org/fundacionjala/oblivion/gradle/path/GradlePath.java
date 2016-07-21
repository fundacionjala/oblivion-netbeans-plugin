/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.gradle.path;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that retrieves the gradle directory path.
 * 
 * @author Marcelo Garnica
 */
public class GradlePath {
    
    private static final Logger LOG = Logger.getLogger(GradlePath.class.getName());
    private static final String EXCLAMATION_MARK = "!";
    private static final String JAR_FILE_PREFIX = "jar:file:";
    private static final String FILE_PREFIX = "file://";
    private static final String GRADLE_FOLDER = "gradle";
    
    /**
     * Retrieves the directory on which the gradle lib files are stored.
     * @return gradlePath - the gradle directory path.
     */
    public static String getGradlePath() {
        String gradlePath = getGradlePath(GradlePath.class.getResource("").toString());
        LOG.log(Level.INFO, "Gradle path: {0}", gradlePath);
        return gradlePath;
    }

    static String getGradlePath(String resourcePath) {
        String gradlePath = "";
        String resourceFilePath = resourcePath;
        int jarFileIndex = resourcePath.lastIndexOf(EXCLAMATION_MARK);
        if (jarFileIndex > -1) {
            resourceFilePath = resourcePath.substring(0, jarFileIndex);
        }
        if (resourceFilePath.startsWith(JAR_FILE_PREFIX)) {
            resourceFilePath = resourceFilePath.replaceAll(JAR_FILE_PREFIX, FILE_PREFIX);
        }
        if (!resourceFilePath.startsWith(FILE_PREFIX)) {
            resourceFilePath = FILE_PREFIX + resourceFilePath;
        }
        try {
            Path jarPath = Paths.get(new URI(resourceFilePath));
            gradlePath = jarPath.resolveSibling(GRADLE_FOLDER).toString();
        } catch (InvalidPathException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return gradlePath;
    }
}
