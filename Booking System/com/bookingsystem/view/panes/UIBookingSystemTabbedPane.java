package com.bookingsystem.view.panes;

import javax.swing.JTabbedPane;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private final UIBookingSystemBookingPanel bookingSystemPanel;
	private final UIBookingSystemAdminPanel bookingSystemAdminPanel;
	private final UIBookingSystemArchivePanel bookingSystemArchive;

	public UIBookingSystemTabbedPane() {
		bookingSystemPanel = new UIBookingSystemBookingPanel();
		this.add(bookingSystemPanel, "View Bookings");
		bookingSystemAdminPanel = new UIBookingSystemAdminPanel();
		bookingSystemArchive = new UIBookingSystemArchivePanel();
		this.add(bookingSystemArchive, "Archives");
	}

	public UIBookingSystemBookingPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }

	public void showAdminPanel() { 	this.add(bookingSystemAdminPanel,"Admin Panel"); }

	public UIBookingSystemAdminPanel getBookingSystemAdminPanel() { return bookingSystemAdminPanel; }

	public UIBookingSystemArchivePanel getBookingSystemArchive() { return  bookingSystemArchive;}
}
