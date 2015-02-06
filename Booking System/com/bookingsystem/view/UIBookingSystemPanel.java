package  com.bookingsystem.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.bookingsystem.model.Booking;

public class UIBookingSystemPanel extends JPanel {

	private JButton btnAddBooking;
	private DefaultListModel<Booking> listBoxOfBookings;
	private JList<Booking> jList;


	public UIBookingSystemPanel() {


		listBoxOfBookings = new DefaultListModel<Booking>();
		jList = new JList<Booking>(listBoxOfBookings);

		JScrollPane jScrollPane = new JScrollPane(jList);

		btnAddBooking = new JButton("import");

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 10, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;

		this.add(jScrollPane);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		this.add(btnAddBooking);
	}


	public void addSubmitListener(ActionListener ai) {
		btnAddBooking.addActionListener(ai);
	}
	
	public void addBookingToList(Booking booking) {
		System.out.println(booking.toString());
		listBoxOfBookings.addElement(booking);
	}

	public void addBookingsToList(List<Booking> listOfBookings) {
		for (Booking booking : listOfBookings) {
			listBoxOfBookings.addElement(booking);
		}
	}

}
