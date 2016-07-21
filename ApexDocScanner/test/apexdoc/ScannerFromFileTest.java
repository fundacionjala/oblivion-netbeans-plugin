/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc;

import apexdoc.scanner.Scanner;
import apexdoc.utils.GenericPair;
import apexdoc.utils.ScannerUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * this class verifies the result of the scanner
 *
 * @author sergio_daza
 */
public class ScannerFromFileTest {

    @Test
    public void namespaceFromFileTest() {
        Scanner scanner = new Scanner("https://developer.salesforce.com/docs/", "35.0", "/en-us/198.0");
        List<GenericPair<String, String>> namespaces = scanner.getElements(ScannerUtils.getContent(getHtmlCode("./files/namespaces.json")), "li", "class=\"link ulchildlink\"");
        assertEquals(27, namespaces.size());
    }

    @Test
    public void methodsFromFileTest() {
        Scanner scanner = new Scanner("https://developer.salesforce.com/docs/", "35.0", "/en-us/198.0");
        List<GenericPair<String, String>> classes = scanner.getElements(getHtmlCode("./files/unlock_Result.html"), "div", "class=\"topic reference nested2\" lang=\"en-us\" lang=\"en-us\"");
        assertEquals(3, classes.size());
    }
    
    public String getHtmlCode(String path) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            String line;
            if ((line = br.readLine()) != null) {
                result.append(line);
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ScannerFromFileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.toString();
    }
}
