package com.bookingsystem.view;

import sun.plugin.javascript.navig.Anchor;

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
    private JPanel jp;
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

        addControlToPanel(jTxtId,0,0,GridBagConstraints.WEST);
        addControlToPanel(jTxtBookingDate, 0, 1, GridBagConstraints.WEST);
        addControlToPanel(jTxtBookingTime,0,2,GridBagConstraints.WEST);
        addControlToPanel(jTxtBookingLocation,0,3,GridBagConstraints.WEST);
        addControlToPanel(jTxtBookingHolder,0,4,GridBagConstraints.WEST);
        addControlToPanel(jTxtAreaEquipment,0,5,GridBagConstraints.WEST);
        setBorder(outline);
    }


    public void addControlToPanel(Component component, int gridX, int gridY, int alignment) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.anchor = alignment;
        add(component, gbc);
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
