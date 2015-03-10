package com.bookingsystem.view;


import com.bookingsystem.helpers.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Alex on 20/02/2015.
 */
public class UIBookingSystemAddPanel extends JPanel {

    private final static String[] days = { "" ,"Sunday", "Monday" ,"Tuesday" ,"Wednesday","Thursday", "Friday", "Saturday" };
    private final static String[] labels = {"Booking Day: ", "Booking Date: ", "Booking Time: ", "Booking Location: ", "Booking Holder: ", "Equipment: "};
    private Component[] components;

    private JTextField txtBookingDay;
    private JTextField txtBookingTime;
    private JTextField txtBookingLocation;
    private JTextField txtBookingHolder;
    private JTextArea txtAreaEquipment;
    private JScrollPane jScrollPane;
    private JDatePickerImpl datePicker;


    public UIBookingSystemAddPanel() {
        txtBookingDay = new JTextField(5);
        txtBookingTime = new JTextField(5);
        txtBookingLocation = new JTextField(5);
        txtBookingHolder = new JTextField(5);
        txtAreaEquipment = new JTextArea(5,15);
        jScrollPane = new JScrollPane(txtAreaEquipment);
        txtBookingDay.setEditable(false);

        setLayout(new GridBagLayout());
        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        final JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        components = new Component[] { txtBookingDay, datePicker, txtBookingTime,txtBookingLocation,txtBookingHolder, jScrollPane };

        for (int i = 0;i<labels.length;i++) {
            addControlToPanel(new JLabel(labels[i]), 0, i, 0, 0);
            addControlToPanel(components[i], 1, i, 0, 0);
        }

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Calendar c = Calendar.getInstance();
                    Date d = null;
                    try {
                        d = new SimpleDateFormat("dd-MM-yyyy").parse(getFormattedDate());
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    c.setTime(d);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    System.out.println(dayOfWeek);
                    txtBookingDay.setText(days[dayOfWeek]);
                }
        });
    }

    public void addControlToPanel(Component component, int gridX, int gridY, double weightX, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        add(component, gbc);
    }

    public int showDialog() {
        return JOptionPane.showOptionDialog(null, this, "Add Booking",
                JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION,null,
                new String[] {"Add", "Cancel" }, "Add");
    }

    public String getTxtAreaEquipmentText() {
        return txtAreaEquipment.getText();
    }

    public String getTxtBookingDayText() {
        return txtBookingDay.toString();
    }

    public String getTxtBookingTimeText() {
        return txtBookingTime.getText();
    }

    public String getTxtBookingLocationText() {
        return txtBookingLocation.getText();
    }

    public String getTxtBookingHolderText() {
        return txtBookingHolder.getText();
    }

    public String getFormattedDate() {
        String datePattern = "dd-MM-yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        return dateFormatter.format((Date) datePicker.getModel().getValue());
    }

    public String[] getBookingStringArray () {
        return new String[]{ getTxtBookingDayText(),  getFormattedDate(), getTxtBookingTimeText(),
                getTxtBookingLocationText(), getTxtBookingHolderText(), getTxtAreaEquipmentText()
        };
    }
}