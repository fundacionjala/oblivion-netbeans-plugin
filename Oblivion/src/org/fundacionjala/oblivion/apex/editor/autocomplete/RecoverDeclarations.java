/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.autocomplete;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.BlockTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.fundacionjala.oblivion.apex.ast.tree.ConstructorTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.DoWhileLoopTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ForLoopTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.IfTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.MethodTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.PropertyTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.TryTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.WhileLoopTreeImpl;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.openide.text.NbDocument;

/**
 * This class runs the compilationUnit on searching the declarations for the
 * class, properties, method and variables.
 *
 * @author sergio_daza
 */
public class RecoverDeclarations {

    
    private final int offset;
    private final StyledDocument styledDocument;
    private final CompilationUnitTree unit;
    public String classNameForCursor;
    public Tree parentForCursor;
    private boolean foundCursor;
    public final Map<String, Elements> collectionOfDeclarations = new HashMap<>();
    public List<ClassItem> classes = new ArrayList<>();
    private static final Map<javax.lang.model.element.Modifier, org.netbeans.modules.csl.api.Modifier> MAP_MODIFIER = new HashMap();
    static {
        MAP_MODIFIER.put(javax.lang.model.element.Modifier.PUBLIC, org.netbeans.modules.csl.api.Modifier.PUBLIC);
        MAP_MODIFIER.put(javax.lang.model.element.Modifier.PRIVATE, org.netbeans.modules.csl.api.Modifier.PRIVATE);
        MAP_MODIFIER.put(javax.lang.model.element.Modifier.PROTECTED, org.netbeans.modules.csl.api.Modifier.PROTECTED);
        MAP_MODIFIER.put(javax.lang.model.element.Modifier.STATIC, org.netbeans.modules.csl.api.Modifier.STATIC);
        MAP_MODIFIER.put(javax.lang.model.element.Modifier.ABSTRACT, org.netbeans.modules.csl.api.Modifier.ABSTRACT);
    }

    public RecoverDeclarations(final int offset, final StyledDocument styledDocument, CompilationUnitTree unit) {
        this.parentForCursor = null;
        this.foundCursor = false;
        this.offset = offset;
        this.styledDocument = styledDocument;
        this.unit = unit;
    }

    public boolean SearchIdentifier() {
        boolean result = false;
        List<? extends Tree> typeDecls = unit.getTypeDecls();
        for (Tree type : typeDecls) {
            if (type instanceof ClassTreeImpl) {
                result |= IsInClass((ClassTreeImpl) type);
            }
        }
        return result;
    }

    private boolean IsInClass(ClassTreeImpl classTree) {
        boolean result = false;
        if (classTree.getBlockEnd() != null) {
            this.classes.add(new ClassItem(classTree.getSimpleName().toString(), "", offset, modifiersAdapter(classTree.getModifiers())));
            Elements elements = new Elements();
            Tree extendsClause = classTree.getExtendsClause();
            if (extendsClause != null) {
                elements.extendsClassName = classTree.getExtendsClause().toString();
            }
            this.collectionOfDeclarations.put(classTree.getSimpleName().toString().toLowerCase(), elements);
            result = SearchInClassChildren(classTree.getChildren(), classTree, classTree.getSimpleName().toString().toLowerCase());
            if (!this.foundCursor) {
                if (IsInRange((CompoundTree) classTree)) {
                    this.classNameForCursor = classTree.getSimpleName().toString();
                    this.parentForCursor = classTree;
                    this.foundCursor = true;
                    result |= true;
                }
            }
        }
        return result;
    }

    private boolean IsInVariableDeclaration(VariableTreeImpl variable, Tree parent, String className) {
        if (variable.getName() != null && variable.getType() != null) {
            this.collectionOfDeclarations.get(className).variables.add(variable);
        }
        return false;
    }

