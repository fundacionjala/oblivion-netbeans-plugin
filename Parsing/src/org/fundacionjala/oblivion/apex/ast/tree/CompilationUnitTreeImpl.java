/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.LineMap;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaFileObject;

import com.sun.source.tree.PackageTree;

/**
 * Represents the abstract tree for an Apex compilation unit.
 * @author Marcelo Garnica
 */
public class CompilationUnitTreeImpl extends CompoundTree<Tree> implements CompilationUnitTree {
    private static final List<? extends ImportTree> EMPTY_IMPORT_LIST = Collections.emptyList();
    private static final List<? extends AnnotationTree> EMPTY_PACKAGE_ANNOTATION_LIST = Collections.emptyList();
    
    private final List<Tree> typeDeclarations;

    public CompilationUnitTreeImpl(Tree typeDeclaration) {
        super(Kind.COMPILATION_UNIT, null, Collections.singletonList(typeDeclaration), null, null);
        this.typeDeclarations = new ArrayList<>(children);
    }

    @Override
    public List<? extends AnnotationTree> getPackageAnnotations() {
        return EMPTY_PACKAGE_ANNOTATION_LIST;
    }

    @Override
    public ExpressionTree getPackageName() {
        return null;
    }

    @Override
    public List<? extends ImportTree> getImports() {
        return EMPTY_IMPORT_LIST;
    }

    @Override
    public List<? extends Tree> getTypeDecls() {
        return typeDeclarations;
    }

    @Override
    public JavaFileObject getSourceFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LineMap getLineMap() {
        return null;
    }

    @Override
    public PackageTree getPackage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
