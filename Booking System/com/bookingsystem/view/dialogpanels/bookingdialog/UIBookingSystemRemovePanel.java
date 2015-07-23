package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemRemovePanel extends UIBookingSystemDialogPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5819787011513181983L;

	public UIBookingSystemRemovePanel() {
		super();
		addDefaultComponentsToPanel();
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Remove Booking",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[] { "Remove", "Cancel" }, "Add");
	}
}
