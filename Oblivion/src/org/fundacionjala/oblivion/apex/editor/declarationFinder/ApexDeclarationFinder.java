/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.declarationFinder;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import java.io.File;
import java.util.List;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.ArrayTypeTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.fundacionjala.oblivion.apex.ast.tree.IdentifierTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.PropertyTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * This class implements the DeclarationFinder for to go to line of declaration
 * type, method or variable.
 *
 * @author sergio_daza
 */
public class ApexDeclarationFinder implements DeclarationFinder {

    private String name = "";
    private StyledDocument styledDocument;
    private static final String CLASSES_FOLDER = "/src/classes/";
    private static final String EXTENSION = ".cls";
    private static final String THIS_KEYWORD = "THIS_KEYWORD";
    private static final String SUPER_KEYWORD = "SUPER_KEYWORD";
    private static final String SUPER_REFERENCE = "super";
    private static final String EMPTY_STRING = "";
    private static final String BLANK_SPACE = " ";
    private static final String EMD_LINE = "\n";
    private static final String TAB = "\t";
    private final String mineType;

    public ApexDeclarationFinder(String mineType) {
        this.mineType = mineType;
    }
    
    @Override
    public DeclarationLocation findDeclaration(ParserResult parserResult, int offset) {
        DeclarationLocation declarationLocation = DeclarationLocation.NONE;
        ApexParserResult task = (ApexParserResult) parserResult;
        CompilationUnitTree compilationUnit = task.getCompilationUnit();
        OffsetRange range = OffsetRange.NONE;
        SearchDeclaration searchDeclaration = new SearchDeclaration(mineType, name, offset, styledDocument, compilationUnit);
        if(!searchDeclaration.SearchIdentifier()) {
            return declarationLocation;
        } 
            
        FoundTree foundResult = searchDeclaration.getFoundTree();
        Tree tree = foundResult.getDeclaration();
        if (name.toLowerCase().equals(SUPER_REFERENCE)) {
            IdentifierTreeImpl identifier = (IdentifierTreeImpl) foundResult.searchSuper();
            if (identifier == null) {
                return DeclarationLocation.NONE;
            } else {
                range = DeclarationFinderUtils.getRange(identifier.getToken(), identifier.getName().toString(), styledDocument);
                searchDeclaration = new SearchDeclaration(mineType, identifier.getName().toString(), range.getStart(), styledDocument, compilationUnit);
                if (!searchDeclaration.SearchIdentifier()) {
                    return declarationLocation;
                }
                foundResult = searchDeclaration.getFoundTree();
                tree = foundResult.getDeclaration();
                name = identifier.getName().toString();
            }
        }
        if (foundResult.getType().equals(TypeOfIdentifier.TYPE_VARIABLE_NAME) || foundResult.getType().equals(TypeOfIdentifier.EXTENDS_CLASS_VARIABLE_NAME)) {
            if (tree != null) {
                ClassTree classTree = (ClassTree) tree;
                CompoundTree compoundTree = (CompoundTree) classTree;
                range = DeclarationFinderUtils.getRange(compoundTree, name, styledDocument);
            } else {
                FileObject searchClassFile = searchClassFile(name + EXTENSION);
                if (searchClassFile != null) {
                    return new DeclarationLocation(searchClassFile, 0);
                }
            }
        } else if (foundResult.getType().equals(TypeOfIdentifier.LOCAL_METHOD_INVOCATION)) {
            if (tree != null) {
                MethodTreeImpl method = (MethodTreeImpl) tree;
                CompoundTree compoundTree = (CompoundTree) method;
                range = DeclarationFinderUtils.getRange(compoundTree, name, styledDocument);
            }
        } else if (foundResult.getType().equals(TypeOfIdentifier.LOCAL_VARIABLE_NAME) || foundResult.getType().equals(TypeOfIdentifier.LOCAL_ARRAY_VARIABLE_NAME) || foundResult.getType().equals(TypeOfIdentifier.EXTERNAL_VARIABLE_NAME)) {
            if (tree != null) {
                if (tree instanceof VariableTreeImpl) {
                    VariableTreeImpl variable = (VariableTreeImpl) tree;
                    Tree typeVariable = variable.getType();
                    IdentifierTreeImpl type = null;
                    if (typeVariable instanceof IdentifierTreeImpl) {
                        type = (IdentifierTreeImpl) typeVariable;
                    } else if (typeVariable instanceof ArrayTypeTreeImpl) {
                        ArrayTypeTreeImpl arrayType = (ArrayTypeTreeImpl) typeVariable;
                        type = (IdentifierTreeImpl) arrayType.getType();
                    } else {
                        ExpressionTree nameExpression = variable.getNameExpression();
                        if (nameExpression != null) {
                        } else {
                            org.fundacionjala.oblivion.apex.Token token = variable.getToken();
                            range = DeclarationFinderUtils.getRange(token, token.getImage(), styledDocument);
                        }
                    }
                    if (type != null) {
                        List<? extends ExpressionTree> identifiers = type.getIdentifiers();
                        ExpressionTree get = identifiers.get(0);
                        BaseTree baseTree = (BaseTree) get;
                        org.fundacionjala.oblivion.apex.Token token = baseTree.getToken();
                        range = DeclarationFinderUtils.getRange(token, token.getImage(), styledDocument);
                    }
                } else if (tree instanceof PropertyTreeImpl) {
                    PropertyTreeImpl property = (PropertyTreeImpl) tree;
                    org.fundacionjala.oblivion.apex.Token token = property.getToken();
                    range = DeclarationFinderUtils.getRange(token, token.getImage(), styledDocument);
                }
            }
        }

        if (range != OffsetRange.NONE) {
            Snapshot snapshot = task.getSnapshot();
            FileObject fo = (snapshot == null || snapshot.getSource().getFileObject() == null)
                    ? null
                    : FileUtil.toFileObject(FileUtil.normalizeFile(new File(snapshot.getSource().getFileObject().getPath())));
            if (fo != null) {
                declarationLocation = new DeclarationLocation(fo, range.getStart() + 1);
            }
        }
        return declarationLocation;
    }

    @Override
    public OffsetRange getReferenceSpan(Document document, int offset) {
        OffsetRange range = OffsetRange.NONE;
        styledDocument = (StyledDocument) document;
        Token token = ProjectUtils.getApexTokenAt(document, offset);
        if (Kind.IDENTIFIER.name().equals(token.id().name()) || THIS_KEYWORD.equals(token.id().name()) || SUPER_KEYWORD.equals(token.id().name())) {
            String content = token.text().toString();
            content = content.replace(EMD_LINE, EMPTY_STRING).replace(TAB, EMPTY_STRING).replace(BLANK_SPACE, EMPTY_STRING);
            this.name = content;
            TokenHierarchy<Document> th = TokenHierarchy.get(document);
            int offsetStart = token.offset(th);
            int offsetEnd = offsetStart + token.length();
            offsetStart = offsetEnd - content.length();
            range = new OffsetRange(offsetStart, offsetEnd);
        }
        return range;
    }

    /**
     * This method searches a file that it has a name equals to the data type.
     *
     * @param fileName
     * @return
     */
    private FileObject searchClassFile(String fileName) {
        FileObject fileObject = null;
        String currentProjectPath = ProjectUtils.getCurrentProjectPath() + CLASSES_FOLDER + fileName;
        File file = new File(currentProjectPath);
        if (file.isFile()) {
            fileObject = FileUtil.toFileObject(FileUtil.normalizeFile(file));
        }
        return fileObject;
    }

}
