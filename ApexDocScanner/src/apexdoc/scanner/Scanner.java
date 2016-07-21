/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package apexdoc.scanner;

import apexdoc.utils.MethodDocumentionBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import apexdoc.utils.GenericPair;
import apexdoc.utils.ListTagHTML;
import apexdoc.utils.ScannerUtils;
import apexdoc.utils.PropertyDocumentationBuilder;
import apexdoc.utils.MethodDocumentionBuilder;
import apexdoc.utils.EnumItemDocumentationBuilder;

/**
 * This class recover the documentation from the html code.
 *
 * @author sergio_daza
 */
public class Scanner {

    private final String rootPath;
    private final String api;
    private final String fileName;
    private final JSONArray classlist = new JSONArray();
    private final JSONArray interfacelist = new JSONArray();
    private final JSONArray enumlist = new JSONArray();
    public final JSONObject jsonObject = new JSONObject();
    private static final String EMPTY_STRING = "";
    private static final char BLANK_CHARACTER = ' ';
    public int numNamespace;
    public int numClasses;
    public int numMethods;
    public int numProperty;
    public int numLinks;
    public int numInterfaces;
    public int numEnum;
    public int numEnumItem;
    private final String urlComplement;

    /**
     *
     * @param rootPath
     * @param api
     * @param complementUrl
     */
    public Scanner(String rootPath, String api, String urlComplement) {
        this.rootPath = rootPath;
        this.api = api;
        this.urlComplement = urlComplement;
        numNamespace = 0;
        numClasses = 0;
        numMethods = 0;
        numProperty = 0;
        numInterfaces = 0;
        numEnum = 0;
        numEnumItem = 0;
        numLinks = 0;
        fileName = "ApexDoc_" + api;
        jsonObject.put("api", api);
    }

    /**
     * Recovers the namespaces from a url.
     *
     * @param url
     */
    public void scannerOfNamespaces(String url) {
        searchNamespaces(ScannerUtils.getContent(ScannerUtils.getText(url)));
    }

    public void searchNamespaces(String htmlCode) {
        this.numLinks++;
        List<GenericPair<String, String>> namespaces = getElements(htmlCode, "li", "class=\"link ulchildlink\"");
        this.numNamespace = namespaces.size();
        for (GenericPair<String, String> value : namespaces) {
            int indexOf = value.getSecond().indexOf("<br>");
            for (GenericPair<String, String> tagA : getTagA(value.getSecond())) {
                if (tagA.getSecond().contains("Namespace")) {
                    for (GenericPair urlFromTag : getAttributes(tagA.getFirst())) {
                        if (urlFromTag.getFirst().equals("href")) {
                            String docUrl = this.rootPath + urlFromTag.getSecond();
                            scannerOfClasses(docUrl, tagA.getSecond());
                        }
                    }
                }
            }
        }

    }

    /**
     * Recovers the classes from a url.
     *
     * @param docUrl
     * @param nameSpace
     */
    public void scannerOfClasses(String docUrl, String nameSpace) {
        String[] docUrlSplit = docUrl.split("/");
        String jsonUrl = "https://developer.salesforce.com/docs/get_document_content/apexcode/" + docUrlSplit[docUrlSplit.length - 1] + urlComplement;
       
        searchClasses(ScannerUtils.getContent(ScannerUtils.getText(jsonUrl)), nameSpace, docUrl);
    }

