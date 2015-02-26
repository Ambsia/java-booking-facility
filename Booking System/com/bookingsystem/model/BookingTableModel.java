package com.bookingsystem.model;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Alex on 10/02/2015.
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
