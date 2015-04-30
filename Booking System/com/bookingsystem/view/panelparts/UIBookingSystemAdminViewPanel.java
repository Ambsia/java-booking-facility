package com.bookingsystem.view.panelparts;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.LogTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableLogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminViewPanel extends JPanel {


	UIBookingSystemJTableLogs bookingSystemJTableLogs;
	JScrollPane jScrollPane;
	public UIBookingSystemAdminViewPanel() {
		bookingSystemJTableLogs = new UIBookingSystemJTableLogs(new LogTableModel());
		setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(bookingSystemJTableLogs);
		addControlToPanel(jScrollPane, 0);
	}

	void addControlToPanel(Component component, int gridY) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = gridY;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;

		add(component, gbc);
	}
	public void addLogToList(Log log) {
		bookingSystemJTableLogs.addRowToList(log);
	}

	public void addLogsToList(ArrayList<Log> listOfLogs) {
		System.out.println("trying to actually add bookings to list");
		ArrayList<Object> objectArrayList = new ArrayList<>();
		for (Log log : listOfLogs) {
			objectArrayList.add(log);
		}
		bookingSystemJTableLogs.addArrayOfRowsToList(objectArrayList);
	}

	public Log getLogFromList(int logId) {
		return (Log) bookingSystemJTableLogs.getRowFromList(logId);
	}

	public int getIndexOfSelectedRow() {
		return bookingSystemJTableLogs.getSelectedRow();
	}

	public int getIDOfSelectedRow() {
		return bookingSystemJTableLogs.getIDOfSelectedRow();
	}

	public int getRowCountOfTable() {
		return bookingSystemJTableLogs.getRowCount();
	}

	public void removeLogFromTable() {
		bookingSystemJTableLogs.removeRowFromList();
	}

	public void removeAllLogs() {
		bookingSystemJTableLogs.removeAllRowsFromList();
	}
}


