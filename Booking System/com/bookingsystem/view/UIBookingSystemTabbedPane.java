package com.bookingsystem.view;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private UIBookingSystemPanel bookingSystemPanel;

	
	public UIBookingSystemTabbedPane() {
		bookingSystemPanel = new UIBookingSystemPanel();
		this.add(bookingSystemPanel, "View Bookings");
	}
	
	
	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }
}
