/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeParameterTree;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerDeclarationTree;
import org.fundacionjala.oblivion.apex.grammar.ast.trigger.TriggerParameterTree;

/**
 * Represents a tree node for a trigger declaration. For Example
 * <p>
 *      Trigger TriggerObject on Account(after insert){}
 * </p>
 * @author Pablo Romero
 */
public class TriggerDeclarationTreeImpl extends CompoundTree<Tree> implements TriggerDeclarationTree {
    private static final List<? extends TypeParameterTree> EMPTY_TYPE_PARAMETERS = Collections.emptyList();

    private final List<TriggerParameterTree> parameters;
    private final IdentifierTree name;
    private final IdentifierTree object;
    private final List<? extends Tree> members;
    public TriggerDeclarationTreeImpl(Token nameToken, IdentifierTree name, IdentifierTree object, List<TriggerParameterTree> parameters, List<? extends Tree> members) {
        super(Tree.Kind.COMPILATION_UNIT, nameToken);
        this.name = name;
        this.object = object;
        this.members = members;
        this.parameters = parameters;
        addMember(this.name);
        addMember(this.object);
        addMembers(this.members);
        addMembers(this.parameters);
        updateParentReference();
    }

    public List<? extends AnnotationTree> getPackageAnnotations(){
        return Collections.emptyList();
    }

    @Override
    public List<? extends Tree> getMembers() {
        return members;
    }

    @Override
    public List<TriggerParameterTree> getParameter() {
        return this.parameters;
    }
}
