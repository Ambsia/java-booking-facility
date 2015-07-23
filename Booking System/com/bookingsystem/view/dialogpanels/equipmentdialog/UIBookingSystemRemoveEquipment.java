package com.bookingsystem.view.dialogpanels.equipmentdialog;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import com.bookingsystem.helpers.TextFieldRestriction;

public class UIBookingSystemRemoveEquipment extends JPanel {

	private JLabel lblWarningMessage;
	public static String newline = System.getProperty("line.separator");

	public UIBookingSystemRemoveEquipment() {
		Font f = new Font("Arial", Font.PLAIN, 14);
		lblWarningMessage = new JLabel();
		lblWarningMessage.setFont(f);
		lblWarningMessage
				.setText("Are you sure you want to remove the selected equipment?"
						+ " If existing bookings require this equipment, they will be removed.");

		// setLayout(new GridBagLayout());
		this.add(lblWarningMessage);
	}

	public int showDialog() {
		return JOptionPane.showOptionDialog(null, this, "Remove Equipment",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,
				new String[] { "Remove", "Cancel" }, "Remove");
	}

}
