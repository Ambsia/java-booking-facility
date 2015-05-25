package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemAccountAddPanel;
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

	private final UIBookingSystemAdminPanel bookingSystemAdminPanel;
	private final UIBookingSystemAdminViewPanel bookingSystemAdminViewPanel;
	private final UIBookingSystemAccountAddPanel bookingSystemAccountAddPanel;
	private int currentAccountIDBeingProcessed;
	private final Handler handler;

	public AccountHandler(Handler handler) {
		this.handler = handler;
		this.bookingSystemAdminPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemAdminPanel();
		this.bookingSystemAdminViewPanel = bookingSystemAdminPanel.getBookingSystemAdminViewPanel();
		this.bookingSystemAccountAddPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel().getBookingSystemAccountAddPanel();
		this.currentAccountIDBeingProcessed = -1;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Log log = new Log(e.getActionCommand(), this.getClass().getSimpleName(), new Date());
		switch (e.getActionCommand()) {
			case "Load Accounts":
				handler.getAccountManagementBusinessLayer().getAllAccounts();
				bookingSystemAdminPanel.removeAllAccounts();
				for (Object object : IteratorUtils.toList(handler.getAccountManagementBusinessLayer().iterator())) {
					bookingSystemAdminPanel.addAccountToList((Account) object);
				}
				break;
			case "Add Account":
				try {
					if (handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel() >= 3) {
						if (bookingSystemAccountAddPanel.showDialog() == 0) {
							String username = bookingSystemAccountAddPanel.getAccountNameText();
							char[] chars = bookingSystemAccountAddPanel.getAccountPasswordText();
							String password = "";
							for (char c : chars) {
								password +=c;
							}
							int level = Integer.parseInt(bookingSystemAccountAddPanel.getAccountUserLevelText());

							Account account = new Account(0, level, username, password);
							handler.getAccountManagementBusinessLayer().addAccount(account);
							log.setAccountIDCreated(account.getUserID());
							bookingSystemAdminPanel.addAccountToList(account);
						}
					} else {
						MessageBox.infoMessageBox("You do not have enough access to do this.");
					}
				} catch (NumberFormatException nfe) {
					MessageBox.errorMessageBox("Error");
				}
				break;
			case "Remove Account":
				this.currentAccountIDBeingProcessed = bookingSystemAdminPanel.getIDOfSelectedRow();
				if ( currentAccountIDBeingProcessed != -1) {
					if (handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel() >= 3) {
						Account account = bookingSystemAdminPanel.getAccountFromList(bookingSystemAdminPanel.getIndexOfSelectedRow());
						if (account.getUserLevel() < handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel()) {
							log.setAccountIDDeleted(bookingSystemAdminPanel.getIDOfSelectedRow());
							handler.getAccountManagementBusinessLayer().setCurrentAccountID(bookingSystemAdminPanel.getIDOfSelectedRow());
							handler.getAccountManagementBusinessLayer().setCurrentIndexOfAccountInList(bookingSystemAdminPanel.getIndexOfSelectedRow());
							handler.getAccountManagementBusinessLayer().removeAccount();
							bookingSystemAdminPanel.removeAccountFromTable();
							handler.getLoggerBusinessLayer().removeLogsForAccount(account.getUserID());
						} else {
							MessageBox.errorMessageBox("The user's privilege level is too high, and cannot be removed.");
						}
					} else {
						MessageBox.errorMessageBox("You do not have enough access to do this.");
					}
				} else {
					MessageBox.errorMessageBox("You must select an account to remove.");
				}
				break;
			case "View Exceptions":

				break;
			case "View Activity":
				this.currentAccountIDBeingProcessed = bookingSystemAdminPanel.getIDOfSelectedRow();
				if ( currentAccountIDBeingProcessed != -1) {
					bookingSystemAdminViewPanel.removeAllLogs();
					for (Object object : IteratorUtils.toList(handler.getLoggerBusinessLayer().getLogsForAccount(bookingSystemAdminPanel.getIDOfSelectedRow()).iterator())) {
						bookingSystemAdminViewPanel.addLogToList((Log) object);
					}
				} else {
					MessageBox.errorMessageBox("You must select an account to view logs.");
				}
				break;
		}
		handler.getLoggerBusinessLayer().insertLog(log);
	}
}
