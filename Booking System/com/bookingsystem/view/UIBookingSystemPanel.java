package  com.bookingsystem.view;

import com.bookingsystem.model.Booking;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UIBookingSystemPanel extends JPanel {

	private JButton btnAddBooking;
	private DefaultListModel<Booking> listBoxOfBookings;
	private JList<Booking> jList;

	public UIBookingSystemPanel() {
		listBoxOfBookings = new DefaultListModel<Booking>();
		jList = new JList<Booking>(listBoxOfBookings);
		btnAddBooking = new JButton();

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 10, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;

		this.add(jList);
	}



	public void addBookingToList(Booking booking) {
		listBoxOfBookings.addElement(booking);
	}

	public void addBookingsToList(List<Booking> listOfBookings) {
		for (Booking booking : listOfBookings) {
			listBoxOfBookings.addElement(booking);
		}
	}

}
