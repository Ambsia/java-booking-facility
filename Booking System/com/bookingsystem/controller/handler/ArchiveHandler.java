package com.bookingsystem.controller.handler;

import com.bookingsystem.model.Booking;
import com.bookingsystem.view.panes.UIBookingSystemArchivePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 30/05/2015.
 */
public class ArchiveHandler implements ActionListener {
    private UIBookingSystemArchivePanel bookingSystemArchive;
    private Handler handler;

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    public ArchiveHandler(Handler handler) {
        this.handler = handler;
        bookingSystemArchive = handler.getView().getBookingSystemTabbedPane().getBookingSystemArchive();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load":
                bookingSystemArchive.removeAllBookings();
                bookingSystemArchive.addBookingsToList(handler.getBookingBusinessLayer().getArchivedBookings());

                int totalBookings = handler.getArchiveBusinessLayer().getTotalBookings();
                int juniorBookings = 0;
                int seniorBookings = 0;
                int totalDaysBooked = handler.getArchiveBusinessLayer().getTotalDaysBooked();
                int totalBookingsCompleted = handler.getArchiveBusinessLayer().getTotalBookingsCompleted();
                Date[] busiestHours = handler.getArchiveBusinessLayer().getBusiestHours();
                String mostUsedEquipment = handler.getArchiveBusinessLayer().getMostUsedEquipment();
                String mostUsedLocation = handler.getArchiveBusinessLayer().getMostUsedLocation();
                String mostBookingsHeldBy = handler.getArchiveBusinessLayer().getStaffMemberWithTheMostBookingsMade();

                bookingSystemArchive.getUiBookingSystemArchiveViewPanel().setJTextArea("Booking Statistical view from dd/mm/yyyy to dd/mm/yyyy:\n" +"\n" +
                        "Total Bookings Made: " + totalBookings + "\n" +"\n" +
                        "Total Junior School Bookings: " + juniorBookings + "\n" +"\n" +
                        "Total Senior School Bookings: " + seniorBookings + "\n" +"\n"+
                        "Total Days Booked: " + totalDaysBooked + "\n" +"\n"+
                        "Average Bookings Each Day:  " + totalBookings / totalDaysBooked + "\n" +"\n"+
                        "Busiest Hours:   " + BOOKING_TIME_FORMAT.format(busiestHours[0]) + "-" + BOOKING_TIME_FORMAT.format(busiestHours[1]) +"\n" + "\n" +
                        "Total Bookings Completed:   " + totalBookingsCompleted +"\n"+ "\n" +
                        "Most Used Equipment: " + mostUsedEquipment +"\n"+ "\n" +
                        "Most Used Location:  " + mostUsedLocation +"\n"+ "\n" +
                        "Highest Amount Of Bookings Made By:  " + mostBookingsHeldBy);
                break;
        }
    }
}
