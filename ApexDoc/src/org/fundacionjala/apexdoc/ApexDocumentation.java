/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.apexdoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.fundacionjala.apexdoc.utils.ProposalItem;
import org.fundacionjala.apexdoc.utils.ProposalType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.openide.util.Exceptions;

/**
 * this class returns the documentation of Apex
 *
 * @author sergio_daza
 */
public class ApexDocumentation {

    private final int offset;
    private static final List<ProposalItem> CLASS_LIST = new ArrayList<ProposalItem>();
    private static final Map<String, List<ProposalItem>> CLASS_MAP = new HashMap<String, List<ProposalItem>>();

    public ApexDocumentation(int offset) {
        this(offset, "/org/fundacionjala/apexdoc/files/ApexDoc_35.0.json");
    }

    public ApexDocumentation(int offset, String documentPath) {
        this.offset = offset;
        if (CLASS_LIST.isEmpty() && CLASS_MAP.isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(getCode(documentPath));
                loadProposalsOfClassOrInterface((JSONArray) jsonObject.get("Classes"), "class");
                loadProposalsOfClassOrInterface((JSONArray) jsonObject.get("Interfaces"), "interface");
                loadProposalsOfEnums((JSONArray) jsonObject.get("Enum"), "enum");
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private void loadProposalsOfEnums(JSONArray itemsArray, String proposalType) {
        if(itemsArray != null) {
            Iterator<JSONObject> classIterator = itemsArray.iterator();
            while (classIterator.hasNext()) {
                JSONObject nextClass = classIterator.next();
                CLASS_LIST.add(new ProposalItem(proposalType, (String) nextClass.get("name"), "", (String) nextClass.get("description"), 1));
                String className = (String) nextClass.get("name");
                List<ProposalItem> items = loadProposalItem((JSONArray) nextClass.get("enumItem"));
                CLASS_MAP.put(className.toLowerCase(), items);
            }
        }
    }
    
    private void loadProposalsOfClassOrInterface(JSONArray itemsArray, String proposalType) {
        if(itemsArray != null) {
            Iterator<JSONObject> classIterator = itemsArray.iterator();
            while (classIterator.hasNext()) {
                JSONObject nextClass = classIterator.next();
                CLASS_LIST.add(new ProposalItem(proposalType, (String) nextClass.get("name"), "", (String) nextClass.get("description"), 1));
                String className = (String) nextClass.get("name");
                List<ProposalItem> items = loadProposalItem((JSONArray) nextClass.get("methods"));
                items.addAll(loadProposalItem((JSONArray) nextClass.get("property")));
                CLASS_MAP.put(className.toLowerCase(), items);
            }
        }
    }

    private List<ProposalItem> loadProposalItem(JSONArray itemList) {
        List<ProposalItem> methodItems = new ArrayList<ProposalItem>();
        if (itemList != null) {
            Iterator<JSONObject> methodIterator = itemList.iterator();
            while (methodIterator.hasNext()) {
                JSONObject nextMethod = methodIterator.next();
                JSONArray modifiers = (JSONArray) nextMethod.get("modifiers");
                String typeReturn = (String) nextMethod.get("return");
                methodItems.add(new ProposalItem((String) nextMethod.get("type"), (String) nextMethod.get("name"), typeReturn, (String) nextMethod.get("description"), 1, (String) nextMethod.get("parameters"), modifiers));
            }
        }
        return methodItems;
    }
    
    public List<CompletionProposal> getProposals(String className) {
        return getProposals(className, false);
    }
    
    public List<CompletionProposal> getProposals(String className, boolean isStatic) {
        List<CompletionProposal> proposals = new ArrayList<CompletionProposal>();
        if (className.isEmpty()) {
            proposals.addAll(getClassProposal());
        } else {
            List<ProposalItem> methodProposals = getMethodProposals(className, isStatic);
            if (methodProposals != null) {
                proposals.addAll(methodProposals);
            }
        }
        return proposals;
    }

    private List<ProposalItem> getMethodProposals(String className, boolean isStatic) {
        className = isListOrMap(className);
        List<ProposalItem> items = CLASS_MAP.get(className.toLowerCase());
        List<ProposalItem> resultItems = new ArrayList<ProposalItem>();
        if (items != null) {
            for (ProposalItem item : items) {
                if (isStatic) {
                    if (item.getProposalType().equals(ProposalType.METHOD_STATIC) || item.getProposalType().equals(ProposalType.ENUM_ITEM)) {
                        item.setAnchorOffset(offset);
                        resultItems.add(item);
                    }
                } else if (item.getProposalType().equals(ProposalType.PROPERTY) || item.getProposalType().equals(ProposalType.METHOD_PUBLIC)) {
                    item.setAnchorOffset(offset);
                    resultItems.add(item);
                }
            }

            return resultItems;
        }
        return null;
    }
    
    public List<CompletionProposal> getConstructorsProposals(String className) {
        className = isListOrMap(className);
        List<ProposalItem> items = CLASS_MAP.get(className.toLowerCase());
        List<CompletionProposal> resultItems = new ArrayList<CompletionProposal>();
        if (items != null) {
            for (ProposalItem item : items) {
                if (item.getProposalType().equals(ProposalType.CONSTRUCTOR)) {
                    item.setAnchorOffset(offset);
                    resultItems.add(item);
                }
            }

            return resultItems;
        }
        return null;
    }

    private String isListOrMap(String className) {
        String listOrMap = className.replaceAll(" ", "").replace("&lt;", "<");
        if (className.length() < 6) {
            return className;
        } else if (listOrMap.substring(0, 5).equalsIgnoreCase("list<")) {
            return "list";
        } else if (listOrMap.substring(0, 4).equalsIgnoreCase("map<")) {
            return "map";
        } else {
            return className;
        }
    }
    
    private List<ProposalItem> getClassProposal() {
        for (int i = 0; i < CLASS_LIST.size(); i++) {
            CLASS_LIST.get(i).setAnchorOffset(offset);
        }
        return CLASS_LIST;
    }

    public List<ProposalItem> getClassList() {
        return CLASS_LIST;
    }

    public Map<String, List<ProposalItem>> getClassMap() {
        return CLASS_MAP;
    }

    /**
     * This method opens and read a file that is inside the .jar of project.
     *
     * @return
     */
    public String getCode(String path) {
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
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result.toString();
    }
    
    public String getTypeOfMethod(String className, String methodName) {
        if (className != null) {
            className = isListOrMap(className);
            List<ProposalItem> proposals = CLASS_MAP.get(className);
            if (proposals != null) {
                for (ProposalItem proposal : proposals) {
                    if (!proposal.getProposalType().equals(ProposalType.CONSTRUCTOR) && proposal.name.equalsIgnoreCase(methodName)) {
                        return proposal.type.toLowerCase().trim();
                    }
                }
            }
        }
        return null;
    }
    
    public String getTypeOfMember(String className, String memberName) {
        if (className != null) {
            className = isListOrMap(className);
            List<ProposalItem> proposals = CLASS_MAP.get(className);
            if (proposals != null) {
                for (ProposalItem proposal : proposals) {
                    if (proposal.getProposalType().equals(ProposalType.PROPERTY) && proposal.name.equalsIgnoreCase(memberName)) {
                        return proposal.type.toLowerCase().trim();
                    }
                }
            }
        }
        return "";
    }

}
