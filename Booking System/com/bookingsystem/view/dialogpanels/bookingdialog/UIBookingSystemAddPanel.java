package com.bookingsystem.view.dialogpanels.bookingdialog;

import javax.swing.JOptionPane;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAddPanel extends UIBookingSystemDialogPanel {
    public UIBookingSystemAddPanel() {
        super();
        
        setLayout(super.getLayout());
        addDefaultComponentsToPanel();
        super.repaint();
    }

    @Override
    public int showDialog() {
    	clearInputs();
        return JOptionPane.showOptionDialog(null, this, "Add Booking",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
                new String[] {"Add", "Cancel" }, "Add");
    }
}
