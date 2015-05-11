package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Author: [Alex]
 */
public class LogTableModel extends DefaultTableModel {
    public LogTableModel() {
        addColumn("ID");
        addColumn("Event");
        addColumn("Class");
        addColumn("Timestamp");
        addColumn("ID Modified");
    }
}
