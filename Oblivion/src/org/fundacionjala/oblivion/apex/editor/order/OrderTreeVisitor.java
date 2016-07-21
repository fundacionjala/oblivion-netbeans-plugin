/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.editor.order;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.fundacionjala.oblivion.apex.ast.tree.BaseTree;
import org.fundacionjala.oblivion.apex.ast.tree.visitors.ApexTreeVisitorAdapter;
import org.fundacionjala.oblivion.apex.Token;
import org.fundacionjala.oblivion.apex.ast.tree.AnnotationTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ClassTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.CompoundTree;
import org.fundacionjala.oblivion.apex.ast.tree.IdentifierTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.ModifiersTreeImpl;
import org.fundacionjala.oblivion.apex.ast.tree.VariableTreeImpl;
import org.openide.text.NbDocument;

/**
 * Collects the class members and their position in the document.
 *
 * @author Amir Aranibar
 */
public class OrderTreeVisitor extends ApexTreeVisitorAdapter<Void, ClassMemberGrouper> {

    private static final Logger LOG = Logger.getLogger(OrderTreeVisitor.class.getName());
    private static final int TEXT_LENGTH = 1;
    private static final char NEWLINE_CHARACTER = '\n';
    private static final char TAB_CHARACTER = '\t';
    private static final char WHITESPACE_CHARACTER = ' ';
    private static final char ASTERISK_CHARACTER = '*';
    private static final char SLASH_CHARACTER = '/';
    private static final int INVALID_POSITION = -1;

    private final StyledDocument document;

    private boolean outerClass;

    public OrderTreeVisitor(Document document) {
        this.document = (StyledDocument) document;
        outerClass = true;
    }

    @Override
    public Void visitBlock(BlockTree bt, ClassMemberGrouper classMemberGrouper) {
        addClassMember((BaseTree) bt, classMemberGrouper);

        return null;
    }

    @Override
    public Void visitClass(ClassTree ct, ClassMemberGrouper classMemberGrouper) {
        if (outerClass) {
            ClassTreeImpl classTree = (ClassTreeImpl) ct;
            int startPosition = findStartPosition(classTree);
            int blockEndPosition = getTokenPosition(classTree.getBlockEnd());
            int blockStartPosition = getTokenPosition(classTree.getBlockStart());

            if (startPosition != INVALID_POSITION && blockEndPosition != INVALID_POSITION) {
                ClassMemberClass parentClassTree = new ClassMemberClass(classTree, startPosition, blockEndPosition, INVALID_POSITION, blockStartPosition, null);
                classMemberGrouper.addClass(parentClassTree);
            }

            outerClass = false;
        } else {
            addClassMember((BaseTree) ct, classMemberGrouper);
        }

        return null;
    }

    @Override
    public Void visitMethod(MethodTree mt, ClassMemberGrouper classMemberGrouper) {
        addClassMember((BaseTree) mt, classMemberGrouper);

        return null;
    }

    @Override
    public Void visitVariable(VariableTree vt, ClassMemberGrouper classMemberGrouper) {
        addClassMember((BaseTree) vt, classMemberGrouper);

        return null;
    }

    /**
     * Adds a tree to the class member list if its parent is a class.
     *
     * @param tree the tree to be added.
     * @param classMemberGrouper the grouper.
     */
    private void addClassMember(BaseTree tree, ClassMemberGrouper classMemberGrouper) {
        Tree parentTree = tree.getParent();

        if (parentTree instanceof ClassTreeImpl) {
            addClassMember(tree, (ClassTreeImpl) parentTree, classMemberGrouper);
        }
    }

    /**
     * Adds a tree to the class member list with its parent class.
     *
     * @param tree the tree to be added.
     * @param parentClassTree the parent class of the tree.
     * @param classMemberGrouper the grouper.
     */
    private void addClassMember(BaseTree tree, ClassTreeImpl parentClassTree, ClassMemberGrouper classMemberGrouper) {
        int startPosition = findStartPosition(tree);
        int endPosition = findEndPosition(tree);
        int commentPosition = findStartCommentOrNewLinePosition(startPosition);

        if (startPosition > INVALID_POSITION && endPosition > INVALID_POSITION) {
            endPosition++;

            if (tree instanceof ClassTreeImpl) {
                ClassTreeImpl classTree = (ClassTreeImpl) tree;
                int blockStartPosition = getTokenPosition((classTree).getBlockStart());
                classMemberGrouper.addClass(new ClassMemberClass(classTree, startPosition, endPosition, commentPosition, blockStartPosition, parentClassTree));
            } else if (tree instanceof MethodTree || tree instanceof BlockTree || tree instanceof VariableTree) {
                classMemberGrouper.addMember(new ClassMember<>(tree, startPosition, endPosition, commentPosition, parentClassTree));
            }
        }
    }

