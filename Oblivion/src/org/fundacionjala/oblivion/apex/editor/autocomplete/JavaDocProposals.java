/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contain the proposals for javaDoc
 * 
 * @author sergio_daza
 */
public class JavaDocProposals {

    public static final List<JavaDocItem> PROPOSALS = new ArrayList<JavaDocItem>();

    static {
        PROPOSALS.add(new JavaDocItem("@author"));
        PROPOSALS.add(new JavaDocItem("@deprecated"));
        PROPOSALS.add(new JavaDocItem("@param"));
        PROPOSALS.add(new JavaDocItem("@see"));
        PROPOSALS.add(new JavaDocItem("@serial"));
        PROPOSALS.add(new JavaDocItem("@since"));
        PROPOSALS.add(new JavaDocItem("@version"));
    }

    public static List<JavaDocItem> getProposals(int offset) {
        for (int i = 0; i < PROPOSALS.size(); i++) {
            PROPOSALS.get(i).setAnchorOffset(offset);
        }
        return PROPOSALS;
    }
}
