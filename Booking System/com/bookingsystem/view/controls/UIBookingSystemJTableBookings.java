package com.bookingsystem.view.controls;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.tablemodel.BookingTableModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class UIBookingSystemJTableBookings extends UIBookingSystemJTable {
    private final BookingTableModel bookingTableModel;

    public UIBookingSystemJTableBookings(BookingTableModel bookingTableModel) {
        super();
        this.bookingTableModel = bookingTableModel;
        this.setModel(this.bookingTableModel);
    }

    public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
        for (Object object : arrayList) {
            addRowToList(object);
        }
    }

    public void addRowToList(Object data) {
        Booking booking = (Booking) data;
        this.bookingTableModel.addRow(new Object[]{booking.getBookingID(),
                booking.getBookingDay(),
                BOOKING_DATE_FORMAT.format(booking.getBookingDate()),
                BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()),
                booking.getBookingLocation(),
                booking.getBookingHolder(),
                booking.getRequiredEquipment().GetEquipmentName()});
    }

    public Object getRowFromList(int identifierOfData) {
        if (identifierOfData >= 0) {
            return new Booking((int) bookingTableModel.getValueAt(identifierOfData, 0),
                    (String) bookingTableModel.getValueAt(identifierOfData, 1),
                    (Date) bookingTableModel.getValueAt(identifierOfData, 2),
                    (Date) bookingTableModel.getValueAt(identifierOfData, 3),
                    (Date) bookingTableModel.getValueAt(identifierOfData, 4),
                    (String) bookingTableModel.getValueAt(identifierOfData, 5),
                    (String) bookingTableModel.getValueAt(identifierOfData, 6),
                    (Equipment) bookingTableModel.getValueAt(identifierOfData, 7));
        } else return null;
    }

    public void replaceRowInList(Object rowData) {
        Booking newBooking = (Booking) rowData;
        bookingTableModel.setValueAt(newBooking.getBookingID(), this.getSelectedRow(), 0);
        bookingTableModel.setValueAt(newBooking.getBookingDay(), this.getSelectedRow(), 1);
        bookingTableModel.setValueAt(BOOKING_DATE_FORMAT.format(newBooking.getBookingDate()), this.getSelectedRow(), 2);
        bookingTableModel.setValueAt(BOOKING_TIME_FORMAT.format(newBooking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(newBooking.getBookingCollectionTime()), this.getSelectedRow(), 3);
        bookingTableModel.setValueAt(newBooking.getBookingLocation(), this.getSelectedRow(), 4);
        bookingTableModel.setValueAt(newBooking.getBookingHolder(), this.getSelectedRow(), 5);
        bookingTableModel.setValueAt(newBooking.getRequiredEquipment().GetEquipmentName(), this.getSelectedRow(), 6);
    }

    public void removeRowFromList() {
        if (this.getSelectedRow() < bookingTableModel.getRowCount() && this.getSelectedRow() >= 0) {
            bookingTableModel.removeRow(this.getSelectedRow());
        } else {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
    }

    public void removeAllRowsFromList() {
        int rowCount = bookingTableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            bookingTableModel.removeRow(i);
        }
    }
}
