package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;

public class UIBookingSystemFindPanel extends UIBookingSystemDialogPanel {

	public UIBookingSystemFindPanel() {
		super();
		addDefaultComponentsToPanel();
		try {
			((JTextField) getComponentsAsList()[0]).setEditable(true);
		} catch (Exception e) {
			MessageBox.errorMessageBox(e.toString() + "Definitely Broken");
		}
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Find Booking",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
				new String[] {"Find", "Cancel" }, "Add");
	}

}