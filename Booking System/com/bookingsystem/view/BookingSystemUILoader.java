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
		setLayout(new BorderLayout());

		this.setTitle("LGS Booking System");

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	public UIBookingSystemMenuBarLoader getMenuBarLoader() {
		return this.menuBarLoader;
	}

	public void showLoginPanel() {
		this.add(loginPanel);
		this.setVisible(true);
	}

	public void showBookingSystemPanel() {
		this.setDimension(1050,460);

		this.setJMenuBar(menuBarLoader);

		this.add(bookingSystemPanel);

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
