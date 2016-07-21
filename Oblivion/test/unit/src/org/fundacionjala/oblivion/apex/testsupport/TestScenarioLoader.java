/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.testsupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Helps to load an xml file which describes several grammar scenarios, it is mean to be used by a unit test that is
 * executed using the Parameterized Junit functionality
 *
 * @author Alejandro Ruiz
 */
public class TestScenarioLoader {

    private static final String ID_ATTRIBUTE = "id";
    private static final String TEST_ELEMENT = "test";
    private static final String TEST_CONTENT = "testContent";

    /**
     * Reads a scenario file and creates a valid structure to be used in a Parameterized unit test
     *
     * @param file that contains the scenario tests
     * @return Compatible structure to be used by a Parameterized unit test
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Iterable<Object[]> load(String file) throws ParserConfigurationException, SAXException, IOException {
        return load(file, ID_ATTRIBUTE, TEST_CONTENT);
    }
    
    /**
     * Reads a scenario file and creates a valid structure to be used in a Parameterized unit test
     *
     * @param file that contains the scenario tests
     * @param params
     * @return Compatible structure to be used by a Parameterized unit test
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Iterable<Object[]> load(String file, String... params) throws ParserConfigurationException, SAXException, IOException {
        List<Object[]> scenarios = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(file));
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && TEST_ELEMENT.equalsIgnoreCase(node.getNodeName())) {
                Element elem = (Element) node;
                scenarios.add(getScenario(elem, params));
            }
        }
        return scenarios;
    }

    /**
     * Retrieves a scenario object array from a given elem node.
     * @param elem The element from which the scenario will be built.
     * @param params the parameters to build the object array.
     * @return The scenario object array.
     */
    private static Object[] getScenario(Element elem, String... params) {
        NamedNodeMap attributes = elem.getAttributes();
        Map<String, String> values = new HashMap<>();
        String name;
        String value;
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            name = attribute.getNodeName();
            value = attribute.getNodeValue().trim();
            values.put(name, value);
        }
        NodeList childNodes = elem.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.CDATA_SECTION_NODE) {
                name = TEST_CONTENT;
            } else {
                name = childNode.getNodeName();
            }
            value = childNode.getNodeValue().trim();
            values.put(name, value);
        }
        
        List<String> toReturn = new ArrayList<>();
        for (String param : params) {
            toReturn.add(values.get(param));
        }
        return toReturn.toArray();
    }

}
