/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc;

import apexdoc.utils.ScannerUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class verifies the download of a json from an url
 *
 * @author sergio_daza
 */
public class DownloadJsonTest {

    /**
     * this method verify if exists a json with documentation of namespaces for
     * the url
     */
    @Test
    public void donwloadJsonforNamespacesTest() {
        boolean result;
        try {
            String json = ScannerUtils.getText("https://developer.salesforce.com/docs/get_document_content/apexcode/apex_reference.htm/en-us/198.0");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            result = !jsonObject.isEmpty();
        } catch (ParseException ex) {
            result = false;
            Logger.getLogger(DownloadJsonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(true, result);
    }

    /**
     * this method verify if exists a json with documentation of classes for the
     * url
     */
    @Test
    public void donwloadJsonforClassesTest() {
        boolean result;
        try {
            String[] docUrlSplit = "https://developer.salesforce.com/docs/atlas.en-us.198.0.apexcode.meta/apexcode/apex_namespace_Approval.htm".split("/");
            String jsonUrl = "https://developer.salesforce.com/docs/get_document_content/apexcode/" + docUrlSplit[docUrlSplit.length - 1] + "/en-us/198.0";
            String json = ScannerUtils.getText(jsonUrl);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            result = !jsonObject.isEmpty();
        } catch (ParseException ex) {
            result = false;
            Logger.getLogger(DownloadJsonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(true, result);
    }
}
