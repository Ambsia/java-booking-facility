package com.bookingsystem.view.dialogpanels.accountdialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bookingsystem.helpers.TextFieldRestriction;

/**
 * Created by Alex on 04/05/2015
 */
public class UIBookingSystemAccountAddPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 419911671046158169L;
    private static final String[] LABELS = {"Account Name:",
            "Account Password:", "Account Level:"};
    private final JTextField txtAccountName;
    private final JPasswordField txtAccountPassword;
    private final JTextField txtAccountLevel;
    private Component[] components;

    public UIBookingSystemAccountAddPanel() {
    	
        txtAccountName = new JTextField(5);
        txtAccountPassword = new JPasswordField(5);
        txtAccountLevel = new JTextField(5);
        txtAccountLevel.setDocument(new TextFieldRestriction());
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
        components = new Component[]{txtAccountName, txtAccountPassword,
                txtAccountLevel};

        for (int i = 0; i < LABELS.length; i++) {
            addControlToPanel(new JLabel(LABELS[i]), 0, i);
            addControlToPanel(components[i], 1, i);
        }
    }

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public void addTheseComponentsToPanel(Component[] components, String[]
    // LABELS) {
    // for (int i = 0; i < LABELS.length; i++) {
    // addControlToPanel(new JLabel(LABELS[i]), 0, i);
    // addControlToPanel(components[i], 1, i);
    // }
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public Component[] getComponentsAsList() {
    // return components;
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    public String getAccountNameText() {
        return txtAccountName.getText();
    }

    public char[] getAccountPasswordText() {
        return txtAccountPassword.getPassword();
    }

    public String getAccountUserLevelText() {
        return txtAccountLevel.getText();
    }

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public void setTextOfComponents(Object[] list) {
    // txtAccountName.setText((String) list[0]);
    // txtAccountPassword.setText((String) list[1]);
    // txtAccountLevel.setText((String) list[2]);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Add Account",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Add", "Cancel"}, "Add");
    }
}
