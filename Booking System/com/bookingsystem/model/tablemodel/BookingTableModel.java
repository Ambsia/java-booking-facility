package com.bookingsystem.model.tablemodel;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingTableModel extends AbstractTableModel implements
        Iterable<Booking> {

    /**
     *
     */
    private static final long serialVersionUID = 392304419118777610L;
    private static final String[] columnNames = {"Booking ID", "Day", "Date",
            "Time Of Booking", "Room Booked", "Booking Holder", "Equipment"};

    private static final int COLUMN_NO = 0;
    private static final int COLUMN_DAY = 1;
    private static final int COLUMN_DATE = 2;
    private static final int COLUMN_TIME = 3;
    private static final int COLUMN_ROOM = 4;
    private static final int COLUMN_HOLDER = 5;
    private static final int COLUMN_EQUIPMENT = 6;

    private final List<Booking> bookingList;

    public BookingTableModel(List<Booking> bookingList) {
        this.bookingList = bookingList;
        int indexCount = 1;
        for (Booking booking : bookingList) {
            booking.setBookingIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return bookingList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (bookingList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking booking = bookingList.get(rowIndex);
        Object returnValue;

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
            case COLUMN_ROOM:
                returnValue = booking.getBookingLocation();
                break;
            case COLUMN_HOLDER:
                returnValue = booking.getBookingHolder();
                break;
            case COLUMN_EQUIPMENT:
                returnValue = booking.getRequiredEquipment().getEquipmentName();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Booking booking = bookingList.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_NO:
                booking.setBookingIndex((int) value);
                break;
            case COLUMN_DAY:
                booking.setBookingDay((String) value);
                break;
            case COLUMN_DATE:
                booking.setBookingDate((Date) value);
                break;
            case COLUMN_TIME:
                booking.setBookingStartTime((Date) value);
                break;
            case COLUMN_ROOM:
                booking.setBookingLocation((String) value);
                break;
            case COLUMN_HOLDER:
                booking.setBookingHolder((String) value);
                break;
            case COLUMN_EQUIPMENT:
                booking.setBookingEquipment((Equipment) value);
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);

    }

    public void clearBookingList() {
        this.bookingList.clear();
        this.fireTableDataChanged();
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

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public void removeBooking(Booking booking) {
    // this.bookingList.remove(booking);
    // this.fireTableDataChanged();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    public Booking getBooking(int id) {
        for (Booking b : this.bookingList) {
            if (b.getBookingID() == id) {
                return b;
            }
        }
        return null;
    }

    public void removeRows(List<Integer> indices) {
        Collections.sort(indices);
        for (int i = indices.size() - 1; i >= 0; i--) {
            this.bookingList.remove(indices.get(i).intValue());
            fireTableRowsDeleted(indices.get(i), indices.get(i));
        }
        fireTableDataChanged();
    }

    @Override
    public Iterator<Booking> iterator() {
        return bookingList.iterator();
    }

}