    public void searchClasses(String htmlCode, String nameSpace, String docUrl) {
        this.numLinks++;
        List<GenericPair<String, String>> classes = getElements(htmlCode, "li", "class=\"link ulchildlink\"");
        for (GenericPair<String, String> value : classes) {
            String classUrl = EMPTY_STRING;
            int indexOf = value.getSecond().indexOf("<br>");
            List<GenericPair<String, String>> tagAList = getTagA(value.getSecond());
            if (!tagAList.isEmpty()) {
                GenericPair<String, String> tagA = tagAList.get(0);
                boolean isClass = tagA.getSecond().contains("Class");
                boolean isInterface = tagA.getSecond().contains("Interface");
                boolean isEnum = tagA.getSecond().contains("Enum");
                if (isClass || isInterface || isEnum) {
                    JSONObject jsonObject = new JSONObject();
                    if(isClass) {
                        jsonObject.put("name", tagA.getSecond().replaceAll("Class", EMPTY_STRING).trim());
                        this.numClasses++;
                    } else if(isInterface) {
                        jsonObject.put("name", tagA.getSecond().replaceAll("Interface", EMPTY_STRING).trim());
                        this.numInterfaces++;
                    } else {
                        jsonObject.put("name", tagA.getSecond().replaceAll("Enum", EMPTY_STRING).trim());
                        this.numEnum++;
                    }
                    for (GenericPair urlFromTag : getAttributes(tagA.getFirst())) {
                        if (urlFromTag.getFirst().equals("href")) {
                            classUrl = this.rootPath + urlFromTag.getSecond();
                            String[] classUrlSplit = classUrl.split("/");
                            String url = classUrlSplit[classUrlSplit.length - 1];
                            int indexOfChar = url.indexOf("#");
                            String jsonClassUrl = "https://developer.salesforce.com/docs/get_document_content/apexcode/" + url + urlComplement;
                            if (indexOfChar > 0) {
                                jsonClassUrl = "https://developer.salesforce.com/docs/get_document_content/apexcode/" + url.substring(0, indexOfChar) + urlComplement;
                            }
                            if(isEnum) { 
                                jsonObject = getElementsOfEnum(jsonObject, jsonClassUrl, classUrl, tagA.getSecond().replaceAll("Class", EMPTY_STRING).trim());
                            } else {
                                jsonObject = getElementsOfClass(jsonObject, jsonClassUrl, classUrl, tagA.getSecond().replaceAll("Class", EMPTY_STRING).trim());
                            }
                        }
                    }
                    String description = "<h3 style=\" color: blue; \" >Apex.  <a href = \"" + docUrl + "\">" + nameSpace + "</h3> "
                            + "<h3>" + tagA.getSecond() + "</h3>"
                            + ScannerUtils.removeTags(value.getSecond().substring(indexOf + "<br>".length()))
                            + "<br><br><a href = \"" + classUrl + "\">" + classUrl + "</a>"
                            + "<br><br><b>Api</b> " + api;
                    jsonObject.put("description", description);
                    if(isClass) {
                        classlist.add(jsonObject);
                    } else if(isInterface) {
                        interfacelist.add(jsonObject);
                    } else {
                        enumlist.add(jsonObject);
                    }
                }
            }
        }

    }

    /**
     * Recovers the methods for a class.
     *
     * @param jsonClassUrl
     * @param classUrl
     * @param className
     * @return a method list.
     */
    public JSONObject getElementsOfClass(JSONObject jsonObject, String jsonClassUrl, String classUrl, String className) {
        JSONArray methodlist = new JSONArray();
        JSONArray propertylist = new JSONArray();
        String htmlCode = ScannerUtils.getContent(ScannerUtils.getText(jsonClassUrl));
        this.numLinks++;
        List<GenericPair<String, String>> methods = getElements(htmlCode, "div", "class=\"topic reference nested2\" lang=\"en-us\" lang=\"en-us\"");
        for (GenericPair<String, String> method : methods) {
            MethodDocumentionBuilder methodDoc = new MethodDocumentionBuilder(this.api, method.getSecond(), className, classUrl);
            if (methodDoc.isValid()) {
                methodlist.add(methodDoc.getJsonObject());
                this.numMethods++;
            } else {
                PropertyDocumentationBuilder propertyDoc = new PropertyDocumentationBuilder(this.api, method.getSecond(), className, classUrl);
                if(propertyDoc.isValid()) {
                    propertylist.add(propertyDoc.getJsonObject());
                    this.numProperty++;
                } 
            }
        }
        jsonObject.put("methods", methodlist);
        jsonObject.put("property", propertylist);
        return jsonObject;
    }
    
    private JSONObject getElementsOfEnum(JSONObject jsonObject, String jsonEnumUrl, String classUrl, String className) {
        JSONArray enumItems = new JSONArray();
        String htmlCode = ScannerUtils.getContent(ScannerUtils.getText(jsonEnumUrl));
        this.numLinks++;
        List<GenericPair<String, String>> tableBody = getElements(htmlCode, "tbody", "class=\"tbody\"");
        if(!tableBody.isEmpty()) {
            List<GenericPair<String, String>> trTags = getElements(htmlCode, "tr", "");
            for (GenericPair<String, String> trTag : trTags) {
                EnumItemDocumentationBuilder enumItem = new EnumItemDocumentationBuilder(this.api, trTag.getSecond(), className, classUrl);
                if(enumItem.isValid()) {
                    enumItems.add(enumItem.getJsonObject());
                    this.numEnumItem++;
                }
            }
        }
        jsonObject.put("enumItem", enumItems);
        return jsonObject;
    }

