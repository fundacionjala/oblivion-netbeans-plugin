/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.fundacionjala.apexdoc.ApexDocumentation;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.lexer.ApexTokenId;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.fundacionjala.oblivion.salesforce.project.ProjectUtils;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CodeCompletionHandler;
import org.netbeans.modules.csl.api.CodeCompletionResult;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.ParameterInfo;
import org.netbeans.modules.csl.spi.DefaultCompletionResult;
import org.netbeans.modules.csl.spi.ParserResult;

/**
 * Class that generate proposal list
 *
 * @author sergio_daza
 */
public class AutoCompleteHandler implements CodeCompletionHandler {

    private static final Collection<Character> AUTOPOPUP_STOP_CHARS = new TreeSet<>(
            Arrays.asList('=', ';', '+', '-', '*', '/', '%', '(', ')', '[',
                    ']', '{', '}', '?', ' ', '\t', '\n'));
    private static final Collection<String> SEPARATORS = new TreeSet<>(
            Arrays.asList("=", "+", "-", "*", "/", "%", "(", "[",
                    "{", "?", ":", ",", ">", "<", "&&", "||","return"));
    private static final Collection<String> INVALID_TOKENS = new TreeSet<>(
            Arrays.asList("STRING_LITERAL_STRING", "SINGLE_LINE_COMMENT", "MULTI_LINE_COMMENT"));
    private static final String[] separatorOrAssign = {"LPAREN_SEPARATOR", "SEMICOLON_SEPARATOR", 
        "LBRACE_SEPARATOR", "LBRACKET_SEPARATOR", "ASSIGN_OPERATOR",
        "COMMA_SEPARATOR", "SINGLE_LINE_COMMENT", "MULTI_LINE_COMMENT"};
    private static final String END_LINE_CHARACTERS = ";{}/";
    private static final String EMPTY_STRING = "";
    private StyledDocument styledDocument;
    private CompilationUnitTree unit = null;
    private Map<String, Elements> collectionOfDeclarations;
    private Tree parentForCursor;
    private String classNameForCursor;
    private List<CompletionProposal> docProposals = new ArrayList<>();
    private DefaultCompletionResult defaultCompletionResult = null;
    private List<ProposalItem> proposalsAux = new ArrayList<>();
    private String prefixText = EMPTY_STRING;
    private int prefixOffset = 0;
    private int atSymbolOffset = -1;
    private int currentOffset = 0;
    private boolean jumpProposals = false;
    private boolean showJavaDocProposals = false;
    private boolean atSimbolPressed = false;
    private boolean openSingleQuote = false;
    private QueryType queryTypeReturn = QueryType.NONE;

