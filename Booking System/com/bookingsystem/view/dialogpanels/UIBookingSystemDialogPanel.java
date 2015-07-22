package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.helpers.ComboBoxRenderer;
import com.bookingsystem.helpers.DateLabelFormatter;
import com.bookingsystem.helpers.TextFieldRestriction;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;



import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemDialogPanel extends JPanel implements UIBookingSystemDialogInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 479014392857523664L;
	private final static String[] DAYS = { "" ,"Sunday", "Monday" ,"Tuesday" ,"Wednesday","Thursday", "Friday", "Saturday" };
	private final static String[] LABELS = {"Booking Day: ", "Booking Date: ", "Booking Start: ", "Booking Collection: ", "Booking Location: ", "Booking Holder: ", "Equipment: ", "Recurring Booking: "};
	private Component[] components;
	private final JTextField txtBookingDay;
	private final JTextField txtBookingLocation;
	private final JTextField txtBookingHolder;
	private final JTextArea txtAreaEquipment;
	private final JComboBox<Equipment> equipmentJComboBox;
	private final JScrollPane jScrollPane;
	private final JDatePickerImpl datePicker;
	private final SpinnerDateModel spinnerStartDateTimeModel;
	private final SpinnerDateModel spinnerCollectionDateTimeModel;
	private final JSpinner jSpinnerStartTime;
	private final JSpinner jSpinnerCollectionTime;
	private final UtilDateModel model;
	private final JCheckBox chkRecurring;
	private final JTextField txtWeeksRecurring;
	private final GridBagConstraints gbc = new GridBagConstraints();
	private final JLabel l = new JLabel("Weeks: ");

	private ComboBoxRenderer cmbRenderer;
	protected UIBookingSystemDialogPanel() {
		cmbRenderer = new ComboBoxRenderer();
		
		setLayout(new GridBagLayout());

		txtBookingDay = new JTextField(5);
		txtBookingLocation = new JTextField(5);
		txtBookingHolder = new JTextField(5);
		txtAreaEquipment = new JTextArea(5,15);
		equipmentJComboBox = new JComboBox<>();
		equipmentJComboBox.setRenderer(cmbRenderer);
		jScrollPane = new JScrollPane(txtAreaEquipment);
		txtBookingDay.setEditable(false);
		chkRecurring = new JCheckBox();
		txtWeeksRecurring = new JTextField(5);
		txtWeeksRecurring.setDocument(new TextFieldRestriction());
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
					ex.printStackTrace();
				}
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				txtBookingDay.setText(DAYS[dayOfWeek]);
			}
		});
		
		chkRecurring.addActionListener(e -> {
            txtWeeksRecurring.setVisible(chkRecurring.isSelected());
            l.setVisible(chkRecurring.isSelected());
        });
	}
	private void addControlToPanel(GridBagConstraints gbcc,Component component, int gridX, int gridY) {
		gbcc.insets = new Insets(2,2,2,2);
		gbcc.gridx = gridX;
		gbcc.gridy = gridY;
		gbcc.gridwidth = 4;
		gbcc.fill = GridBagConstraints.BOTH;
		gbcc.anchor = GridBagConstraints.LAST_LINE_END;
		gbcc.weighty = 1;
		add(component, gbcc);
	}	
	
	private void addControl(GridBagConstraints gbcc, Component component,int x, int y) {
		gbcc.insets = new Insets(2,2,2,2);
		gbcc.gridx = x;
		gbcc.gridy = y;
		gbcc.gridwidth = 1;
		gbcc.anchor = GridBagConstraints.PAGE_START;
		gbcc.fill = GridBagConstraints.NONE;
		gbcc.weighty = 1;
		gbcc.weightx = .5;
		add(component, gbcc);
		component.setVisible(true);
	}


	protected void addDefaultComponentsToPanel() {
		components = new Component[] { txtBookingDay, datePicker, jSpinnerStartTime,jSpinnerCollectionTime,txtBookingLocation,txtBookingHolder, equipmentJComboBox };
		for (int i = 0;i<LABELS.length;i++) {	
			if(i == 7) { 
				addControl(gbc,new JLabel(LABELS[7]), 0,7);
				addControl(gbc,chkRecurring,1,7);
				addControl(gbc,l,3,7);
				addControl(gbc,txtWeeksRecurring,4,7);
			}	else { 
				addControlToPanel(gbc,new JLabel(LABELS[i]), 0, i);
				addControlToPanel(gbc,components[i], 1, i);
			}
		}
		revalidate();
	}

