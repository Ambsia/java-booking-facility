package com.bookingsystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.bookingsystem.model.Account;
import com.bookingsystem.view.*;

public class BookingSystemController {

	private BookingSystemUILoader view;
	private UILoginPanel loginPanel;
	private Account accountModel; // only need to instantiate it when used

	// account not instantiated until logged in or created!
	public BookingSystemController(BookingSystemUILoader view) {
		this.view = view;
		loginPanel = view.GetLoginPanel();

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

			System.out.println(accountModel.toString());
		}
	}

	public class ClearHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			loginPanel.ClearTextBoxes();
		}
	}
}