    /**
     * Recover the attributes from a html code.
     *
     * @param htmlCode
     * @return
     */
    public List<GenericPair<String, String>> getAttributes(String htmlCode) {
        htmlCode = htmlCode.replaceAll("<", EMPTY_STRING).replaceAll(">", EMPTY_STRING).trim();
        List<GenericPair<String, String>> attributes = new ArrayList<>();
        String valueAttribute = EMPTY_STRING;
        boolean hasSpace = false;
        GenericPair<Integer, String> nameAttribute = getNameAttribute(htmlCode);
        attributes.add(new GenericPair("name", nameAttribute.getSecond()));
        attributes.addAll(getAttributes(nameAttribute.getFirst(), htmlCode.trim()));
        return attributes;
    }

    private List<GenericPair<String, String>> getAttributes(Integer index, String htmlCode) {
        List<GenericPair<String, String>> result = new ArrayList<>();
        String word = EMPTY_STRING;
        String prefix = EMPTY_STRING;
        boolean foundPrefix = false;
        while (index < htmlCode.length()) {
            char charAt = htmlCode.charAt(index);
            index++;
            if (charAt == BLANK_CHARACTER || charAt == '=') {
                foundPrefix = true;
            }
            if (charAt != BLANK_CHARACTER) {
                if (foundPrefix) {
                    word += charAt;
                } else {
                    prefix += charAt;
                }
            }
            if (charAt == BLANK_CHARACTER && foundPrefix) {
                break;
            }
        }
        if (!prefix.trim().isEmpty() && !word.trim().isEmpty()) {
            result.add(new GenericPair(prefix, word.trim().replaceAll("\"", EMPTY_STRING).replaceAll("=", EMPTY_STRING)));
        }
        if (index < htmlCode.length()) {
            result.addAll(getAttributes(index, htmlCode.trim()));
        }
        return result;
    }

    /**
     * Recover the tag of type A from a html code.
     *
     * @param htmlCode
     * @return
     */
    public List<GenericPair<String, String>> getTagA(String htmlCode) {
        ListTagHTML listTagA = new ListTagHTML("a", EMPTY_STRING);
        for (int i = 0; i < htmlCode.length(); i++) {
            char charAt = htmlCode.charAt(i);
            listTagA.isTag(charAt);
        }
        return listTagA.getValue();
    }

    /**
     * Recover the name of a tag html.
     *
     * @param htmlCode
     * @return
     */
    private GenericPair<Integer, String> getNameAttribute(String htmlCode) {
        String name = EMPTY_STRING;
        int index;
        for (index = 0; index < htmlCode.length(); index++) {
            char charAt = htmlCode.charAt(index);
            if (charAt == BLANK_CHARACTER) {
                break;
            }
            name += charAt;
        }
        return new GenericPair(index, name);
    }

    public List<GenericPair<String, String>> getElements(String htmlCode, String tagName, String conditionText) {
        ListTagHTML listNamespaces = new ListTagHTML(tagName, conditionText);
        for (int i = 0; i < htmlCode.length(); i++) {
            char charAt = htmlCode.charAt(i);
            listNamespaces.isTag(charAt);
        }
        return listNamespaces.getValue();
    }

    /**
     * Saves the documentation found in a json file.
     *
     * @param pathFile
     * @return
     */
    public boolean saveJson(String pathFile) {
        try {
            jsonObject.put("namespace", this.numNamespace);
            jsonObject.put("class", this.numClasses);
            jsonObject.put("interface", this.numInterfaces);
            jsonObject.put("method", this.numMethods);
            jsonObject.put("enumItem", this.numEnumItem);
            jsonObject.put("enum", this.numEnum);
            jsonObject.put("page", this.numLinks);
            jsonObject.put("Classes", classlist);
            jsonObject.put("Interfaces", interfacelist);
            jsonObject.put("Enum", enumlist);
            FileWriter file = new FileWriter(pathFile + this.fileName + ".json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Api: " + this.api + "\n"
                + "Namespace: " + this.numNamespace + "\n"
                + "Class: " + this.numClasses + "\n"
                + "Enum: " + this.numEnum + "\n"
                + "EnumItem: " + this.numEnumItem + "\n"
                + "Interface: " + this.numInterfaces + "\n"
                + "Method: " + this.numMethods + "\n"
                + "property: " + this.numProperty + "\n"
                + "Pages: " + this.numLinks;
    }
}
