package com.bookingsystem.view.panes;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.AccountTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTable;
import com.bookingsystem.view.controls.UIBookingSystemJTableAccounts;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookings;
import com.bookingsystem.view.panelparts.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alex on 24/05/2015.
 */
public class UIBookingSystemArchive extends JPanel {
    private final UIBookingSystemJTable bookingSystemJTable;
    private BookingTableModel model;
    public UIBookingSystemArchive() {
        bookingSystemJTable = new UIBookingSystemJTableBookings(new BookingTableModel());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;


        bookingSystemJTable.getColumn("Booking ID").setMinWidth(0);
        bookingSystemJTable.getColumn("Booking ID").setMaxWidth(0);
        bookingSystemJTable.getColumn("Booking ID").setPreferredWidth(0);
        bookingSystemJTable.getColumn("Day").setMaxWidth(85);
        bookingSystemJTable.getColumn("Date").setMaxWidth(80);

        JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
        this.add(jScrollPane, gbc);
    }

    public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
        return bookingSystemJTable.getSelectedRowAsStringArrayList();
    }

    public void addBookingToList(Booking booking) {
        bookingSystemJTable.addRowToList(booking);
    }

    public void addBookingsToList(ArrayList<Booking> listOfBookings) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (Booking b : listOfBookings) {
            objectArrayList.add(b);
        }
        bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
    }

    public Booking getBookingFromList(int bookingId) {
        return (Booking) bookingSystemJTable.getRowFromList(bookingId);
    }

    public int getIndexOfSelectedRow() {
        return bookingSystemJTable.getSelectedRow();
    }

    public int getIDOfSelectedRow() {
        return bookingSystemJTable.getIDOfSelectedRow();
    }

    public int getRowCountOfTable() {
        return bookingSystemJTable.getRowCount();
    }

    public void replaceBookingInList(Booking newBooking) {
        bookingSystemJTable.replaceRowInList(newBooking);
    }

    public void removeBookingFromTable() {
        bookingSystemJTable.removeRowFromList();
    }

    public void removeAllBookings() {
        bookingSystemJTable.removeAllRowsFromList();
    }

}
