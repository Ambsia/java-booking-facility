package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.DatabaseConnector;

import java.sql.Date;

/**
 * Author: [Alex]
 */
public class BusinessLayer {
    private final DatabaseConnector databaseConnector;
    private LoggerBusinessLayer loggerBusinessLayer = null;

    public BusinessLayer() {
        databaseConnector = new DatabaseConnector();
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

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public void setLoggerBusinessLayer(LoggerBusinessLayer loggerBusinessLayer) {
        this.loggerBusinessLayer = loggerBusinessLayer;
    }

    public LoggerBusinessLayer getLoggerBusinessLayer() {
        return loggerBusinessLayer;
    }

}