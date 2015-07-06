package com.bookingsystem.view.panelparts.controlpanes;

import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemAccountAddPanel;
import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemChangePasswordPanel;
import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemChangeUserLevel;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemAddEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemEditEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemRemoveEquipment;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;

/**
 * Author: [Alex]
 */
public class UIBookingSystemEquipmentControlPanel extends UIBookingSystemControlPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5157872014641211611L;
	private final UIBookingSystemAddEquipment bookingSystemEquipmentAddPanel;
	private final UIBookingSystemEditEquipment bookingSystemEquipmentEditPanel;
	private final UIBookingSystemRemoveEquipment bookingSystemEquipmentRemovePanel;

	public UIBookingSystemEquipmentControlPanel() {
		super();
		super.setColumnns(4);
		setLayout(new GridBagLayout());
		setButtonNames(new String[] {"Refresh", "Import","Add","Edit","Remove"});
		setButtonDimension(new Dimension(125, 25));
		createControlPanel();

		bookingSystemEquipmentAddPanel = new UIBookingSystemAddEquipment();
		bookingSystemEquipmentEditPanel = new UIBookingSystemEditEquipment();
		bookingSystemEquipmentRemovePanel = new UIBookingSystemRemoveEquipment();
	}

	
	public void restrictControls() {
	        ArrayList<JButton> jButtonArrayList = getControlButtonList();
	        for(JButton jButton : jButtonArrayList) {
	            if (jButton.getText() == "Import" || jButton.getText() == "Add"|| 
	            		jButton.getText() == "Edit"||jButton.getText() == "Remove") {
	                jButton.setText("No Access");
	            }
	        }
	}
	  
	public UIBookingSystemEditEquipment getBookingSystemEquipmenttEditPanel() {
		return  bookingSystemEquipmentEditPanel;
	}

	public UIBookingSystemRemoveEquipment getBookingSystemEquipmentRemovePanel() {
		return bookingSystemEquipmentRemovePanel;
	}


	public UIBookingSystemAddEquipment getBookingSystemEquipmenttAddPanel() {
		return bookingSystemEquipmentAddPanel;
	}

}
