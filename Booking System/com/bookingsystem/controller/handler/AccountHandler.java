package com.bookingsystem.controller.handler;

import com.bookingsystem.model.Log;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class AccountHandler implements ActionListener {

	private AccountBusinessLayer accountBusinessLayer;
	private UIBookingSystemAdminPanel bookingSystemAdminPanel;
	private LoggerBusinessLayer loggerBusinessLayer;
	public AccountHandler(AccountBusinessLayer accountBusinessLayer, UIBookingSystemAdminPanel bookingSystemAdminPanel, LoggerBusinessLayer loggerBusinessLayer) {
		this.accountBusinessLayer = accountBusinessLayer;
		this.bookingSystemAdminPanel = bookingSystemAdminPanel;
		this.loggerBusinessLayer = loggerBusinessLayer;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Log log = new Log(e.getActionCommand(), this.getClass().toString(), new Date());
		switch (e.getActionCommand()) {
			case "Load Accounts":
				bookingSystemAdminPanel.addAccountsToList(accountBusinessLayer.getAllAccounts());
				break;
			case "Add Account":
				bookingSystemAdminPanel.getIDOfSelectedRow();
				System.out.println("add account clicked");
				break;
			case "Remove Account":
				System.out.println("remove account clicked");
				break;
			case "View Activity":
				System.out.println("view account clicked");
				break;
			case "View Logs":
				System.out.println("view logs clicked");
				break;
		}
		loggerBusinessLayer.insertLog(log);
	}
}
