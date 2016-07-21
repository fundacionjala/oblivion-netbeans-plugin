/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.LineMap;
import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.tools.JavaFileObject;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerCompilationUnitTree;

/**
 * Represents the abstract syntax tree for the trigger compilation units. 
 * @author Pablo Romero
 */
public class TriggerCompilationUnitTreeImpl extends CompoundTree<Tree> implements TriggerCompilationUnitTree{

    private static final List<? extends ImportTree> EMPTY_IMPORT_LIST = Collections.emptyList();
    private static final List<? extends AnnotationTree> EMPTY_PACKAGE_ANNOTATION_LIST = Collections.emptyList();
    
    private final List<Tree> TriggerTypeDeclarations; 
    
    public TriggerCompilationUnitTreeImpl( Tree triggerDeclaration) {
        super(Kind.COMPILATION_UNIT, null, Collections.singletonList(triggerDeclaration), null, null);
        this.TriggerTypeDeclarations = new ArrayList<>(children);
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
        return this.TriggerTypeDeclarations;
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
    public List<Tree> getTriggerDeclarations() {
        return this.TriggerTypeDeclarations;
    }
}
