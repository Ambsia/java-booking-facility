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
	private final JTextField txtBookingDay;
	private final JTextField txtBookingLocation;
	private final JTextField txtBookingHolder;
	private final JTextArea txtAreaEquipment;
	private final JScrollPane jScrollPane;
	private final JDatePickerImpl datePicker;
	private final SpinnerDateModel spinnerStartDateTimeModel;
	private final SpinnerDateModel spinnerCollectionDateTimeModel;
	private final JSpinner jSpinnerStartTime;
	private final JSpinner jSpinnerCollectionTime;
	private final UtilDateModel model;

	protected UIBookingSystemDialogPanel() {
		txtBookingDay = new JTextField(5);
		txtBookingLocation = new JTextField(5);
		txtBookingHolder = new JTextField(5);
		txtAreaEquipment = new JTextArea(5,15);
		jScrollPane = new JScrollPane(txtAreaEquipment);
		txtBookingDay.setEditable(false);
		Time time = Time.valueOf("12:00:00");
		Date date = new Date();
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
				}
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				txtBookingDay.setText(DAYS[dayOfWeek]);
			}
		});
	}
	private void addControlToPanel(Component component, int gridX, int gridY) {
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


	protected void addDefaultComponentsToPanel() {
		components = new Component[] { txtBookingDay, datePicker, jSpinnerStartTime,jSpinnerCollectionTime,txtBookingLocation,txtBookingHolder, jScrollPane };

		for (int i = 0;i<LABELS.length;i++) {
			addControlToPanel(new JLabel(LABELS[i]), 0, i);
			addControlToPanel(components[i], 1, i);
		}
	}

	protected void addTheseComponentsToPanel(Component[] components, String[] LABELS) {
		for (int i = 0;i<LABELS.length;i++) {
			addControlToPanel(new JLabel(LABELS[i]), 0, i);
			addControlToPanel(components[i], 1, i);
		}
	}
	protected Component[] getComponentsAsList() {
		return components;
	}
	public String[] getLabels() {
		return LABELS;
	}
	private String getTxtAreaEquipmentText() {
		return txtAreaEquipment.getText();
	}

	private String getTxtBookingDayText() {
		return txtBookingDay.getText();
	}

	private String getTxtBookingLocationText() {
		return txtBookingLocation.getText();
	}

	private String getTxtBookingHolderText() {
		return txtBookingHolder.getText();
	}

	private String getTxtBookingStartTimeText() {
		String bookingStartTime = "";
		try {
			Date d = (Date) jSpinnerStartTime.getModel().getValue();
			bookingStartTime = "" + d.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookingStartTime;
	}

	private String getTxtBookingCollectionTimeText() {
		String bookingCollectionTime = "";
		try {
			Date date = (Date) jSpinnerCollectionTime.getModel().getValue();
			bookingCollectionTime = "" + date.getTime();
		} catch (Exception ignored) {
		}
		return bookingCollectionTime;
	}

	private String getFormattedDate() {
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
