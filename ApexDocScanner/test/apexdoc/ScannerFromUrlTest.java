/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc;

import apexdoc.scanner.Scanner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * this class verifies the result of the scanner
 *
 * @author sergio_daza
 */
public class ScannerFromUrlTest {

    Scanner scanner = new Scanner("https://developer.salesforce.com/docs/", "35.0", "/en-us/198.0");

    public ScannerFromUrlTest() {
        scanner.scannerOfClasses("https://developer.salesforce.com/docs/atlas.en-us.198.0.apexcode.meta/apexcode/apex_namespace_Approval.htm", "approval namespace");
    }

    /**
     * This method verifies that it found namespaces, classes and methods.
     */
    @Test
    public void ScannerOfClassesTest() {
        assertEquals(true, scanner.numClasses > 0);
        assertEquals(true, scanner.numMethods > 0);
    }

    /**
     * This method verifies that the json is saved.
     */
    @Test
    public void SaveJsonTest() {
        assertEquals(false, scanner.jsonObject.isEmpty());
        assertEquals(true, scanner.saveJson("./test/apexdoc/files/"));
    }

}
