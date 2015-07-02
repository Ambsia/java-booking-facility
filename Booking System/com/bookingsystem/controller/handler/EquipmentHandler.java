package com.bookingsystem.controller.handler;

import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemAddEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemEditEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemRemoveEquipment;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 02/07/2015
 */
public class EquipmentHandler implements ActionListener {

    private final Handler handler;

    private final UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel;
    private final UIBookingSystemAddEquipment bookingSystemAddEquipment;
    private final UIBookingSystemEditEquipment bookingSystemEditEquipment;
    private final UIBookingSystemRemoveEquipment bookingSystemRemoveEquipment;

    public EquipmentHandler(Handler handler) {
        this.handler = handler;

        this.bookingSystemEquipmentControlPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemEquipmentPanel().getBookingSystemEquipmentControlPanel();
        this.bookingSystemAddEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttAddPanel();
        this.bookingSystemEditEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttEditPanel();
        this.bookingSystemRemoveEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmentRemovePanel();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toString()) {
            case "Add Equipment":
                    this.bookingSystemAddEquipment.showDialog();
                break;
            case "Edit Equipment":
                    this.bookingSystemEditEquipment.showDialog();
                break;
            case "Remove Equipment":
                    this.bookingSystemRemoveEquipment.showDialog();
                break;
            default:;
        }
    }
}
