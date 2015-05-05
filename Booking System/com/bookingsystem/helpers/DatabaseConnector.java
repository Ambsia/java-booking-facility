package com.bookingsystem.helpers;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 04/05/2015.
 */
public class DatabaseConnector  {

    private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;
    private String connectionString;
    private CallableStatement callableStatement;
    private Connection connection;

    public DatabaseConnector() {
        this.returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
        this.connectionString = this.returnSpecifiedPropertyValues.getDatabaseConnectionString();
        try {
            System.out.println("connection made");
            this.connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.callableStatement = null;
    }

    public void openConnection() throws SQLException {
        System.out.println("opening connection");
        this.connection = DriverManager.getConnection(connectionString);
    }

    public void closeConnection() throws SQLException {
        System.out.println("closing connection");
        this.connection.close();
        this.callableStatement = null;
    }

    public CallableStatement getCallableStatement() {
        return this.callableStatement;
    }

    public void createNewCallableStatement(String sqlString)  {
        try {
            System.out.println("creating callable statement");
            this.callableStatement = connection.prepareCall(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery() {
        ResultSet rs = null;
        try {
            System.out.println("executing callable statement and returning a result set");
            rs = this.callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean execute() {
        try {
            System.out.println("executing callable statement returning a boolean");
            return this.callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
