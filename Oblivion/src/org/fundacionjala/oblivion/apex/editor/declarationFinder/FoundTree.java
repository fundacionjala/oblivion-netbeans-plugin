/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.declarationFinder;

import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.IdentifierTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodInvocationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.PropertyTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.TriggerDeclarationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.lexer.ApexLanguageHierarchy;

/**
 * Class that recovers the declarations for to methods and variables
 *
 * @author sergio_daza
 */
public class FoundTree {

    private static final String EMPTY_STRING = "";
    private static final String THIS_REFERENCE = "this";
    private static final String SUPER_REFERENCE = "super";
    private TypeOfIdentifier type;
    private IdentifierTreeImpl identifier;
    private Tree parent;
    private String identifierName;
    private String reference = EMPTY_STRING;
    private String mimeType = EMPTY_STRING;
    public String className = EMPTY_STRING;
    public MethodInvocationTreeImpl methodInvocation;
    public List<ClassTreeImpl> classes = new ArrayList<>();
    public List<MethodTreeImpl> methods = new ArrayList<>();
    public List<Tree> declarations = new ArrayList<>();
    public IdentifierTreeImpl extendsIdentifier = null;

    public FoundTree() {
        this(EMPTY_STRING, EMPTY_STRING);
    }

    public FoundTree(String name, String mimeType) {
        this.type = TypeOfIdentifier.UNKNOWN;
        this.identifier = null;
        this.identifierName = name;
        this.mimeType = mimeType;
    }

    public void saveTreeIdentifier(IdentifierTreeImpl identifier, Tree parent, TypeOfIdentifier type, String className) {
        if (this.type == TypeOfIdentifier.UNKNOWN) {
            this.identifier = identifier;
            this.parent = parent;
            this.type = type;
            this.className = className;
            if (identifier.getReference()!= null) {
                this.reference = identifier.getReference().getImage();
            } else {
                this.reference = EMPTY_STRING;
            }
        }
    }

    public Tree getDeclaration() {
        Tree tree = null;
        if (type.equals(TypeOfIdentifier.TYPE_VARIABLE_NAME) || type.equals(TypeOfIdentifier.EXTENDS_CLASS_VARIABLE_NAME)) {
            tree = searchInClasses();
        } else if (type.equals(TypeOfIdentifier.LOCAL_METHOD_INVOCATION)) {
            tree = searchMethod();
        } else if (type.equals(TypeOfIdentifier.SUPER_VARIABLE_NAME)) {
            return this.identifier;
        } else if ((type.equals(TypeOfIdentifier.LOCAL_VARIABLE_NAME) || type.equals(TypeOfIdentifier.LOCAL_ARRAY_VARIABLE_NAME)
                || type.equals(TypeOfIdentifier.EXTERNAL_VARIABLE_NAME)) && !reference.equals(SUPER_REFERENCE)) {
            tree = searchVariable(this.parent);
        }
        return tree;
    }

    public TypeOfIdentifier getType() {
        return type;
    }

    private Tree searchInClasses() {
        if (!classes.isEmpty()) {
            ClassTreeImpl cl = classes.get(0);
            Token token = cl.getToken();
            return cl;
        }
        return null;
    }

    private Tree searchMethod() {
        boolean isTrigger =  this.mimeType.equals(ApexLanguageHierarchy.APEX_TRIGGER_MIME_TYPE);
        if (this.identifierName.equals(THIS_REFERENCE)) {
            this.identifierName = this.className;
        }
        for (MethodTreeImpl method : methods) {
            if (method.getName().toString().equalsIgnoreCase(identifierName)) {
                if (methodInvocation.getArguments().size() == method.getParameters().size()) {
                    Tree parent = method.getParent();
                    if (parent != null) {
                        if (parent instanceof ClassTreeImpl) {
                            ClassTreeImpl classParent = (ClassTreeImpl) parent;
                            if (classParent != null) {
                                if (classParent.getSimpleName().toString().equalsIgnoreCase(className)) {
                                    return method;
                                }
                            }
                        } else if (parent instanceof TriggerDeclarationTreeImpl && this.mimeType.equals(ApexLanguageHierarchy.APEX_TRIGGER_MIME_TYPE)) {
                            return method;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Tree searchSuper() {
        return searchSuper(this.parent);
    }

    private Tree searchSuper(Tree parent) {
        if (parent instanceof ClassTreeImpl) {
            ClassTreeImpl cl = (ClassTreeImpl) parent;
            return cl.getExtendsClause();
        }
        if (parent instanceof BaseTree) {
            BaseTree baseTreeParent = (BaseTree) parent;
            return searchSuper(baseTreeParent.getParent());
        }
        return null;
    }

    private Tree searchVariable(Tree parent) {
        if (parent != null) {
            for (Tree declaration : declarations) {
                if (declaration instanceof VariableTreeImpl) {
                    VariableTreeImpl variable = (VariableTreeImpl) declaration;
                    if (variable instanceof BaseTree) {
                        BaseTree baseTree = (BaseTree) variable;
                        if (reference.equals(THIS_REFERENCE)) {
                            Tree thisBaseTree = variable.getParent();
                            if (thisBaseTree instanceof ClassTreeImpl) {
                                ClassTreeImpl classParent = (ClassTreeImpl) thisBaseTree;
                                if (classParent.getSimpleName().toString().equalsIgnoreCase(className)) {
                                    return variable;
                                }
                            }
                        } else if (baseTree.getParent().toString().equalsIgnoreCase(parent.toString())) {
                            return variable;
                        }
                    }
                } 
                else if (declaration instanceof PropertyTreeImpl) {
                    return (PropertyTreeImpl) declaration;
                } 
            }
            if (parent instanceof BaseTree) {
                BaseTree baseTreeParent = (BaseTree) parent;
                return searchVariable(baseTreeParent.getParent());
            }
        }
        return null;
    }

}
