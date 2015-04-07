package com.bookingsystem.view;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEditPanel extends UIBookingSystemAddPanel {


	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Find Booking",
				JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION, null,
				new String[]{"Find", "Cancel"}, "Add");
	}
}

