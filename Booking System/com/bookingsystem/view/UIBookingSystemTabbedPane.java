package com.bookingsystem.view;

import java.awt.*;

import javax.swing.JTabbedPane;

public class UIBookingSystemTabbedPane extends JTabbedPane{

	private UIBookingSystemPanel bookingSystemPanel;

	
	public UIBookingSystemTabbedPane() {
		this.setBackground(Color.DARK_GRAY);
		bookingSystemPanel = new UIBookingSystemPanel();
		bookingSystemPanel.setBackground(Color.DARK_GRAY);
		this.add(bookingSystemPanel, "View Bookings");
	}
	
	
	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }
}
