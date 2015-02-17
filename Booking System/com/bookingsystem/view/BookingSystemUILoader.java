package  com.bookingsystem.view;

import java.awt.*;

import javax.swing.*;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemMenuBarLoader menuBarLoader;
	private UIBookingSystemViewPanel bookingSystemViewPanel;

	public BookingSystemUILoader() {
		this.setDimension(500, 250);
		bookingSystemPanel = new UIBookingSystemPanel();
		loginPanel = new UILoginPanel();
		menuBarLoader = new UIBookingSystemMenuBarLoader();
		bookingSystemViewPanel = new UIBookingSystemViewPanel();
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
		this.setDimension(1150,460);
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		this.setJMenuBar(menuBarLoader);


		gridBagConstraints.gridx = 0;
		gridBagConstraints.weightx = 0.7;

		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		this.add(bookingSystemPanel,gridBagConstraints);


		gridBagConstraints.ipadx = 250;
		gridBagConstraints.ipady = 100;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0.2;
		gridBagConstraints.gridx = 1;
		this.add(bookingSystemViewPanel,gridBagConstraints);


		this.setVisible(true);
	}
	
	public void setDimension(int x, int y) {
		Dimension d = new Dimension(x,y);
		this.setSize(d);
		this.setMinimumSize(d);

	}

	public UIBookingSystemPanel getBookingSystemPanel() { return bookingSystemPanel; }

	public UIBookingSystemViewPanel getBookingSystemViewPanel() { return bookingSystemViewPanel; }

	public UILoginPanel getLoginPanel() { return loginPanel; }

	public void removeLoginPanel() { this.remove(loginPanel); }

	public void removeBookingSystemPanel() {this.remove(bookingSystemPanel); }


}
