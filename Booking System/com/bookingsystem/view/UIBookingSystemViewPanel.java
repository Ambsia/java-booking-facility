package com.bookingsystem.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Alex on 10/02/2015.
 */
public class UIBookingSystemViewPanel extends JPanel {

    private JTextField jTextField;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;

    Border outline = BorderFactory.createLineBorder(Color.black);
    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        jTextField = new JTextField(10);
        jTextField1= new JTextField(10);
        jTextField2= new JTextField(10);
        jTextField3= new JTextField(10);
        jTextField4= new JTextField(10);
        jTextField5= new JTextField(10);

        gbc.weighty= 1;
        setBorder(outline);
       // gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridy=0;
        this.add(jTextField,gbc);
        gbc.gridy++;
        this.add(jTextField1,gbc);
        gbc.gridy++;
        this.add(jTextField2,gbc);
        gbc.gridy++;
        this.add(jTextField3,gbc);
        gbc.gridy++;
        this.add(jTextField3,gbc);
        gbc.gridy++;
        this.add(jTextField4,gbc);
        gbc.gridy++;
        this.add(jTextField5,gbc);
    }
}
