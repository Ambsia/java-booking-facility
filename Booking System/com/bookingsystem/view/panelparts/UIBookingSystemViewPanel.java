package com.bookingsystem.view.panelparts;



import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Created by Alex on 10/02/2015.
*/

public class UIBookingSystemViewPanel extends JPanel {

    public static ArrayList<JLabel> listOfViewBoxes;

    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());
        listOfViewBoxes = new ArrayList<>();

        for(int i = 0; i <= 6;i++) {
            listOfViewBoxes.add( new JLabel());
            addControlToPanel(listOfViewBoxes.get(i),i);
        }
    }

    public void addControlToPanel(Component component, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(component, gbc);
    }

    public String getTextFromField(int i) {
    	return listOfViewBoxes.get(i).getText();
    }
    public static void setTextToField(ArrayList<String> listOfStrings) {
        listOfStrings.add(0,"Booking #".concat(listOfStrings.get(0)));
        listOfStrings.remove(1);
        for (int i =0; i<=6; i++)
            listOfViewBoxes.get(i).setText(listOfStrings.get(i));
    }
}
