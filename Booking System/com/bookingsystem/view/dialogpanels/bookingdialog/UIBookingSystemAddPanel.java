package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;
import java.awt.*;

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
        return JOptionPane.showOptionDialog(null, this, "Add Booking",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
                new String[] {"Add", "Cancel" }, "Add");
    }
}
