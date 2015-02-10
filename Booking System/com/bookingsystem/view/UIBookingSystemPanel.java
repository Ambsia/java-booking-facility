package  com.bookingsystem.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.BookingTableModel;

public class UIBookingSystemPanel extends JPanel {

	private JButton btnAddBooking;

	private JTable jTable;

	BookingTableModel model;
	UIBookingSystemViewPanel uiBookingSystemViewPanel;
	public UIBookingSystemPanel() {

		uiBookingSystemViewPanel = new UIBookingSystemViewPanel();

		Border outline = BorderFactory.createLineBorder(Color.black);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 10, 10, 10);


		this.setBorder(outline);

		gbc.anchor = GridBagConstraints.NORTHWEST;

		gbc.weightx = 0.8;
		gbc.weighty = 0.8;
		gbc.gridx= 0;
		gbc.gridy= 0;
		jTable = new JTable(new BookingTableModel()) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		model = (BookingTableModel) jTable.getModel();

		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(800,300));

		jScrollPane.setBorder(outline);
		this.add(jScrollPane, gbc);
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx++;
		this.add(uiBookingSystemViewPanel, gbc);
	}

	public void initialiseBookingTable() {


	}


	public void addSubmitListener(ActionListener ai) {
		btnAddBooking.addActionListener(ai);
	}
	
	public void addBookingToList(Booking booking) {
		model.addRow(new Object[]{booking.getBookingID(),
				booking.getBookingDay(),
				booking.getBookingDate(),
				booking.getBookingTime(),
				booking.getBookingLocation(),
				booking.getBookingHolder(),
				booking.getRequiredEquipment().GetEquipmentName()});

	}

	public void addBookingsToList(List<Booking> listOfBookings) {

		for (Booking booking : listOfBookings) {
			model.addRow(new Object[]{booking.getBookingID(),
					booking.getBookingDay(),
					booking.getBookingDate(),
					booking.getBookingTime(),
					booking.getBookingLocation(),
					booking.getBookingHolder(),
					booking.getRequiredEquipment().GetEquipmentName()});
		}
	}



}
