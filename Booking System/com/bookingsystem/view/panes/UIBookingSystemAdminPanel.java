package com.bookingsystem.view.panes;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.tablemodel.AccountTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTable;
import com.bookingsystem.view.controls.UIBookingSystemJTableAccounts;
import com.bookingsystem.view.panelparts.UIBookingSystemAdminControlPanel;
import com.bookingsystem.view.panelparts.UIBookingSystemAdminViewPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAdminPanel extends JPanel {
    private final UIBookingSystemAdminViewPanel bookingSystemAdminViewPanel;


    private final UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel;
    private final UIBookingSystemJTable accountSystemJTable;

    public UIBookingSystemAdminPanel() {
        accountSystemJTable = new UIBookingSystemJTableAccounts(new AccountTableModel());
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

    public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
        return accountSystemJTable.getSelectedRowAsStringArrayList();
    }

    public void addAccountToList(Account account) {
        accountSystemJTable.addRowToList(account);
    }

    public void addAccountsToList(ArrayList<Account> listOfAccounts) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (Account account : listOfAccounts) {
            objectArrayList.add(account);
        }
        accountSystemJTable.addArrayOfRowsToList(objectArrayList);
    }

    public Account getAccountFromList(int accountId) {
        System.out.println("account id " + accountId);
        return (Account) accountSystemJTable.getRowFromList(accountId);
    }

    public int getIndexOfSelectedRow() {
        return accountSystemJTable.getSelectedRow();
    }

    public int getIDOfSelectedRow() {
        return accountSystemJTable.getIDOfSelectedRow();
    }

    public int getRowCountOfTable() {
        return accountSystemJTable.getRowCount();
    }

    public void replaceAccountInList(Account newAccount) {
        accountSystemJTable.replaceRowInList(newAccount);
    }

    public void removeAccountFromTable() {
        accountSystemJTable.removeRowFromList();
    }

    public void removeAllAccounts() {
        accountSystemJTable.removeAllRowsFromList();
    }

    public UIBookingSystemAdminViewPanel getBookingSystemAdminViewPanel() {
        return bookingSystemAdminViewPanel;
    }

    public UIBookingSystemAdminControlPanel getBookingSystemAdminControlPanel() {
        return bookingSystemAdminControlPanel;
    }


}
