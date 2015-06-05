package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Alex on 24/05/2015.
 */
public class ArchiveTableModel extends  DefaultTableModel {
    public ArchiveTableModel() {
        addColumn("Booking ID");
        addColumn("Day");
        addColumn("Date");
        addColumn("Time Of Booking");
        addColumn("Location");
        addColumn("Holder");
        addColumn("Equipment");
    }
}

