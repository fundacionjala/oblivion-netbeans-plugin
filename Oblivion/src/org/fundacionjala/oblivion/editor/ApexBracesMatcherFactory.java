/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.editor;

import org.fundacionjala.oblivion.apex.utils.MimeType;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.BracesMatcherFactory;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;
import org.netbeans.spi.editor.bracesmatching.support.BracesMatcherSupport;

/**
 * Factory class which returns the default default BracesMatcher implementation. 
 * The default matcher is basically a character matcher, which looks for the following character pairs: '(', ')', '[', ']', '{', '}', '<', '>'.
 * @author Marcelo Garnica
 */
@MimeRegistrations({@MimeRegistration(mimeType = MimeType.APEX_CLASS_MIME_TYPE, service = BracesMatcherFactory.class),
                    @MimeRegistration(mimeType = MimeType.APEX_TRIGGER_MIME_TYPE, service = BracesMatcherFactory.class)})
public class ApexBracesMatcherFactory implements BracesMatcherFactory {
    
    private static final int START_OFFSET_AREA_NO_RESTRICTION = -1;
    private static final int END_OFFSET_AREA_NO_RESTRICTION = -1;

    @Override
    public BracesMatcher createMatcher(MatcherContext matcherContext) {
        return BracesMatcherSupport.defaultMatcher(matcherContext, START_OFFSET_AREA_NO_RESTRICTION, END_OFFSET_AREA_NO_RESTRICTION); 
    }
    
}
