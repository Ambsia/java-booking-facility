package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Author: [Alex]
 */
public class AccountManagementBusinessLayer extends BusinessLayer implements Iterable<Account> {
    private final ArrayList<Account> accounts;
    private int currentAccountID;
    private int currentIndexInAccountList;

    public AccountManagementBusinessLayer() {
        this.accounts = new ArrayList<>();
        this.currentAccountID = -1;
        this.currentIndexInAccountList = -1;
    }

    public void addAccount(Account account) {
        Log log = new Log("Adding an account.",this.getClass().getSimpleName(),new Date());
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spInsertAccount(?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, account.getUserLevel());
                    callableStatement.setString(2, account.getUsername());
                    callableStatement.setString(3, account.getHashedPassword());
                    callableStatement.setString(4, account.getUserSalt());
                    callableStatement.registerOutParameter(5, Types.INTEGER);
                    getDatabaseConnector().execute();
                    account.setUserID(callableStatement.getInt(5));
                    this.accounts.add(account);
                } catch (SQLException e) {
                    getLoggerBusinessLayer().exceptionCaused(log,e);
                    MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    public void removeAccount() {
        Log log = new Log("Removing an account.",this.getClass().getSimpleName(),new Date());
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spRemoveAccount(?)}");
                if (this.currentAccountID != -1) {
                    try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                        callableStatement.setInt(1, currentAccountID);
                        getDatabaseConnector().execute();
                        removeAccountFromList();
                    } catch (SQLException e) {
                        getLoggerBusinessLayer().exceptionCaused(log,e);
                        MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
                    }
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    public void getAllAccounts() {
        Log log = new Log("Getting all accounts.",this.getClass().getSimpleName(),new Date());
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetAllAccounts}");
                accounts.clear();
                try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    while (rs.next()) {
                        this.accounts.add(new Account(rs.getInt(1), rs.getInt(4), rs.getString(2), rs.getString(3)));
                    }
                } catch (SQLException e) {
                    getLoggerBusinessLayer().exceptionCaused(log,e);
                    MessageBox.errorMessageBox("There was an issue while retrieving accounts.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }


    public void editAccount(int accountID) {
    }

    @Override
    public Iterator<Account> iterator() {
        return accounts.iterator();
    }

    public void setCurrentAccountID(int currentAccountID) {
        this.currentAccountID = currentAccountID;
    }

    void removeAccountFromList() {
        if (this.currentIndexInAccountList >= 0 && accounts.size() > 0) {
            this.accounts.remove(this.currentIndexInAccountList);
        } else {
            MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
        }
    }

    void addAccountToListAtAGivenPosition(Account account) {
        if (currentIndexInAccountList >= 0 && currentIndexInAccountList <= accounts.size()) {
            this.accounts.add(currentIndexInAccountList, account);
        }
    }

    public void setCurrentIndexOfAccountInList(int indexOfBookingInList) {
        try {
            if (indexOfBookingInList >= 0 && indexOfBookingInList <= accounts.size()) {
                this.currentIndexInAccountList = indexOfBookingInList;
            }
        } catch (IndexOutOfBoundsException e) {
            MessageBox.errorMessageBox("Big problems......");
        }
    }
}
