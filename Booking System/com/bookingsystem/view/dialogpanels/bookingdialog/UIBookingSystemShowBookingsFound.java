package com.bookingsystem.view.dialogpanels.bookingdialog;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.ArchiveTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookings;
import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

/**
 * Author: [Alex]
 */
public class UIBookingSystemShowBookingsFound extends UIBookingSystemDialogPanel {
	private final UIBookingSystemJTableBookings bookingSystemJTable;

	public UIBookingSystemShowBookingsFound() {
		super();
		this.setLayout(getLayout());
		bookingSystemJTable = new UIBookingSystemJTableBookings(new ArchiveTableModel());
		JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
		jScrollPane.setPreferredSize(new Dimension(800,600));
		addTheseComponentsToPanel(new Component[]{jScrollPane}, new String[]{""});
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Bookings Found",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[]{ "Close"}, "Close");
	}

	public void addBookingsToList(ArrayList<Booking> listOfBookings)
	{
		ArrayList<Object> objectArrayList = new ArrayList<>();
		for (Booking b : listOfBookings) {
			objectArrayList.add(b);
		}
		bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
	}

	public void clearBookingsFromFoundList() {
		bookingSystemJTable.removeAllRowsFromList();
	}
}
