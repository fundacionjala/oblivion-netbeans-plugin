/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.AnnotationTree;
import org.fundacionjala.oblivion.apex.grammar.ast.ModifiersTree;
import com.sun.source.tree.TreeVisitor;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.grammar.parser.ModifierSet;

/**
 * Implements the {@link ModifiersTree} for Apex language.
 *
 * @author Adrian Grajeda
 */
public final class ModifiersTreeImpl extends BaseTree implements ModifiersTree {

    private final LinkedHashMap<Integer, Token> modifiers;
    private final Set<Modifier> flags;
    private final List<? extends AnnotationTree> annotations;

    ModifiersTreeImpl(LinkedHashMap<Integer, Token> modifiers, List<? extends AnnotationTree> annotations) {
        super(Kind.MODIFIERS, null);
        this.flags = new HashSet<>();
        this.annotations = annotations;
        this.modifiers = modifiers;
        createModifiers();
    }
    
    @Override
    public Set<Modifier> getFlags() {
        return Collections.unmodifiableSet(flags);
    }

    public Set<Integer> getApexModifiers() {
        return modifiers.keySet();
    }

    @Override
    public List<? extends AnnotationTree> getAnnotations() {
        return Collections.unmodifiableList(annotations);
    }

    @Override
    public <R, D> R accept(TreeVisitor<R, D> tv, D d) {
        tv.visitModifiers(this, d);
        return null;
    }

    @Override
    public LinkedHashMap<Integer, Token> getModifiers() {
        return modifiers;
    }

    private void createModifiers() {
        if (modifiers != null) {
            for (Map.Entry<Integer, Token> modifier : modifiers.entrySet()) {
                parseToModifier(modifier.getKey());
            }
        }
    }

    private void parseToModifier(int flag) {
        ModifierSet modifierSet = new ModifierSet();
        if (modifierSet.isAbstract(flag)) {
            flags.add(Modifier.ABSTRACT);
        }
        else if (modifierSet.isFinal(flag)) {
            flags.add(Modifier.FINAL);
        }
        else if (modifierSet.isPrivate(flag)) {
            flags.add(Modifier.PRIVATE);
        }
        else if (modifierSet.isProtected(flag)) {
            flags.add(Modifier.PROTECTED);
        }
        else if (modifierSet.isPublic(flag)) {
            flags.add(Modifier.PUBLIC);
        }
        else if (modifierSet.isStatic(flag)) {
            flags.add(Modifier.STATIC);
        }
        else if (modifierSet.isTransient(flag)) {
            flags.add(Modifier.TRANSIENT);
        }
    }

    @Override
    public void addModifiers(ModifiersTree modifiers) {
        if(modifiers != null) {
            this.modifiers.putAll(modifiers.getModifiers());
            this.flags.clear();
            createModifiers();
        }
    }
}
