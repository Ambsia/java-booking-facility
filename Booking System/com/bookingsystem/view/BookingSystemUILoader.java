package  com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;

	public BookingSystemUILoader() {
		Dimension d = new Dimension(500, 250);
		this.setSize(d);
		this.setMinimumSize(d);

		this.setTitle("LGS Booking System");


		this.setLocationRelativeTo(null);
	}


	public void showLoginPanel() {
		loginPanel = new UILoginPanel();
		this.add(loginPanel);
	}

	public void showBookingSystemPanel() {
		bookingSystemPanel = new UIBookingSystemPanel();
		this.add(bookingSystemPanel);
	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public UILoginPanel getLoginPanel() { return loginPanel; }

	public void removeLoginPanel() { this.remove(loginPanel); }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }


}
