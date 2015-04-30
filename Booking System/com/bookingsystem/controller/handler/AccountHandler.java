package com.bookingsystem.controller.handler;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.panelparts.UIBookingSystemAdminViewPanel;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;
import org.apache.commons.collections.IteratorUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class AccountHandler implements ActionListener {

	private final AccountManagementBusinessLayer accountManagementBusinessLayer;
	private final UIBookingSystemAdminPanel bookingSystemAdminPanel;
	private final UIBookingSystemAdminViewPanel bookingSystemAdminViewPanel;
	private final LoggerBusinessLayer loggerBusinessLayer;
	public AccountHandler(AccountManagementBusinessLayer accountManagementBusinessLayer, UIBookingSystemAdminPanel bookingSystemAdminPanel, LoggerBusinessLayer loggerBusinessLayer) {
		this.accountManagementBusinessLayer = accountManagementBusinessLayer;
		this.bookingSystemAdminPanel = bookingSystemAdminPanel;
		this.loggerBusinessLayer = loggerBusinessLayer;
		this.bookingSystemAdminViewPanel = bookingSystemAdminPanel.getBookingSystemAdminViewPanel();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Log log = new Log(e.getActionCommand(), this.getClass().toString(), new Date());
		switch (e.getActionCommand()) {
			case "Load Accounts":
				accountManagementBusinessLayer.getAllAccounts();
				bookingSystemAdminPanel.removeAllAccounts();
				for (Object object : IteratorUtils.toList(accountManagementBusinessLayer.iterator())) {
					bookingSystemAdminPanel.addAccountToList((Account) object);
				}
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
				bookingSystemAdminViewPanel.removeAllLogs();
				accountManagementBusinessLayer.setCurrentAccountID(bookingSystemAdminPanel.getIDOfSelectedRow());

				for (Object object : IteratorUtils.toList(accountManagementBusinessLayer.getLogsForAccount().iterator())) {
					bookingSystemAdminViewPanel.addLogToList((Log) object);
				}
				System.out.println("view logs clicked");
				break;
		}
		loggerBusinessLayer.insertLog(log);
	}
}
