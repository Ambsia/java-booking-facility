package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Author: [Alex]
 */
public class AccountTableModel extends DefaultTableModel {
    public AccountTableModel() {
        addColumn("Account ID");
        addColumn("Account Name");
        addColumn("Account Level");
    }
}
