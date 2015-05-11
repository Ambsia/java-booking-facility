package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingTableModel extends DefaultTableModel {
    public BookingTableModel() {
        addColumn("Booking ID");
        addColumn("Day");
        addColumn("Date");
        addColumn("Time Of Booking");
        addColumn("Room Booked");
        addColumn("Booking Holder");
        addColumn("Equipment");

    }
}
