/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.VariableTree;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Modifier;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ModifiersTreeImpl;
import org.fundacionjala.oblivion.apex.editor.order.ClassMember;
import org.fundacionjala.oblivion.apex.editor.order.ClassMemberClass;
import org.fundacionjala.oblivion.apex.editor.order.ClassMemberGrouper;
import org.fundacionjala.oblivion.apex.editor.order.OrderDocumentProcessor;
import org.fundacionjala.oblivion.apex.editor.order.IOrderDocumentProcessor;
import org.fundacionjala.oblivion.apex.editor.order.IOrderPreferencesLoader;
import org.fundacionjala.oblivion.apex.editor.order.OrderTreeVisitor;
import org.fundacionjala.oblivion.apex.editor.order.OrderPreferencesLoader;
import org.fundacionjala.oblivion.apex.editor.preferences.AccessModifier;
import org.fundacionjala.oblivion.apex.editor.preferences.ClassMemberType;
import org.fundacionjala.oblivion.apex.grammar.parser.ModifierSet;
import org.fundacionjala.oblivion.apex.parser.ApexLanguageParser.ApexParserResult;
import org.netbeans.editor.BaseAction;

/**
 * This class is used by NetBeans to start the order members process.
 *
 * @author Amir Aranibar
 */
public class ApexMembersOrganizer extends BaseAction {

    private static final Logger LOG = Logger.getLogger(ApexMembersOrganizer.class.getName());
    private static final String ACTION = "organize-members";

    private ClassMemberType[] membersSortOrder;
    private AccessModifier[] sortMembersByVisibility;
    private boolean orderAlphabetically;
    private boolean sortByVisibility;
    private final IOrderPreferencesLoader preferencesLoader;
    private final IOrderDocumentProcessor documentProcessor;

    public ApexMembersOrganizer() {
        super(ACTION);
        preferencesLoader = new OrderPreferencesLoader();
        this.documentProcessor = new OrderDocumentProcessor();
    }

