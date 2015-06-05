package com.bookingsystem.controller.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.view.panes.UIBookingSystemArchivePanel;

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
    	try {
        switch (e.getActionCommand()) {
            case "Load":
                //bookingSystemArchive.removeAllBookings();
                if (!handler.getBookingBusinessLayer().getArchivedBookings().isEmpty()) {
                //bookingSystemArchive.addBookingsToList(handler.getBookingBusinessLayer().getArchivedBookings());
                
                int totalBookings = handler.getArchiveBusinessLayer().getTotalBookings();
                int juniorBookings = 0;
                int seniorBookings = 0;
                int totalDaysBooked = handler.getArchiveBusinessLayer().getTotalDaysBooked();
                int totalBookingsCompleted = handler.getArchiveBusinessLayer().getTotalBookingsCompleted();
                
                int averageBookingsADay = totalDaysBooked != 0 ? (totalBookings / totalDaysBooked) : 0;
                Date[] busiestHours = handler.getArchiveBusinessLayer().getBusiestHours();
                
                ArrayList<Equipment> mostUsedEquipmentList = handler.getArchiveBusinessLayer().getMostUsedEquipment();
                String mostUsedEquipmentString = "";
                for (Equipment equipment : mostUsedEquipmentList) {
                	mostUsedEquipmentString += "- " + equipment.getEquipmentName() + " (" + equipment.getEquipmentUsageStatistic() + ")" + "\n";
                }
                
                String mostUsedLocationString = "";
                ArrayList<String> mostUsedLocationList = handler.getArchiveBusinessLayer().getMostUsedLocation();
                for (String s : mostUsedLocationList) {
                	mostUsedLocationString += s +"\n";
                }
                
                String mostBookingsHeldByString = "";
                ArrayList<String> mostBookingsHeldByList = handler.getArchiveBusinessLayer().getStaffMemberWithTheMostBookingsMade();
                for (String s : mostBookingsHeldByList) {
                	mostBookingsHeldByString += s +"\n";
                }
                
                
                bookingSystemArchive.getUiBookingSystemArchiveViewPanel().setJTextArea("Booking Statistical view from dd/mm/yyyy to dd/mm/yyyy:\n" +"\n" +
                        "Total Bookings Made: " + totalBookings + "\n" +"\n" +
                        "Total Junior School Bookings: " + juniorBookings + "\n" +"\n" +
                        "Total Senior School Bookings: " + seniorBookings + "\n" +"\n"+
                        "Total Days Booked: " + totalDaysBooked + "\n" +"\n"+
                        "Average Bookings Each Day:  " + averageBookingsADay + "\n" +"\n"+
                        "Busiest Hours:   " + BOOKING_TIME_FORMAT.format(busiestHours[0]) + "-" + BOOKING_TIME_FORMAT.format(busiestHours[1]) +"\n" + "\n" +
                        "Total Bookings Completed:   " + totalBookingsCompleted +"\n"+ "\n" +
                        "Most Used Equipment: " + "\n" + mostUsedEquipmentString + "\n" +
                        "Most Used Location:  " + "\n"+ mostUsedLocationString + "\n" +
                        "Highest Amount Of Bookings Made By:  " +  "\n" + mostBookingsHeldByString);
                }
                break;
        }
    	} catch (Exception exception) {
    		MessageBox.errorMessageBox("An error occured whilst loading archives, please reload all bookings in the main booking panel.");
    	}
    }
}
