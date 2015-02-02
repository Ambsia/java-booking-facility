package  com.bookingsystem.view;

import java.awt.Dimension;

import javax.swing.JFrame;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;

	public BookingSystemUILoader() {
		this.setDimension(500, 250);
		bookingSystemPanel = new UIBookingSystemPanel();
		loginPanel = new UILoginPanel();
		this.setTitle("LGS Booking System");


		this.setLocationRelativeTo(null);
	}


	public void showLoginPanel() {
		
		this.add(loginPanel);
		this.setVisible(true);
	}

	public void showBookingSystemPanel() {
		this.setDimension(1000,450);
		
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
