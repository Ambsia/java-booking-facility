package com.bookingsystem.model.tablemodel;

import com.bookingsystem.model.Account;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.List;

/**
 * Author: [Alex]
 */
public class AccountTableModel extends AbstractTableModel {
    /**
     *
     */
    private static final long serialVersionUID = 3941353289504819621L;
    private static final int COLUMN_NO = 0;
    private static final int COLUMN_ACCOUNT_NAME = 1;
    private static final int COLUMN_ACCOUNT_LEVEL = 2;

    private static final String[] columnNames = {"Account ID", "Account Name",
            "Account Level"};

    private final List<Account> accountList;

    public AccountTableModel(List<Account> accountList) {
        super();
        this.accountList = accountList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return accountList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (accountList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = accountList.get(rowIndex);
        Object returnValue;
        switch (columnIndex) {
            case COLUMN_NO:
                returnValue = account.getUserID();
                break;
            case COLUMN_ACCOUNT_NAME:
                returnValue = account.getUsername();
                break;
            case COLUMN_ACCOUNT_LEVEL:
                returnValue = account.getUserLevel();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Account account = accountList.get(rowIndex);
        System.out.println(columnIndex);
        switch (columnIndex) {
            case COLUMN_NO:
                break;
            case COLUMN_ACCOUNT_NAME:
                break;
            case COLUMN_ACCOUNT_LEVEL:
                account.setUserLevel((int) value);
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);

    }

    public void clearAccountList() {
        this.accountList.clear();
        this.fireTableDataChanged();
    }

    public void addAccount(Account account) {
        this.accountList.add(account);
        this.fireTableDataChanged();
    }

    public void addAccountList(List<Account> accountList) {
        for (Account account : accountList) {
            this.accountList.add(account);
            this.fireTableDataChanged();
        }
    }

    // --Commented out by Inspection START (21/06/2015 00:48):
    // public void removeAccount(Account account) {
    // this.accountList.remove(account);
    // this.fireTableDataChanged();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:48)

    public Account getAccount(int id) {
        for (Account account : this.accountList) {
            if (account.getUserID() == id) {
                return account;
            }
        }
        return null;
    }

    public void removeRows(List<Integer> indices) {
        Collections.sort(indices);
        for (int i = indices.size() - 1; i >= 0; i--) {
            this.accountList.remove(indices.get(i).intValue());
            fireTableRowsDeleted(indices.get(i), indices.get(i));
        }
        fireTableDataChanged();
    }

}
