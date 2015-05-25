package com.bookingsystem.view.panes;

import javax.swing.*;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private final UIBookingSystemPanel bookingSystemPanel;
	private final UIBookingSystemAdminPanel bookingSystemAdminPanel;
	private final UIBookingSystemArchive bookingSystemArchive;

	public UIBookingSystemTabbedPane() {
		bookingSystemPanel = new UIBookingSystemPanel();
		this.add(bookingSystemPanel, "View Bookings");
		bookingSystemAdminPanel = new UIBookingSystemAdminPanel();
		bookingSystemArchive = new UIBookingSystemArchive();
		this.add(bookingSystemArchive, "Archives");
	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }

	public void showAdminPanel() { 	this.add(bookingSystemAdminPanel,"Admin Panel"); }

	public UIBookingSystemAdminPanel getBookingSystemAdminPanel() { return bookingSystemAdminPanel; }
}
