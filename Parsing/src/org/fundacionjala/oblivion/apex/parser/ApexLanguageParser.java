/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.parser;

import com.sun.source.tree.CompilationUnitTree;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.fundacionjala.oblivion.apex.ast.tree.ApexTreeFactory;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ApexParser;
import org.fundacionjala.oblivion.apex.lexer.ApexLanguageHierarchy;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.spi.ParseException;

/**
 * Implements a Apex language parser.
 *
 * @see Parser
 * @author Maria Garcia
 */
public class ApexLanguageParser extends Parser {

    private Snapshot snapshot;
    private ApexParser apexParser;
    private CompilationUnitTree compilationUnit;

    /**
     * Called by infrastructure when Source is changed, and a new Snapshot has been created for it.
     *
     * @param snapshot A snapshot that should be parsed.
     * @param task A task asking for parsing result.
     * @param event A scheduler event
     */
    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
        this.snapshot = snapshot;
        Reader reader = new StringReader(snapshot.getText().toString());
        apexParser = new ApexParser(reader);
        apexParser.setTreeFactory(new ApexTreeFactory());
        try {
            if (snapshot.getMimeType().equalsIgnoreCase(ApexLanguageHierarchy.APEX_MIME_TYPE)) {

                compilationUnit = apexParser.CompilationUnit();
            } else if (snapshot.getMimeType().equalsIgnoreCase(ApexLanguageHierarchy.APEX_TRIGGER_MIME_TYPE)){
                compilationUnit = apexParser.TriggerCompilationUnit();
            }

        } catch (org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException ex) {
            apexParser.contextErrorRecover(ex);
        } catch (java.lang.Error error){
            error.printStackTrace();
        }
    }

    /**
     * Called to get the result of parsing.
     *
     * @param task The task asking for parsing result
     * @return
     */
    @Override
    public Result getResult(Task task) {
        return new ApexParserResult(snapshot, apexParser, compilationUnit);
    }

    /**
     * Register a new listener.
     *
     * @param changeListener
     */
    @Override
    public void addChangeListener(ChangeListener changeListener) {
    }

    /**
     * Unregister a listener.
     *
     * @param changeListener
     */
    @Override
    public void removeChangeListener(ChangeListener changeListener) {
    }

    /**
     * Represents a parser result
     */
    public static class ApexParserResult extends ParserResult {

        private final ApexParser apexParser;
        private boolean valid = true;
        private CompilationUnitTree compilationUnit;

        ApexParserResult(Snapshot snapshot, ApexParser apexParser) {
            super(snapshot);
            this.apexParser = apexParser;
        }

        ApexParserResult(Snapshot snapshot, ApexParser apexParser, CompilationUnitTree compilationUnit) {
            this(snapshot, apexParser);
            this.compilationUnit = compilationUnit;
        }
        
        public CompilationUnitTree getCompilationUnit() {
            return compilationUnit;
        }

        /**
         * Get the apex parser.
         *
         * @return The parser if the parsing still hasn't finish
         * @throws org.netbeans.modules.parsing.spi.ParseException
         */
        public ApexParser getApexParser() throws ParseException {
            if (!valid) {
                throw new ParseException();
            }
            return apexParser;
        }

        /**
         * Called by Parsing API when Task is finished.
         */
        @Override
        protected void invalidate() {
            valid = false;
        }

        @Override
        public List<? extends Error> getDiagnostics() {
            return Collections.emptyList();
        }
    }
}