    /**
     * This method searching variables and methods in the parser result to
     * return a proposal list
     *
     * @param context
     * @return a CodeCompletionProposal
     */
    @Override
    public CodeCompletionResult complete(CodeCompletionContext context) {
        List<CompletionProposal> completionProposals = new ArrayList<>();
        int offset = context.getCaretOffset() - context.getPrefix().length();
        currentOffset = offset;
        ApexParserResult task = (ApexParserResult) context.getParserResult();
        if (task.getCompilationUnit().getTypeDecls().size() > 0) {
            this.unit = task.getCompilationUnit();
        }
        if (this.unit == null || jumpProposals) {
            if (showJavaDocProposals && atSimbolPressed && atSymbolOffset > 0) {
                completionProposals.addAll(JavaDocProposals.getProposals(atSymbolOffset));
                return new DefaultCompletionResult(completionProposals, false);
            } else {
                return DefaultCompletionResult.NONE;
            }
        } else {
            RecoverDeclarations recoverDeclarations = new RecoverDeclarations(offset, styledDocument, this.unit);
            recoverDeclarations.SearchIdentifier();
            List<ProposalItem> proposals = recoverDeclarations.getProposals();
            if (!proposals.isEmpty()) {
                proposalsAux = proposals;
            }
            ApexDocumentation apexDocumentation = new ApexDocumentation(offset);
            this.docProposals = apexDocumentation.getProposals(EMPTY_STRING);
            if (prefixText.equals(".") || prefixText.equals("new") || prefixText.equals(")")) {
                proposals = getProposalsOfDotOrNew(offset);
                recoverDeclarations = new RecoverDeclarations(offset, styledDocument, this.unit);
                recoverDeclarations.SearchIdentifier();
                if(this.docProposals != null) {
                    completionProposals.addAll(this.docProposals);
                }
                completionProposals.addAll(proposals);
                defaultCompletionResult = new DefaultCompletionResult(completionProposals, false);
            } else if (!proposals.isEmpty()) {
                this.collectionOfDeclarations = recoverDeclarations.collectionOfDeclarations;
                this.parentForCursor = recoverDeclarations.parentForCursor;
                this.classNameForCursor = recoverDeclarations.classNameForCursor;
                for (int i = 0; i < proposals.size(); i++) {
                    proposals.get(i).setAnchorOffset(offset);
                }
                completionProposals.addAll(this.docProposals);
                completionProposals.addAll(proposals);
                defaultCompletionResult = new DefaultCompletionResult(completionProposals, false);
            } else if (SEPARATORS.contains(prefixText)) {
                for (int i = 0; i < proposalsAux.size(); i++) {
                    proposalsAux.get(i).setAnchorOffset(prefixOffset);
                }
                completionProposals.addAll(this.docProposals);
                completionProposals.addAll(proposalsAux);
                defaultCompletionResult = new DefaultCompletionResult(completionProposals, false);
            } else {
                return DefaultCompletionResult.NONE;
            }
        }
        return this.defaultCompletionResult;
    }
    
    /**
     * This method return the HTML documentation for the given program element
     * (returned in CompletionProposals by the complete method)
     *
     * @param pr
     * @param eh
     * @return
     */
    @Override
    public String document(ParserResult pr, ElementHandle eh) {
        return eh.getIn();
    }

    /**
     * Resolve a link that was written into the HTML returned by document
     * (org.netbeans.modules.csl.spi.ParserResult,org.netbeans.modules.csl.api.ElementHandle).
     *
     * @param string
     * @param eh
     * @return
     */
    @Override
    public ElementHandle resolveLink(String string, ElementHandle eh) {
        return eh;
    }

    /**
     * Compute the prefix to be used for completion at the given caretOffset
     *
     * @param pr
     * @param offset
     * @param bln
     * @return
     */
    @Override
    public String getPrefix(ParserResult pr, int offset, boolean bln) {
        prefixText = EMPTY_STRING;
        jumpProposals = false;
        currentOffset = offset;
        queryTypeReturn = QueryType.NONE;
        TokenHierarchy<?> tokenHierarchy = pr.getSnapshot().getTokenHierarchy();
        TokenSequence<?> tokenSequence = tokenHierarchy.tokenSequence();
        tokenSequence.move(offset);
        tokenSequence.moveNext();
        Token<? extends ApexTokenId> token = (Token<? extends ApexTokenId>) tokenSequence.token();
        if (INVALID_TOKENS.contains(token.id().name())) {
            if (token.id().name().equals("MULTI_LINE_COMMENT")) {
                showJavaDocProposals = true;
                queryTypeReturn = QueryType.ALL_COMPLETION;
            } else {
                showJavaDocProposals = false;
            }

            jumpProposals = true;
        }
        tokenSequence.move(token.offset(tokenHierarchy) - 1);
        tokenSequence.moveNext();
        token = (Token<? extends ApexTokenId>) tokenSequence.token();
        if (token != null) {
            prefixOffset = offset;
            prefixText = token.text() + EMPTY_STRING;
            prefixText = prefixText.replace("\n", EMPTY_STRING).replace("\t", EMPTY_STRING).replace(" ", EMPTY_STRING);
        }
        if(token.id().name().equals("IDENTIFIER")) {
            return prefixText;
        }
        return EMPTY_STRING;
    }

