package com.bookingsystem.view.controls;

import java.util.ArrayList;
import java.util.Calendar;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.tablemodel.BookingTableModel;

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

	@Override
	public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
		for (Object object : arrayList) {
			addRowToList(object);
		}
	}

	@Override
	public void addRowToList(Object data) {
		Booking booking = (Booking) data;
		Calendar date1 = Calendar.getInstance();
		date1.set(Calendar.AM_PM, Calendar.AM);
		date1.set(Calendar.DAY_OF_MONTH, 25);
		date1.set(Calendar.MONTH, 11);
		date1.set(Calendar.HOUR, 00);
		date1.set(Calendar.MINUTE, 00);
		date1.set(Calendar.SECOND, 00);
		date1.set(Calendar.MILLISECOND, 0);
		String date = "";
		String time = "";
		if (BOOKING_DATE_FORMAT.format(booking.getBookingDate()).equals(BOOKING_DATE_FORMAT.format(date1.getTime()))
				&& BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime()))) {
			date = "Unknown";
			time= "Unknown";
		} else if (BOOKING_DATE_FORMAT.format(booking.getBookingDate()).equals(BOOKING_DATE_FORMAT.format(date1.getTime()))) {
			date = "Unknown";
			time= BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime());
		} else if(BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime()))) {
			time = "Unknown";
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
		} else if (BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime())) || BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals("00:00") || BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()).equals("00:00")) {
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
			time= "Unknown";
		} else {
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
			time= BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime());
		}
		this.bookingTableModel.addRow(new Object[]{booking.getBookingID(),
				booking.getBookingDay(),
				date,
				time,
				booking.getBookingLocation(),
				booking.getBookingHolder(),
				booking.getRequiredEquipment().getEquipmentName()});

	}

	@Override
	public Object getRowFromList(int identifierOfData) {
		if (identifierOfData >= 0) {
			try {
				return new Booking((int) bookingTableModel.getValueAt(identifierOfData, 0),
						(String) bookingTableModel.getValueAt(identifierOfData, 1),
						stringToDate((String) bookingTableModel.getValueAt(identifierOfData, 2)),
						stringToTime((String)bookingTableModel.getValueAt(identifierOfData, 3),false),
						stringToTime((String)bookingTableModel.getValueAt(identifierOfData, 3),true),
						(String) bookingTableModel.getValueAt(identifierOfData, 4),
						(String) bookingTableModel.getValueAt(identifierOfData, 5),
						convertStringToEquipment((String)bookingTableModel.getValueAt(identifierOfData, 6)));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}

	private Equipment convertStringToEquipment(String s) {
		return new Equipment(s);
	}
	@Override
	public void replaceRowInList(Object rowData) {
		Booking booking = (Booking) rowData;
		Calendar date1 = Calendar.getInstance();
		date1.set(Calendar.AM_PM,Calendar.AM);
		date1.set(Calendar.DAY_OF_MONTH,25);
		date1.set(Calendar.MONTH,11);
		date1.set(Calendar.HOUR, 00);
		date1.set(Calendar.MINUTE, 00);
		date1.set(Calendar.SECOND, 00);
		date1.set(Calendar.MILLISECOND, 0);
		String date = "";
		String time = "";
		if (BOOKING_DATE_FORMAT.format(booking.getBookingDate()).equals(BOOKING_DATE_FORMAT.format(date1.getTime()))
				&& BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime()))) {
			date = "Unknown";
			time= "Unknown";
		} else if (BOOKING_DATE_FORMAT.format(booking.getBookingDate()).equals(BOOKING_DATE_FORMAT.format(date1.getTime()))) {
			date = "Unknown";
			time= BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime());
		} else if(BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime()))) {
			time = "Unknown";
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
		} else if (BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals(BOOKING_TIME_FORMAT.format(date1.getTime())) || BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals("00:00") || BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()).equals("00:00")) {
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
			time= "Unknown";
		} else {
			date = BOOKING_DATE_FORMAT.format(booking.getBookingDate());
			time= BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime());
		}
		bookingTableModel.setValueAt(booking.getBookingID(),this.getSelectedRow(),0);
		bookingTableModel.setValueAt(booking.getBookingDay(),this.getSelectedRow(),1);
		bookingTableModel.setValueAt(date, this.getSelectedRow(),2);
		bookingTableModel.setValueAt(time,this.getSelectedRow(),3);
		bookingTableModel.setValueAt(booking.getBookingLocation(),this.getSelectedRow(),4);
		bookingTableModel.setValueAt(booking.getBookingHolder(),this.getSelectedRow(),5);
		bookingTableModel.setValueAt(booking.getRequiredEquipment().getEquipmentName(),this.getSelectedRow(),6);
	}

	@Override
	public void removeRowFromList() {
		if (this.getSelectedRow() < bookingTableModel.getRowCount() && this.getSelectedRow() >= 0) {
			bookingTableModel.removeRow(this.getSelectedRow());
		} else {
			throw new IndexOutOfBoundsException("Index does not exist");
		}
	}

	public void removeSelectedRowsFromList() {
		int[] rows = this.getSelectedRows();
		for(int i=0;i<rows.length;i++){
			bookingTableModel.removeRow(rows[i]-i);
		}
	}
	@Override
	public void removeAllRowsFromList() {
		int rowCount = bookingTableModel.getRowCount();
		for (int i = rowCount-1;i>=0;i--) {
			bookingTableModel.removeRow(i);
		}
	}
	public void removeRow(int row) {
		bookingTableModel.removeRow(row);
	}

	public int selectedRowCount() {
		return super.getRowCount();
	}

	@Override
	public int[] getSelectedRows() {
		return super.getSelectedRows();
	}
}
