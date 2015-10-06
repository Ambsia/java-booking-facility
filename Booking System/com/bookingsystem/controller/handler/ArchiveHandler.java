package com.bookingsystem.controller.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.collections.IteratorUtils;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.view.panes.UIBookingSystemArchivePanel;

/**
 * Created by Alex on 30/05/2015
 */
public class ArchiveHandler implements ActionListener {
    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat(
            "HH:mm", Locale.ENGLISH);
    private final UIBookingSystemArchivePanel bookingSystemArchive;
    private final Handler handler;

    public ArchiveHandler(Handler handler) {
        this.handler = handler;
        bookingSystemArchive = handler.getView().getBookingSystemTabbedPane()
                .getBookingSystemArchive();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Refresh":
                    handler.getBookingBusinessLayer().populateBookingListOnLoad();
                    bookingSystemArchive.getJTableModel().clearArchiveList();
                    bookingSystemArchive.getJTableModel().addBookingList(
                            IteratorUtils.toList(handler.getBookingBusinessLayer()
                                    .getArchivedBookings().iterator()));
                    break;
                case "View Statistics":
                    // bookingSystemArchive.removeAllBookings();
                    if (!handler.getBookingBusinessLayer().getArchivedBookings()
                            .isEmpty() || !IteratorUtils.toList(
                            handler.getBookingBusinessLayer().iterator())
                            .isEmpty()) {
                        // bookingSystemArchive.addBookingsToList(handler.getBookingBusinessLayer().getArchivedBookings());

                        int totalBookings = handler.getArchiveBusinessLayer()
                                .getTotalBookings();
                        int juniorBookings = 0;
                        int seniorBookings = 0;
                        int totalDaysBooked = handler.getArchiveBusinessLayer()
                                .getTotalDaysBooked();
                        int totalBookingsCompleted = handler.getArchiveBusinessLayer().getTotalBookingsCompleted();

                        int averageBookingsADay = totalDaysBooked != 0 ? (totalBookings / totalDaysBooked) : 0;
                        Date[] busiestHours = handler.getArchiveBusinessLayer()
                                .getBusiestHours();

                        String mostUsedEquipmentString = "";
                        for (Equipment equipment: handler.getBookingBusinessLayer().getEquipments()) {
                            mostUsedEquipmentString += "- " + equipment.getEquipmentName() + " (" + equipment.getEquipmentUsage() + ")" + "\n";
                        }

                        String mostUsedLocationString = "";
                        ArrayList < String > mostUsedLocationList = handler.getArchiveBusinessLayer().getMostUsedLocation();
                        for (String s: mostUsedLocationList) {
                            mostUsedLocationString += s + "\n";
                        }

                        String mostBookingsHeldByString = "";
                        ArrayList < String > mostBookingsHeldByList = handler.getArchiveBusinessLayer()
                                .getStaffMemberWithTheMostBookingsMade();
                        for (String s: mostBookingsHeldByList) {
                            mostBookingsHeldByString += s + "\n";
                        }

                        bookingSystemArchive.getUiBookingSystemArchiveViewPanel()
                                .setJTextArea(
                                        "Total Bookings Made: " +  totalBookings + "\n" + "\n" + "Total Days Booked: " + totalDaysBooked + "\n" + "\n" + "Average Bookings Each Day:  " + averageBookingsADay + "\n" + "\n" + "Busiest Hours:   " + BOOKING_TIME_FORMAT.format(busiestHours[0]) + "-" + BOOKING_TIME_FORMAT.format(busiestHours[1]) + "\n" + "\n" + "Total Bookings Completed:   " + totalBookingsCompleted + "\n" + "\n" + "Most Used Equipment: " + "\n" + mostUsedEquipmentString + "\n" + "Most Used Location:  " + "\n" + mostUsedLocationString + "\n" + "Highest Amount Of Bookings Made By:  " + "\n" + mostBookingsHeldByString);
                    } else {
                        bookingSystemArchive.getUiBookingSystemArchiveViewPanel()
                                .setJTextArea("No archives or current bookings found.");
                    }
                    break;
            }
        } catch (Exception exception) {
            MessageBox.errorMessageBox("An error occurred whilst loading archives, please reload all bookings in the main booking panel.");
        }
    }
}