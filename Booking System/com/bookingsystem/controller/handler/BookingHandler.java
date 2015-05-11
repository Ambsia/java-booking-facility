package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.view.dialogpanels.bookingdialog.*;
import com.bookingsystem.view.panelparts.UIBookingSystemControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemPanel;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class BookingHandler implements ActionListener {

    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT_2 = new SimpleDateFormat("dd.MMM.yy", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT_2 = new SimpleDateFormat("H", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT_3 = new SimpleDateFormat("(HH:mm)", Locale.ENGLISH);

    private final UIBookingSystemAddPanel bookingSystemAddPanel;
    private final UIBookingSystemFindPanel bookingSystemFindPanel;
    private final UIBookingSystemEditPanel bookingSystemEditPanel;
    private final UIBookingSystemRemovePanel bookingSystemRemovePanel;
    private final UIBookingSystemShowBookingsFound bookingSystemShowBookingsFound;
    private final UIBookingSystemPanel bookingSystemPanel;
    private final List<Integer> listOfBadBookingIDs;
    private final Handler handler;
    private int bookingIDCurrentlyBeingProcessed;

    public BookingHandler(Handler handler) {
        this.handler = handler;

        UIBookingSystemControlPanel bookingSystemControlPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel();
        this.bookingSystemAddPanel = bookingSystemControlPanel.getUIBookingSystemAddPanel();
        this.bookingSystemFindPanel = bookingSystemControlPanel.getUIBookingSystemFindPanel();
        this.bookingSystemEditPanel = bookingSystemControlPanel.getUIBookingSystemEditPanel();
        this.bookingSystemRemovePanel = bookingSystemControlPanel.getUIBookingSystemRemovePanel();
        this.bookingSystemShowBookingsFound = bookingSystemControlPanel.getUIBookingSystemShowBookingsFound();
        this.bookingSystemPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel();

        listOfBadBookingIDs = new ArrayList<>();
        bookingIDCurrentlyBeingProcessed = 1;
    }

    @Override
    public void actionPerformed(ActionEvent eventOccurred) {
        Log log = new Log(eventOccurred.getActionCommand(), this.getClass().getSimpleName(), new Date());
        if (handler.getAccountBusinessLayer().isAccountFound()) {
            switch (eventOccurred.getActionCommand()) {
                case "Import":
                    JFileChooser jFileChooser = new JFileChooser();
                    File file;
                    FileInputStream fileInputStream;
                    XSSFWorkbook workBook;
                    XSSFSheet sheet;
                    XSSFRow row;
                    int rows;
                    Booking importedBooking;
                    try {
                        int returnVal = jFileChooser.showOpenDialog(bookingSystemPanel);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if (jFileChooser.getSelectedFile().getName()
                                    .endsWith(".xlsx")) {
                                file = jFileChooser.getSelectedFile();
                                System.out.println("Opening: " + file.getName() + "."
                                        + "\n");
                                fileInputStream = new FileInputStream(file);
                                workBook = (XSSFWorkbook) WorkbookFactory
                                        .create(new PushbackInputStream(fileInputStream));

                                sheet = workBook.getSheetAt(0);
                                rows = sheet.getPhysicalNumberOfRows();
                                for (int r = 0; r < rows; r++) {
                                    row = sheet.getRow(r);
                                    if (row.toString() != "") {
                                        if (row.getCell((short) 0).toString() != "") {
                                            this.bookingIDCurrentlyBeingProcessed = r;

                                            importedBooking = new Booking(this.bookingIDCurrentlyBeingProcessed,
                                                    validateDayAsString(row.getCell((short) 0).toString()),
                                                    stringToDate(row.getCell((short) 1).toString()),
                                                    stringToTime(row.getCell((short) 2).toString(), false),
                                                    stringToTime(row.getCell((short) 2).toString(), true),
                                                    row.getCell((short) 3).toString(),
                                                    row.getCell((short) 4).toString(),
                                                    new Equipment(row.getCell((short) 5).toString()));

                                            handler.getBookingBusinessLayer().insertBooking(importedBooking);
                                            bookingSystemPanel
                                                    .addBookingToList(importedBooking);
                                        }
                                    }
                                }
                            } else {
                                MessageBox.errorMessageBox(".xlsx spreadsheets are only accepted.");
                                break;
                            }
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        handler.getLoggerBusinessLayer().exceptionCaused(log, e);
                        MessageBox.errorMessageBox(e.toString());
                    }
                    populateBadBookingMessageBox();
                    break;
                case "Search":
                    if (bookingSystemFindPanel.showDialog() == 0) {
                        bookingSystemShowBookingsFound.clearBookingsFromFoundList();
                        this.bookingIDCurrentlyBeingProcessed = 1;
                        Booking bookingToFind = convertStringArrayToBooking(bookingSystemFindPanel.getBookingStringArray());
                        System.out.println(bookingToFind.toString());
                        MessageBox.infoMessageBox("A search will be performed based on the given details." + "\n" + bookingToFind.toString());
                        bookingSystemShowBookingsFound.addBookingsToList(handler.getBookingBusinessLayer().findBookings(bookingToFind));
                        bookingSystemShowBookingsFound.showDialog();
                    }
                    break;
                case "Export":
                    System.out.println("export clicked");
                    break;
                case "Add":
                    if (bookingSystemAddPanel.showDialog() == 0) {
                        Booking newBooking = convertStringArrayToBooking(bookingSystemAddPanel.getBookingStringArray());
                        if (newBooking.isValid()) {
                            handler.getBookingBusinessLayer().insertBooking(newBooking);
                            bookingSystemPanel.addBookingToList(newBooking);
                            log.setBookingIDInserted(newBooking.getBookingID());
                        } else {
                            MessageBox.errorMessageBox("Please enter all of the required details for booking");
                        }
                    }
                    break;
                case "Remove":
                    this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
                    if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                        bookingSystemRemovePanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
                        if (bookingSystemRemovePanel.showDialog() == 0) {
                            handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
                            handler.getBookingBusinessLayer().removeBooking(this.bookingIDCurrentlyBeingProcessed);
                            bookingSystemPanel.removeBookingFromTable();
                            log.setBookingIDDeleted(this.bookingIDCurrentlyBeingProcessed);
                        }
                    } else {
                        MessageBox.warningMessageBox("Please select a booking to remove");
                    }
                    break;
                case "Edit":
                    this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
                    if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                        bookingSystemEditPanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
                        if (bookingSystemEditPanel.showDialog() == 0) {
                            Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
                            if (newBooking.isValid()) {
                                handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
                                handler.getBookingBusinessLayer().modifyBooking(this.bookingIDCurrentlyBeingProcessed, newBooking);
                                bookingSystemPanel.replaceBookingInList(newBooking);
                                log.setBookingIDEdited(this.bookingIDCurrentlyBeingProcessed);
                            } else {
                                MessageBox.errorMessageBox("Please enter all of the required details for booking");
                            }
                        }
                    } else {
                        MessageBox.warningMessageBox("Please select a booking to modify");
                    }
                    break;
                case "Load":
                    handler.getBookingBusinessLayer().populateBookingListOnLoad();
                    bookingSystemPanel.removeAllBookings();
                    for (Object object : IteratorUtils.toList(handler.getBookingBusinessLayer().iterator())) {
                        bookingSystemPanel.addBookingToList((Booking) object);
                    }
                    if (bookingSystemPanel.getRowCountOfTable() == 0) {
                        MessageBox.infoMessageBox("No bookings found.");
                    }
                    break;
                case "Today's":
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.HOUR, 12);
                    date.set(Calendar.MINUTE, 00);
                    date.set(Calendar.SECOND, 00);
                    date.set(Calendar.MILLISECOND, 0);
                    Booking b = new Booking(0, "", new Date(), date.getTime(), date.getTime(), "", "", new Equipment(""));
                    bookingSystemPanel.removeAllBookings();
                    bookingSystemPanel.addBookingsToList(handler.getBookingBusinessLayer().findBookings(b));
                    break;
                case "Filter":

                    break;
                default:
                    System.out.println("control handler not found");
            }
            handler.getLoggerBusinessLayer().insertLog(log);
        }
    }

    private Date stringToDate(String stringToConvert) {
        String s = stringToConvert.replaceAll("[^A-Za-z0-9. ]", "."); //will replace all non-alpha characters with a period
        try {
            return BOOKING_DATE_FORMAT.parse(s); //tries to match the string with the format dd.MM.yy
        } catch (ParseException parseException_1) {
            try {
                return BOOKING_DATE_FORMAT_2.parse(s); //tries to match the string with teh format dd.MMM.yy
            } catch (ParseException parseException_2) {
                listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed); //adds the id of the booking that cannot be properly processed
            }
        }
        return new Date(); // always returns something we can manage
    }

    private Object[] convertStringArrayListToObjectList(ArrayList<String> arrayOfStrings) {
        return new Object[]{arrayOfStrings.get(1),
                stringToDate(arrayOfStrings.get(2)),
                stringToTime(arrayOfStrings.get(3), false),
                stringToTime(arrayOfStrings.get(3), true),
                arrayOfStrings.get(4),
                arrayOfStrings.get(5),
                arrayOfStrings.get(6)};
    }

    private Booking convertStringArrayToBooking(String[] bookingStrings) {
        String stringToValidate = "";
        Date date = new Date();
        if (bookingStrings[1].toString() == "") {  //checking if the date has been entered as an empty string,
            date.setTime(0);                    // if not we set the time as 0 and then proceed to format it
            stringToValidate = BOOKING_DATE_FORMAT.format(date);
        } else {
            stringToValidate = bookingStrings[1];
        }
        return new Booking(this.bookingIDCurrentlyBeingProcessed,
                bookingStrings[0],
                stringToDate(stringToValidate),
                stringToTime(bookingStrings[2], false),
                stringToTime(bookingStrings[3], true),
                bookingStrings[4],
                bookingStrings[5],
                new Equipment(bookingStrings[6]));
    }

    private Date stringToTime(String unVerifiedStringToConvert, boolean collectionTime) {
        String lstrippedUnverifiedStringToConvert = unVerifiedStringToConvert.replaceAll("[a-zA-z ]", "");
        String strippedUnverifiedStringToConvert = lstrippedUnverifiedStringToConvert.replaceAll("[?().]", "");
        int test = 0;
        String verifiedStringToConvert;
        if (!strippedUnverifiedStringToConvert.contains("-")) {
            verifiedStringToConvert = strippedUnverifiedStringToConvert;
        } else {
            if (!collectionTime) {
                verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(0, strippedUnverifiedStringToConvert.indexOf('-'));
            } else {
                verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(strippedUnverifiedStringToConvert.indexOf('-') + 1, strippedUnverifiedStringToConvert.length());
                if (verifiedStringToConvert.equals("")) {
                    verifiedStringToConvert = strippedUnverifiedStringToConvert.replace("-", "");
                }
            }
        }
        try { //check if i can parse to long, if not work another way of converting the unverified string into a usable date format..
            long dayMs = Long.parseLong(unVerifiedStringToConvert);
            return new Date(dayMs);
        } catch (NumberFormatException nfe) {
            try {
                return BOOKING_TIME_FORMAT.parse(verifiedStringToConvert);
            } catch (ParseException parseException_1) {
                try {
                    return BOOKING_TIME_FORMAT_3.parse(verifiedStringToConvert);
                } catch (ParseException parseException_2) {
                    try {
                        test = Integer.parseInt(verifiedStringToConvert);
                        System.out.println(test);
                        if (test > 24 && test % 10 == 0) {
                            test = test / 10;
                        }
                        if (test + 12 < 24) {
                            if (test >= 12) {
                                test = test + 12;
                            }
                        }
                        System.out.println(test);

                        verifiedStringToConvert = Integer.toString(test);
                        return BOOKING_TIME_FORMAT_2.parse(verifiedStringToConvert);
                    } catch (ParseException parseException_3) {
                        if (!listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
                            listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
                        }
                    }
                }
            }
        }
        return new Date();
    }

    private void populateBadBookingMessageBox() {
        String s = "The following bookings with the ids: ";
        if (!listOfBadBookingIDs.isEmpty()) {
            for (int i : listOfBadBookingIDs) {
                s += i + ", ";
            }
            s += " have a incorrectly formatted date or time.";
            listOfBadBookingIDs.clear();
            MessageBox.warningMessageBox(s);
        }
    }

    private String validateDayAsString(String day) {
        String validatedString;
        if (day.matches("Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday")) { //if the string day matches any of these, we do not need to worry about building a validated string
            return day;
        } else {
            char[] chars = day.toCharArray(); //create character array and build it with the string day as a char array
            int validatedStartIndex = 0;
            for (int k = 0; k < chars.length; k++) { //for every character, this loop will try build a string matching the first 3 letters of each day
                if (k + 3 < chars.length) { //if index k+3 is less than the length, we can proceed with the following
                    String dayShort = "";
                    dayShort += chars[k];
                    dayShort += chars[k + 1];
                    dayShort += chars[k + 2];
                    if (dayShort.matches("Mon|Tue|Wed|Thu|Fri|Sat|Sun")) { //if the string matches any of these then break the loop
                        validatedStartIndex = k; //set the validated start index as the current index and then break the loop
                        break;
                    }
                }
            }
            for (int i = 0; i < chars.length; i++) { //this loop will look for the substring "day" and will use the validated start index to build a string consisting of a day of week
                if (chars[i] == 'd') {
                    if (i + 2 < day.length() && day.substring(i, i + 3).equals("day")) {
                        validatedString = day.substring(validatedStartIndex, i + 3);
                        if (validatedString.matches("Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday")) {
                            return validatedString;
                        }
                    }
                }
            }
            return day;
        }
    }
}


//

// This trick ensures that we get the data properly even if it
// doesn't start from first few rows
// for (int i = 0; i < 10 || i < rows; i++) {
// row = sheet.getRow(i);
// if (row != null) {
// tmp = sheet.getRow(i).getPhysicalNumberOfCells();
// if (tmp > cols) cols = tmp;
// }
