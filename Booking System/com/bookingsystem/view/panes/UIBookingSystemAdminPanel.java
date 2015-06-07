package com.bookingsystem.view.panes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.tablemodel.AccountTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTable;
import com.bookingsystem.view.panelparts.UIBookingSystemAdminViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemAdminControlPanel;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAdminPanel extends JPanel {
    private final UIBookingSystemAdminViewPanel bookingSystemAdminViewPanel;


    private final UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel;
    private final JTable accountSystemJTable;
    private AccountTableModel accountTableModel;
    public UIBookingSystemAdminPanel() {
        accountSystemJTable = new JTable(accountTableModel);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        bookingSystemAdminViewPanel = new UIBookingSystemAdminViewPanel();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.9;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane jScrollPane = new JScrollPane(accountSystemJTable);
        gbc.gridheight = 1;
        this.add(jScrollPane, gbc);

        bookingSystemAdminControlPanel = new UIBookingSystemAdminControlPanel();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = .2;
        gbc.weighty = .1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(bookingSystemAdminControlPanel, gbc);

        JScrollPane jScrollPane1 = new JScrollPane(bookingSystemAdminViewPanel);
        gbc.gridheight = 2;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = .8;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(jScrollPane1, gbc);

    }
    public void setJTableModel(AccountTableModel accountTableModel) {
        this.accountTableModel = accountTableModel;
        this.accountSystemJTable.setModel(this.accountTableModel);
        this.accountSystemJTable.setAutoCreateRowSorter(true);
        this.accountTableModel.fireTableDataChanged();
    }

    public AccountTableModel getJTableModel() {
        return this.accountTableModel;
    }

    public int selectedRowCount() {
        return accountSystemJTable.getSelectedRowCount();
    }

    public int rowViewIndexToModel(int row) {
        return accountSystemJTable.convertRowIndexToModel(row);
    }

    public Object getValueAt(int row, int column) {
        return accountTableModel.getValueAt(row,column);
    }

    public List<Integer> getSelectedRows() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i<accountSystemJTable.getSelectedRows().length;i++) {
            integerList.add(rowViewIndexToModel(accountSystemJTable.getSelectedRows()[i]));
        }
        return integerList;
    }

    public int getSelectedRow() {
        return accountSystemJTable.getSelectedRow();
    }
//    public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
//        return accountSystemJTable.getSelectedRowAsStringArrayList();
//    }
//
//    public void addAccountToList(Account account) {
//        accountSystemJTable.addRowToList(account);
//    }
//
//    public void addAccountsToList(ArrayList<Account> listOfAccounts) {
//        ArrayList<Object> objectArrayList = new ArrayList<>();
//        for (Account account : listOfAccounts) {
//            objectArrayList.add(account);
//        }
//        accountSystemJTable.addArrayOfRowsToList(objectArrayList);
//    }
//
//    public Account getAccountFromList(int accountId) {
//        System.out.println("account id " +accountId);
//        return (Account) accountSystemJTable.getRowFromList(accountId);
//    }
//
//    public int getIndexOfSelectedRow() {
//        return accountSystemJTable.getSelectedRow();
//    }
//
//    public int getIDOfSelectedRow() {
//        return accountSystemJTable.getIDOfSelectedRow();
//    }
//
//    public int getRowCountOfTable() {
//        return accountSystemJTable.getRowCount();
//    }
//
//    public void replaceAccountInList(Account newAccount) {
//        accountSystemJTable.replaceRowInList(newAccount);
//    }
//
//    public void removeAccountFromTable() {
//        accountSystemJTable.removeRowFromList();
//    }
//
//    public void removeAllAccounts() {
//        accountSystemJTable.removeAllRowsFromList();
//    }

    public UIBookingSystemAdminViewPanel getBookingSystemAdminViewPanel() { return bookingSystemAdminViewPanel; }

    public UIBookingSystemAdminControlPanel getBookingSystemAdminControlPanel() { return bookingSystemAdminControlPanel; }


}