    private boolean IsInAttributeDeclaration(VariableTreeImpl attribute, Tree parent, String className) {
        if (attribute.getName() != null && attribute.getType() != null) {
            this.collectionOfDeclarations.get(className).attributes.add(attribute);
            Set<Modifier> modifiersAdapter = modifiersAdapter(attribute.getModifiers());
            this.collectionOfDeclarations.get(className).item_attributes.add(new AttributeItem(attribute.getName().toString(), attribute.getType().toString(), offset, modifiersAdapter(attribute.getModifiers())));
        }
        return false;
    }

    private boolean IsInPropertyDeclaration(PropertyTreeImpl property, Tree parent, String className) {
        if (property.getName() != null && property.getType() != null) {
            this.collectionOfDeclarations.get(className).item_properties.add(new PropertyItem(property.getName().toString(), property.getType().toString(), offset));
        }
        return false;
    }

    private boolean IsInMethodTree(MethodTreeImpl method, String className) {
        if (method.getName() != null && method.getReturnType() != null) {
            MethodItem methodItem = new MethodItem(method.getName().toString(), method.getReturnType().toString(), offset, modifiersAdapter(method.getModifiers()), ElementKind.METHOD);
            if (method.getParameters() != null) {
                methodItem.variablesMethod = method.getParameters();
            }
            this.collectionOfDeclarations.get(className).item_methods.add(methodItem);
        }
        List<? extends VariableTree> parameters = method.getParameters();
        for (VariableTree variable : parameters) {
            IsInVariableDeclaration((VariableTreeImpl) variable, method, className);
        }
        boolean result = SearchInMethodBlock((BlockTreeImpl) method.getBody(), method.getBody(), className);
        if (IsInRange((CompoundTree) method.getBody()) && !this.foundCursor) {
            this.classNameForCursor = className;
            this.parentForCursor = method.getBody();
            this.foundCursor = true;
            result = true;
        }
        return result;
    }

    private boolean IsInBlock(BlockTreeImpl blockTree, Tree parent, String className) {
        boolean result = false;
        if (blockTree != null) {
            List<? extends StatementTree> statements = blockTree.getStatements();
            for (StatementTree statement : statements) {
                result |= SearchOnBodyBlock(statement, blockTree, className);
            }
        }
        if (IsInRange((CompoundTree) blockTree) && !this.foundCursor) {
            this.classNameForCursor = className;
            this.parentForCursor = blockTree;
            this.foundCursor = true;
            result = true;
        }
        return result;
    }

    private boolean IsInIf(IfTreeImpl ifTree, Tree parent, String className) {
        boolean result = false;
        result |= SearchOnBodyBlock(ifTree.getThenStatement(), parent, className);
        result |= SearchOnBodyBlock(ifTree.getElseStatement(), parent, className);
        return result;
    }

    private boolean IsInForLoop(ForLoopTreeImpl forLoop, Tree parent, String className) {
        boolean result = false;
        result |= SearchInExpression(forLoop.getCondition(), parent, className);
        result |= SearchOnBodyBlock(forLoop.getStatement(), parent, className);
        List<? extends StatementTree> initializer = forLoop.getInitializer();
        for (StatementTree variable : initializer) {
            SearchOnBodyBlock(variable, parent, className);
        }
        return result;
    }

    private boolean IsInWhileLoop(WhileLoopTreeImpl whileLoop, Tree parent, String className) {
        boolean result = false;
        result |= SearchInExpression(whileLoop.getCondition(), parent, className);
        result |= SearchOnBodyBlock(whileLoop.getStatement(), parent, className);
        return result;
    }

    private boolean IsInDoWhileLoop(DoWhileLoopTreeImpl doWhileLoop, Tree parent, String className) {
        boolean result = false;
        result |= SearchInExpression(doWhileLoop.getCondition(), parent, className);
        result |= SearchOnBodyBlock(doWhileLoop.getStatement(), parent, className);
        return result;
    }

