/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc;

import org.json.simple.parser.ParseException;
import apexdoc.scanner.Scanner;

/**
 * This class ejecute the scanner.
 *
 * @author sergio_daza
 */
public class ApexDocScanner {

    /**
     * @param args the command line arguments
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws ParseException {
//        Scanner scanner = new Scanner("https://developer.salesforce.com/docs/", "29.0", "/en-us/186.0");
        Scanner scanner = new Scanner("https://developer.salesforce.com/docs/", "35.0", "/en-us/198.0");
        scanner.scannerOfNamespaces("https://developer.salesforce.com/docs/get_document_content/apexcode/apex_reference.htm/en-us/198.0");
        scanner.saveJson("./dist/");
        System.out.println("result \n"+scanner.toString());
    }
}
