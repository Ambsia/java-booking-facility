package com.bookingsystem.view.dialogpanels;

import com.bookingsystem.view.UIBookingSystemDialogPanel;

import javax.swing.*;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAddPanel extends UIBookingSystemDialogPanel {
    public UIBookingSystemAddPanel() {
        super();
        addDefaultComponentsToPanel();
    }

    @Override
    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Add Booking",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
                new String[] {"Add", "Cancel" }, "Add");
    }
}
