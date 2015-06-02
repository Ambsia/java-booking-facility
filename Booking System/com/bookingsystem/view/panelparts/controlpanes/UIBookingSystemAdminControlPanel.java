package com.bookingsystem.view.panelparts.controlpanes;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemAccountAddPanel;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminControlPanel extends UIBookingSystemControlPanel {

	private UIBookingSystemAccountAddPanel bookingSystemAccountAddPanel;
	public UIBookingSystemAdminControlPanel() {
		super();
		setLayout(new GridBagLayout());
		setButtonNames(new String[] {"Load Accounts","Add Account","Remove Account","View Activity", "View Exceptions", ""});
		setButtonDimension(new Dimension(135, 25));
		createControlPanel();

		bookingSystemAccountAddPanel = new UIBookingSystemAccountAddPanel();
	}

	@Override
	public void addListeners(ActionListener al) {
		super.addListeners(al);
	}

	public UIBookingSystemAccountAddPanel getBookingSystemAccountAddPanel() {
		return bookingSystemAccountAddPanel;
	}

}