    /**
     * Consider a keystroke and decide whether it should automatically invoke
     * some type of completion. If so, return the desired type, otherwise return
     * CodeCompletionHandler.QueryType.NONE.
     *
     * @param component
     * @param typedText
     * @return
     */
    @Override
    public QueryType getAutoQuery(JTextComponent component, String typedText) {
        QueryType result = queryTypeReturn;
        if (typedText.length() == 0) {
            result = QueryType.NONE;
        } else {
            char lastChar = typedText.charAt(typedText.length() - 1);
            if (AUTOPOPUP_STOP_CHARS.contains(lastChar)) {
                result = QueryType.STOP;
            }
            if (lastChar == '\'') {
                if (openSingleQuote) {
                    this.jumpProposals = false;
                    openSingleQuote = false;
                } else {
                    this.jumpProposals = true;
                    openSingleQuote = true;
                }
            }
            if (lastChar == ' ' || lastChar == '\n' || lastChar == '\t') {
                atSimbolPressed = false;
                atSymbolOffset = -1;
            } else if (lastChar == '@') {
                atSimbolPressed = true;
                atSymbolOffset = currentOffset;
            } else if(lastChar == '.') {
                queryTypeReturn = QueryType.ALL_COMPLETION;
                return queryTypeReturn;
            }
        }
        return result;
    }
    
    /**
     * Perform code template parameter evaluation for use in code template
     * completion or editing. The actual set of parameters defined by the
     * languaCompute parameter info for the given offset - parameters
     * surrounding the given offset, which particular parameter in that list
     * we're currently on, and so on. ge plugins is not defined and will be
     * language specific. Return null if the variable is not known or supported.
     *
     * @param string
     * @param pr
     * @param i
     * @param string1
     * @param map
     * @return
     */
    @Override
    public String resolveTemplateVariable(String string, ParserResult pr, int i, String string1, Map map) {
        return EMPTY_STRING;
    }

    /**
     * Compute the set of applicable templates for a given text selection
     *
     * @param dcmnt
     * @param i
     * @param i1
     * @return
     */
    @Override
    public Set<String> getApplicableTemplates(Document dcmnt, int i, int i1) {
        if (dcmnt.getLength() > 1) {
            this.styledDocument = (StyledDocument) dcmnt;
        }
        return Collections.emptySet();
    }

    /**
     * Compute parameter info for the given offset - parameters surrounding the
     * given offset, which particular parameter in that list we're currently on,
     * and so on.
     *
     * @param pr
     * @param i
     * @param cp
     * @return
     */
    @Override
    public ParameterInfo parameters(ParserResult pr, int i, CompletionProposal cp) {
        return ParameterInfo.NONE;
    }

