package com.bookingsystem.model.tablemodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.bookingsystem.model.Booking;

/**
 * Created by Alex on 24/05/2015.
 */
public class ArchiveTableModel extends AbstractTableModel {
        /**
	 * 
	 */
	private static final long serialVersionUID = -5569825021693799992L;
		private static final int COLUMN_NO = 0;
        private static final int COLUMN_DAY = 1;
        private static final int COLUMN_DATE = 2;
        private static final int COLUMN_TIME_OF_BOOKING = 3;
        private static final int COLUMN_ROOM_BOOKED = 4;
        private static final int COLUMN_BOOKING_HOLDER = 5;
        private static final int COLUMN_EQUIPMENT = 6;
        static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        private String[] columnNames = {"Booking ID", "Day", "Date", "Time Of Booking", "Room Booked", "Booking Holder", "Equipment"};
        private List<Booking> bookingList;

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
                Booking booking = bookingList.get(rowIndex);
                Object returnValue = null;
                switch (columnIndex) {
                        case COLUMN_NO:
                                returnValue = booking.getBookingID();
                                break;
                        case COLUMN_DAY:
                                returnValue = booking.getBookingDay();
                                break;
                        case COLUMN_DATE:
                                returnValue = booking.getBookingDate();
                                break;
                        case COLUMN_TIME_OF_BOOKING:
                                returnValue = BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) +"-"+ BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime());
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
                this.fireTableRowsInserted(0,bookingList.size()-1);
        }

        public void addBookingList(List<Booking> bookingList) {
                this.bookingList.addAll(bookingList);
        }

        public void removeBooking(Booking booking) {
                this.bookingList.remove(booking);
                this.fireTableRowsDeleted(this.bookingList.size()-3, this.bookingList.size()-2);
        }

        public void clearArchiveList() {
                this.bookingList.clear();
        }
}

