package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.DatabaseConnector;

import java.sql.Date;
import java.util.Calendar;

/**
 * Author: [Alex]
 */
public class BusinessLayer {
	private DatabaseConnector databaseConnector;

	public BusinessLayer() {
		databaseConnector = new DatabaseConnector();
	}

	public DatabaseConnector getDatabaseConnector() {
		return  databaseConnector;
	}

	//invoke the connection here and delegate it across
	static java.sql.Date convertFromJAVADateToSQLDate(
			java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
}