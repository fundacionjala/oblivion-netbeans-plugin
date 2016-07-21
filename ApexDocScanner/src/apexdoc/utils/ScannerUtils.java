/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import apexdoc.scanner.Scanner;

/**
 * Utils for the scanner of documentation
 *
 * @author sergio_daza
 */
public class ScannerUtils {

    private static final char BLANK_CHARACTER = ' ';
    private static final String EMPTY_STRING = "";
    private static final String BLANK_SPACE = " ";
    private static final String EMD_LINE = "\n";
    private static final String TAB = "\t";
    private static final String LESS_THAN = "<";
    private static final String GREATER_THAN = ">";
    private static final char CHAR_LESS_THAN = '<';
    private static final char CHAR_GREATER_THAN = '>';
    private static final String HTML_LESS_THAN = "&lt;";
    private static final String HTML_GREATER_THAN = "&gt;";
    private static final String CAPTION_CONTENT = "content";

    public static String getContent(String json) {
        String result = EMPTY_STRING;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            result = (String) jsonObject.get(CAPTION_CONTENT);
        } catch (ParseException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }
        return result;
    }

    public static String getText(String path) {
        String result = EMPTY_STRING;
        try {
            URL url = new URL(path);
            BufferedReader bs = new BufferedReader(new InputStreamReader(url.openStream()));
            String txt;
            while ((txt = bs.readLine()) != null) {
                result += txt;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ScannerUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScannerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static String removeTags(String htmlCode) {
        htmlCode = htmlCode.replaceAll(TAB, BLANK_SPACE).replaceAll(EMD_LINE, BLANK_SPACE);
        String result = EMPTY_STRING;
        if (!htmlCode.isEmpty()) {
            char lastChar = htmlCode.charAt(0);
            boolean disregard = false;
            boolean hasSpace = false;
            for (int i = 0; i < htmlCode.length(); i++) {
                char charAt = htmlCode.charAt(i);
                if (charAt == CHAR_LESS_THAN) {
                    disregard = true;
                } else if (charAt == CHAR_GREATER_THAN) {
                    disregard = false;
                    continue;
                }
                if (!disregard) {
                    if (charAt == BLANK_CHARACTER && !hasSpace) {
                        result = result + charAt;
                        hasSpace = true;
                    }
                    if (charAt != BLANK_CHARACTER) {
                        result = result + charAt;
                    }
                    if (lastChar != charAt) {
                        hasSpace = false;
                    }
                    lastChar = charAt;
                }
            }
            result = result.replaceAll(HTML_LESS_THAN, LESS_THAN).replaceAll(HTML_GREATER_THAN, GREATER_THAN);
        }
        return result;
    }

}
