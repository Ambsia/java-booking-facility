package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class LoggerBusinessLayer extends BusinessLayer {

	private final String databaseConnectionString;
	private Account accountCurrentlyLoggedIn;

	public LoggerBusinessLayer() {
		ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
	}

	public void insertLog(Log log) {
		try (Connection connection = DriverManager.getConnection(databaseConnectionString);
		     CallableStatement callableStatement = connection.prepareCall("{CALL spInsertLog(?,?,?,?,?,?,?,?,?)}"))
		{
			callableStatement.setString(1, log.getEventLogged());
			callableStatement.setString(2, log.getClassEvent());
			callableStatement.setTimestamp(3, getTimeStamp(log.getDateAndTimeOfEvent()));
			callableStatement.setInt(4, accountCurrentlyLoggedIn.getUserID());
			callableStatement.setInt(5, log.getBookingIDInserted());
			callableStatement.setInt(6, log.getBookingIDEdited());
			callableStatement.setInt(7, log.getBookingIDDeleted());
			callableStatement.setInt(8, log.getAccountIDCreated());
			callableStatement.setInt(9, log.getAccountIDDeleted());

			callableStatement.execute();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert a log.\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public java.sql.Timestamp getTimeStamp(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	public void exceptionCaused(Log log, Exception exceptionType) {
	}

	public void removeLogsForAccount(int currentAccountID) {
		try (Connection connection = DriverManager.getConnection(databaseConnectionString);
			 CallableStatement csRemoveLogsForAccount = connection.prepareCall("{CALL spRemoveLogsForAccount(?)}");) {
			csRemoveLogsForAccount.setInt(1, currentAccountID);
			csRemoveLogsForAccount.execute();

		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while removing logs.\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
		}

	public ArrayList<Log> getLogsForAccount(int currentAccountID) {
		ArrayList<Log> logArrayList = new ArrayList<>();
		ArrayList<Integer> logIDS = new ArrayList<>();
		ArrayList<Integer> integers = new ArrayList<>();
		if (currentAccountID != -1) {
			try (Connection connection = DriverManager.getConnection(databaseConnectionString);
				 CallableStatement csGetLogsForAccount = connection.prepareCall("{CALL spGetLogsForAccount(?)}");
				 CallableStatement csGetIDPlayedWith = connection.prepareCall("{CALL spGetIDPlayedWith(?,?)}")) {

				csGetLogsForAccount.setInt(1,currentAccountID);

				ResultSet rs = csGetLogsForAccount.executeQuery();
				while (rs.next()) {
					logArrayList.add(new Log(rs.getString(2),rs.getString(3),rs.getTimestamp(4)));
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

	public void setAccountCurrentlyLoggedIn(Account accountCurrentlyLoggedIn) {
		this.accountCurrentlyLoggedIn = accountCurrentlyLoggedIn;
	}
}
