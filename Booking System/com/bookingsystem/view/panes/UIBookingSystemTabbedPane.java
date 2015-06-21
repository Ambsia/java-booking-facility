package com.bookingsystem.view.panes;

import javax.swing.*;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8800935066090636571L;
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

	// --Commented out by Inspection (21/06/2015 00:50):public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }

	public void showAdminPanel() { 	this.add(bookingSystemAdminPanel,"Admin Panel"); }

	public UIBookingSystemAdminPanel getBookingSystemAdminPanel() { return bookingSystemAdminPanel; }

	public UIBookingSystemArchivePanel getBookingSystemArchive() { return  bookingSystemArchive;}
}
