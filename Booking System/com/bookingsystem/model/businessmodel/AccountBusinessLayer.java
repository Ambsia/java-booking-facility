package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: [Alex] on [$Date]
 */

public class AccountBusinessLayer extends BusinessLayer {

    private static final int ACCOUNT_FOUND = 0;
    private static final int ACCOUNT_NOT_FOUND = 1;
    private static final int CONNECTION_FAILED = 2;

    private int error_code = 0;
    private boolean accountFound;
    private Account accountLoggedIn;

    public AccountBusinessLayer() {
        this.accountLoggedIn = null;
    }

    public Account retrieveAccount(String username, String password) {
        Account account = new Account(0, 0, username, password);
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{ CALL spGetAccount(?,?) }");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setString(1, account.getUsername());
                    callableStatement.setString(2, account.getHashedPassword());
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        if (rs.next()) {
                            account.setUserID(rs.getInt(1));
                            account.setUserLevel(rs.getInt(2));
                            accountFound = true;
                            this.accountLoggedIn = account;
                            getDatabaseConnector().closeConnection();
                            this.error_code = ACCOUNT_FOUND;
                            return account;
                        } else {
                            this.error_code = ACCOUNT_NOT_FOUND;
                        }
                    }
                } catch (SQLException e) {
                    //getLoggerBusinessLayer().exceptionCaused(log,e);
                    MessageBox.errorMessageBox("There was an issue while we were trying to retrieve that account from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        this.error_code = CONNECTION_FAILED;
        return null;
    }

    public int getError_code() {
        return this.error_code;
    }

    public Account getAccountLoggedIn() {
        return this.accountLoggedIn;
    }

    public boolean isAccountFound() {
        return accountFound;
    }
}