package com.bookingsystem.view.controls;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.BookingProblemModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alex on 24/05/2015
 */
public class UIBookingSystemJTableBookingProblems extends UIBookingSystemJTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6315016386872298228L;
	private final BookingProblemModel bookingProblemModel;

	public UIBookingSystemJTableBookingProblems(
			BookingProblemModel bookingProblemModel) {
		super();
		this.bookingProblemModel = bookingProblemModel;
		this.setModel(this.bookingProblemModel);
	}

	public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
		arrayList.forEach(this::addRowToList);
	}

	public void addRowToList(Object data) {
		Booking booking = (Booking) data;
		bookingProblemModel.addRow(new Object[] { booking.getBookingID(),
				booking.getBookingDay(), booking.getBookingHolder(),
				booking.getBookingLocation(), booking.getRequiredEquipment() });
	}

	public Object getRowFromList(int identifierOfData) {
		if (identifierOfData >= 0) {
			return new Log((String) bookingProblemModel.getValueAt(
					identifierOfData, 0),
					(String) bookingProblemModel
							.getValueAt(identifierOfData, 1),
					(Date) bookingProblemModel.getValueAt(identifierOfData, 2));
		} else
			return null;
	}

	public boolean isRowInTable(int ident) {
		if (ident >= 0) {
			for (int i = bookingProblemModel.getRowCount() - 1; i >= 0; --i) {
				if ((int) bookingProblemModel.getValueAt(i, 0) == ident) {
					return true;
				}
			}
		}
		return false;
	}

	public void removeRowFromList() {
		if (this.getSelectedRow() < bookingProblemModel.getRowCount()
				&& this.getSelectedRow() >= 0) {
			bookingProblemModel.removeRow(this.getSelectedRow());
		} else {
			throw new IndexOutOfBoundsException("Index does not exist");
		}
	}

	public int getIDOfRow(int index) {
		return (int) bookingProblemModel.getValueAt(0, index);
	}

	public void removeAllRowsFromList() {
		int rowCount = bookingProblemModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			bookingProblemModel.removeRow(i);
		}
	}

}
