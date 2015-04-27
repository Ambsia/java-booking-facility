package com.bookingsystem.view.panelparts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminViewPanel extends JPanel {

	public static ArrayList<JLabel> listOfViewBoxes;

	public UIBookingSystemAdminViewPanel() {
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
		if (listOfStrings != null) {
			listOfStrings.add(0, "Booking #".concat(listOfStrings.get(0))); // changing index 0 to contain "Booking #"
			listOfStrings.remove(1); //Remove 1 because we have concatenated the id with 0
			for (int i = 0; i <= 6; i++) {
				if (listOfStrings.get(2) == "") { // check if the string at index 2 is empty, if it is then the view panel should display nothing
					listOfStrings.add(0, "");
					listOfStrings.remove(1);
					return;
				}
				listOfViewBoxes.get(i).setText(listOfStrings.get(i)); // otherwise we're good to set the text
			}
		}
	}
}


