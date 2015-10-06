package com.bookingsystem.model.businessmodel;

import java.sql.Date;

import com.bookingsystem.helpers.DatabaseConnector;

/**
 * Author: [Alex]
 */
class BusinessLayer {
    private final DatabaseConnector databaseConnector;

    BusinessLayer() {
        databaseConnector = new DatabaseConnector();
    }

    // invoke the connection here and delegate it across
    static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }

    DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}