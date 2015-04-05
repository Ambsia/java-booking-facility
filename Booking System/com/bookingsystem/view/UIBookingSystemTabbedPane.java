package com.bookingsystem.view;

import javax.swing.JTabbedPane;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemAdminPanel bookingSystemAdminPanel;

	public UIBookingSystemTabbedPane() {
		bookingSystemPanel = new UIBookingSystemPanel();
		this.add(bookingSystemPanel, "View Bookings");
		bookingSystemAdminPanel = new UIBookingSystemAdminPanel();
		this.add(bookingSystemAdminPanel,"Admin Panel");
	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }

	public UIBookingSystemAdminPanel getBookingSystemAdminPanel() { return bookingSystemAdminPanel; }
}
