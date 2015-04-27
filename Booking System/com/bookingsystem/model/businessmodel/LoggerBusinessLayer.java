package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class LoggerBusinessLayer extends BusinessLayer {
	private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;

	private String databaseConnectionString;
	private Account accountCurrentlyLoggedIn;

	public LoggerBusinessLayer() {
		returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
	}

	public void insertLog(Log log) {
		try (Connection connection = DriverManager.getConnection(databaseConnectionString);
		     CallableStatement callableStatement = connection.prepareCall("{CALL spInsertLog(?,?,?,?,?,?,?,?,?)}"))
		{
			callableStatement.setString(1, log.getEventLogged());
			callableStatement.setString(2, log.getClassEvent());
			callableStatement.setDate(3, convertFromJAVADateToSQLDate(log.getDateAndTimeOfEvent()));
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

	public void exceptionCaused(Log log, Exception exceptionType) {

	}

	public ArrayList<Log> getAccountActivity(int accountID) {
		ArrayList<Log> logArrayList = new ArrayList<Log>();
		try (Connection connection = DriverManager.getConnection(databaseConnectionString);
		     CallableStatement callableStatement = connection.prepareCall("{CALL spGetLogsForAccount(?)}"))
		{
			callableStatement.setInt(1,accountID);
			ResultSet rs = callableStatement.executeQuery();

			while (rs.next()) {
				logArrayList.add(new Log(rs.getString(1),rs.getString(2),rs.getDate(3)));
			}

		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to get the selected accounts activity.\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
		return logArrayList;
	}

	public void setAccountCurrentlyLoggedIn(Account accountCurrentlyLoggedIn) {
		this.accountCurrentlyLoggedIn = accountCurrentlyLoggedIn;
	}
}
