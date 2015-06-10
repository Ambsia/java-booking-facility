package com.bookingsystem.view.dialogpanels.bookingdialog;

import javax.swing.JOptionPane;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

/**
 * Author: [Alex]
 */
public class UIBookingSystemShowBookingsFound extends UIBookingSystemDialogPanel {
	//private final UIBookingSystemJTableBookings bookingSystemJTable;

	/**
	 * 
	 */
	private static final long serialVersionUID = -797960503107108736L;

	public UIBookingSystemShowBookingsFound() {
		super();
		this.setLayout(getLayout());
		//bookingSystemJTable = new UIBookingSystemJTableBookings(new ArchiveTableModel());
		//JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
	//	jScrollPane.setPreferredSize(new Dimension(800,600));
	//	addTheseComponentsToPanel(new Component[]{jScrollPane}, new String[]{""});
	}

	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Bookings Found",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				new String[]{ "Close"}, "Close");
	}

//	public void addBookingsToList(ArrayList<Booking> listOfBookings)
//	{
//		ArrayList<Object> objectArrayList = new ArrayList<>();
//		for (Booking b : listOfBookings) {
//			objectArrayList.add(b);
//		}
//		bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
//	}

	//public void clearBookingsFromFoundList() {
	//	bookingSystemJTable.removeAllRowsFromList();
	//}
}
