package com.bookingsystem.view.dialogpanels.equipmentdialog;

import com.bookingsystem.model.Equipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UIBookingSystemEditEquipment extends JPanel {

    private static final String[] LABELS = {"ID:", "Name:", "Desc:", "Usage:"};
    private final JTextField txtEquipmentID;
    private final JTextField txtEquipmentName;
    private final JTextField txtEquipmentDesc;
    private final JTextField txtEquipmentUsage;
    private final JButton btnResetUsageStatistic;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private Component[] components;

    public UIBookingSystemEditEquipment() {
        txtEquipmentID = new JTextField(25);
        txtEquipmentName = new JTextField(25);
        txtEquipmentDesc = new JTextField(25);
        txtEquipmentUsage = new JTextField(3);
        txtEquipmentUsage.setEditable(false);
        txtEquipmentID.setEditable(false);
        btnResetUsageStatistic = new JButton("Reset Statistic");
        setLayout(new GridBagLayout());
        addDefaultComponentsToPanel();

    }

    private void addControlToPanel(GridBagConstraints gbcc,
                                   Component component, int gridX, int gridY) {
        gbcc.insets = new Insets(2, 2, 2, 2);
        gbcc.gridx = gridX;
        gbcc.gridy = gridY;
        gbcc.gridwidth = 3;
        gbcc.fill = GridBagConstraints.BOTH;
        gbcc.anchor = GridBagConstraints.LAST_LINE_END;
        gbcc.weighty = 1;
        add(component, gbcc);
    }

    private void addControl(GridBagConstraints gbcc, Component component,
                            int x, int y) {
        gbcc.insets = new Insets(2, 2, 2, 2);
        gbcc.gridx = x;
        gbcc.gridy = y;
        gbcc.gridwidth = 1;
        gbcc.anchor = GridBagConstraints.PAGE_START;
        gbcc.fill = GridBagConstraints.BOTH;
        gbcc.weighty = 1;
        gbcc.weightx = .5;

        add(component, gbcc);
        component.setVisible(true);
    }

    void addDefaultComponentsToPanel() {
        components = new Component[]{txtEquipmentID, txtEquipmentName,
                txtEquipmentDesc};
        for (int i = 0; i < LABELS.length; i++) {
            if (i == 3) {
                addControl(gbc, new JLabel(LABELS[i]), 0, i);
                addControl(gbc, txtEquipmentUsage, 1, i);
                addControl(gbc, btnResetUsageStatistic, 2, i);
            } else {
                //	System.out.println(i + " = i ");
                addControlToPanel(gbc, new JLabel(LABELS[i]), 0, i);
                addControlToPanel(gbc, components[i], 1, i);
            }
        }
        // addControl(gbc,txtWeeksRecurring,4,2);
    }

    public String getEquipmentName() {
        return txtEquipmentName.getText();
    }

    public String getEquipmentDescription() {
        return txtEquipmentDesc.getText();
    }

    public int getEquipmentID() {
        return Integer.parseInt(txtEquipmentID.getText());
    }

    public void setTxtBoxesText(Equipment e) {
        this.txtEquipmentName.setText(e.getEquipmentName());
        this.txtEquipmentDesc.setText(e.getEquipmentDescription());
        this.txtEquipmentUsage.setText("" + e.getEquipmentUsage());
        this.txtEquipmentID.setText("" + e.getEquipmentID());
    }

    public void addResetUsageActionListener(ActionListener al) {
        btnResetUsageStatistic.addActionListener(al);
    }

    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Edit Equipment",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Edit", "Cancel"}, "Edit");
    }

}