    public List<ProposalItem> getProposalsOfDotOrNew(int offset) {
        String textResult = EMPTY_STRING;
        int newOffset = offset - 2;
        boolean searchConstructors = false;
        List<ProposalItem> result = new ArrayList<>();
        Token[] tokenKind = new Token[4];
        tokenKind[0] = ProjectUtils.getApexTokenAt(this.styledDocument, newOffset);
        newOffset = newOffset - sizeTextToken(tokenKind[0].text());
        String tokenText_0 = formatOfTokenText(tokenKind[0].text());
        String tokenName_0 = tokenKind[0].id().name();
        tokenKind[1] = ProjectUtils.getApexTokenAt(this.styledDocument, newOffset);
        newOffset = newOffset - sizeTextToken(tokenKind[1].text());
        String tokenText_1 = formatOfTokenText(tokenKind[1].text());
        String tokenName_1 = tokenKind[1].id().name();
        tokenKind[2] = ProjectUtils.getApexTokenAt(this.styledDocument, newOffset);
        newOffset = newOffset - sizeTextToken(tokenKind[2].text());
        String tokenName_2 = tokenKind[2].id().name();
        tokenKind[3] = ProjectUtils.getApexTokenAt(this.styledDocument, newOffset);
        String tokenName_3 = tokenKind[3].id().name();
        if (tokenName_0.equals("THIS_KEYWORD") && isSeparatorOrAssign(tokenName_1)) {
            if (this.collectionOfDeclarations != null && this.collectionOfDeclarations.containsKey(this.classNameForCursor)) {
                result.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).item_attributes);
                result.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).item_methods);
                for (int i = 0; i < result.size(); i++) {
                    result.get(i).setAnchorOffset(offset);
                }
            }
            this.docProposals.clear();
        } else if (tokenName_0.equals("SUPER_KEYWORD") && isSeparatorOrAssign(tokenName_1)) {
            if (this.collectionOfDeclarations != null && this.collectionOfDeclarations.containsKey(this.classNameForCursor)) {
                String extendsName = this.collectionOfDeclarations.get(this.classNameForCursor).extendsClassName.toLowerCase();
                if (!extendsName.isEmpty() && this.collectionOfDeclarations.get(extendsName) != null) {
                    result.addAll(this.collectionOfDeclarations.get(extendsName).item_attributes);
                    result.addAll(this.collectionOfDeclarations.get(extendsName).item_methods);
                    for (int i = 0; i < result.size(); i++) {
                        result.get(i).setAnchorOffset(offset);
                    }
                }
            }
            this.docProposals.clear();
        } else if (tokenText_0.equals(")")) {
            textResult = getTypeNameForMethod(offset - 2);
            if(textResult==null) {
                this.docProposals.clear();
                return result;
            }
        } else if (tokenName_0.equals("NEW_KEYWORD") && tokenName_1.equals("ASSIGN_OPERATOR")) {
            textResult = getTypeNameForNew(newOffset).toLowerCase();
            if (!textResult.isEmpty()) {
                searchConstructors = true;
                ApexDocumentation apexDocumentation = new ApexDocumentation(offset);
                this.docProposals = apexDocumentation.getConstructorsProposals(textResult);
            }
        } else if (isIdentifierValid(tokenName_0)) {
            if (isSeparatorOrAssign(tokenName_1) || SEPARATORS.contains(tokenText_1)) {
                if (tokenName_0.equals("STRING_LITERAL_STRING")) {
                    textResult = "string";
                } else {
                    textResult = getTypeName(formatOfTokenText(tokenText_0), this.parentForCursor, false).toLowerCase();
                }
            } else if (tokenName_1.equals("DOT_SEPARATOR") && tokenName_2.equals("THIS_KEYWORD") && isSeparatorOrAssign(tokenName_3)) {
                textResult = getTypeName(formatOfTokenText(tokenText_0), this.parentForCursor, true).toLowerCase();
            } else if (tokenName_1.equals("DOT_SEPARATOR") && (isIdentifierValid(tokenName_2)  || tokenName_2.equals("RPAREN_SEPARATOR"))) {
                textResult = getTypeNameOfMember(offset - 2);
                if (textResult == null) {
                    this.docProposals.clear();
                    return result;
                }
            }
        }
        
        if (!textResult.isEmpty()) {
            if (this.collectionOfDeclarations != null && this.collectionOfDeclarations.containsKey(textResult)) {
                if (searchConstructors) {
                    result.addAll(getPublicConstructors(this.collectionOfDeclarations.get(textResult).item_methods));
                } else if (tokenText_0.equals(textResult)) {
                    result.addAll(getStaticProposals(this.collectionOfDeclarations.get(textResult).item_attributes));
                    result.addAll(getStaticProposals(this.collectionOfDeclarations.get(textResult).item_methods));
                } else {
                    result.addAll(getPorposalsAfterDot(this.collectionOfDeclarations.get(textResult).item_attributes));
                    result.addAll(getPorposalsAfterDot(this.collectionOfDeclarations.get(textResult).item_methods));
                }
                if(this.docProposals != null) {
                    this.docProposals.clear();
                }
            } else if (!searchConstructors) {
                ApexDocumentation apexDocumentation = new ApexDocumentation(offset);
                boolean isStatic = (tokenText_0.equals(textResult)) ? true: false;
                if (textResult.isEmpty()) {
                    this.docProposals.clear();
                } else {
                    this.docProposals = apexDocumentation.getProposals(textResult, isStatic);
                }
            }
        }
        for (int i = 0; i < result.size(); i++) {
            result.get(i).withReference = false;
            result.get(i).setAnchorOffset(offset);
        }
        return result;
    }
    
    private boolean isSeparatorOrAssign(String text) {
        for(int i=0; i<separatorOrAssign.length; i++) {
            if(separatorOrAssign[i].equals(text)) {
                return true;
            }
        }
        return false;
    }

    private int sizeTextToken(CharSequence text) {
        String result = text + EMPTY_STRING;
        return result.length();
    }

    private String formatOfTokenText(CharSequence text) {
        String result = text + EMPTY_STRING;
        return result.replaceAll("\n", EMPTY_STRING).replaceAll("\t", EMPTY_STRING).trim().toLowerCase();
    }

    private String getTypeName(String variableName, Tree parent, boolean onlyAttribute) {
        if (this.collectionOfDeclarations != null && this.collectionOfDeclarations.containsKey(this.classNameForCursor)) {
            List<VariableTreeImpl> items = this.collectionOfDeclarations.get(this.classNameForCursor).attributes;
            if (!onlyAttribute) {
                items.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).variables);
            }
            if (parent != null) {
                for (int i = 0; i < items.size(); i++) {
                    VariableTreeImpl variable = items.get(i);
                    if (variable.getParent().equals(parent)) {
                        if (variable.getName().toString().toLowerCase().equals(variableName.toLowerCase())) {
                            return variable.getType().toString();
                        }
                    }
                }
                if (parent instanceof BaseTree) {
                    BaseTree baseTree = (BaseTree) parent;
                    return getTypeName(variableName, baseTree.getParent(), onlyAttribute);
                }
            }
        }
        return variableName;
    }

    private String getTypeNameForNew(int newOffset) {
        Token<? extends ApexTokenId> token = ProjectUtils.getApexTokenAt(this.styledDocument, newOffset);
        newOffset = newOffset - sizeTextToken(token.text());
        String tokenText = EMPTY_STRING + token.text();
        if (END_LINE_CHARACTERS.contains(tokenText.trim())) {
            return EMPTY_STRING;
        } else {
            return getTypeNameForNew(newOffset) + tokenText.trim();
        }
    }
    
    private boolean isComment(Token token) {
        return token.id().name().equals("SINGLE_LINE_COMMENT") || token.id().name().equals("MULTI_LINE_COMMENT");
    }
    
    private boolean isIdentifierValid(String tokenName) {
        return tokenName.equals("IDENTIFIER") || tokenName.contains("_KEYWORD")
                || tokenName.contains("_OTHER") || tokenName.equals("STRING_LITERAL_STRING");
    }
    
    private String getTypeNameOfMember(int offset) {
        Token tokenMemberName = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(tokenMemberName.text());
        Token tokenDot = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(tokenDot.text());
        if(isIdentifierValid(tokenMemberName.id().name()) && tokenDot.id().name().equals("DOT_SEPARATOR")) {
            Token tokenVariable = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
            int offsetOfVariable = offset;
            offset = offset - sizeTextToken(tokenVariable.text());
            if (isIdentifierValid(tokenVariable.id().name())) {
                Token tokenDotOrEndLIne = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
                offset = offset - sizeTextToken(tokenDotOrEndLIne.text());
                if (isEndLine(formatOfTokenText(tokenDotOrEndLIne.text())) || isComment(tokenDotOrEndLIne)) {
                    String className = getTypeName(formatOfTokenText(tokenVariable.text()), this.parentForCursor, false).toLowerCase();
                    return getAttributeTypeOf(className, formatOfTokenText(tokenMemberName.text()));
                } else if (tokenDotOrEndLIne.id().name().equals("DOT_SEPARATOR")) {
                    Token tokenThisOrVariable = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
                    if (tokenThisOrVariable.id().name().equals("THIS_KEYWORD")) {
                        String className = getTypeName(formatOfTokenText(tokenVariable.text()), this.parentForCursor, true).toLowerCase();
                        return getAttributeTypeOf(className, formatOfTokenText(tokenMemberName.text()));
                    } else 
                        {
                        return getAttributeTypeOf(getTypeNameOfMember(offsetOfVariable), formatOfTokenText(tokenMemberName.text()));
                    } 
                }
            } if (tokenVariable.id().name().equals("RPAREN_SEPARATOR")) {
                return getAttributeTypeOf(getTypeNameForMethod(offsetOfVariable), formatOfTokenText(tokenMemberName.text()));
            }
        }
        return null;
    }

    private String getAttributeTypeOf(String className, String memberName) {
        if (this.collectionOfDeclarations.get(className) != null) {
            for (AttributeItem attribute : this.collectionOfDeclarations.get(className).item_attributes) {
                if (attribute.name.equalsIgnoreCase(memberName)) {
                    return attribute.getType();
                }
            }
        } else {
            final ApexDocumentation apexDocumentation = new ApexDocumentation(1);
            return apexDocumentation.getTypeOfMember(className, memberName);
        }
        return EMPTY_STRING;
    }

    private String getTypeNameForMethod(int offset) {
        Token<? extends ApexTokenId> token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(token.text());
        GenericPair<Integer, Integer> parameters = getParamentersNumber(0, offset);
        if (parameters.getFirst() >= 0) {
            String typeOfMethod = getTypeOfMethod(parameters.getSecond(), parameters.getFirst());
            if (typeOfMethod != null) {
                if (!typeOfMethod.trim().isEmpty()) {
                    return typeOfMethod.trim().toLowerCase();
                }
            }
        }
        return null;
    }

    private String getTypeOfMethod(int offset, int parametersNumber) {
        Token<? extends ApexTokenId> token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(token.text());
        String tokenText = EMPTY_STRING + token.text();
        String methodName = "";
        methodName = tokenText.trim();
        token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(token.text());
        if (isEndLine(formatOfTokenText(token.text())) || isComment(token)) {
            for (MethodItem method : this.collectionOfDeclarations.get(this.classNameForCursor).item_methods) {
                if (method.isMethod(methodName, parametersNumber)) {
                    return method.getType();
                }
            }
        } else {
            if (token.id().name().equals("DOT_SEPARATOR")) {
                int offsetOfmember = offset;
                token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
                String tokenName = token.id().name();
                tokenText = formatOfTokenText(token.text());
                offset = offset - sizeTextToken(token.text());
                if (isIdentifierValid(tokenName)) {
                    Token tokenPrefix = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
                    offset = offset - sizeTextToken(tokenPrefix.text());
                    token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
                    boolean isThis = token.id().name().equals("THIS_KEYWORD");
                    if (isEndLine(formatOfTokenText(tokenPrefix.text())) || isComment(tokenPrefix) || isThis) {
                        if (tokenName.equals("STRING_LITERAL_STRING")) {
                            return getTypeOfMethodForClass("string", methodName, parametersNumber);
                        } else {
                            String txtResult = getTypeName(formatOfTokenText(tokenText), this.parentForCursor, isThis).toLowerCase();
                            return getTypeOfMethodForClass(txtResult.toLowerCase(), methodName, parametersNumber);
                        }
                    } else {
                        if (isIdentifierValid(tokenName)) {
                            return getTypeOfMethodForClass(getTypeNameOfMember(offsetOfmember), methodName, parametersNumber);
                        }
                    }
                } else {
                    GenericPair<Integer, Integer> parameters = getParamentersNumber(0, offset);
                    if (parameters.getFirst() >= 0) {
                        return getTypeOfMethodForClass(getTypeOfMethod(parameters.getSecond(), parameters.getFirst()), methodName, parametersNumber);
                    }
                }
            }
        }
        return null;
    }

    private boolean isEndLine(String prefix) {
        return (END_LINE_CHARACTERS + "=,(+-*/%&&||").contains(prefix);
    }

    private String getTypeOfMethodForClass(String className, String methodName, int parametersNumber) {
        if (this.collectionOfDeclarations.get(className) != null) {
            for (MethodItem method : this.collectionOfDeclarations.get(className).item_methods) {
                if (method.isMethod(methodName, parametersNumber)) {
                    return method.getType();
                }
            }
        } else {
            final ApexDocumentation apexDocumentation = new ApexDocumentation(1);
            return apexDocumentation.getTypeOfMethod(className, methodName);
        }
        return null;
    }

    private GenericPair<Integer, Integer> getParamentersNumber(int parameterNumber, int offset) {
        Token<? extends ApexTokenId> token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(token.text());
        String tokenText = EMPTY_STRING + token.text();
        if (END_LINE_CHARACTERS.contains(tokenText.trim())) {
            return new GenericPair(-1, -1);
        } else if (tokenText.trim().equals("(")) {
            return new GenericPair(parameterNumber, offset);
        } else if (tokenText.trim().equals(",")) {
            return getParamentersNumber(parameterNumber + 1, offset);
        } else if (tokenText.trim().equals(")")) {
            offset = skipParenthesis(offset, 0);
            if (parameterNumber == 0) {
                return getParamentersNumber(1, offset);
            } else {
                return getParamentersNumber(parameterNumber, offset);
            }
        } else if (parameterNumber == 0) {
            return getParamentersNumber(1, offset);
        } else {
            return getParamentersNumber(parameterNumber, offset);
        }
    }

    private int skipParenthesis(int offset, int parenthesisOpen) {
        Token<? extends ApexTokenId> token = ProjectUtils.getApexTokenAt(this.styledDocument, offset);
        offset = offset - sizeTextToken(token.text());
        String tokenText = EMPTY_STRING + token.text();
        if (tokenText.trim().equals("(")) {
            if (parenthesisOpen == 0) {
                return offset;
            } else {
                return skipParenthesis(offset, parenthesisOpen - 1);
            }
        } else if (tokenText.trim().equals(")")) {
            return skipParenthesis(offset, parenthesisOpen + 1);
        } else {
            return skipParenthesis(offset, parenthesisOpen);
        }
    }
        
    private Collection<? extends ProposalItem> getPorposalsAfterDot(Collection<? extends ProposalItem> items) {
        Iterator<? extends ProposalItem> it = items.iterator();
        while (it.hasNext()) {
            ProposalItem item = it.next();
            if (item.isConstructor() || item.isModifier(Modifier.STATIC)) {
                it.remove();
            }
        }
        return items;
    }

    private Collection<? extends ProposalItem> getStaticProposals(Collection<? extends ProposalItem> items) {
        Iterator<? extends ProposalItem> it = items.iterator();
        while (it.hasNext()) {
            ProposalItem item = it.next();
            if (!item.isModifier(Modifier.STATIC) || !item.isModifier(Modifier.PUBLIC)) {
                it.remove();
            }
        }
        return items;
    }

    private Collection<? extends ProposalItem> getPublicConstructors(Collection<? extends ProposalItem> items) {
        Iterator<? extends ProposalItem> it = items.iterator();
        while (it.hasNext()) {
            ProposalItem item = it.next();
            if (!item.isConstructor() || !item.isModifier(Modifier.PUBLIC)) {
                it.remove();
            }
        }
        return items;
    }

}
