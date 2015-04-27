package  com.bookingsystem.view;

import com.bookingsystem.view.controls.UIBookingSystemMenuBarLoader;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;

import javax.swing.*;
import java.awt.*;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemMenuBarLoader menuBarLoader;
	private UIBookingSystemTabbedPane bookingSystemTabbedPane;

	public BookingSystemUILoader() {
		Dimension d = new Dimension(500,250);
		this.setSize(d);
		this.setMinimumSize(d);
		loginPanel = new UILoginPanel();
		menuBarLoader = new UIBookingSystemMenuBarLoader();
		bookingSystemTabbedPane = new UIBookingSystemTabbedPane();
		setLayout(new BorderLayout());
		this.setTitle("LGS Booking System");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UIBookingSystemMenuBarLoader getMenuBarLoader() {
		return this.menuBarLoader;
	}

	public void showLoginPanel() {
		this.add(loginPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void showBookingSystemPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		this.setJMenuBar(menuBarLoader);

		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		this.add(bookingSystemTabbedPane, gridBagConstraints);

		this.setPreferredSize(new Dimension(1148,550));
		this.setMinimumSize(new Dimension(1000,500));

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public UIBookingSystemTabbedPane getBookingSystemTabbedPane() {
		return bookingSystemTabbedPane;
	}
	
	public void removeBookingSystemTabbedPane() {
		this.remove(bookingSystemTabbedPane);
	}

	public UILoginPanel getLoginPanel() { return loginPanel; }

	public void removeLoginPanel() { this.remove(loginPanel); }




}
