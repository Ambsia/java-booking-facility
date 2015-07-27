package com.bookingsystem.view.controls;

import javax.swing.*;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemJTable extends JTable {

    /**
     *
     */
    private static final long serialVersionUID = 5534068596222951987L;

    UIBookingSystemJTable() {
        this.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public int getIDOfSelectedRow() {
    // if (this.getSelectedRow() != -1) {
    // return this.getValueAt(this.getSelectedRow(), 0) != null ? (int)
    // this.getValueAt(this.getSelectedRow(), 0) : -1;
    // }
    // return -1;
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

}
