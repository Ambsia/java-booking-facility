package com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 22/03/2015.
 */
public class UIBookingSystemAdminPanel extends JPanel {

    public UIBookingSystemAdminPanel() {
        this.setLayout(new GridBagLayout());
        JLabel jLabel = new JLabel("ACP");


        this.add(jLabel);
    }

}
