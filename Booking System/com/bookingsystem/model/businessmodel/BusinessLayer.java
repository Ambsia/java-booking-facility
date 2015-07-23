package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.DatabaseConnector;

import java.sql.Date;

/**
 * Author: [Alex]
 */
class BusinessLayer {
	private final DatabaseConnector databaseConnector;

	BusinessLayer() {
		databaseConnector = new DatabaseConnector();
	}

	DatabaseConnector getDatabaseConnector() {
		return databaseConnector;
	}

	// invoke the connection here and delegate it across
	static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
}