package com.bookingsystem.view.dialogpanels;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.bookingsystem.helpers.DateLabelFormatter;
import com.bookingsystem.helpers.TextFieldRestriction;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemDialogPanel extends JPanel implements UIBookingSystemDialogInterface {

	private final static String[] DAYS = { "" ,"Sunday", "Monday" ,"Tuesday" ,"Wednesday","Thursday", "Friday", "Saturday" };
	private final static String[] LABELS = {"Booking Day: ", "Booking Date: ", "Booking Start: ", "Booking Collection: ", "Booking Location: ", "Booking Holder: ", "Equipment: ", "Recuring Booking: "};
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
	private final JCheckBox chkRecuring;
	private final JTextField txtWeeksRecuring;
	GridBagConstraints gbc = new GridBagConstraints();
	JLabel l = new JLabel("Weeks: ");
	protected UIBookingSystemDialogPanel() {
		setLayout(new GridBagLayout());

		txtBookingDay = new JTextField(5);
		txtBookingLocation = new JTextField(5);
		txtBookingHolder = new JTextField(5);
		txtAreaEquipment = new JTextArea(5,15);
		jScrollPane = new JScrollPane(txtAreaEquipment);
		txtBookingDay.setEditable(false);
		chkRecuring = new JCheckBox();
		txtWeeksRecuring = new JTextField(5);
		txtWeeksRecuring.setDocument(new TextFieldRestriction());
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
		
		chkRecuring.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtWeeksRecuring.setVisible(chkRecuring.isSelected());
				l.setVisible(chkRecuring.isSelected());
			}
		});
	}
	private void addControlToPanel(GridBagConstraints gbcc,Component component, int gridX, int gridY) {
		GridBagConstraints gbc = gbcc;
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.weighty = 1;
		add(component, gbc);
	}
	
	private void addControl(GridBagConstraints gbcc, Component component,int x, int y) {
		GridBagConstraints gbc = gbcc;
		gbc.insets = new Insets(2,2,2,2);
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weighty = 1;
		gbc.weightx = .5;
		add(component, gbc);
		component.setVisible(true);
	}


	protected void addDefaultComponentsToPanel() {
		components = new Component[] { txtBookingDay, datePicker, jSpinnerStartTime,jSpinnerCollectionTime,txtBookingLocation,txtBookingHolder, jScrollPane };
		for (int i = 0;i<LABELS.length;i++) {	
			if(i == 7) { 
				addControl(gbc,new JLabel(LABELS[7]), 0,7);
				addControl(gbc,chkRecuring,1,7);
				addControl(gbc,l,3,7);
				addControl(gbc,txtWeeksRecuring,4,7);
			}	else { 
				addControlToPanel(gbc,new JLabel(LABELS[i]), 0, i);
				addControlToPanel(gbc,components[i], 1, i);
			}
		}
		revalidate();
	}

	protected void addTheseComponentsToPanel(Component[] components, String[] LABELS) {
		for (int i = 0;i<LABELS.length;i++) {
			addControlToPanel(gbc,new JLabel(LABELS[i]), 0, i);
			addControlToPanel(gbc,components[i], 1, i);
		}

	}
	
	public void clearInputs() {
		txtBookingDay.setText("");
		Time time = Time.valueOf("12:00:00");
		Date date = new Date();
		date.setTime(time.getTime());
		model.setValue(null);
		jSpinnerStartTime.setValue(date);
		jSpinnerCollectionTime.setValue(date);
		txtBookingLocation.setText("");
		txtBookingHolder.setText("");
		txtAreaEquipment.setText("");
		txtWeeksRecuring.setVisible(false);
		l.setVisible(false);
		chkRecuring.setSelected(false);
		revalidate();
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
	
	public boolean getRecuringSelected() {
		return chkRecuring.isSelected();
	}
	
	public int getWeeksRecuringFor() {
		if(chkRecuring.isSelected()) {
			try { 
			return Integer.parseInt(txtWeeksRecuring.getText());
			} catch (NumberFormatException nfe) {
				return 0;
			}
		}
		return 0;
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
