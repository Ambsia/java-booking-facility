package com.bookingsystem.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemControlPanel extends JPanel {

    private ArrayList<JButton> controlButtonList;
    private UIBookingSystemAddPanel uiBookingSystemAddPanel;
    private UIBookingSystemFindPanel uiBookingSystemFindPanel;

    public UIBookingSystemControlPanel() {
        setLayout(new GridBagLayout());
        String[] buttonNames = {"Search","Add", "Edit", "Remove", "Repeat", "", "Twat", "Nob Head"};
        controlButtonList = new ArrayList<>();
        uiBookingSystemAddPanel = new UIBookingSystemAddPanel();
        uiBookingSystemFindPanel = new UIBookingSystemFindPanel();
        Dimension buttonDimension = new Dimension(83,25);
        for ( int buttonNo = 0, colsPassed = 0, rowsPassed = 0; buttonNo<buttonNames.length;buttonNo++) {
        	JPanel jPanel = new JPanel();
        	JButton jButton = new JButton(buttonNames[buttonNo]);
        	jButton.setPreferredSize(buttonDimension);

                if (colsPassed == 3) { rowsPassed++; colsPassed = 0; }

        	addControlToPanel(jPanel, colsPassed++, rowsPassed);
        	controlButtonList.add(jButton);
            jPanel.add(controlButtonList.get(buttonNo));
        }
    }

    public void addControlToPanel(Component component, int gridX, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(component, gbc);
    }

    public UIBookingSystemAddPanel getUIBookingSystemAddPanel() {
        return uiBookingSystemAddPanel;
    }

    public void addListeners(ActionListener al) {
        for (JButton button : controlButtonList) {
            button.addActionListener(al);
        }
    }

    public UIBookingSystemFindPanel getUIBookingSystemFindPanel() {
        return uiBookingSystemFindPanel;
    }
}
