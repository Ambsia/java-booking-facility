package  com.bookingsystem.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import javax.management.ListenerNotFoundException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.TableView;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.BookingTableModel;
import com.bookingsystem.model.Equipment;

public class UIBookingSystemPanel extends JPanel {

	private JButton btnAddBooking;

	private JTable jTable;
	Border outline = BorderFactory.createLineBorder(Color.black);

	BookingTableModel model;
	public UIBookingSystemPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();


		gbc.insets = new Insets(10, 10, 10, 10);


		this.setBorder(outline);

		gbc.anchor = GridBagConstraints.NORTHWEST;

		gbc.gridx= 0;
		gbc.gridy= 0;
		gbc.ipadx = 750;
		gbc.ipady = 350;
		jTable = new JTable(new BookingTableModel()) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

		}; jTable.getTableHeader().setReorderingAllowed(false);

		jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				ArrayList<String> bookingData = new ArrayList<String>();
				for(int i = 0; i<=6;i++) {
					bookingData.add(jTable.getValueAt(jTable.getSelectedRow(),i).toString());
				}
				UIBookingSystemViewPanel.setTextToField(bookingData);

			}
		});

		model = (BookingTableModel) jTable.getModel();

		JScrollPane jScrollPane = new JScrollPane(jTable);

		jScrollPane.setBorder(outline);
		this.add(jScrollPane, gbc);
	}



	public void addSubmitListener(ActionListener ai) {
		btnAddBooking.addActionListener(ai);
	}
	
	public void addBookingToList(Booking booking, Color c) {
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
