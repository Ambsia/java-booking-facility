package com.bookingsystem.view.controls;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemJTable extends JTable implements BookingSystemJTableInterface<Object> {

	static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
	static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

	UIBookingSystemJTable() {
		this.getTableHeader().setReorderingAllowed(false);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public int getIDOfSelectedRow() {
		if (this.getSelectedRow() != -1) {
			return this.getValueAt(this.getSelectedRow(), 0) != null ? (int) this.getValueAt(this.getSelectedRow(), 0) : -1;
		} else {
			return -1;
		}
	}

	public ArrayList<String> getSelectedRowAsStringArrayList() {
		ArrayList<String> data = new ArrayList<>();
		if (this.getSelectedRow() != -1) {
			for (int i = 0; i < this.getColumnCount(); i++) {
				data.add(this.getValueAt(this.getSelectedRow(), i).toString());
			}
			return data;
		} else { //adding empty strings, easier for the view panel to process, and we do not have to deal with nulls
			for (int i = 0; i < this.getColumnCount(); i++) {
				data.add("");
			}
			return data;
		}
	}
}
