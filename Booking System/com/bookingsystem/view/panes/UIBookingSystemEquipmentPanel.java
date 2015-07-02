package com.bookingsystem.view.panes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.view.panelparts.UIBookingSystemBookingViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;

public class UIBookingSystemEquipmentPanel extends JPanel {

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

	/**
	 * 
	 */
	//private final UIBookingSystemBookingEquipmentViewPanel bookingSystemEquipmentViewPanel;
	private final UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel;
	//private final UIBookingSystemJTableBookings bookingSystemJTable;
	//private BookingTableModel model;
	
	private final JTable bookingSystemEquipmentTable;
	private EquipmentTableModel equipmentTableModel;
	public UIBookingSystemEquipmentPanel() {
	//	bookingSystemJTable = new UIBookingSystemJTableBookings(bookingSystemModel);

		bookingSystemEquipmentTable = new JTable(equipmentTableModel);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//bookingSystemViewPanel = new UIBookingSystemBookingViewPanel();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;

//		bookingSystemJTable.getColumn("Day").setMaxWidth(85);
//		bookingSystemJTable.getColumn("Date").setMaxWidth(80);
		JScrollPane jScrollPane = new JScrollPane(bookingSystemEquipmentTable);
		gbc.gridheight = 2;
		this.add(jScrollPane, gbc);
		//JScrollPane jScrollPane1 = new JScrollPane(bookingSystemViewPanel);
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.weightx = .8;
		gbc.weighty = .9;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		//jScrollPane1.setMinimumSize(new Dimension(100, 100));
		//jScrollPane1.setPreferredSize(new Dimension(100, 100));
		//this.add(jScrollPane1, gbc);
		bookingSystemEquipmentControlPanel = new UIBookingSystemEquipmentControlPanel();
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .1;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		bookingSystemEquipmentControlPanel.setMinimumSize(new Dimension(100, 100));
		bookingSystemEquipmentControlPanel.setPreferredSize(new Dimension(100, 100));
		this.add(bookingSystemEquipmentControlPanel, gbc);

	}
	public void setJTableModel(EquipmentTableModel equipmentTableModel) {
		this.equipmentTableModel = equipmentTableModel;
		this.bookingSystemEquipmentTable.setModel(this.equipmentTableModel);
		this.bookingSystemEquipmentTable.setAutoCreateRowSorter(true);
		bookingSystemEquipmentTable.getColumn("Equipment ID").setMinWidth(25);
		bookingSystemEquipmentTable.getColumn("Equipment ID").setMaxWidth(25);
		bookingSystemEquipmentTable.getColumn("Equipment ID").setPreferredWidth(25);
	}

	public JTable getBookingSystemEquipmentTable() {
		return bookingSystemEquipmentTable;
	}

//	public BookingTableModel getJTableModel() {
//		return this.bookingSystemModel;
//	}
//
//	public void changeSelection(int row) {
//		bookingSystemJTable.changeSelection(row,0,false,false);
//		bookingSystemJTable.repaint();
//	}
//	public int selectedRowCount() {
//		return bookingSystemJTable.getSelectedRowCount();
//	}
//
//	public int rowViewIndexToModel(int row) {
//		return bookingSystemJTable.convertRowIndexToModel(row);
//	}
//
//	public Object getValueAt(int row, int column) {
//		return bookingSystemModel.getValueAt(row,column);
//	}
//
//	public int returnRowIndexForValue(final int j) {
//		for (int i = 0; i <= bookingSystemJTable.getRowCount(); i++)
//			if (bookingSystemJTable.getValueAt(i, 0).equals(j))
//				return i;
//		return -1;
//	}
//
//	public List<Integer> getSelectedRows() {
//		List<Integer> integerList = new ArrayList<>();
//		for (int i = 0; i<bookingSystemJTable.getSelectedRows().length;i++) {
//			integerList.add(rowViewIndexToModel(bookingSystemJTable.getSelectedRows()[i]));
//		}
//		return integerList;
//	}
//
//	public int getSelectedRow() {
//		return bookingSystemJTable.getSelectedRow();
//	}
//
	public UIBookingSystemEquipmentControlPanel getBookingSystemEquipmentControlPanel() {return this.bookingSystemEquipmentControlPanel;}

}