    /**
     * Finds the end position of a tree.
     *
     * @param tree the tree to find the end position.
     * @return the end position
     */
    private int findEndPosition(BaseTree tree) {
        int endTokenPosition = INVALID_POSITION;

        if (tree instanceof MethodTree) {
            MethodTree methodTree = (MethodTree) tree;
            endTokenPosition = findEndPosition((BaseTree) methodTree.getBody());
            if (endTokenPosition == INVALID_POSITION) {
                endTokenPosition = getTokenPosition(((CompoundTree) methodTree).getBlockEnd());
            }
        } else if (tree instanceof CompoundTree) {
            CompoundTree<?> compoundTree = ((CompoundTree<?>) tree);
            endTokenPosition = getTokenPosition(compoundTree.getBlockEnd());
        } else if (tree instanceof VariableTreeImpl) {
            Token semicolonToken = ((VariableTreeImpl) tree).getSemicolon();
            if (semicolonToken != null) {
                endTokenPosition = getTokenPosition(semicolonToken);
            }
        }

        return endTokenPosition;
    }

    /**
     * Finds the start position of a tree.
     *
     * @param tree the tree to find the start position.
     * @return the start position.
     */
    private int findStartPosition(BaseTree tree) {
        int startPostion = INVALID_POSITION;
        ModifiersTreeImpl modifiersTree = null;
        Tree type = null;
        Token identifier = tree.getToken();

        if (tree instanceof ClassTreeImpl) {
            ClassTreeImpl classTree = ((ClassTreeImpl) tree);
            modifiersTree = (ModifiersTreeImpl) classTree.getModifiers();
            type = classTree.getType();
        } else if (tree instanceof MethodTree) {
            MethodTree methodTree = ((MethodTree) tree);
            modifiersTree = (ModifiersTreeImpl) methodTree.getModifiers();
            type = methodTree.getReturnType();
        } else if (tree instanceof VariableTree) {
            VariableTree variableTree = ((VariableTree) tree);
            modifiersTree = (ModifiersTreeImpl) variableTree.getModifiers();
            type = variableTree.getType();
        } else if (tree instanceof BlockTree) {
            CompoundTree<?> blockTree = ((CompoundTree<?>) tree);
            startPostion = getTokenPosition(blockTree.getBlockStart());
        }

        if (type instanceof ArrayTypeTree) {
            type = ((ArrayTypeTree) type).getType();
        }

        if (identifier != null) {
            startPostion = getTokenPosition(identifier);
        }

        if (type != null && type instanceof BaseTree) {
            startPostion = getTokenPosition(((BaseTree) type).getToken());
        }

        if (modifiersTree != null) {
            int modifierPosition = getPositionFirstAccessModifier(modifiersTree.getModifiers());
            int annotationPosition = getPositionFirstAnnotation(modifiersTree.getAnnotations());

            if (modifierPosition != INVALID_POSITION && ((startPostion != INVALID_POSITION && modifierPosition < startPostion) || startPostion == INVALID_POSITION)) {
                startPostion = modifierPosition;
            }

            if (annotationPosition != INVALID_POSITION && ((startPostion != INVALID_POSITION && annotationPosition < startPostion) || startPostion == INVALID_POSITION)) {
                startPostion = annotationPosition;
            }
        }

        return startPostion;
    }

    /**
     * Finds the start position of the tree comment or the previous newline.
     *
     * @param startPosition the position to start to find.
     * @return the start position of the tree comment or the previous newline.
     */
    private int findStartCommentOrNewLinePosition(int startPosition) {
        int currentPosition = startPosition - 1;
        boolean findSecondNewLine = false;

        try {
            while (currentPosition >= 0) {
                char character = getCharacter(currentPosition);

                if (character == NEWLINE_CHARACTER) {
                    startPosition = currentPosition;
                    if (findSecondNewLine) {
                        break;
                    }
                    findSecondNewLine = true;
                } else if (character != WHITESPACE_CHARACTER && character != TAB_CHARACTER) {
                    if (character != SLASH_CHARACTER && findSecondNewLine) {
                        int singleLineCommentPosition = findSingleLineCommentPosition(currentPosition);
                        if (currentPosition > singleLineCommentPosition) {
                            startPosition = singleLineCommentPosition;
                            break;
                        }
                    } else if (character == SLASH_CHARACTER) {
                        int commentPosition = findMultiLineCommentPosition(currentPosition);
                        if (currentPosition > commentPosition) {
                            startPosition = commentPosition;
                        }
                    }

                    break;
                }

                currentPosition--;
            }
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        }

        return startPosition;
    }

