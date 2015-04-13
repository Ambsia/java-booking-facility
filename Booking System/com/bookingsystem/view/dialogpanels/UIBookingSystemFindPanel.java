package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.view.dialogpanels.UIBookingSystemAddPanel;

import javax.swing.*;

public class UIBookingSystemFindPanel extends UIBookingSystemAddPanel {

	public UIBookingSystemFindPanel() {
		super();
		addDefaultComponentsToPanel();
		getTxtBookingDay().setEditable(true);
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Find Booking",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
				new String[] {"Find", "Cancel" }, "Add");
	}

}