    private boolean IsInTry(TryTreeImpl tryTree, Tree parent, String className) {
        boolean result = false;
        result |= IsInBlock((BlockTreeImpl) tryTree.getFinallyBlock(), parent, className);
        result |= IsInBlock((BlockTreeImpl) tryTree.getBlock(), parent, className);
        List<? extends CatchTree> catches = tryTree.getCatches();
        for (CatchTree catchTree : catches) {
            VariableTree parameter = catchTree.getParameter();
            result |= IsInVariableDeclaration((VariableTreeImpl) parameter, parent, className);
            BlockTree block = catchTree.getBlock();
            result |= IsInBlock((BlockTreeImpl) block, parent, className);
        }
        return result;
    }

    private boolean SearchOnBodyBlock(StatementTree statement, Tree parent, String className) {
        if (statement instanceof VariableTreeImpl) {
            return IsInVariableDeclaration((VariableTreeImpl) statement, parent, className);
        } else if (statement instanceof BlockTreeImpl) {
            return IsInBlock((BlockTreeImpl) statement, parent, className);
        } else if (statement instanceof IfTreeImpl) {
            return IsInIf((IfTreeImpl) statement, parent, className);
        } else if (statement instanceof ForLoopTreeImpl) {
            return IsInForLoop((ForLoopTreeImpl) statement, parent, className);
        } else if (statement instanceof WhileLoopTreeImpl) {
            return IsInWhileLoop((WhileLoopTreeImpl) statement, parent, className);
        } else if (statement instanceof DoWhileLoopTreeImpl) {
            return IsInDoWhileLoop((DoWhileLoopTreeImpl) statement, parent, className);
        } else if (statement instanceof TryTreeImpl) {
            return IsInTry((TryTreeImpl) statement, parent, className);
        }
        return false;
    }

    private boolean SearchInClassChildren(Collection<? extends Tree> children, Tree parent, String className) {
        boolean result = false;
        Iterator itr = children.iterator();
        List<ClassTreeImpl> classes = new ArrayList<>();
        while (itr.hasNext()) {
            Object element = itr.next();
            if (element instanceof ClassTreeImpl) {
                classes.add((ClassTreeImpl) element);
            } else if (element instanceof VariableTreeImpl) {
                result |= IsInAttributeDeclaration((VariableTreeImpl) element, parent, className);
            } else if (element instanceof ExpressionTree) {
                result |= SearchInExpression((ExpressionTree) element, parent, className);
            }
        }
        for (ClassTreeImpl cl : classes) {
            result |= IsInClass(cl);
        }
        return result;
    }

    private boolean SearchInExpression(ExpressionTree expression, Tree parent, String className) {
        boolean result = false;
        if (expression instanceof ConstructorTreeImpl) {
            result |= IsInConstructor((ConstructorTreeImpl) expression, className);
        } else if (expression instanceof MethodTreeImpl) {
            result |= IsInMethodTree((MethodTreeImpl) expression, className);
        } else if (expression instanceof VariableTreeImpl) {
            result |= IsInVariableDeclaration((VariableTreeImpl) expression, parent, className);
        } else if (expression instanceof PropertyTreeImpl) {
            result |= IsInPropertyDeclaration((PropertyTreeImpl) expression, parent, className);
        } else if (expression instanceof BlockTreeImpl) {
            result |= IsInBlock((BlockTreeImpl) expression, parent, className);
        }
        return result;
    }