    /**
     * Finds the tree's multi-line comment if there is a comment return its
     * start position.
     *
     * @param startPosition the position to start to find.
     * @return the start position of the tree comment.
     * @throws BadLocationException
     */
    private int findMultiLineCommentPosition(int startPosition) throws BadLocationException {
        int currentPosition = startPosition;

        while (currentPosition >= 0) {
            char character = getCharacter(currentPosition);

            if (character == SLASH_CHARACTER) {
                currentPosition--;
                character = getCharacter(currentPosition);
                if (character == ASTERISK_CHARACTER) {
                    int slashAsteriskPosition = findMultiLineCommentStartPosition(currentPosition);
                    if (currentPosition > slashAsteriskPosition) {
                        startPosition = slashAsteriskPosition;
                        break;
                    }
                }
            } else if (character != WHITESPACE_CHARACTER) {
                break;
            }

            currentPosition--;
        }

        return startPosition;
    }

    /**
     * Finds the start position of the tree's multi-line comment.
     *
     * @param startPosition the position to start to find.
     * @return the start position of the tree comment.
     * @throws BadLocationException
     */
    private int findMultiLineCommentStartPosition(int startPosition) throws BadLocationException {
        int currentPosition = startPosition - 1;

        while (currentPosition >= 0) {
            char character = getCharacter(currentPosition);

            if (character == ASTERISK_CHARACTER) {
                currentPosition--;
                character = getCharacter(currentPosition);
                if (character == SLASH_CHARACTER) {
                    startPosition = getPositionAfterPreviousClassMember(currentPosition);
                    break;
                }
            }

            currentPosition--;
        }

        return startPosition;
    }

    /**
     * Finds the start position of the tree's single-line comment.
     *
     * @param startPosition the position to start to find.
     * @return the start position of the tree comment.
     * @throws BadLocationException
     */
    private int findSingleLineCommentPosition(int startPosition) throws BadLocationException {
        int currentPosition = startPosition;

        while (currentPosition >= 0) {
            char character = getCharacter(currentPosition);

            if (character == SLASH_CHARACTER) {
                currentPosition--;
                char secondSlash = getCharacter(currentPosition);
                if (secondSlash == SLASH_CHARACTER) {
                    startPosition = getPositionAfterPreviousClassMember(currentPosition);
                    break;
                } else {
                    currentPosition++;
                }
            } else if (character == NEWLINE_CHARACTER) {
                break;
            }

            currentPosition--;
        }

        return startPosition;
    }

    /**
     *
     * @param startPosition
     * @return
     * @throws BadLocationException
     */
    private int getPositionAfterPreviousClassMember(int startPosition) throws BadLocationException {
        int currentPosition = startPosition - 1;

        while (currentPosition >= 0) {
            char character = getCharacter(currentPosition);

            if (character != WHITESPACE_CHARACTER) {
                if (character == NEWLINE_CHARACTER) {
                    startPosition = currentPosition;
                } else {
                    startPosition = ++currentPosition;
                }

                break;
            }

            currentPosition--;
        }

        return startPosition;
    }

    /**
     * Gets the position of the first access modifier.
     *
     * @param modifierMap the access modifier list.
     * @return the position of the first access modifier.
     */
    private int getPositionFirstAccessModifier(LinkedHashMap<Integer, Token> modifierMap) {
        int position = INVALID_POSITION;

        for (Map.Entry<Integer, Token> entrySet : modifierMap.entrySet()) {
            Token token = entrySet.getValue();
            int tokenPosition = getTokenPosition(token);

            if (tokenPosition != INVALID_POSITION && tokenPosition < position) {
                position = tokenPosition;
            } else if (tokenPosition != INVALID_POSITION && position == INVALID_POSITION) {
                position = tokenPosition;
            }
        }

        return position;
    }

    /**
     * Gets the position of the first annotation
     *
     * @param annotationList the annotation list.
     * @return the position of the first annotation
     */
    private int getPositionFirstAnnotation(List<? extends AnnotationTree> annotationList) {
        int position = INVALID_POSITION;

        for (AnnotationTree annotation : annotationList) {
            if (annotation instanceof AnnotationTreeImpl) {
                Token firstToken = ((AnnotationTreeImpl) annotation).getSymbol();
                if (firstToken == null) {
                    firstToken = ((IdentifierTreeImpl) annotation.getAnnotationType()).getToken();
                }

                int tokenPosition = getTokenPosition(firstToken);
                if (tokenPosition != INVALID_POSITION && tokenPosition < position) {
                    position = tokenPosition;
                } else if (tokenPosition != INVALID_POSITION && position == INVALID_POSITION) {
                    position = tokenPosition;
                }
            }
        }

        return position;
    }

    /**
     * Gets the position of a token.
     *
     * @param token the token to get its position.
     * @return the position of the token.
     */
    private int getTokenPosition(Token token) {
        int tokenPosition = INVALID_POSITION;

        if (token != null) {
            tokenPosition = NbDocument.findLineOffset(document, token.getBeginLine() - 1) + token.getBeginColumn() - 1;
        }

        return tokenPosition;
    }

    /**
     * Gets a character from a specific position.
     *
     * @param position the position of the character to be returned
     * @return the found character
     * @throws BadLocationException
     */
    private char getCharacter(int position) throws BadLocationException {
        return document.getText(position, TEXT_LENGTH).charAt(0);
    }

}
