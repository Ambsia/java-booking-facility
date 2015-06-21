package com.bookingsystem.view.dialogpanels.bookingdialog;

import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;

import javax.swing.*;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAddPanel extends UIBookingSystemDialogPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5267785275189676299L;

	public UIBookingSystemAddPanel() {
        super();
        
        setLayout(super.getLayout());
        addDefaultComponentsToPanel();
        super.repaint();
    }

    @Override
    public int showDialog() {
    	//clearInputs();
        return JOptionPane.showOptionDialog(null, this, "Add Booking",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,null,
                new String[] {"Add", "Cancel" }, "Add");
    }
}
