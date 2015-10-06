package com.bookingsystem.view.dialogpanels.equipmentdialog;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UIBookingSystemRemoveEquipment extends JPanel {

    public static String newline = System.getProperty("line.separator");
    private JLabel lblWarningMessage;

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
                new String[]{"Remove", "Cancel"}, "Remove");
    }

}
