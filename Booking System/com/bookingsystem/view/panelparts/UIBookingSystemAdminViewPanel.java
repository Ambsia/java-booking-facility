package com.bookingsystem.view.panelparts;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.LogTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableLogs;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminViewPanel extends JPanel {
	private UIBookingSystemJTableLogs bookingSystemJTableLogs;
	private JScrollPane jScrollPane;
	public UIBookingSystemAdminViewPanel() {
		bookingSystemJTableLogs = new UIBookingSystemJTableLogs(new LogTableModel());
		setLayout(new GridBagLayout());
		jScrollPane = new JScrollPane(bookingSystemJTableLogs);
		bookingSystemJTableLogs.getColumn("ID").setPreferredWidth(40);

		bookingSystemJTableLogs.getColumn("Event").setPreferredWidth(80);

		bookingSystemJTableLogs.getColumn("ID Modified").setPreferredWidth(40);
		addControlToPanel(jScrollPane);
	}

	private void addControlToPanel(Component component) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
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


