package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.view.UIBookingSystemDialogPanel;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemRemovePanel extends UIBookingSystemDialogPanel {
	public UIBookingSystemRemovePanel() {
		super();
		addDefaultComponentsToPanel();
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Remove Booking",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[]{"Remove", "Cancel"}, "Add");
	}
}
