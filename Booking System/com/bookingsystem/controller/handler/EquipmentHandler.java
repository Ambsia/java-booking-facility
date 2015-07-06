package com.bookingsystem.controller.handler;

import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemAddEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemEditEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemRemoveEquipment;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemEquipmentPanel;

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
    private int currentEquipmentIDBeingHandled;
    private final UIBookingSystemEquipmentPanel bookingSystemEquipmentPanel;
    private final EquipmentTableModel equipmentTableModel;
    public EquipmentHandler(Handler handler) {
        this.handler = handler;
        this.bookingSystemEquipmentPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemEquipmentPanel();
        this.equipmentTableModel = bookingSystemEquipmentPanel.getTableModel();
        this.currentEquipmentIDBeingHandled = -1;
        this.bookingSystemEquipmentControlPanel = this.bookingSystemEquipmentPanel.getBookingSystemEquipmentControlPanel();
        this.bookingSystemAddEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttAddPanel();
        this.bookingSystemEditEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttEditPanel();
        this.bookingSystemRemoveEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmentRemovePanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toString()) {
            case "Add Equipment":
            	int modelRow = this.bookingSystemEquipmentPanel.rowViewIndexToModel(this.bookingSystemEquipmentPanel.getSelectedRow());
                this.currentEquipmentIDBeingHandled = (int) bookingSystemEquipmentPanel.getValueAt(modelRow, 0);
                if (this.currentEquipmentIDBeingHandled != -1) {
                	if (this.bookingSystemAddEquipment.showDialog() == 0) {
                		
                	
                	}
                }
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
