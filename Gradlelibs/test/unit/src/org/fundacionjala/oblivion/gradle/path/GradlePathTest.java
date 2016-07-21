/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle.path;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link GradlePath}
 * @author Marcelo Garnica
 */
public class GradlePathTest {
    
    private final String expectedPathTemplate =  "%s%2$1s%3$s%2$1sMy User%2$1sAppData%2$1sRoaming%2$1sNetbeans%2$1s8.01%2$1smodules%2$1sgradle";
    
    /**
     * Test of getGradlePath method for windows OS.
     */
    @Test
    public void testGetGradlePathOnWindows() {
        String resourcePath = "jar:file:/C:/Users/My%20User/AppData/Roaming/Netbeans/8.01/modules/my_gradle_module.jar!/gradleJarFile";
        String fileSeparator = System.getProperty("file.separator");
        String startingCharacter = isUnix() ? fileSeparator : "";
        String expectedPath = String.format(expectedPathTemplate, startingCharacter + "C:", fileSeparator, "Users");
        String acutalPath = GradlePath.getGradlePath(resourcePath);
        assertEquals(expectedPath, acutalPath);
    }
    
    /**
     * Test of getGradlePath method for linux OS.
     */
    @Test
    public void testGetGradlePathOnLinux() {
        String resourcePath = "jar:file:/home/My%20User/AppData/Roaming/Netbeans/8.01/modules/my_gradle_module.jar!/gradleJarFile";
        String fileSeparator = System.getProperty("file.separator");
        String expectedPath = String.format(expectedPathTemplate, "", fileSeparator, "home");
        String acutalPath = GradlePath.getGradlePath(resourcePath);
        assertEquals(expectedPath, acutalPath);
    }
    
    /**
     * Test of getGradlePath when the resource path doesn't have a jar prefix.
     */
    @Test
    public void testGetGradlePathWithouJarPrefix() {
        String resourcePath = "/C:/Users/My%20User/AppData/Roaming/Netbeans/8.01/modules/my_gradle_module.jar!/gradleJarFile";
        String fileSeparator = System.getProperty("file.separator");
        String startingCharacter = isUnix() ? fileSeparator : "";
        String expectedPath = String.format(expectedPathTemplate, startingCharacter + "C:", fileSeparator, "Users");
        String acutalPath = GradlePath.getGradlePath(resourcePath);
        assertEquals(expectedPath, acutalPath);
    }
    
    /**
     * Test of getGradlePath method when the resource path doesn't have an exclamation mark.
     */
    @Test
    public void testGetGradlePathWithouExclamationMark() {
        String resourcePath = "/C:/Users/My%20User/AppData/Roaming/Netbeans/8.01/modules/gradleJarFile";
        String fileSeparator = System.getProperty("file.separator");
        String startingCharacter = isUnix() ? fileSeparator : "";
        String expectedPath = String.format(expectedPathTemplate, startingCharacter + "C:", fileSeparator, "Users");
        String acutalPath = GradlePath.getGradlePath(resourcePath);
        assertEquals(expectedPath, acutalPath);
    }
    
    private boolean isUnix() {
        String osName = System.getProperty("os.name").toLowerCase(); 
        return (osName.contains("nix") || osName.contains("nux") || osName.indexOf("aix") > 0 );
    }
}
