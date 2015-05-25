package com.bookingsystem.view.controls;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.BookingProblemModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.model.tablemodel.LogTableModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alex on 24/05/2015.
 */

    public class UIBookingSystemJTableBookingProblems extends UIBookingSystemJTable {
    private final BookingProblemModel bookingProblemModel;

    public UIBookingSystemJTableBookingProblems(BookingProblemModel bookingProblemModel) {
        super();
        this.bookingProblemModel = bookingProblemModel;
        this.setModel(this.bookingProblemModel);
    }

    @Override
    public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
        for (Object object : arrayList) {
            addRowToList(object);
        }
    }

    @Override
    public void addRowToList(Object data) {
        Booking booking = (Booking) data;
        bookingProblemModel.addRow(new Object[]{booking.getBookingID(), booking.getBookingDay(),
               booking.getBookingHolder(),
                        booking.getBookingLocation(),booking.getRequiredEquipment() });
    }

    @Override
    public Object getRowFromList(int identifierOfData) {
        if (identifierOfData >= 0) {
            return new Log((String) bookingProblemModel.getValueAt(identifierOfData, 0),
                    (String) bookingProblemModel.getValueAt(identifierOfData, 1),
                    (Date) bookingProblemModel.getValueAt(identifierOfData, 2));
        } else return null;
    }

    public boolean isRowInTable(int ident) {
        if (ident >= 0) {
            for (int i = bookingProblemModel.getRowCount() - 1; i >= 0; --i) {
                if ((int)bookingProblemModel.getValueAt(i,0) == ident) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void replaceRowInList(Object rowData) {
    }

    @Override
    public void removeRowFromList() {
        if (this.getSelectedRow() < bookingProblemModel.getRowCount() && this.getSelectedRow() >= 0) {
            bookingProblemModel.removeRow(this.getSelectedRow());
        } else {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
    }

    @Override
    public void removeAllRowsFromList() {
        int rowCount = bookingProblemModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            bookingProblemModel.removeRow(i);
        }
    }

}

