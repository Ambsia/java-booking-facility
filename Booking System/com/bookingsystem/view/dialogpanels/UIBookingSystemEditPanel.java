package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.view.dialogpanels.UIBookingSystemAddPanel;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEditPanel extends UIBookingSystemAddPanel {
	public UIBookingSystemEditPanel() {
		super();
		addDefaultComponentsToPanel();
	}
	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Edit Booking",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[]{"Edit", "Cancel"}, "Add");
	}
}

