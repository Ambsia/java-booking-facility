package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookings;
import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class UIBookingSystemShowBookingsFound extends UIBookingSystemDialogPanel {
	private UIBookingSystemJTableBookings bookingSystemJTable;
	private JScrollPane jScrollPane;

	public UIBookingSystemShowBookingsFound() {
		super();
		bookingSystemJTable = new UIBookingSystemJTableBookings(new BookingTableModel());
		jScrollPane = new JScrollPane(bookingSystemJTable);
		addTheseComponentsToPanel(new Component[] { jScrollPane },new String[] { ""});
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Bookings Found",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[]{ "Close"}, "Close");
	}

	public void addBookingsToList(ArrayList<Booking> listOfBookings)
	{
		ArrayList<Object> objectArrayList = new ArrayList<Object>();
		for (Booking b : listOfBookings) {
			objectArrayList.add(b);
		}
		bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
	}

	public void clearBookingsFromFoundList() {
		bookingSystemJTable.removeAllRowsFromList();
	}
}
