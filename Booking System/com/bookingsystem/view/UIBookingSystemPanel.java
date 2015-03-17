package  com.bookingsystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.BookingTableModel;
import com.bookingsystem.model.Equipment;


public class UIBookingSystemPanel extends JPanel {

	private UIBookingSystemViewPanel bookingSystemViewPanel;
	private UIBookingSystemControlPanel bookingSystemControlPanel;
	private JButton btnAddBooking;

	private JTable jTable;

	private DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
	private static DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	private BookingTableModel model;
	public UIBookingSystemPanel() {


		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		bookingSystemViewPanel = new UIBookingSystemViewPanel();
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx= 0;
		gbc.gridy= 0;

		gbc.weightx = 0.6;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		jTable = new JTable(new BookingTableModel()) {
			private static final long serialVersionUID = 1L;

			@Override
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

		this.add(jScrollPane, gbc);

		JScrollPane jScrollPane1 = new JScrollPane(bookingSystemViewPanel);
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .9;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		jScrollPane1.setMinimumSize(new Dimension(100,100));
		jScrollPane1.setPreferredSize(new Dimension(100,100));
		this.add(jScrollPane1,gbc);

		bookingSystemControlPanel = new UIBookingSystemControlPanel();
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.weightx = .4;
		gbc.weighty = .1;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		bookingSystemControlPanel.setMinimumSize(new Dimension(100,100));
		bookingSystemControlPanel.setPreferredSize(new Dimension(100,100));

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
				BOOKING_DATE_FORMAT.format(booking.getBookingDate()),
				BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()),
				booking.getBookingLocation(),
				booking.getBookingHolder(),
				booking.getRequiredEquipment().GetEquipmentName()});

	}

	public void addBookingsToList(List<Booking> listOfBookings) {
		for (Booking booking : listOfBookings) {
			model.addRow(new Object[]{booking.getBookingID(),
					booking.getBookingDay(),
					BOOKING_DATE_FORMAT.format(booking.getBookingDate()),
					BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()),
					booking.getBookingLocation(),
					booking.getBookingHolder(),
					booking.getRequiredEquipment().GetEquipmentName()});
		}
	}
	
	public Booking getBookingFromList(int bookingId) {
		if (bookingId >= 0 && bookingId != (int) model.getValueAt(0, bookingId)) {
			return new Booking((int) model.getValueAt(0, bookingId),
					(String) model.getValueAt(1, bookingId),
					(Date) model.getValueAt(2, bookingId),
					(Date) model.getValueAt(3, bookingId),
					(Date) model.getValueAt(3, bookingId),
					(String) model.getValueAt(4, bookingId),
					(String) model.getValueAt(5, bookingId),
					(Equipment) model.getValueAt(6, bookingId) );

		}
		else return null;
	}



}