    public ApexMembersOrganizer(IOrderPreferencesLoader preferencesLoader, IOrderDocumentProcessor documentProcessor) {
        super(ACTION);
        this.preferencesLoader = preferencesLoader;
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void actionPerformed(ActionEvent event, JTextComponent target) {
        Document document = target.getDocument();
        loadPreferences(document);
        ApexParserResult parserResult = getParserResult(document);

        if (parserResult != null && document != null) {
            ClassMemberGrouper classMemberGrouper = new ClassMemberGrouper();
            OrderTreeVisitor visitor = new OrderTreeVisitor(document);
            parserResult.getCompilationUnit().accept(visitor, classMemberGrouper);

            orderClassMembers(document, classMemberGrouper);
        }
    }

    /**
     * Adds a class member in to the list grouped by access modifier.
     *
     * @param member the class member.
     * @param modifier the access modifier to define the list.
     * @param modifiersGroupMap the map that contains the list for each access
     * modifier.
     * @param document the document to get the text.
     * @throws BadLocationException
     */
    private void addMemberByModifier(ClassMember<?> member, AccessModifier modifier, HashMap<AccessModifier, List<ClassMember<?>>> modifiersGroupMap, Document document) throws BadLocationException {
        String memberText = document.getText(member.getStartPosition(), member.getEndPosition() - member.getStartPosition());
        List<ClassMember<?>> membersByModifier = modifiersGroupMap.get(modifier);

        if (membersByModifier == null) {
            membersByModifier = new ArrayList<>();
            modifiersGroupMap.put(modifier, membersByModifier);
        }

        member.setText(memberText);
        membersByModifier.add(member);
    }

    /**
     * Returns the class member kind as String.
     *
     * @param member the class member.
     * @param kind the kind of the BaseTree.
     * @param isStatic defines if the class member is static.
     * @return the class member kind as String.
     */
    private ClassMemberType getClassMemberKind(ClassMember<?> member, Kind kind, boolean isStatic) {
        ClassMemberType classMemberKind = null;

        switch (kind) {
            case VARIABLE:
            case OTHER:
                if (isStatic) {
                    classMemberKind = ClassMemberType.STATIC_FIELD;
                } else {
                    classMemberKind = ClassMemberType.FIELD;
                }

                break;
            case METHOD:
                Tree type = ((MethodTree) member.getType()).getReturnType();

                if (type == null) {
                    classMemberKind = ClassMemberType.CONSTRUCTOR;
                } else if (isStatic) {
                    classMemberKind = ClassMemberType.STATIC_METHOD;
                } else {
                    classMemberKind = ClassMemberType.METHOD;
                }

                break;
            case BLOCK:
                if (isStatic) {
                    classMemberKind = ClassMemberType.STATIC_INITIALIZER;
                } else {
                    classMemberKind = ClassMemberType.INSTANCE_INITIALIZER;
                }

                break;
            case CLASS:
                if (isStatic) {
                    classMemberKind = ClassMemberType.STATIC_CLASSE;
                } else {
                    classMemberKind = ClassMemberType.CLASS;
                }

                break;
        }

        return classMemberKind;
    }

    /**
     * Given a list of access modifiers, gets the first access modifier.
     *
     * @param modifiersList the list of access modifiers.
     * @return the first access modifier.
     */
    private AccessModifier getFirstAccessModifier(ModifiersTreeImpl modifiersList) {
        AccessModifier firstModifier = null;

        if (modifiersList != null && sortByVisibility) {
            for (Integer modifier : modifiersList.getApexModifiers()) {
                switch (modifier) {
                    case ModifierSet.GLOBAL:
                        firstModifier = AccessModifier.GLOBAL;
                        break;
                    case ModifierSet.PUBLIC:
                        firstModifier = AccessModifier.PUBLIC;
                        break;
                    case ModifierSet.PROTECTED:
                        firstModifier = AccessModifier.PROTECTED;
                        break;
                    case ModifierSet.PRIVATE:
                        firstModifier = AccessModifier.PRIVATE;
                        break;
                }

                if (firstModifier != null) {
                    break;
                }
            }
        }

        if (firstModifier == null) {
            firstModifier = AccessModifier.DEFAULT;
        }

        return firstModifier;
    }

    /**
     * Given a class member, gets the list of access modifiers.
     *
     * @param member the class member.
     * @return the list of access modifiers
     */
    private ModifiersTreeImpl getModifiersList(ClassMember<?> member) {
        BaseTree baseTree = member.getType();
        ModifiersTreeImpl modifiersTree = null;

        if (baseTree instanceof ClassTree) {
            modifiersTree = (ModifiersTreeImpl) ((ClassTree) baseTree).getModifiers();
        } else if (baseTree instanceof MethodTree) {
            modifiersTree = (ModifiersTreeImpl) ((MethodTree) baseTree).getModifiers();
        } else if (baseTree instanceof VariableTree) {
            modifiersTree = (ModifiersTreeImpl) ((VariableTree) baseTree).getModifiers();
        }

        return modifiersTree;
    }

    /**
     * Gets the parse result of a file object.
     *
     * @param fileObject the file object that contains the source text
     * @return the parse result.
     */
    private ApexParserResult getParserResult(Document document) {
        documentProcessor.processDocument(document);
        return documentProcessor.getParserResult();
    }

    /**
     * Groups the class members by kind.
     *
     * @param groupMap the map that contains the list of class members, grouped
     * by kind.
     * @param members the list of class members of a specific class.
     * @param document the document to get the text of a class member.
     */
    private void groupMembersByKind(LinkedHashMap<ClassMemberType, LinkedHashMap<AccessModifier, List<ClassMember<?>>>> groupMap, List<ClassMember<?>> members, ClassMemberClass parentClass, Document document) {
        int membersLength = members.size();

        for (int index = 0; index < membersLength; index++) {
            ClassMember<?> member = members.get(index);
            Kind kind = member.getKind();
            ModifiersTreeImpl modifiersList = getModifiersList(member);
            boolean isStatic = isStatic(member.getType(), modifiersList);
            AccessModifier firstModifier = getFirstAccessModifier(modifiersList);
            ClassMemberType classMemberKind = getClassMemberKind(member, kind, isStatic);

            if (index + 1 < membersLength) {
                ClassMember<?> previousMember = members.get(index + 1);
                if (previousMember.getEndPosition() > member.getCommentStartPosition()) {
                    member.clearCommentStartPosition();
                    member.setStartPosition(previousMember.getEndPosition());
                }
            }

            try {
                LinkedHashMap<AccessModifier, List<ClassMember<?>>> groupByKind = groupMap.get(classMemberKind);

                if (classMemberKind == ClassMemberType.STATIC_INITIALIZER) {
                    setStaticBlockStartPosition(members, member, parentClass, index);
                }

                if (groupByKind == null) {
                    groupByKind = new LinkedHashMap<>();
                    groupMap.put(classMemberKind, groupByKind);
                }

                addMemberByModifier(member, firstModifier, groupByKind, document);
            } catch (BadLocationException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    /**
     * Sets a new start position to include the static keyword as part of the
     * block
     *
     * @param members the list of class members.
     * @param member the block to set the start position.
     * @param parentClass the parent class of the block.
     * @param index the current index of the list.
     */
    private void setStaticBlockStartPosition(List<ClassMember<?>> members, ClassMember<?> member, ClassMemberClass parentClass, int index) {
        int membersLength = members.size();

        if (index + 1 < membersLength) {
            ClassMember<?> previousMember = members.get(index + 1);
            member.setStartPosition(previousMember.getEndPosition());
        } else {
            member.setStartPosition(parentClass.getBlockStartPosition());
        }

        member.clearCommentStartPosition();
    }

    /**
     * Inserts the class members in a specific document following the ordering
     * rules.
     *
     * @param parentClass the parent class to insert the class members.
     * @param groupMap the map with the grouped class members.
     * @param document the document to insert the class members.
     */
    private void insertMembersByClassToDocument(ClassMemberClass parentClass, LinkedHashMap<ClassMemberType, LinkedHashMap<AccessModifier, List<ClassMember<?>>>> groupMap, Document document) {
        int startPosition = parentClass.getBlockStartPosition();

        for (ClassMemberType orderingRule : membersSortOrder) {
            LinkedHashMap<AccessModifier, List<ClassMember<?>>> membersByModifier = groupMap.get(orderingRule);

            if (membersByModifier != null) {
                insertMembersByModifierToDocument(orderingRule, membersByModifier, document, startPosition);
            }
        }
    }

    /**
     * Inserts the class members in a specific document following the visibility
     * rules.
     *
     * @param groupKind the current kind of class members.
     * @param groupMap the class members.
     * @param document the document to insert the class members.
     * @param startPosition the start position to insert each class member.
     */
    private void insertMembersByModifierToDocument(ClassMemberType groupKind, LinkedHashMap<AccessModifier, List<ClassMember<?>>> groupMap, Document document, int startPosition) {
        for (AccessModifier accessModifier : sortMembersByVisibility) {
            List<ClassMember<?>> membersGroup = groupMap.get(accessModifier);

            if (membersGroup != null) {
                insertMembersToDocument(groupKind, membersGroup, document, startPosition);
            }
        }
    }

    /**
     * Inserts the class members in a specific document sorted by identifier or
     * position.
     *
     * @param group the list of class members.
     * @param document the document to insert the class members.
     * @param startPosition the start position to insert each class member.
     */
    private void insertMembersToDocument(ClassMemberType groupKind, List<ClassMember<?>> group, Document document, int startPosition) {
        if (orderAlphabetically && groupKind != ClassMemberType.INSTANCE_INITIALIZER && groupKind != ClassMemberType.STATIC_INITIALIZER) {
            orderMembersAlphabetically(group);
        } else {
            Collections.sort(group);
        }

        for (ClassMember<?> member : group) {
            try {
                String text = member.getText();
                if (text != null) {
                    document.insertString(startPosition, text, null);
                    startPosition += text.length();
                }
            } catch (BadLocationException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    /**
     * Finds the static modifier in a list of modifiers.
     *
     * @param modifiersList the list of modifiers.
     * @return true if there is a static modifier, otherwise false.
     */
    private boolean isStatic(BaseTree tree, ModifiersTreeImpl modifiersList) {
        boolean isStatic = false;

        if (tree instanceof BlockTree) {
            isStatic = ((BlockTree) tree).isStatic();
        } else if (modifiersList != null) {
            for (Modifier modifier : modifiersList.getFlags()) {
                if (modifier == Modifier.STATIC) {
                    isStatic = true;
                    break;
                }
            }
        }

        return isStatic;
    }

    /**
     * Loads the ordering options values defined in the editor preferences.
     *
     * @param document the document to load its preferences defined by the user.
     */
    private void loadPreferences(Document document) {
        preferencesLoader.loadPreferences(document);
        membersSortOrder = preferencesLoader.getSortedMembers();
        orderAlphabetically = preferencesLoader.orderAlphabetically();
        sortByVisibility = preferencesLoader.sortByVisibility();

        if (sortByVisibility) {
            sortMembersByVisibility = preferencesLoader.getSortedModifiers();
        } else {
            sortMembersByVisibility = new AccessModifier[]{AccessModifier.DEFAULT};
        }
    }

    /**
     * Reorders the class members of a document.
     *
     * @param document the document that contains the class members.
     * @param classMemberGrouper the class members grouped by kind.
     */
    private void orderClassMembers(Document document, ClassMemberGrouper classMemberGrouper) {
        setClassMembers(classMemberGrouper);
        List<ClassMemberClass> classList = classMemberGrouper.getClasses();
        Collections.sort(classList, Collections.reverseOrder());

        for (ClassMemberClass memberClass : classList) {
            LinkedHashMap<ClassMemberType, LinkedHashMap<AccessModifier, List<ClassMember<?>>>> groupMap = new LinkedHashMap<>();
            List<ClassMember<?>> members = memberClass.getMembers();
            Collections.sort(members, Collections.reverseOrder());

            groupMembersByKind(groupMap, members, memberClass, document);
            removeMembersFromDocument(members, document);
            insertMembersByClassToDocument(memberClass, groupMap, document);
        }
    }

    /**
     * Reorders alphabetically a list of class members.
     *
     * @param members the list of class members.
     */
    private void orderMembersAlphabetically(List<ClassMember<?>> members) {
        Collections.sort(members, new Comparator<ClassMember<?>>() {
            @Override
            public int compare(ClassMember<?> t1, ClassMember<?> t2) {
                String identifierText1 = t1.getType().getToken().getImage();
                String identifierText2 = t2.getType().getToken().getImage();

                return identifierText1.compareTo(identifierText2);
            }
        });
    }

    /**
     * Removes a list of class members from a specific document.
     *
     * @param members the list of class members.
     * @param document the document to remove the class members.
     */
    private void removeMembersFromDocument(List<ClassMember<?>> members, Document document) {
        for (ClassMember<?> member : members) {
            try {
                document.remove(member.getStartPosition(), member.getEndPosition() - member.getStartPosition());
            } catch (BadLocationException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    /**
     * Sets the class member in a parent class.
     *
     * @param member the class member.
     * @param classList the list of parent classes.
     */
    private void setClassMember(ClassMember<?> member, List<ClassMemberClass> classList) {
        ClassTreeImpl parentClass = member.getParentClass();

        if (parentClass == null) {
            return;
        }

        for (ClassMemberClass memberClass : classList) {
            BaseTree baseTree = memberClass.getType();
            if (parentClass.equals(baseTree)) {
                memberClass.addMember(member);
                break;
            }
        }
    }

    /**
     * Sets all class members in their parent class.
     *
     * @param classMemberGrouper the list with all class members.
     */
    private void setClassMembers(ClassMemberGrouper classMemberGrouper) {
        List<ClassMemberClass> classList = classMemberGrouper.getClasses();
        List<ClassMember<?>> memberList = classMemberGrouper.getAllMembers();

        for (ClassMember<?> member : memberList) {
            setClassMember(member, classList);
        }
    }
}
