package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemAddEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemEditEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemRemoveEquipment;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemEquipmentPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

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
    public void actionPerformed(ActionEvent eventOccurred) {
        Log log = new Log(eventOccurred.getActionCommand(), this.getClass().getSimpleName(), new Date());
        switch (eventOccurred.getActionCommand().toString()) {
            case "Add Equipment":
            	if (bookingSystemAddEquipment.showDialog() == 0) {
            		Equipment newEquipment = new Equipment(bookingSystemAddEquipment.getEquipmentName());
            		if (newEquipment.verify()) {
            			newEquipment.setEquipmentDescription(bookingSystemAddEquipment.getEquipmentDescription());
            			handler.getBookingBusinessLayer().getEquipments().addEquipment(newEquipment);
            			log.setIdPlayedWith(newEquipment.getEquipmentID());
            		} else {
            			MessageBox.errorMessageBox("Equipment missing name.");
            		}
            	}
            	handler.getLoggerBusinessLayer().insertLog(log);
                break;
            case "Edit Equipment":
            	 if(bookingSystemEquipmentPanel.selectedRowCount() > 0) {
            	int modelRow = this.bookingSystemEquipmentPanel.rowViewIndexToModel(this.bookingSystemEquipmentPanel.getSelectedRow());
                this.currentEquipmentIDBeingHandled = (int) bookingSystemEquipmentPanel.getValueAt(modelRow, 0);
                if (this.currentEquipmentIDBeingHandled != -1) {
                	if (this.bookingSystemAddEquipment.showDialog() == 0) {
                		
                	
                	}
                }
            	 } else {  MessageBox.errorMessageBox("Please select the equipment you would like to modify.");

            	 }
                break;
            case "Remove Equipment":
                    this.bookingSystemRemoveEquipment.showDialog();
                    
                break;
            default:;
        }
    }
}
