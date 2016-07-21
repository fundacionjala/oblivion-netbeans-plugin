/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.grammar.parser;

/*import java.util.UUID;
import org.fundacionjala.oblivion.apex.grammar.jcclexer.ParseException;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.Severity;
import org.netbeans.modules.parsing.api.Snapshot;
import org.openide.filesystems.FileObject;*/

/**
 * Implements Error interface and contains all information related to a parser error.
 * 
 * @author Maria Garcia
 */
public class ParseError {} //implements Error {
//    private static final String DESCRIPTION = "An error occured during parsing";
//    
//    private final ParseException exception;
//    private final Snapshot snapshot;
//    
//    public ParseError (Snapshot snapshot, ParseException exception) {
//        this.snapshot = snapshot;
//        this.exception = exception;
//    }
//    
//    /**
//     * Provide a description for the error.
//     */
//    @Override
//    public String getDisplayName() {
//        return exception.getMessage();
//    }
//
//    /**
//     * Provide a full sentence description of the error.
//     * 
//     * @return 
//     */
//    @Override
//    public String getDescription() {
//        return DESCRIPTION;
//    }
//
//    /**
//     * Return a unique id/key for this error.
//     * 
//     * @return 
//     */
//    @Override
//    public String getKey() {
//        return UUID.randomUUID().toString();
//    }
//
//    /**
//     * Get the file object associated with the error.
//     * 
//     * @return 
//     */
//    @Override
//    public FileObject getFile() {
//        return snapshot.getSource().getFileObject();
//    }
//
//    /**
//     * Get the start position of the error in the parsing input source.
//     * 
//     * @return 
//     */
//    @Override
//    public int getStartPosition() {
//        return exception.currentToken.beginLine + 1;
//    }
//
//    /**
//     * Get the end position of the error in the parsing input source.
//     * 
//     * @return 
//     */
//    @Override
//    public int getEndPosition() {
//        return exception.currentToken.endLine + 1;
//    }
//
//    /**
//     * Define the way how an error annotation for the error behaves in the editor.
//     * 
//     * @return True if the error annotation should span over the whole line
//     * False if the annotation is restricted exactly by the range defined by getStart/EndPostion()
//     */
//    @Override
//    public boolean isLineError() {
//        return true;
//    }
//
//    /**
//     * Get the severity for the error.
//     * 
//     * @return An Error severity level 
//     */
//    @Override
//    public Severity getSeverity() {
//        return Severity.ERROR;
//    }
//
//    /**
//     * Return optional parameters for the error.
//     * 
//     * @return An array of Objects
//     */
//    @Override
//    public Object[] getParameters() {
//        return new Object[] {};
//    }
//}
