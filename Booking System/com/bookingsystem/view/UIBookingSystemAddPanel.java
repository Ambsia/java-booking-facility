package com.bookingsystem.view;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.bookingsystem.helpers.DateLabelFormatter;


/**
 * Created by Alex on 20/02/2015.
 */
public class UIBookingSystemAddPanel extends JPanel {

    private final static String[] days = { "" ,"Sunday", "Monday" ,"Tuesday" ,"Wednesday","Thursday", "Friday", "Saturday" };
    private final static String[] labels = {"Booking Day: ", "Booking Date: ", "Booking Start Time: ", "Booking Collection Time: ", "Booking Location: ", "Booking Holder: ", "Equipment: "};

    private Component[] components;

    private JTextField txtBookingDay;
    private JTextField txtBookingStartTime;
    private JTextField txtBookingCollectionTime;
    private JTextField txtBookingLocation;
    private JTextField txtBookingHolder;
    private JTextArea txtAreaEquipment;
    private JScrollPane jScrollPane;
    private JDatePickerImpl datePicker;
    private Date date = new Date();
    private SpinnerDateModel spinnerStartDateTimeModel;
    private SpinnerDateModel spinnerCollectionDateTimeModel;

    private JSpinner jSpinnerStartTime;
    private JSpinner jSpinnerCollectionTime;

    public UIBookingSystemAddPanel() {
        txtBookingDay = new JTextField(5);
        txtBookingStartTime = new JTextField(5);
        txtBookingCollectionTime = new JTextField(5);
        txtBookingLocation = new JTextField(5);
        txtBookingHolder = new JTextField(5);
        txtAreaEquipment = new JTextArea(5,15);
        jScrollPane = new JScrollPane(txtAreaEquipment);
        txtBookingDay.setEditable(false);
        Time time = Time.valueOf("12:00:00");

        date.setTime(time.getTime());
        spinnerStartDateTimeModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spinnerCollectionDateTimeModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);

        jSpinnerStartTime = new JSpinner(spinnerStartDateTimeModel);
        jSpinnerCollectionTime = new JSpinner(spinnerCollectionDateTimeModel);

        JSpinner.DateEditor dateEditorCollectionTime = new JSpinner.DateEditor(jSpinnerCollectionTime, "HH:mm aa");
        jSpinnerCollectionTime.setEditor(dateEditorCollectionTime);

        JSpinner.DateEditor dateEditorStartTime = new JSpinner.DateEditor(jSpinnerStartTime, "HH:mm aa");
        jSpinnerStartTime.setEditor(dateEditorStartTime);



        setLayout(new GridBagLayout());
        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        final JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        components = new Component[] { txtBookingDay, datePicker, jSpinnerStartTime,jSpinnerCollectionTime,txtBookingLocation,txtBookingHolder, jScrollPane };

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
                        d = new SimpleDateFormat("dd.MM.yy").parse(getFormattedDate());
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
        return txtBookingDay.getText();
    }

    public String getTxtBookingStartTimeText() {
        return jSpinnerStartTime.getModel().getValue().toString();
    }

    public String getTxtBookingCollectionTimeText() {
        return jSpinnerCollectionTime.getModel().getValue().toString();
    }

    public String getTxtBookingLocationText() {
        return txtBookingLocation.getText();
    }

    public String getTxtBookingHolderText() {
        return txtBookingHolder.getText();
    }

    public String getFormattedDate() {
        String datePattern = "dd.MM.yy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        return dateFormatter.format((Date) datePicker.getModel().getValue());
    }

    public String[] getBookingStringArray () {
        return new String[]{ getTxtBookingDayText(),  getFormattedDate(), getTxtBookingStartTimeText(),getTxtBookingCollectionTimeText(),
                getTxtBookingLocationText(), getTxtBookingHolderText(), getTxtAreaEquipmentText()
        };
    }
}
