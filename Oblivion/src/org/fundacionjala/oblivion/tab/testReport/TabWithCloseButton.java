/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.tab.testReport;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import org.openide.util.NbBundle;

@NbBundle.Messages({"TabWithCloseButton_ToolTipCloseTab=Close Tab"})

/**
 * This class make a closure button on the tab.
 *
 * @author sergio_daza
 */
public class TabWithCloseButton extends JPanel {

    private JTabbedPane testResultTab;

    public TabWithCloseButton(JTabbedPane jTabbedPane) {
        testResultTab = jTabbedPane;
        setOpaque(false);
        JLabel title = new JLabel() {
            @Override
            public String getText() {
                int index = testResultTab.indexOfTabComponent(TabWithCloseButton.this);
                if (index != -1) {
                    return testResultTab.getTitleAt(index);
                }
                return null;
            }
        };
        add(title);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JButton buttonClose = new ButtonClose(testResultTab, this);
        add(buttonClose);
    }

    public class ButtonClose extends JButton implements MouseListener {

        private JTabbedPane panel;
        private TabWithCloseButton buttonClose;

        public ButtonClose(JTabbedPane pane, TabWithCloseButton buttonClose) {
            panel = pane;
            this.buttonClose = buttonClose;
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText(Bundle.TabWithCloseButton_ToolTipCloseTab());
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(this);
            setRolloverEnabled(true);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = panel.indexOfTabComponent(ButtonClose.this.buttonClose);
                    if (index != -1) {
                        TestResultTopComponent.closeTab(index);
                    }
                }
            });
        }

        @Override
        public void updateUI() {
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(0));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            g2.drawLine(3, 4, 9, 10);
            g2.drawLine(9, 4, 3, 10);
            g2.dispose();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }
}
