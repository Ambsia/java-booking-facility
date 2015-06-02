package com.bookingsystem.view.panes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookings;
import com.bookingsystem.view.panelparts.UIBookingSystemBookingViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;


public class UIBookingSystemBookingPanel extends JPanel {


	private final UIBookingSystemBookingViewPanel bookingSystemViewPanel;
	private final UIBookingSystemBookingControlPanel bookingSystemControlPanel;
	private final UIBookingSystemJTableBookings bookingSystemJTable;
	private BookingTableModel model;
	public UIBookingSystemBookingPanel() {
		bookingSystemJTable = new UIBookingSystemJTableBookings(new BookingTableModel());

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


		bookingSystemJTable.getColumn("Booking ID").setMinWidth(0);
		bookingSystemJTable.getColumn("Booking ID").setMaxWidth(0);
		bookingSystemJTable.getColumn("Booking ID").setPreferredWidth(0);
		bookingSystemJTable.getColumn("Day").setMaxWidth(85);
		bookingSystemJTable.getColumn("Date").setMaxWidth(80);


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

	public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
		return bookingSystemJTable.getSelectedRowAsStringArrayList();
	}
	
	public void addBookingToList(Booking booking) {
		bookingSystemJTable.addRowToList(booking);
	}

	public void addBookingsToList(ArrayList<Booking> listOfBookings) {
		ArrayList<Object> objectArrayList = new ArrayList<>();
		for (Booking b : listOfBookings) {
			objectArrayList.add(b);
		}
		bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
	}
	
	public Booking getBookingFromList(int bookingId) {
		return (Booking) bookingSystemJTable.getRowFromList(bookingId);
	}

	public int getIndexOfSelectedRow() {
		return bookingSystemJTable.getSelectedRow();
	}

	public int getIDOfSelectedRow() {
		return bookingSystemJTable.getIDOfSelectedRow();
	}

	public int getRowCountOfTable() {
		return bookingSystemJTable.getRowCount();
	}

	public void replaceBookingInList(Booking newBooking) {
		bookingSystemJTable.replaceRowInList(newBooking);
	}

	public void removeBookingFromTable() {
		bookingSystemJTable.removeRowFromList();
	}

	public void removeAllBookings() {
		bookingSystemJTable.removeAllRowsFromList();
	}

	public UIBookingSystemBookingViewPanel getBookingSystemViewPanel() { return bookingSystemViewPanel; }

	public UIBookingSystemBookingControlPanel getBookingSystemControlPanel() { return bookingSystemControlPanel; }

	public int selectedRowCount() {
		return bookingSystemJTable.getSelectedRowCount();
	}

	public void removeRow(int row) {
		bookingSystemJTable.removeRowFromList();
	}

	public void removeSelectedRowsFromList() {
		bookingSystemJTable.removeSelectedRowsFromList();
	}

	public int getIDWithIndex(int index) { return bookingSystemJTable.getIDWithIndex(index); }

	public int[] getSelectedRows() {
		return bookingSystemJTable.getSelectedRows();
	}

}
