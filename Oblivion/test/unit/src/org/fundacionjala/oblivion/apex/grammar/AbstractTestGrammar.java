/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.text.Document;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Source;
import org.openide.filesystems.FileUtil;
import org.openide.util.Utilities;

/**
 * Base class for grammar tests.
 *
 * @author Marcelo Garnica
 */
public abstract class AbstractTestGrammar {

    protected final static String PARSER_ERRORS_ARE_EXPECTED = "Parser errors are expected";
    protected final static String NO_PARSER_ERRORS_ARE_EXPECTED = "No parser errors are expected";
    protected final static String WRONG_METHOD_DECLARATION = "Unexpected:";

    /**
     * Returns an ApexParser for the given file name and the class resource folder.
     *
     * @param fileName
     * @return the file's ApexParser.
     * @throws FileNotFoundException
     */
    protected ApexParser getParser(String fileName) throws FileNotFoundException {
        ApexParser parser = new ApexParser(getClass().getResourceAsStream(String.format(getResourceFolder(), fileName)));
        parser.setTreeFactory(new ApexTreeFactory());
        return parser;
    }

    protected ParserResult getParserResult(String fileName) throws URISyntaxException {
        Source classSource = getSource(fileName);
        ApexLanguageParser apexLanguageParser = new ApexLanguageParser();
        apexLanguageParser.parse(classSource.createSnapshot(), null, null);
        return (ParserResult) apexLanguageParser.getResult(null);
    }

    protected Document getDocument(String fileName) throws URISyntaxException {
        return getSource(fileName).getDocument(true);
    }

    protected Source getSource(String fileName) throws URISyntaxException {
        URL resource = getClass().getResource(String.format(getResourceFolder(), fileName));
        File classFile = Utilities.toFile(resource.toURI());
        return Source.create(FileUtil.toFileObject(classFile));
    }

    /**
     * Returns the folder that will have the files to be parsed.
     *
     * @return the containing folder of the files to be parsed.
     */
    protected abstract String getResourceFolder();

    /**
     * Executes the compilation unit method of an ApexParser, catches any ParseException and sets a recovery context.
     *
     * @param parser - The apex parser to be executed.
     */
    protected void runParser(ApexParser parser) {
        try {
            parser.CompilationUnit();
        } catch (ParseException ex) {
            parser.contextErrorRecover(ex);
        }
    }
}