// --Commented out by Inspection START (21/06/2015 00:50):
//	protected void addTheseComponentsToPanel(Component[] components, String[] LABELS) {
//		for (int i = 0;i<LABELS.length;i++) {
//			addControlToPanel(gbc,new JLabel(LABELS[i]), 0, i);
//			addControlToPanel(gbc,components[i], 1, i);
//		}
//
//	}
// --Commented out by Inspection STOP (21/06/2015 00:50)

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
		txtWeeksRecurring.setVisible(false);
		l.setVisible(false);
		chkRecurring.setSelected(false);
		revalidate();
	} 
	protected Component[] getComponentsAsList() {
		return components;
	}
// --Commented out by Inspection START (21/06/2015 00:50):
//	public String[] getLabels() {
//		return LABELS;
//	}
// --Commented out by Inspection STOP (21/06/2015 00:50)
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

	public void setEquipmentJComboBox(List<Equipment> equipments) {
		equipmentJComboBox.removeAllItems();
		this.cmbRenderer.removeAll();
		System.out.println(equipmentJComboBox.getItemCount());

		equipments.forEach(equipmentJComboBox::addItem);
		
		ArrayList<String> tooltips = new ArrayList<String>();
		for (Equipment e : equipments) {
			tooltips.add(e.getEquipmentDescription());
		}
		
		this.cmbRenderer.setTooltips(tooltips);
	}

	public void addEquipmentToComboBox(Equipment equipment) {
		equipmentJComboBox.addItem(equipment);
		this.cmbRenderer.addTooltip(equipment.getEquipmentDescription());
	}


	public void addEquipmentListToComboBox(List<Equipment> equipmentList) {
		equipmentList.forEach(equipmentJComboBox::addItem);
		ArrayList<String> tooltips = new ArrayList<String>();
//	for (Equipment e : equipmentList) {
	//		this.cmbRenderer.addTooltip(e.getEquipmentDescription());
	//	}

	}

	public void replaceEquipment(Equipment equipment) {
		for (int i = 0; i<equipmentJComboBox.getItemCount();i++) {
			if (equipmentJComboBox.getItemAt(i).getEquipmentID() == equipment.getEquipmentID()) {
				equipmentJComboBox.getItemAt(i).setEquipmentName(equipment.getEquipmentName());
				equipmentJComboBox.getItemAt(i).setEquipmentDescription(equipment.getEquipmentDescription());
				equipmentJComboBox.getItemAt(i).setEquipmentUsage(equipment.getEquipmentUsage());
				//this.cmbRenderer.replaceToolTip(i,equipment.getEquipmentDescription());
			}
		}
	}



	public void removeEquipmentFromComboBox(int equipmentID) {
		for (int i = 0; i<equipmentJComboBox.getItemCount();i++) {
			if (equipmentJComboBox.getItemAt(i).getEquipmentID() == equipmentID) {
				equipmentJComboBox.removeItemAt(i);
				this.cmbRenderer.removeToolTip(i);
			}
		}
	}



	public Equipment getSelectedEquipment() {
		return equipmentJComboBox.getItemAt(equipmentJComboBox.getSelectedIndex());
	}
	
	
	public boolean getRecurringSelected() {
		return chkRecurring.isSelected();
	}
	
	public int getWeeksRecurringFor() {
		if(chkRecurring.isSelected()) {
			try { 
			return Integer.parseInt(txtWeeksRecurring.getText());
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
				getTxtBookingLocationText(), getTxtBookingHolderText(), Integer.toString(getSelectedEquipment().getEquipmentID())};
	}

	public void setTextOfComponents(Object list) {
		Booking booking = (Booking) list;
		txtBookingDay.setText(booking.getBookingDay());
		model.setValue(booking.getBookingDate());
		spinnerStartDateTimeModel.setValue(booking.getBookingStartTime());
		spinnerCollectionDateTimeModel.setValue(booking.getBookingCollectionTime());
		txtBookingLocation.setText(booking.getBookingLocation());
		txtBookingHolder.setText(booking.getBookingHolder());
		equipmentJComboBox.setSelectedItem(booking.getRequiredEquipment());
	}
}