package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEditPanel extends UIBookingSystemDialogPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4426220699131550868L;
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

