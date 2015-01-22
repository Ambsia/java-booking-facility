package com.bookingsystem.view;

import java.awt.Dimension;
import javax.swing.JFrame;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;

	public BookingSystemUILoader() {
		Dimension d = new Dimension(500, 250);
		this.setSize(d);
		this.setMinimumSize(d);

		this.setTitle("LGS Booking System");

		loginPanel = new UILoginPanel();

		this.add(loginPanel);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public UILoginPanel GetLoginPanel() {
		return loginPanel;
	}

	public void RemoveLoginPanel() {
		this.remove(loginPanel);
		loginPanel = null;
	}

}
