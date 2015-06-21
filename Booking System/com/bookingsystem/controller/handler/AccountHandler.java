package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.AccountTableModel;
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
	private final AccountTableModel accountTableModel;

	public AccountHandler(Handler handler) {
		this.handler = handler;
		this.bookingSystemAdminPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemAdminPanel();
		this.bookingSystemAdminViewPanel = bookingSystemAdminPanel.getBookingSystemAdminViewPanel();
		this.bookingSystemAccountAddPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel().getBookingSystemAccountAddPanel();
		this.accountTableModel = bookingSystemAdminPanel.getJTableModel();
		this.currentAccountIDBeingProcessed = -1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		Log log = new Log(e.getActionCommand(), this.getClass().getSimpleName(), new Date());
		switch (e.getActionCommand()) {
			case "Load Accounts":
				accountTableModel.clearAccountList();
				handler.getAccountManagementBusinessLayer().getAllAccounts();
				accountTableModel.addAccountList(IteratorUtils.toList(handler.getAccountManagementBusinessLayer().iterator()));
				break;
			case "Add Account":
				try {
					if (handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel() >= 3) {
						if (bookingSystemAccountAddPanel.showDialog() == 0) {
							String username = bookingSystemAccountAddPanel.getAccountNameText();
							char[] chars = bookingSystemAccountAddPanel.getAccountPasswordText();
							String password = "";
							for (char c : chars) {
								password += c;
							}
							int level = Integer.parseInt(bookingSystemAccountAddPanel.getAccountUserLevelText());

							Account account = new Account(0, level, username, password);
							handler.getAccountManagementBusinessLayer().addAccount(account);
							log.setAccountIDCreated(account.getUserID());
							accountTableModel.addAccount(account);
						}
					} else {
						MessageBox.infoMessageBox("You do not have enough access to do this.");
					}
				} catch (NumberFormatException nfe) {
					MessageBox.errorMessageBox("Error");
				}
				break;
			case "Remove Account":
				if (bookingSystemAdminPanel.selectedRowCount() > 0) {
				int modelRow = bookingSystemAdminPanel.rowViewIndexToModel(bookingSystemAdminPanel.getSelectedRow());
				this.currentAccountIDBeingProcessed = (int) bookingSystemAdminPanel.getValueAt(modelRow, 0);

					if (handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel() >= 3) {
						this.currentAccountIDBeingProcessed = (int) accountTableModel.getValueAt(bookingSystemAdminPanel.getSelectedRow(), 0);
						Account account = accountTableModel.getAccount(currentAccountIDBeingProcessed);
						if (account.getUserLevel() < handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel()) {
							try {
								Thread.sleep(5);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							log.setAccountIDDeleted(currentAccountIDBeingProcessed);
							handler.getAccountManagementBusinessLayer().setCurrentAccountID(currentAccountIDBeingProcessed);
							handler.getAccountManagementBusinessLayer().setCurrentIndexOfAccountInList(bookingSystemAdminPanel.getSelectedRow());
							handler.getAccountManagementBusinessLayer().removeAccount();
							handler.getLoggerBusinessLayer().removeLogsForAccount(account.getUserID());
							accountTableModel.removeRows(bookingSystemAdminPanel.getSelectedRows());

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
				if (bookingSystemAdminPanel.selectedRowCount() > 0) {
					int modelRow = bookingSystemAdminPanel.rowViewIndexToModel(bookingSystemAdminPanel.getSelectedRow());
					this.currentAccountIDBeingProcessed = (int) bookingSystemAdminPanel.getValueAt(modelRow, 0);
						bookingSystemAdminViewPanel.getJTableModel().clearArchiveList();
						handler.getLoggerBusinessLayer().getLogsForAccount(this.currentAccountIDBeingProcessed);
						bookingSystemAdminViewPanel.getJTableModel().addLogList(IteratorUtils.toList(handler.getLoggerBusinessLayer().iterator()));
					} else {
						MessageBox.errorMessageBox("You must select an account to view logs.");
					}
					break;
		}
		handler.getLoggerBusinessLayer().insertLog(log);
	}
}
