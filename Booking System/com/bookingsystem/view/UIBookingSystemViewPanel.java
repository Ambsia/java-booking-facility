package com.bookingsystem.view;



import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.soap.Text;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alex on 10/02/2015.
 * int bookingID, String bookingDay, String bookingDate, String bookingTime,
 String bookingLocation, String bookingHolder, Equipment requiredEquipment
 */
public class UIBookingSystemViewPanel extends JPanel {


    Border outline = BorderFactory.createLineBorder(Color.black);

    public static ArrayList<JLabel> listOfViewBoxes;
    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(150,150));
        listOfViewBoxes = new ArrayList<JLabel>();
        for(int i = 0; i <= 6;i++) {
            listOfViewBoxes.add(new JLabel());
            addControlToPanel(listOfViewBoxes.get(i),0,i,GridBagConstraints.WEST);
        }
        setBorder(outline);
    }


    public void addControlToPanel(Component component, int gridX, int gridY, int alignment) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        add(component, gbc);
    }

    public String getTextFromField(int i) {
    	return listOfViewBoxes.get(i).getText();
    }
    public static void setTextToField(ArrayList<String> listOfStrings) {
        for (int i =0; i<=6; i++)
            listOfViewBoxes.get(i).setText(listOfStrings.get(i));
    }




}
