package com.bookingsystem.model.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.bookingsystem.model.Booking;

/**
 * Created by Alex on 24/05/2015
 */
public class ArchiveTableModel extends AbstractTableModel {
    /**
     *
     */
    private static final long serialVersionUID = -5569825021693799992L;
    private static final int COLUMN_NO = 0;
    private static final int COLUMN_DAY = 1;
    private static final int COLUMN_DATE = 2;
    private static final int COLUMN_TIME = 3;
    private static final int COLUMN_ROOM_BOOKED = 4;
    private static final int COLUMN_BOOKING_HOLDER = 5;
    private static final int COLUMN_EQUIPMENT = 6;

    private final String[] columnNames = {"Booking ID", "Day", "Date",
            "Time Of Booking", "Room Booked", "Booking Holder", "Equipment"};
    private final List<Booking> bookingList;

    public ArchiveTableModel(List<Booking> bookingList) {
        super();
        this.bookingList = bookingList;
        int indexCount = 1;
        for (Booking b : bookingList) {
            b.setBookingIndex(indexCount++);
        }
    }

    @Override
    public int getRowCount() {
        return bookingList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object returnValue = null;
        try {
            Booking booking = bookingList.get(rowIndex);

            switch (columnIndex) {
                case COLUMN_NO:
                    returnValue = booking.getBookingID();
                    break;
                case COLUMN_DAY:
                    returnValue = booking.getBookingDay();
                    break;
                case COLUMN_DATE:
                    returnValue = booking.getDateToString();
                    break;
                case COLUMN_TIME:
                    returnValue = booking.getTimeToString();
                    break;
                case COLUMN_ROOM_BOOKED:
                    returnValue = booking.getBookingLocation();
                    break;
                case COLUMN_BOOKING_HOLDER:
                    returnValue = booking.getBookingHolder();
                    break;
                case COLUMN_EQUIPMENT:
                    returnValue = booking.getRequiredEquipment().getEquipmentName();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid column index");
            }
        } catch (IndexOutOfBoundsException ind) {
            System.out.println(ind.toString());
        }
        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Booking booking = bookingList.get(rowIndex);
        if (columnIndex == COLUMN_NO) {
            booking.setBookingIndex((int) value);
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (bookingList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    public void addBooking(Booking booking) {
        this.bookingList.add(booking);
        this.fireTableDataChanged();
    }

    public void addBookingList(List<Booking> bookingList) {
        for (Booking b : bookingList) {
            this.bookingList.add(b);
            this.fireTableDataChanged();
        }
    }

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public void removeBooking(Booking booking) {
    // this.bookingList.remove(booking);
    // this.fireTableRowsDeleted(this.bookingList.size()-3,
    // this.bookingList.size()-2);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    public void clearArchiveList() {
        this.bookingList.clear();
        this.fireTableDataChanged();
    }
}
