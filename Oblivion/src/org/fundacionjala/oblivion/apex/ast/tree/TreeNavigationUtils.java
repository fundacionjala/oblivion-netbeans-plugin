/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.ast.tree;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fundacionjala.oblivion.apex.ast.tree.visitors.TreeFinder;

/**
 * Utility class that return the parent Node of the given child.
 *
 * @author Adrian Grajeda
 */
public class TreeNavigationUtils {

    private static final Logger LOG = Logger.getLogger(TreeNavigationUtils.class.getName());

    /**
     * Return the parent node of the given node
     * @param child
     * @return
     */
    public static Tree getParentNode(Tree child) {
        if (!(child instanceof BaseTree)) {
            LOG.log(Level.FINE, "{0} unrecognized Tree implementation", child);
            return null;
        }

        return (Tree) ((BaseTree) child).getParent();
    }
    
    public static <T extends Tree> List<T> findByName(Tree unit, TreeFinder.Criteria criteria) {
        TreeFinder<T> finder = new TreeFinder<>(criteria);
        unit.accept(finder, null);
        return finder.getResult();
    }

    public static List<ClassTree> findClassTreeByName(final String name, CompilationUnitTree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if (checkNodeType(tree)) {
                    ClassTree classTree = (ClassTree) tree;
                    return classTree.getSimpleName().equals(name);
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof ClassTree);
            }
        };
        return findByName(unit, criteria);
    }
    
    public static List<ClassTree> findAllClasses(Tree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if (checkNodeType(tree)) {
                    return true;
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof ClassTree);
            }
        };
        return findByName(unit, criteria);
    }

    public static List<MethodTree> findMethodTreeByName(final String name, CompilationUnitTree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if (checkNodeType(tree)) {
                    MethodTree methodTree = (MethodTree) tree;
                    return methodTree.getName().equals(name);
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof MethodTree);
            }
        };
        return findByName(unit, criteria);
    }
    
    public static List<MethodTree> findAllMethods(Tree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if (checkNodeType(tree)) {
                    return true;
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof MethodTree);
            }
        };
        return findByName(unit, criteria);
    }

    public static List<VariableTree> findVariableTreeByName(final String name, CompilationUnitTree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if(checkNodeType(tree)) {
                    VariableTree variable = (VariableTree) tree;
                    return variable.getName().equals(name);
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof VariableTree);
            }
        };
        return findByName(unit, criteria);
    }
    
    public static List<VariableTree> findAllVariableTree(Tree unit) {
        TreeFinder.Criteria criteria = new TreeFinder.Criteria() {

            @Override
            public boolean apply(Tree tree) {
                if(checkNodeType(tree)) {
                    return true;
                }
                return Boolean.FALSE;
            }

            @Override
            public boolean checkNodeType(Tree node) {
                return (node instanceof VariableTree);
            }
        };
        return findByName(unit, criteria);
    }
}
