package  com.bookingsystem.view;

import java.awt.*;

import javax.swing.*;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemMenuBarLoader menuBarLoader;

	public BookingSystemUILoader() {
		this.setDimension(500, 250);
		bookingSystemPanel = new UIBookingSystemPanel();
		loginPanel = new UILoginPanel();

		menuBarLoader = new UIBookingSystemMenuBarLoader();

		this.setTitle("LGS Booking System");

		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}


	public UIBookingSystemMenuBarLoader getMenuBarLoader() {
		return this.menuBarLoader;
	}

	public void showLoginPanel() {
		this.add(loginPanel);
		this.setVisible(true);
	}

	public void showBookingSystemPanel() {
		this.setDimension(1000,450);
		this.add(bookingSystemPanel);
		this.setJMenuBar(menuBarLoader);
		this.setVisible(true);
	}
	
	public void setDimension(int x, int y) {
		Dimension d = new Dimension(x,y);
		this.setSize(d);
		this.setMinimumSize(d);
	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public UILoginPanel getLoginPanel() { return loginPanel; }

	public void removeLoginPanel() { this.remove(loginPanel); }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }


}
