package com.bookingsystem.view.panes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.panelparts.UIBookingSystemBookingViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;


public class UIBookingSystemBookingPanel extends JPanel {

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3425259203566131546L;
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
		
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
		sorter.setComparator(2, new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
		        try {
			        if (arg0.equals("Unknown") && !arg1.equals("Unknown")) {
			        	return -1;
			        } else if (arg1.equals("Unknown") && !arg0.equals("Unknown")) { 
			        	return 1;
			        } else if (arg0.equals("Unknown") && arg1.equals("Unknown")) {
			        	return 0;
			        } else {
			            return BOOKING_DATE_FORMAT.parse((String) arg0).compareTo((BOOKING_DATE_FORMAT.parse((String) arg1)));
			        }
		        } catch (ParseException p) {
		        	System.out.println("time");
		        	return 0;
		        }
			}
		});
		
		sorter.setComparator(3, new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				Date d1 =null;
				Date d2 = null;
				
				   if (arg0.equals("Unknown") && !arg1.equals("Unknown")) {
			        	return -1;
			        } else if (arg1.equals("Unknown") && !arg0.equals("Unknown")) { 
			        	return 1;
			        } else if (arg0.equals("Unknown") && arg1.equals("Unknown")) {
			        	return 0;
			        } else {
			        	
						String[] s1ARRAY = (String[]) ((String) arg0).split("-"); //first argument split
						String s1 = s1ARRAY[0];
						
						String[] s2ARRAY = (String[]) ((String) arg1).split("-"); //second argument
						String s2 = s2ARRAY[0];

						try {
							 d1 = BOOKING_TIME_FORMAT.parse(s1);
							 d2 = BOOKING_TIME_FORMAT.parse(s2);
						} catch (ParseException e) {
							 Calendar date = Calendar.getInstance();
			                    date.set(Calendar.AM_PM, Calendar.AM);
			                    date.set(Calendar.HOUR, 00);
			                    date.set(Calendar.MINUTE, 00);
			                   date.set(Calendar.SECOND, 00);
			                   date.set(Calendar.MILLISECOND, 0);
			                   d1= date.getTime();
			                   d2= date.getTime();
						}
						
						return d1.compareTo(d2);
			           
			}
			}
		});
		bookingSystemJTable.getColumn("Booking ID").setMinWidth(0);
		bookingSystemJTable.getColumn("Booking ID").setMaxWidth(0);
      	bookingSystemJTable.getColumn("Booking ID").setPreferredWidth(0);
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
