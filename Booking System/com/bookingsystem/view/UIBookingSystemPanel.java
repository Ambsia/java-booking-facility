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

	private UIBookingSystemViewPanel bookingSystemViewPanel;
	private UIBookingSystemControlPanel bookingSystemControlPanel;
	private JButton btnAddBooking;

	private JTable jTable;
	Border outline = BorderFactory.createLineBorder(Color.black);

	BookingTableModel model;
	public UIBookingSystemPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		bookingSystemViewPanel = new UIBookingSystemViewPanel();

		gbc.insets = new Insets(10, 10, 10, 10);

		this.setBorder(outline);

		gbc.gridx= 0;
		gbc.gridy= 0;

		gbc.weightx = 0.8;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		jTable = new JTable(new BookingTableModel()) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

		};
		jTable.getTableHeader().setReorderingAllowed(false);

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
		gbc.gridheight = 2;
		jScrollPane.setBorder(outline);
		this.add(jScrollPane, gbc);

		JScrollPane jScrollPane1 = new JScrollPane(bookingSystemViewPanel);
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.weightx = .1;
		gbc.weighty = .8;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		jScrollPane1.setMinimumSize(new Dimension(100,100));
		jScrollPane1.setPreferredSize(new Dimension(100,100));
		this.add(jScrollPane1,gbc);

		bookingSystemControlPanel = new UIBookingSystemControlPanel();
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.weightx = .2;
		gbc.weighty = .2;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		bookingSystemControlPanel.setMinimumSize(new Dimension(100,100));
		bookingSystemControlPanel.setPreferredSize(new Dimension(100,100));
		bookingSystemControlPanel.setBorder(outline);

		this.add(bookingSystemControlPanel,gbc);
	}


	public UIBookingSystemViewPanel getBookingSystemViewPanel() { return bookingSystemViewPanel; }

	public UIBookingSystemControlPanel getBookingSystemControlPanel() { return bookingSystemControlPanel; }

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
