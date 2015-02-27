package com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Alex on 18/02/2015.
 */
public class UIBookingSystemControlPanel extends JPanel {

    public ArrayList<JButton> controlButtonList;

    public UIBookingSystemControlPanel() {
        setLayout(new GridBagLayout());
        String[] buttonNames = {"Details","Add", "Edit", "Remove", "Repeat", "",};
        controlButtonList = new ArrayList<JButton>();

        Dimension buttonDimension = new Dimension(83,25);
        for ( int buttonNo = 0, colsPassed = 0, rowsPassed = 0; buttonNo<buttonNames.length;buttonNo++) {
        	JPanel jPanel = new JPanel();
        	JButton jButton = new JButton(buttonNames[buttonNo]);
        	jButton.setPreferredSize(buttonDimension);

        	if (colsPassed == 3) { rowsPassed++; colsPassed = 0; }

            //jPanel.setBackground(Color.RED);
        	addControlToPanel(jPanel, colsPassed++, rowsPassed, 1, 1);

        	controlButtonList.add(jButton);
            jPanel.add(controlButtonList.get(buttonNo));
        }
    }

    public void addControlToPanel(Component component, int gridX, int gridY, double weightX, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        add(component, gbc);
    }

    public void addListeners(ActionListener al) {
        for (JButton button : controlButtonList) {
            button.addActionListener(al);
        }
    }
}
