package com.bookingsystem.view.panes;

import javax.swing.*;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemAdminPanel bookingSystemAdminPanel;

	public UIBookingSystemTabbedPane() {
		bookingSystemPanel = new UIBookingSystemPanel();
		this.add(bookingSystemPanel, "View Bookings");
		bookingSystemAdminPanel = new UIBookingSystemAdminPanel();

	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }

	public void showAdminPanel() { 	this.add(bookingSystemAdminPanel,"Admin Panel"); }

	public UIBookingSystemAdminPanel getBookingSystemAdminPanel() { return bookingSystemAdminPanel; }
}
