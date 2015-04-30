package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: [Alex]
 */
public class AccountManagementBusinessLayer implements Iterable<Account>{
	private ArrayList<Account> accounts;

	private final String databaseConnectionString;
	private int currentAccountID;

	public AccountManagementBusinessLayer() {
		this.accounts = new ArrayList<>();

		this.currentAccountID = -1;
		ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
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

	public ArrayList<Log> getLogsForAccount() {
		ArrayList<Log> logArrayList = new ArrayList<>();
		ArrayList<Integer> logIDS = new ArrayList<>();
		ArrayList<Integer> integers = new ArrayList<>();
		if (this.currentAccountID != -1) {
			try (Connection connection = DriverManager.getConnection(databaseConnectionString);
			     CallableStatement csGetLogsForAccount = connection.prepareCall("{CALL spGetLogsForAccount(?)}");
			     CallableStatement csGetIDPlayedWith = connection.prepareCall("{CALL spGetIDPlayedWith(?,?)}")) {

				csGetLogsForAccount.setInt(1,1);

				ResultSet rs = csGetLogsForAccount.executeQuery();
				while (rs.next()) {
					logArrayList.add(new Log(rs.getString(2),rs.getString(3),rs.getDate(4)));
					logIDS.add(rs.getInt(1));
				}

				csGetIDPlayedWith.registerOutParameter(2, Types.INTEGER);

				for (Integer i : logIDS) {
					csGetIDPlayedWith.setInt(1,i);
					csGetIDPlayedWith.execute();
					integers.add(csGetIDPlayedWith.getInt(2));
				}

				for (int k = 0; k<logArrayList.size(); k++) {
					logArrayList.get(k).setLogID(logIDS.get(k));
					logArrayList.get(k).setIdPlayedWith(integers.get(k));
				}

			} catch (SQLException e) {
				MessageBox.errorMessageBox("There was an issue while retrieving logs for an account.\n" + "Does this make any sense to you.." + e.toString() + "?");
			}
		}
		else {
			MessageBox.errorMessageBox("You must select an account to view logs.");
		}
		return  logArrayList;
	}

	public void editAccount(int accountID) { }

	@Override
	public Iterator<Account> iterator() {
		return accounts.iterator();
	}

	public void setCurrentAccountID(int currentAccountID) {
		this.currentAccountID = currentAccountID;
	}
}
