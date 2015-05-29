package com.bookingsystem.view.panelparts;


import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.BookingProblemModel;
import com.bookingsystem.view.controls.UIBookingSystemJTableBookingProblems;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alex on 10/02/2015.
*/

public class UIBookingSystemViewPanel extends JPanel {

    private static ArrayList<JLabel> listOfViewBoxes;
    private UIBookingSystemJTableBookingProblems bookingSystemJTableBookingProblems;
    private JScrollPane jScrollPane;
    private JLabel jLabel;

    public UIBookingSystemViewPanel() {
        setLayout(new GridBagLayout());

        jLabel = new JLabel("Booking Problems (Unknown dates, conflicts..)");
        addControlToPanel(jLabel,0);
        bookingSystemJTableBookingProblems = new UIBookingSystemJTableBookingProblems(new BookingProblemModel());
        setLayout(new GridBagLayout());
        jScrollPane = new JScrollPane(bookingSystemJTableBookingProblems);
        bookingSystemJTableBookingProblems.getColumn("ID").setPreferredWidth(40);
        bookingSystemJTableBookingProblems.getColumn("Day").setPreferredWidth(60);
        bookingSystemJTableBookingProblems.getColumn("Holder").setPreferredWidth(60);
        bookingSystemJTableBookingProblems.getColumn("Location").setPreferredWidth(60);
        bookingSystemJTableBookingProblems.getColumn("Equipment").setPreferredWidth(60);
        jScrollPane.setPreferredSize(new Dimension(300,200));
        addControlToPanel(jScrollPane, 1);
//        listOfViewBoxes = new ArrayList<>();
//
//        for(int i = 0; i <= 6;i++) {
//            listOfViewBoxes.add( new JLabel());
//            addControlToPanel(listOfViewBoxes.get(i),i);
//        }
    }

    private void addControlToPanel(Component component, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(component, gbc);
    }

    public String getTextFromField(int i) {
    	return listOfViewBoxes.get(i).getText();
    }

//    public static void setTextToField(ArrayList<String> listOfStrings) {
//        if (listOfStrings != null) {
//            listOfStrings.add(0, "Booking #".concat(listOfStrings.get(0))); // changing index 0 to contain "Booking #"
//            listOfStrings.remove(1); //Remove 1 because we have concatenated the id with 0
//            for (int i = 0; i <= 6; i++) {
//                if (listOfStrings.get(2) == "") { // check if the string at index 2 is empty, if it is then the view panel should display nothing
//                    listOfStrings.add(0, "");
//                    listOfStrings.remove(1);
//                    return;
//                }
//                listOfViewBoxes.get(i).setText(listOfStrings.get(i)); // otherwise we're good to set the text
//            }
//        }
//    }


    public void addProblemToList(Booking booking) {
        bookingSystemJTableBookingProblems.addRowToList(booking);
    }

    public void addProblemsToList(ArrayList<Booking> listOfBookings) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (Booking booking : listOfBookings) {
            objectArrayList.add(booking);
        }
        bookingSystemJTableBookingProblems.addArrayOfRowsToList(objectArrayList);
    }

    public int getIndexOfSelectedRow() {
        return bookingSystemJTableBookingProblems.getSelectedRow();
    }

    public int getIDOfSelectedRow() {
        return bookingSystemJTableBookingProblems.getIDOfSelectedRow();
    }

    public int getRowCountOfTable() {
        return bookingSystemJTableBookingProblems.getRowCount();
    }

    public void removeProblemFromTable() {
        bookingSystemJTableBookingProblems.removeRowFromList();
    }

    public boolean isRowInTable(int identifier) { return bookingSystemJTableBookingProblems.isRowInTable(identifier); }

    public void removeAllProblems() {
        bookingSystemJTableBookingProblems.removeAllRowsFromList();
    }
}
