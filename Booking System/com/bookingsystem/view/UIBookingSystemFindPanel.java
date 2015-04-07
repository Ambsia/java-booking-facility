package com.bookingsystem.view;

import com.bookingsystem.helpers.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

public class UIBookingSystemFindPanel extends UIBookingSystemAddPanel {

	public UIBookingSystemFindPanel() {
		this.getTxtBookingDay().setEditable(true);
	}


	@Override
	public String getFormattedDate() {
		return super.getFormattedDate();
	}
	@Override
	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Find Booking",
				JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION,null,
				new String[] {"Find", "Cancel" }, "Add");
	}

}