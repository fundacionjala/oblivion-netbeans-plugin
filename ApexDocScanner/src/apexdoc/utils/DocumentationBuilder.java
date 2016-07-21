/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author sergio_daza
 */
public abstract class DocumentationBuilder {

    protected final String className;
    protected final String htmlCode;
    protected final String classUrl;
    protected final String api;
    protected final int sizeHtmlCode;
    protected final JSONObject jsonObject = new JSONObject();
    protected final JSONArray modifiers = new JSONArray();
    protected static final List<String> MODIFIER_LIST = new ArrayList<>();
    protected boolean errorsOnParameters = false;
    protected boolean isConstructor = false;
    protected static final String EMPTY_STRING = "";
    protected static final String CAPTION_TYPE = "type";
    protected static final String CAPTION_DESCRIPTION = "description";
    protected static final String CAPTION_NAME = "name";
    protected static final String CAPTION_MODIFIERS = "modifiers";
    protected static final String CAPTION_PARAMETERS = "parameters";
    protected static final String CAPTION_RETURN = "return";
    protected static final String LESS_THAN = "<";
    protected static final String GREATER_THAN = ">";
    protected static final String HTML_LESS_THAN = "&lt;";
    protected static final String HTML_GREATER_THAN = "&gt;";
    protected static final char BLANK_CHARACTER = ' ';
    protected String tittle = EMPTY_STRING;
    protected String description = EMPTY_STRING;
    protected String signature = EMPTY_STRING;
    protected String parameters = EMPTY_STRING;
    protected String returnType = EMPTY_STRING;
    protected String type = EMPTY_STRING;

    static {
        MODIFIER_LIST.add("public");
        MODIFIER_LIST.add("proptected");
        MODIFIER_LIST.add("protected");
        MODIFIER_LIST.add("static");
        MODIFIER_LIST.add("final");
    }

    protected DocumentationBuilder(String api, String htmlCode, String className, String classUrl) {
        this.api = api;
        this.htmlCode = htmlCode;
        this.sizeHtmlCode = this.htmlCode.length();
        this.className = className;
        recoverInformation();
        this.classUrl = classUrl;
    }
    
    protected void recoverInformation() {
        
        Map<String, TagHTML> datas = new HashMap<>();
        TagHTML title = new TagHTML("span", "class=\"titlecodeph\"");
        TagHTML description = new TagHTML("div", "class=\"shortdesc\"");
        TagHTML signature = new TagHTML("samp", "class=\"codeph apex_code\"");
        for (int i = 0; i < sizeHtmlCode; i++) {
            char charAt = htmlCode.charAt(i);
            title.isTag(charAt);
            description.isTag(charAt);
            if (title.located() && description.located()) {
                for (int j = i; j < sizeHtmlCode; j++) {
                    i++;
                    charAt = htmlCode.charAt(j);
                    signature.isTag(charAt);
                    if (signature.located()) {
                        break;
                    }
                }
                break;
            }
        }
        this.tittle = getName(title.getValue());
        this.description = ScannerUtils.removeTags(description.getValue());
        this.signature = ScannerUtils.removeTags(signature.getValue().replaceAll(", ", ","));
        scannerSignature(0);
    }
    
    public JSONObject getJsonObject() {
        jsonObject.put(CAPTION_TYPE, this.type);
        jsonObject.put(CAPTION_DESCRIPTION, buildDescription());
        jsonObject.put(CAPTION_NAME, tittle);
        jsonObject.put(CAPTION_MODIFIERS, modifiers);
        jsonObject.put(CAPTION_PARAMETERS, parameters.replaceAll("\\(", EMPTY_STRING).replaceAll("\\)", EMPTY_STRING));
        jsonObject.put(CAPTION_RETURN, returnType.replaceAll(LESS_THAN, HTML_LESS_THAN).replaceAll(GREATER_THAN, HTML_GREATER_THAN));
        return this.jsonObject;
    }
    
    protected String buildDescription() {
        String name = signature.replaceAll(tittle, "%lb%r" + this.tittle + "%l/b%r");
        String result = "%lh3 style=\" color: blue; \"%rApex. %la href = \"" + classUrl + "\"%r " + className + " %l/a%r%l/h3%r"
                + "%li%r " + name + " %l/i%r %lbr%r %lbr%r"
                + description;
        if (!this.isConstructor) {
            result += "%lh3%rReturns: %l/h3%r" + returnType;
        }
        result += "%lbr%r %lbr%r %lb%rApi%l/b%r " + this.api;
        result = result.replaceAll(LESS_THAN, HTML_LESS_THAN).replaceAll(GREATER_THAN, HTML_GREATER_THAN);
        result = result.replaceAll("%l", LESS_THAN).replaceAll("%r", GREATER_THAN);
        return result;
    }
    
    /**
     * This method scanning the signature for  recover the parameters, modifiers,
     * type return. 
     * For methods define if it is constructor.
     * 
     * @param index 
     */
    protected abstract void scannerSignature(int index);
    
    /**
     * This method recovers the name from the tittle.
     * 
     * @param text
     * @return 
     */
    protected abstract String getName(String text);
    
    /**
     * Return true if it meet the conditions.
     * 
     * @return 
     */
    public abstract boolean isValid();
    
}
