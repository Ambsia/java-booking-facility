package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

/**
 * Author: [Alex]
 */
public class LogTableModel extends DefaultTableModel {
	public LogTableModel() {
		addColumn("ID");
		addColumn("Event");
		addColumn("Class");
		addColumn("Timestamp");
		addColumn("ID Modified");
	}
}
