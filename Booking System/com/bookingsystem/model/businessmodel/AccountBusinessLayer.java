package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: [Alex] on [$Date]
 */

public class AccountBusinessLayer extends BusinessLayer {
    private boolean accountFound;
    private Account accountLoggedIn;

    public AccountBusinessLayer() {
        this.accountLoggedIn = null;
    }

    public Account retrieveAccount(String username, String password) {
        Account account = new Account(0, 0, username, password);
        try {
            getDatabaseConnector().openConnection();
            getDatabaseConnector().createNewCallableStatement("{ CALL spGetAccount(?,?) }");
            CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();
            callableStatement.setString(1, account.getUsername());
            callableStatement.setString(2, account.getHashedPassword());

            ResultSet rs = getDatabaseConnector().executeQuery();
            if (rs.next()) {
                account.setUserID(rs.getInt(1));
                account.setUserLevel(rs.getInt(2));
                accountFound = true;
                this.accountLoggedIn = account;
                getDatabaseConnector().closeConnection();
                return account;
            }
        } catch (SQLException e) {
            MessageBox.errorMessageBox("There was an issue while we were trying to retrieve that account from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
        }
        return null;
    }

    public Account getAccountLoggedIn() {
       return this.accountLoggedIn;
    }

    public boolean isAccountFound() {
        return accountFound;
    }
}