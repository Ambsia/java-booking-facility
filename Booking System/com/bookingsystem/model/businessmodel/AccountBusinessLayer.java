package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
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
    private boolean accountFound;
    private String databaseConnectionString;

    public AccountBusinessLayer() {
        returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
        databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
    }

    public void insertAccount(Account account) {
        Statement stmt;
        try {
            Connection con = DriverManager.getConnection(databaseConnectionString);
            stmt = con.createStatement();
            account.generateSalt();
            stmt.execute("EXECUTE spInsertAccount '" + account.getUserLevel() + "','" + account.getUsername() + "'," +
                    "'" + account.getHashedPassword() + "','" + account.getUserSalt() + "'");

            con.close();
            stmt.close();
        } catch (SQLException e) {
            MessageBox.errorMessageBox("There was an issue while we were trying to insert that account in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
        }
    }

    public void removeAccount() {

    }

    public Account retrieveAccount(String username, String password) throws SQLException {
        Statement stmt;
        Account account;
        try {
            Connection con = DriverManager.getConnection(databaseConnectionString);
            stmt = con.createStatement();
            account = new Account(0,0,username,password);
            ResultSet rs = stmt.executeQuery("EXECUTE spGetAccount '" + account.getUsername() + "'"+ ", '" + account.getHashedPassword() +"'");
            if (rs.next())  {
                account.setUserID(rs.getInt(1));
                account.setUserID(rs.getInt(2));
                accountFound = true;
                con.close();
                stmt.close();
                rs.close();
                return account;
            }
        } catch (SQLException e) {
            MessageBox.errorMessageBox("There was an issue while we were trying to retrieve that account from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
        }
        accountFound = true;
        return null;
    }

    public boolean isAccountFound() {
        return accountFound;
    }
}

