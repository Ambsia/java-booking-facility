package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Logger;

/**
 * Author: [Alex] on [$Date]
 */
import java.io.IOException;
import java.sql.*;
public class AccountBusinessLayer {

    private Logger accountLogger;
    private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;

    private String databaseConnectionString;

    public AccountBusinessLayer() throws IOException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
        databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();


    }


    public void addAccount() {

    }

    public void removeAccount() {

    }


    public boolean getAccount(Account account) throws SQLException {
        Statement stmt;
        try {
            Connection con = DriverManager.getConnection(databaseConnectionString);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("EXECUTE spGetAccount '" + account.getUsername() + "'"+ ", '" + account.getHashedPassword() +"'");
            if (rs.next())  {
                account.setUserID(rs.getInt(1));
                account.setUserID(rs.getInt(2));
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}


