package com.bookingsystem.controller;

import com.bookingsystem.model.Account;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemPanel;
import com.bookingsystem.view.UILoginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingSystemController {

	private BookingSystemUILoader view;
	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;

	private Account accountModel; // only need to instantiate it when used

	private boolean loggedInSuccessful;

	// account not instantiated until logged in or created!
	public BookingSystemController(BookingSystemUILoader view) {
		this.view = view;
		view.showLoginPanel();
		view.setVisible(true);

		view.removeLoginPanel();

		view.showBookingSystemPanel();

		bookingSystemPanel = view.getBookingSystemPanel();
		loginPanel = view.getLoginPanel();

		loginPanel.addSubmitListener(new LoginHandler());
		loginPanel.addClearListener(new ClearHandler());
	}

	public void RegisterAccount() {

	}

	public class LoginHandler implements ActionListener {

		@Override

		public void actionPerformed(ActionEvent arg0) {
			String username, unHashedPassword;
			username = loginPanel.GetLoginUsernameText();
			unHashedPassword = loginPanel.GetLoginPasswordText();

			accountModel = new Account(0, 0, username, unHashedPassword);
			loggedInSuccessful = accountModel.login();
			System.out.println(accountModel.toString() + loggedInSuccessful);

			if (loggedInSuccessful) {
				view.removeLoginPanel();
				view.showBookingSystemPanel();
				view.setVisible(true);
			}
		}
	}

	public class ClearHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			loginPanel.ClearTextBoxes();
		}
	}
}
