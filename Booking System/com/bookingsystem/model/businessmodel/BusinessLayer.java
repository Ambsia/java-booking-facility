package com.bookingsystem.model.businessmodel;

import java.sql.Date;

/**
 * Author: [Alex]
 */
class BusinessLayer {
	static java.sql.Date convertFromJAVADateToSQLDate(
			java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}
}