    private boolean IsInConstructor(ConstructorTreeImpl constructor, String className) {
        if (constructor.getName() != null) {
            MethodItem methodItem = new MethodItem(constructor.getName().toString(), "", offset, modifiersAdapter(constructor.getModifiers()), ElementKind.CONSTRUCTOR);
            if (constructor.getParameters() != null) {
                methodItem.variablesMethod = constructor.getParameters();
            }
            this.collectionOfDeclarations.get(className).item_methods.add(methodItem);
        }
        List<? extends VariableTree> parameters = constructor.getParameters();
        for (VariableTree variable : parameters) {
            IsInVariableDeclaration((VariableTreeImpl) variable, constructor, className);
        }
        boolean result = SearchInMethodBlock((BlockTreeImpl) constructor.getBody(), constructor.getBody(), className);
        if (IsInRange((CompoundTree) constructor.getBody()) && !this.foundCursor) {
            this.classNameForCursor = className;
            this.parentForCursor = constructor.getBody();
            this.foundCursor = true;
            result = true;
        }
        return result;
    }

    private boolean SearchInMethodBlock(BlockTreeImpl body, Tree parent, String className) {
        boolean result = false;
        if (body != null) {
            List<? extends StatementTree> statements = body.getStatements();
            for (StatementTree statement : statements) {
                result |= SearchOnBodyBlock(statement, body, className);
            }
        }
        return result;
    }

    public List<ProposalItem> getProposals() {
        List<ProposalItem> result = new ArrayList<>();
        if (this.collectionOfDeclarations.get(this.classNameForCursor) != null) {
            result.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).item_attributes);
            result.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).item_properties);
            result.addAll(this.classes);
            result.addAll(this.collectionOfDeclarations.get(this.classNameForCursor).item_methods);
            result.addAll(getVariables());
        }
        return result;
    }

    private List<VariableItem> getVariables() {
        List<VariableItem> result = new ArrayList<>();
        result = getVariables(result, this.parentForCursor);
        return result;
    }

    private List<VariableItem> getVariables(List<VariableItem> result, Tree parent) {
        if (parent != null) {
            for (VariableTreeImpl variable : this.collectionOfDeclarations.get(this.classNameForCursor).variables) {
                if (variable.getName() != null && variable.getType() != null) {
                    if (variable.getParent().equals(parent)) {
                        VariableItem variableItem = new VariableItem(variable.getName().toString(), variable.getType().toString(), offset, variable.getParent());
                        if (!result.contains(variableItem)) {
                            result.add(variableItem);
                        }
                    }
                }

            }

            if (parent instanceof BaseTree) {
                BaseTree baseTree = (BaseTree) parent;
                return getVariables(result, baseTree.getParent());
            }
        }
        return result;
    }

    private boolean IsInRange(CompoundTree compoundTree) {
        try {
            if (compoundTree != null && compoundTree.getBlockStart() != null && compoundTree.getBlockEnd() != null) {
                int offsetStart = NbDocument.findLineOffset((StyledDocument) styledDocument, compoundTree.getBlockStart().getEndLine() - 1) + compoundTree.getBlockStart().getEndColumn() - 1;
                int offsetEnd = NbDocument.findLineOffset((StyledDocument) styledDocument, compoundTree.getBlockEnd().getEndLine() - 1) + compoundTree.getBlockEnd().getEndColumn() - 1;
                return (offsetStart < this.offset && offsetEnd > this.offset);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }
    
    /**
     * method to cast from Set< javax.lang.model.element.Modifier> to
     * Set< org.netbeans.modules.csl.api.Modifier>
     *
     * @param modifiersFromTree
     * @return a set<org.netbeans.modules.csl.api.Modifier>
     */
    public static Set<org.netbeans.modules.csl.api.Modifier> modifiersAdapter(ModifiersTree modifiers) {
        Set<org.netbeans.modules.csl.api.Modifier> transformed = new HashSet<>();
        if (modifiers != null) {
            Set<javax.lang.model.element.Modifier> modifiersFromTree = modifiers.getFlags();
            for (javax.lang.model.element.Modifier modifierFromTree : modifiersFromTree) {
                if (MAP_MODIFIER.containsKey(modifierFromTree)) {
                    transformed.add(MAP_MODIFIER.get(modifierFromTree));
                }
            }
        }
        return transformed;
    }

}
