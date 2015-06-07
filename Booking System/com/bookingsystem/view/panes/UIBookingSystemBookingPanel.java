package com.bookingsystem.view.panes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.panelparts.UIBookingSystemBookingViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;


public class UIBookingSystemBookingPanel extends JPanel {


	private final UIBookingSystemBookingViewPanel bookingSystemViewPanel;
	private final UIBookingSystemBookingControlPanel bookingSystemControlPanel;
	//private final UIBookingSystemJTableBookings bookingSystemJTable;
	//private BookingTableModel model;
	
	private JTable bookingSystemJTable;
	private BookingTableModel bookingSystemModel;
	public UIBookingSystemBookingPanel() {
	//	bookingSystemJTable = new UIBookingSystemJTableBookings(bookingSystemModel);

		bookingSystemJTable = new JTable(bookingSystemModel);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		bookingSystemViewPanel = new UIBookingSystemBookingViewPanel();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.6;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
//		bookingSystemJTable.getColumn("Booking ID").setMinWidth(0);
//		bookingSystemJTable.getColumn("Booking ID").setMaxWidth(0);
//		bookingSystemJTable.getColumn("Booking ID").setPreferredWidth(0);
//		bookingSystemJTable.getColumn("Day").setMaxWidth(85);
//		bookingSystemJTable.getColumn("Date").setMaxWidth(80);
		JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
		gbc.gridheight = 2;
		this.add(jScrollPane, gbc);
		JScrollPane jScrollPane1 = new JScrollPane(bookingSystemViewPanel);
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .9;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		jScrollPane1.setMinimumSize(new Dimension(100, 100));
		jScrollPane1.setPreferredSize(new Dimension(100, 100));
		this.add(jScrollPane1, gbc);
		bookingSystemControlPanel = new UIBookingSystemBookingControlPanel();
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .1;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		bookingSystemControlPanel.setMinimumSize(new Dimension(100, 100));
		bookingSystemControlPanel.setPreferredSize(new Dimension(100, 100));
		this.add(bookingSystemControlPanel, gbc);

	}

	public UIBookingSystemBookingViewPanel getBookingSystemViewPanel() { return bookingSystemViewPanel; }

	public UIBookingSystemBookingControlPanel getBookingSystemControlPanel() { return bookingSystemControlPanel; }

	public void setJTableModel(BookingTableModel bookingTableModel) {
		this.bookingSystemModel = bookingTableModel;
		this.bookingSystemJTable.setModel(this.bookingSystemModel);
		this.bookingSystemJTable.setAutoCreateRowSorter(true);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(bookingSystemModel);
		bookingSystemJTable.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexToSort = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		this.bookingSystemModel.fireTableDataChanged();
	}

	public BookingTableModel getJTableModel() {
		return this.bookingSystemModel;
	}

	public int selectedRowCount() {
		return bookingSystemJTable.getSelectedRowCount();
	}

	public int rowViewIndexToModel(int row) {
		return bookingSystemJTable.convertRowIndexToModel(row);
	}

	public Object getValueAt(int row, int column) {
		return bookingSystemModel.getValueAt(row,column);
	}

	public List<Integer> getSelectedRows() {
		List<Integer> integerList = new ArrayList<>();
		for (int i = 0; i<bookingSystemJTable.getSelectedRows().length;i++) {
			integerList.add(rowViewIndexToModel(bookingSystemJTable.getSelectedRows()[i]));
		}
		return integerList;
	}

	public int getSelectedRow() {
		return bookingSystemJTable.getSelectedRow();
	}
}
