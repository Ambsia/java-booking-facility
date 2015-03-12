package com.bookingsystem.view;



import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Created by Alex on 10/02/2015.
 * int bookingID, String bookingDay, String bookingDate, String bookingTime,
 String bookingLocation, String bookingHolder, Equipment requiredEquipment
 */

public class UIBookingSystemViewPanel extends JPanel {

    private Border outline = BorderFactory.createLineBorder(Color.black);
    public static ArrayList<JLabel> listOfViewBoxes;

    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());
        listOfViewBoxes = new ArrayList<JLabel>();

        for(int i = 0; i <= 6;i++) {
            listOfViewBoxes.add( new JLabel());
            addControlToPanel(listOfViewBoxes.get(i),0,i,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.EAST);
        }
    }


    public void addControlToPanel(Component component, int gridX, int gridY,int weightX, int weightY, int alignment,int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.anchor = anchor;
        gbc.fill = alignment;
        gbc.weightx = weightX;
        gbc.weighty = weightY;

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
