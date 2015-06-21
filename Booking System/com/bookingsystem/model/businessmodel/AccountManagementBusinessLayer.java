package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: [Alex]
 */
public class AccountManagementBusinessLayer extends BusinessLayer implements Iterable<Account>{
	private final List<Account> accountList;
	private int currentAccountID;
	private int currentIndexInAccountList;

	public AccountManagementBusinessLayer() {
		this.accountList = new ArrayList<>();

		this.currentAccountID = -1;
		this.currentIndexInAccountList = -1;
	}

	public void addAccount(Account account) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement("{CALL spInsertAccount(?,?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
					callableStatement.setInt(1, account.getUserLevel());
					callableStatement.setString(2, account.getUsername());
					callableStatement.setString(3, account.getHashedPassword());
					callableStatement.setString(4, account.getUserSalt());
					callableStatement.registerOutParameter(5, Types.INTEGER);
					getDatabaseConnector().execute();
					account.setUserID(callableStatement.getInt(5));
					this.accountList.add(account);
				} catch (SQLException e) {
					MessageBox.errorMessageBox("There was an issue while adding an account.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
			}
			getDatabaseConnector().closeConnection();
		}
	}

	public void removeAccount() {
		if (this.currentAccountID != -1) {
			getDatabaseConnector().openConnection();
			if (getDatabaseConnector().isConnected()) {
				if (getDatabaseConnector().isConnectionClosed()) {
					getDatabaseConnector().createNewCallableStatement("{CALL spRemoveAccount(?)}");
					try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
						callableStatement.setInt(1, currentAccountID);
						getDatabaseConnector().execute();
						removeAccountFromList();
					} catch (SQLException e) {
						MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
					}
				}
			}
		}
	}

	public void getAllAccounts() {
		accountList.clear();
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement("{CALL spGetAllAccounts}");
				try (ResultSet rs = getDatabaseConnector().executeQuery()) {
					while (rs.next()) {
						this.accountList.add(new Account(rs.getInt(1), rs.getInt(4), rs.getString(2), rs.getString(3)));
					}
				} catch (SQLException e) {
					MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
			}
		}
	}

	// --Commented out by Inspection (21/06/2015 00:45):public void editAccount(int accountID) { }

	@Override
	public Iterator<Account> iterator() {
		return accountList.iterator();
	}

	public void setCurrentAccountID(int currentAccountID) {
		this.currentAccountID = currentAccountID;
	}

	void removeAccountFromList() {
		if (this.currentIndexInAccountList >= 0 && accountList.size() > 0) {
			this.accountList.remove(this.currentIndexInAccountList);
		} else {
			MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
		}
	}

// --Commented out by Inspection START (21/06/2015 00:45):
//	void addAccountToListAtAGivenPosition(Account account){
//		if (currentIndexInAccountList >= 0 && currentIndexInAccountList <= accountList.size()) {
//			this.accountList.add(currentIndexInAccountList, account);
//		}
//	}
// --Commented out by Inspection STOP (21/06/2015 00:45)

	public void setCurrentIndexOfAccountInList(int indexOfBookingInList) {
		try {
			if (indexOfBookingInList >= 0 && indexOfBookingInList <= accountList.size()) {
				this.currentIndexInAccountList = indexOfBookingInList;
			}
		} catch (IndexOutOfBoundsException e) {
			MessageBox.errorMessageBox("Big problems......");
		}
	}
}
