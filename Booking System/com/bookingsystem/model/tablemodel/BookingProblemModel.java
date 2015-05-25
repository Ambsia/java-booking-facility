package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Alex on 24/05/2015.
 */
public class BookingProblemModel extends  DefaultTableModel {
    public BookingProblemModel() {
        addColumn("ID");
        addColumn("Day");
        addColumn("Holder");
        addColumn("Location");
        addColumn("Equipment");
    }
}

