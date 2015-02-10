package com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 10/02/2015.
 */
public class UIBookingSystemViewPanel extends JPanel {

    private JTextField jTextField;
    private JTextField jTextField1;

    public UIBookingSystemViewPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        jTextField = new JTextField(10);



        this.add(jTextField);



    }
}
