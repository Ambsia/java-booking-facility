package com.bookingsystem.helpers;

import java.sql.*;

/**
 * Created by Alex on 04/05/2015
 */
public final class DatabaseConnector {

    private final String connectionString;
    private CallableStatement callableStatement;
    private Connection connection;

    public DatabaseConnector() {
        ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues(
                "sqlconfig.properties");
        this.connectionString = returnSpecifiedPropertyValues
                .getDatabaseConnectionString();
        this.connection = null;
        this.callableStatement = null;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public boolean isConnectionClosed() {
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void openConnection() {
        try {
            this.connection = DriverManager
                    .getConnection(this.connectionString);
        } catch (SQLException e) {
            MessageBox
                    .errorMessageBox("There was an issue making a connection to the database.\n"
                            + "Does this make any sense to you.."
                            + e.toString() + "?");
        }
    }

    public void closeConnection() {
        try {
            this.callableStatement.close();
            this.connection.close();

        } catch (SQLException e) {
            MessageBox
                    .errorMessageBox("There was an issue closing a connection with the database.\n"
                            + "Does this make any sense to you.."
                            + e.toString() + "?");
        }
    }

    public CallableStatement getCallableStatement() {
        return this.callableStatement;
    }

    public void createNewCallableStatement(String sqlString) {
        try {
            this.callableStatement = connection.prepareCall(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery() {
        ResultSet rs = null;
        try {
            rs = this.callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean execute() {
        try {
            return this.callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
