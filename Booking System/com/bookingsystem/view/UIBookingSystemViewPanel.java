package com.bookingsystem.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Alex on 10/02/2015.
 * int bookingID, String bookingDay, String bookingDate, String bookingTime,
 String bookingLocation, String bookingHolder, Equipment requiredEquipment
 */
public class UIBookingSystemViewPanel extends JPanel {

    private JTextField jTxtId;
    private JTextField jTxtBookingDay;
    private JTextField jTxtBookingDate;
    private JTextField jTxtBookingTime;
    private JTextField jTxtBookingLocation;
    private JTextField jTxtBookingHolder;
    private JTextArea  jTxtAreaEquipment;

    Border outline = BorderFactory.createLineBorder(Color.black);
    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());
        jTxtId = new JTextField(6);
        jTxtBookingDay = new JTextField(6);
        jTxtBookingDate = new JTextField(6);
        jTxtBookingTime = new JTextField(6);
        jTxtBookingLocation = new JTextField(6);
        jTxtBookingHolder = new JTextField(6);
        jTxtAreaEquipment = new JTextArea();


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        this.add(jTxtId,gbc);
        gbc.gridy++;
        this.add(jTxtBookingDay,gbc);
        gbc.gridy++;
        this.add(jTxtBookingDate,gbc);
        gbc.gridy++;
        this.add(jTxtBookingTime,gbc);
        gbc.gridy++;
        this.add(jTxtBookingLocation,gbc);
        gbc.gridy++;
        this.add(jTxtBookingHolder,gbc);
        gbc.gridy++;
        this.add(jTxtAreaEquipment,gbc);

        setBorder(outline);
    }

    public JTextField getjTxtId() {
        return jTxtId;
    }

    public JTextField getjTxtBookingDay() {
        return jTxtBookingDay;
    }

    public void setjTxtBookingDay(JTextField jTxtBookingDay) {
        this.jTxtBookingDay = jTxtBookingDay;
    }

    public JTextField getjTxtBookingDate() {
        return jTxtBookingDate;
    }

    public void setjTxtBookingDate(JTextField jTxtBookingDate) {
        this.jTxtBookingDate = jTxtBookingDate;
    }

    public JTextField getjTxtBookingTime() {
        return jTxtBookingTime;
    }

    public void setjTxtBookingTime(JTextField jTxtBookingTime) {
        this.jTxtBookingTime = jTxtBookingTime;
    }

    public JTextField getjTxtBookingLocation() {
        return jTxtBookingLocation;
    }

    public void setjTxtBookingLocation(JTextField jTxtBookingLocation) {
        this.jTxtBookingLocation = jTxtBookingLocation;
    }

    public JTextField getjTxtBookingHolder() {
        return jTxtBookingHolder;
    }

    public void setjTxtBookingHolder(JTextField jTxtBookingHolder) {
        this.jTxtBookingHolder = jTxtBookingHolder;
    }

    public JTextArea getjTxtAreaEquipment() {
        return jTxtAreaEquipment;
    }

    public void setjTxtAreaEquipment(JTextArea jTxtAreaEquipment) {
        this.jTxtAreaEquipment = jTxtAreaEquipment;
    }



}
