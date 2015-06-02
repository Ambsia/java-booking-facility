package com.bookingsystem.view.dialogpanels.bookingdialog;

import javax.swing.JOptionPane;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEditPanel extends UIBookingSystemDialogPanel {
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

