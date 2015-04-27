package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.helpers.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemDialogPanel extends JPanel implements UIBookingSystemDialogInterface {

	private final static String[] DAYS = { "" ,"Sunday", "Monday" ,"Tuesday" ,"Wednesday","Thursday", "Friday", "Saturday" };
	private final static String[] LABELS = {"Booking Day: ", "Booking Date: ", "Booking Start Time: ", "Booking Collection Time: ", "Booking Location: ", "Booking Holder: ", "Equipment: "};
	private Component[] components;
	private JTextField txtBookingDay;
	private JTextField txtBookingLocation;
	private JTextField txtBookingHolder;
	private JTextArea txtAreaEquipment;
	private JScrollPane jScrollPane;
	private JDatePickerImpl datePicker;
	private Date date = new Date();
	private SpinnerDateModel spinnerStartDateTimeModel;
	private SpinnerDateModel spinnerCollectionDateTimeModel;
	private JSpinner jSpinnerStartTime;
	private JSpinner jSpinnerCollectionTime;
	private UtilDateModel model;

	public UIBookingSystemDialogPanel() {
		txtBookingDay = new JTextField(5);
		txtBookingLocation = new JTextField(5);
		txtBookingHolder = new JTextField(5);
		txtAreaEquipment = new JTextArea(5,15);
		jScrollPane = new JScrollPane(txtAreaEquipment);
		txtBookingDay.setEditable(false);
		Time time = Time.valueOf("12:00:00");
		date.setTime(time.getTime());

		spinnerStartDateTimeModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		spinnerCollectionDateTimeModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		jSpinnerStartTime = new JSpinner(spinnerStartDateTimeModel);
		jSpinnerCollectionTime = new JSpinner(spinnerCollectionDateTimeModel);
		JSpinner.DateEditor dateEditorCollectionTime = new JSpinner.DateEditor(jSpinnerCollectionTime, "HH:mm");
		jSpinnerCollectionTime.setEditor(dateEditorCollectionTime);
		JSpinner.DateEditor dateEditorStartTime = new JSpinner.DateEditor(jSpinnerStartTime, "HH:mm");
		jSpinnerStartTime.setEditor(dateEditorStartTime);

		setLayout(new GridBagLayout());
		model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		final JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar c = Calendar.getInstance();
				try {
					Date date = new SimpleDateFormat("dd.MM.yy").parse(getFormattedDate());
					c.setTime(date);
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				txtBookingDay.setText(DAYS[dayOfWeek]);
			}
		});
	}
	public void addControlToPanel(Component component, int gridX, int gridY) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.weightx = 0;
		gbc.weighty = 0;
		add(component, gbc);
	}


	public void addDefaultComponentsToPanel() {
		components = new Component[] { txtBookingDay, datePicker, jSpinnerStartTime,jSpinnerCollectionTime,txtBookingLocation,txtBookingHolder, jScrollPane };

		for (int i = 0;i<LABELS.length;i++) {
			addControlToPanel(new JLabel(LABELS[i]), 0, i);
			addControlToPanel(components[i], 1, i);
		}
	}

	public void addTheseComponentsToPanel(Component[] components, String[] LABELS) {
		for (int i = 0;i<LABELS.length;i++) {
			addControlToPanel(new JLabel(LABELS[i]), 0, i);
			addControlToPanel(components[i], 1, i);
		}
	}
	public Component[] getComponentsAsList() {
		return components;
	}
	public String[] getLabels() {
		return LABELS;
	}
	public String getTxtAreaEquipmentText() {
		return txtAreaEquipment.getText();
	}

	public String getTxtBookingDayText() {
		return txtBookingDay.getText();
	}

	public String getTxtBookingLocationText() {
		return txtBookingLocation.getText();
	}

	public String getTxtBookingHolderText() {
		return txtBookingHolder.getText();
	}

	public String getTxtBookingStartTimeText() {
		String bookingStartTime = "";
		try {
			Date d = (Date) jSpinnerStartTime.getModel().getValue();
			bookingStartTime = "" + d.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookingStartTime;
	}

	public String getTxtBookingCollectionTimeText() {
		String bookingCollectionTime = "";
		try {
			Date date = (Date) jSpinnerCollectionTime.getModel().getValue();
			bookingCollectionTime = "" + date.getTime();
		} catch (Exception ignored) {
		}
		return bookingCollectionTime;
	}

	public String getFormattedDate() {
		String datePattern = "dd.MM.yy";
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		if (datePicker.getModel().getValue() == null) return "";
		return dateFormatter.format((Date) datePicker.getModel().getValue());
	}

	public String[] getBookingStringArray () {
		return new String[]{ getTxtBookingDayText(),  getFormattedDate(), getTxtBookingStartTimeText(),getTxtBookingCollectionTimeText(),
				getTxtBookingLocationText(), getTxtBookingHolderText(), getTxtAreaEquipmentText()
		};
	}

	public void setTextOfComponents(Object[] list) {
		txtBookingDay.setText((String)list[0]);
		model.setValue((Date)list[1]);
		spinnerStartDateTimeModel.setValue(list[2]);
		spinnerCollectionDateTimeModel.setValue(list[3]);
		txtBookingLocation.setText((String)list[4]);
		txtBookingHolder.setText((String)list[5]);
		txtAreaEquipment.setText((String)list[6]);
	}
}
