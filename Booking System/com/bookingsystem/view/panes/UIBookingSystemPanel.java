package com.bookingsystem.view.panes;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.controls.UIBookingSystemJTable;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookings;
import com.bookingsystem.view.panelparts.UIBookingSystemControlPanel;
import com.bookingsystem.view.panelparts.UIBookingSystemViewPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;


public class UIBookingSystemPanel extends JPanel {

    private final UIBookingSystemViewPanel bookingSystemViewPanel;
    private final UIBookingSystemControlPanel bookingSystemControlPanel;
    private final UIBookingSystemJTable bookingSystemJTable;
    private BookingTableModel model;

    public UIBookingSystemPanel() {
        bookingSystemJTable = new UIBookingSystemJTableBookings(new BookingTableModel());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        bookingSystemViewPanel = new UIBookingSystemViewPanel();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;


        bookingSystemJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                UIBookingSystemViewPanel.setTextToField(getCurrentlySelectedRowAsStringArrayList());
            }
        });

        bookingSystemJTable.getColumn("Booking ID").setMaxWidth(80);
        bookingSystemJTable.getColumn("Day").setMaxWidth(85);
        bookingSystemJTable.getColumn("Date").setMaxWidth(80);


        JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
        gbc.gridheight = 2;
        this.add(jScrollPane, gbc);
        JScrollPane jScrollPane1 = new JScrollPane(bookingSystemViewPanel);
        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = .4;
        gbc.weighty = .9;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        jScrollPane1.setMinimumSize(new Dimension(100, 100));
        jScrollPane1.setPreferredSize(new Dimension(100, 100));
        this.add(jScrollPane1, gbc);
        bookingSystemControlPanel = new UIBookingSystemControlPanel();
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.weightx = .4;
        gbc.weighty = .1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.fill = GridBagConstraints.BOTH;
        bookingSystemControlPanel.setMinimumSize(new Dimension(100, 100));
        bookingSystemControlPanel.setPreferredSize(new Dimension(100, 100));
        this.add(bookingSystemControlPanel, gbc);
    }

    public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
        return bookingSystemJTable.getSelectedRowAsStringArrayList();
    }

    public void addBookingToList(Booking booking) {
        bookingSystemJTable.addRowToList(booking);
    }

    public void addBookingsToList(ArrayList<Booking> listOfBookings) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (Booking b : listOfBookings) {
            objectArrayList.add(b);
        }
        bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
    }

    public Booking getBookingFromList(int bookingId) {
        return (Booking) bookingSystemJTable.getRowFromList(bookingId);
    }

    public int getIndexOfSelectedRow() {
        return bookingSystemJTable.getSelectedRow();
    }

    public int getIDOfSelectedRow() {
        return bookingSystemJTable.getIDOfSelectedRow();
    }

    public int getRowCountOfTable() {
        return bookingSystemJTable.getRowCount();
    }

    public void replaceBookingInList(Booking newBooking) {
        bookingSystemJTable.replaceRowInList(newBooking);
    }

    public void removeBookingFromTable() {
        bookingSystemJTable.removeRowFromList();
    }

    public void removeAllBookings() {
        bookingSystemJTable.removeAllRowsFromList();
    }

    public UIBookingSystemViewPanel getBookingSystemViewPanel() {
        return bookingSystemViewPanel;
    }

    public UIBookingSystemControlPanel getBookingSystemControlPanel() {
        return bookingSystemControlPanel;
    }

}
