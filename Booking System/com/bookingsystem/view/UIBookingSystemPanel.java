package  com.bookingsystem.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import com.bookingsystem.model.Booking;

public class UIBookingSystemPanel extends JPanel {

	private JButton btnAddBooking;
	private DefaultListModel<Booking> listBoxOfBookings;
	private JList<Booking> jList;

	public UIBookingSystemPanel() {
		Border outline = BorderFactory.createLineBorder(Color.black);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		listBoxOfBookings = new DefaultListModel<Booking>();
		jList = new JList<Booking>(listBoxOfBookings);

		JScrollPane jScrollPane = new JScrollPane(jList);
		jList.setPreferredSize(new Dimension(500,200));
		jScrollPane.setPreferredSize(jList.getPreferredSize());
		btnAddBooking = new JButton("Add");

    	this.setBorder(outline);


		gbc.insets = new Insets(2, 2, 10, 2);
		jScrollPane.setBorder(outline);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.ipadx = 30;
		gbc.ipady = 10;
		gbc.weightx = 1;

		this.add(jScrollPane, gbc);
		//this.add(btnAddBooking);
	}


	public void addSubmitListener(ActionListener ai) {
		btnAddBooking.addActionListener(ai);
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
