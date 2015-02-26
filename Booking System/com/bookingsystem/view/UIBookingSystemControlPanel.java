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
        String[] buttonNames = {"Details","Add", "Edit", "Remove", "Repeat", "Remove ", ""};
        controlButtonList = new ArrayList<JButton>();

       
        int rowsPassed = 0;
        int colsPassed = 0;
        for ( int buttonNo = 0; buttonNo<buttonNames.length;buttonNo++) {
        	JPanel jPanel = new JPanel();

        	
        	
        	if (colsPassed == 3) { rowsPassed++; colsPassed = 0; } 

        	addControlToPanel(jPanel, colsPassed++, rowsPassed,1,1);

        	controlButtonList.add(new JButton(buttonNames[buttonNo]));
            jPanel.add(controlButtonList.get(buttonNo));
            
            
        }
        
        /*for (int rowsLoopedThrough = 0;rowsLoopedThrough < 2;rowsLoopedThrough++) {
            for (int colsLoopedThrough = 0; colsLoopedThrough < 3; colsLoopedThrough++) {
                JPanel jPanel = new JPanel();
                addControlToPanel(jPanel, colsLoopedThrough, rowsLoopedThrough, 1, 1);
                controlButtonList.add(new JButton(buttonNames[buttonNo]));
                jPanel.add(controlButtonList.get(buttonNo));
                buttonNo++;
            }
        }*/
    }

    public void addControlToPanel(Component component, int gridX, int gridY,double weightX, double weightY) {
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
