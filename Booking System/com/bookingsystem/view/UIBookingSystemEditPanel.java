package com.bookingsystem.view;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEditPanel extends UIBookingSystemAddPanel {

	public void populateTextBoxes() {

	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Edit Booking",
				JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION, null,
				new String[]{"Edit", "Cancel"}, "Add");
	}
}

