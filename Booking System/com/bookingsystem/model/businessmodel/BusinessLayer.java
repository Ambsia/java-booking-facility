package com.bookingsystem.model.businessmodel;

import java.sql.Date;

import com.bookingsystem.helpers.DatabaseConnector;

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
	protected static java.sql.Date convertFromJAVADateToSQLDate(
			java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
}