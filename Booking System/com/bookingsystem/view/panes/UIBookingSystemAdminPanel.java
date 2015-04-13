package com.bookingsystem.view.panes;

import javax.swing.*;
import java.awt.*;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemAdminPanel extends JPanel {

    public UIBookingSystemAdminPanel() {
        this.setLayout(new GridBagLayout());
        JLabel jLabel = new JLabel("ACP");
        this.add(jLabel);
    }

}
