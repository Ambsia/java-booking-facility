package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: [Alex]
 */
public class AccountManagementBusinessLayer extends BusinessLayer implements Iterable<Account>{
	private final ArrayList<Account> accounts;

	private final String databaseConnectionString;
	private int currentAccountID;
	private int currentIndexInAccountList;

	public AccountManagementBusinessLayer() {
		this.accounts = new ArrayList<>();

		this.currentAccountID = -1;
		ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
		this.currentIndexInAccountList = -1;
	}

	public void addAccount(Account account) {
		try (Connection connection = DriverManager.getConnection(databaseConnectionString) ;
			 CallableStatement callInsertAccount = connection.prepareCall("{CALL spInsertAccount(?,?,?,?,?)}")) {
			callInsertAccount.setInt(1, account.getUserLevel());
			callInsertAccount.setString(2, account.getUsername());
			callInsertAccount.setString(3, account.getHashedPassword());
			callInsertAccount.setString(4, account.getUserSalt());
			callInsertAccount.registerOutParameter(5, Types.INTEGER);

			callInsertAccount.execute();
			account.setUserID(callInsertAccount.getInt(5));
			this.accounts.add(account);

		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void removeAccount() {
		if (this.currentAccountID != -1) {
			try (Connection connection = DriverManager.getConnection(databaseConnectionString);
				 CallableStatement callRemoveAccount = connection.prepareCall("{CALL spRemoveAccount(?)}")) {
				callRemoveAccount.setInt(1, currentAccountID);
				callRemoveAccount.execute();
				removeAccountFromList();
			} catch (SQLException e) {
				MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
			}
		}
	}

	public void getAllAccounts() {
		accounts.clear();
		try (Connection connection = DriverManager.getConnection(databaseConnectionString) ;
		     CallableStatement callableStatement = connection.prepareCall("{CALL spGetAllAccounts}")) {
			ResultSet rs = callableStatement.executeQuery();
			while (rs.next()) {
				this.accounts.add(new Account(rs.getInt(1), rs.getInt(4), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}


	public void editAccount(int accountID) { }

	@Override
	public Iterator<Account> iterator() {
		return accounts.iterator();
	}

	public void setCurrentAccountID(int currentAccountID) {
		this.currentAccountID = currentAccountID;
	}

	void removeAccountFromList() {
		if (this.currentIndexInAccountList >= 0 && accounts.size() > 0) {
			this.accounts.remove(this.currentIndexInAccountList);
		} else {
			MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
		}
	}

	void addAccountToListAtAGivenPosition(Account account){
		if (currentIndexInAccountList >= 0 && currentIndexInAccountList <= accounts.size()) {
			this.accounts.add(currentIndexInAccountList, account);
		}
	}

	public void setCurrentIndexOfAccountInList(int indexOfBookingInList) {
		try {
			if (indexOfBookingInList >= 0 && indexOfBookingInList <= accounts.size()) {
				this.currentIndexInAccountList = indexOfBookingInList;
			}
		} catch (IndexOutOfBoundsException e) {
			MessageBox.errorMessageBox("Big problems......");
		}
	}
}
