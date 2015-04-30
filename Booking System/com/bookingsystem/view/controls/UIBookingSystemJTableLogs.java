package com.bookingsystem.view.controls;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.model.tablemodel.LogTableModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class UIBookingSystemJTableLogs extends UIBookingSystemJTable {
	private final LogTableModel logTableModel;
	public UIBookingSystemJTableLogs(LogTableModel logTableModel) {
		super();
		this.logTableModel = logTableModel;
		this.setModel(this.logTableModel);
	}

	@Override
	public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
		for (Object object : arrayList) {
			addRowToList(object);
		}
	}

	@Override
	public void addRowToList(Object data) {
		Log log = (Log) data;
		logTableModel.addRow(new Object[]{log.getLogID(),log.getEventLogged(),
				log.getClassEvent(),
				log.getDateAndTimeOfEvent(),
				log.getIdPlayedWith()});
	}

	@Override
	public Object getRowFromList(int identifierOfData) {
		if (identifierOfData >= 0 && identifierOfData != (int) logTableModel.getValueAt(0, identifierOfData)) {
			return new Log((String) logTableModel.getValueAt(0, identifierOfData),
					(String) logTableModel.getValueAt(1, identifierOfData),
					(Date) logTableModel.getValueAt(2, identifierOfData));
		}
		else return null;
	}

	@Override
	public void replaceRowInList(Object rowData) {
	}

	@Override
	public void removeRowFromList() {
		if (this.getSelectedRow() < logTableModel.getRowCount() && this.getSelectedRow() >= 0) {
			logTableModel.removeRow(this.getSelectedRow());
		} else {
			throw new IndexOutOfBoundsException("Index does not exist");
		}
	}

	@Override
	public void removeAllRowsFromList() {
		int rowCount = logTableModel.getRowCount();
		for (int i = rowCount-1;i>=0;i--) {
			logTableModel.removeRow(i);
		}
	}

}
