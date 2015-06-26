package com.bookingsystem.view.dialogpanels.accountdialog;

import com.bookingsystem.helpers.TextFieldRestriction;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by Alex on 23/06/2015
 */
public class UIBookingSystemChangeUserLevel extends JPanel {

    /**
     *
     */

    private static final String[] LABELS = {"User Level:"};
    private Component[] components;

    private final JTextField txtUserLevel;
    private final JLabel lblUsername;

    public UIBookingSystemChangeUserLevel() {
        lblUsername = new JLabel("Username");
        txtUserLevel = new JTextField(3);
        txtUserLevel.setDocument(new TextFieldRestriction());
        setLayout(new GridBagLayout());
        addControlToPanel(lblUsername,0,0);
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
        components = new Component[]{txtUserLevel};
        for (int i = 0; i < LABELS.length; i++) {
            addControlToPanel(new JLabel(LABELS[i]), 0, i+2);
            addControlToPanel(components[i], 1, i+2);
        }
    }

    public int getNewAccountLevel() {
        try {
            return Integer.parseInt(String.valueOf(txtUserLevel.getText()));
        } catch (Exception pe) {
            return -1;
        }
    }


    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Change User Level",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Change", "Cancel"}, "Change");
    }

    public void clearTextBoxes() {
        this.txtUserLevel.setText("");
    }

    public void setLblUsernameText(String username) {
        this.lblUsername.setText(username);
    }
}
