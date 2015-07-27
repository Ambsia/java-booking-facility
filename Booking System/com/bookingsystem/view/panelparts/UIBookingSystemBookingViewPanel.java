package com.bookingsystem.view.panelparts;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.tablemodel.BookingProblemModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookingProblems;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 10/02/2015.
 */

public class UIBookingSystemBookingViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4535420505698893143L;
    private final UIBookingSystemJTableBookingProblems bookingSystemJTableBookingProblems;
    private final JScrollPane jScrollPane;
    private final JLabel jLabel;

    public UIBookingSystemBookingViewPanel() {
        setLayout(new GridBagLayout());

        jLabel = new JLabel("Booking Problems (Unknown dates, conflicts..)");
        addControlToPanel(jLabel, 0);
        bookingSystemJTableBookingProblems = new UIBookingSystemJTableBookingProblems(
                new BookingProblemModel());
        setLayout(new GridBagLayout());
        jScrollPane = new JScrollPane(bookingSystemJTableBookingProblems);
        bookingSystemJTableBookingProblems.getColumn("ID").setMinWidth(0);
        bookingSystemJTableBookingProblems.getColumn("ID").setMaxWidth(00);
        bookingSystemJTableBookingProblems.getColumn("ID")
                .setPreferredWidth(00);
        bookingSystemJTableBookingProblems.getColumn("Day").setPreferredWidth(
                60);
        bookingSystemJTableBookingProblems.getColumn("Holder")
                .setPreferredWidth(60);
        bookingSystemJTableBookingProblems.getColumn("Location")
                .setPreferredWidth(60);
        bookingSystemJTableBookingProblems.getColumn("Equipment")
                .setPreferredWidth(60);
        jScrollPane.setPreferredSize(new Dimension(300, 200));
        addControlToPanel(jScrollPane, 1);
        // listOfViewBoxes = new ArrayList<>();
        //
        // for(int i = 0; i <= 6;i++) {
        // listOfViewBoxes.add( new JLabel());
        // addControlToPanel(listOfViewBoxes.get(i),i);
        // }

    }

    private void addControlToPanel(Component component, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(component, gbc);
    }

    // public static void setTextToField(ArrayList<String> listOfStrings) {
    // if (listOfStrings != null) {
    // listOfStrings.add(0, "Booking #".concat(listOfStrings.get(0))); //
    // changing index 0 to contain "Booking #"
    // listOfStrings.remove(1); //Remove 1 because we have concatenated the id
    // with 0
    // for (int i = 0; i <= 6; i++) {
    // if (listOfStrings.get(2) == "") { // check if the string at index 2 is
    // empty, if it is then the view panel should display nothing
    // listOfStrings.add(0, "");
    // listOfStrings.remove(1);
    // return;
    // }
    // listOfViewBoxes.get(i).setText(listOfStrings.get(i)); // otherwise we're
    // good to set the text
    // }
    // }
    // }

    public void addProblemToList(Booking booking) {
        bookingSystemJTableBookingProblems.addRowToList(booking);
    }

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public void addProblemsToList(ArrayList<Booking> listOfBookings) {
    // ArrayList<Object> objectArrayList = new ArrayList<>();
    // for (Booking booking : listOfBookings) {
    // objectArrayList.add(booking);
    // }
    // bookingSystemJTableBookingProblems.addArrayOfRowsToList(objectArrayList);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public int getIndexOfSelectedRow() {
    // return bookingSystemJTableBookingProblems.getSelectedRow();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public int getIDOfSelectedRow() {
    // return bookingSystemJTableBookingProblems.getIDOfSelectedRow();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public int getRowCountOfTable() {
    // return bookingSystemJTableBookingProblems.getRowCount();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public void removeProblemFromTable() {
    // bookingSystemJTableBookingProblems.removeRowFromList();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    public boolean isRowInTable(int identifier) {
        return bookingSystemJTableBookingProblems.isRowInTable(identifier);
    }

    public void removeAllProblems() {
        bookingSystemJTableBookingProblems.removeAllRowsFromList();
    }

    public JTable getBookingSystemJTableProblems() {
        return bookingSystemJTableBookingProblems;
    }
}
