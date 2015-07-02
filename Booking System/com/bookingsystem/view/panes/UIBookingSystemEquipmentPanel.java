package com.bookingsystem.view.panes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.panelparts.UIBookingSystemBookingViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;

public class UIBookingSystemEquipmentPanel extends JPanel {

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

	/**
	 * 
	 */
	private final UIBookingSystemBookingEquipmentViewPanel bookingSystemEquipmentViewPanel;
	private final UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel;
	//private final UIBookingSystemJTableBookings bookingSystemJTable;
	//private BookingTableModel model;
	
	private final JTable bookingSystemJTable;
	private BookingTableModel bookingSystemModel;
	public UIBookingSystemEquipmentPanel() {
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
		UIBookingSystemEquipmentControlPanel = new UIBookingSystemBookingControlPanel();
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .1;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		UIBookingSystemEquipmentControlPanel.setMinimumSize(new Dimension(100, 100));
		UIBookingSystemEquipmentControlPanel.setPreferredSize(new Dimension(100, 100));
		this.add(UIBookingSystemEquipmentControlPanel, gbc);

	}

}
