package com.bookingsystem.view.dialogpanels.equipmentdialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bookingsystem.helpers.TextFieldRestriction;

public class UIBookingSystemAddEquipment extends JPanel {
	
	private static final String[] LABELS = {"Equipment Name:", "Equipment Description:"};
    private Component[] components;
    private final JTextField txtEquipmentName;
    private final JTextField txtEquipmentDesc;

    public UIBookingSystemAddEquipment() {
    	txtEquipmentName = new JTextField(15);
    	txtEquipmentDesc = new JTextField(15);
        setLayout(new GridBagLayout());
        addDefaultComponentsToPanel();

    }

    private void addControlToPanel(Component component, int gridX, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.weightx = 0;
        gbc.weighty = 0;
        add(component, gbc);
    }


    void addDefaultComponentsToPanel() {
        components = new Component[]{txtEquipmentName, txtEquipmentDesc};
        for (int i = 0; i < LABELS.length; i++) {
            addControlToPanel(new JLabel(LABELS[i]), 0, i);
            addControlToPanel(components[i], 1, i);
        }
    }

    public String getEquipmentName() {
        return txtEquipmentName.getText();
    }

    public String getEquipmentDescription() {
        return txtEquipmentDesc.getText();
    }

    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Add Equipment",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Add", "Cancel"}, "Add");
    }

}
