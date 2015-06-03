package com.bookingsystem.controller.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemAddPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemEditPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemFindPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemRemovePanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemShowBookingsFound;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemBookingPanel;

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
    private final UIBookingSystemBookingPanel bookingSystemPanel;
    private final List<Integer> listOfBadBookingIDs;
    private final Handler handler;
    private int bookingIDCurrentlyBeingProcessed;
    Calendar date1 = Calendar.getInstance();

    public BookingHandler(Handler handler) {
        this.handler = handler;
        date1.set(Calendar.AM_PM,Calendar.AM);
        date1.set(Calendar.DAY_OF_MONTH,25);
        date1.set(Calendar.MONTH,11);
        date1.set(Calendar.HOUR, 00);
        date1.set(Calendar.MINUTE, 00);
        date1.set(Calendar.SECOND, 00);
        date1.set(Calendar.MILLISECOND, 0);
        date1.set(Calendar.YEAR, 2015);

        //date1's date is 25.12.01 -- never bookings on christmas!
        //date1's time is 00:00 -- never bookings at midnight!

        UIBookingSystemBookingControlPanel bookingSystemControlPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel();
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
                    try {
                        int returnVal = jFileChooser.showOpenDialog(bookingSystemPanel);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if (jFileChooser.getSelectedFile().getName()
                                    .endsWith(".xlsx")) {
                                bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
                                File file = jFileChooser.getSelectedFile();
                                try (XSSFWorkbook workBook = (XSSFWorkbook) WorkbookFactory.create(file)) { //creates a smaller footprint when creating the file without a stream
                                    XSSFSheet sheet = workBook.getSheetAt(1);
                                    int rows = sheet.getPhysicalNumberOfRows();
                                    workBook.close();
                                    ArrayList<Booking> bookingList = new ArrayList<Booking>();
                                    for (int r = 1; r < rows; r++) {
                                        XSSFRow row = sheet.getRow(r);
                                        if (row.toString() != "") {
                                            if (row.getCell((short) 0).toString() != "") {
                                                Booking importedBooking = new Booking(r,
                                                        validateDayAsString(row.getCell((short) 0).toString()),
                                                        stringToDate(row.getCell((short) 1).toString()),
                                                        stringToTime(row.getCell((short) 2).toString(), false),
                                                        stringToTime(row.getCell((short) 2).toString(), true),
                                                        row.getCell((short) 3).toString(),
                                                        row.getCell((short) 4).toString(),
                                                        new Equipment(row.getCell((short) 5).toString()));
                                                if (importedBooking.isBeforeToday() && !BOOKING_DATE_FORMAT.format(importedBooking.getBookingDate()).equals("25.12.15")) {
                                                    importedBooking.setBookingCompleted(true); //booking is before today's date therefore doesn't need to be shown
                                                }
                                                bookingList.add(importedBooking);//not the correct id, will only add the problem bookings to the list if the id is correct...need to retrieve the id or add bookings different when importing
                                            }
                                        }
                                    }
                                    handler.getBookingBusinessLayer().insertBookings(bookingList);
                                    bookingSystemPanel.addBookingsToList(bookingList);
                                    for (Booking b : bookingList) {
                                        checkBookingDateTimeForErrors(b);
                                    }
                                    bookingList.clear();
                                    generateBadBookingTable();
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
                        System.out.println(e.toString());
                        MessageBox.errorMessageBox(e.toString());
                    }
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Export":
                    FileOutputStream fos;
                    XSSFWorkbook workbook;
                    XSSFSheet sheet;
                    Row row;
                    try {
                        int dialogResult = JOptionPane.showOptionDialog(null, "Would you like to generate a spreadsheet for all bookings or selected bookings?", "Generate Spreadsheet",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                new String[]{"All", "Selected"}, "All");
                        if (dialogResult == 0 || dialogResult == 1) {
                            JFileChooser jFileChooser1 = new JFileChooser();
                            if (jFileChooser1.showSaveDialog(bookingSystemPanel) == JFileChooser.APPROVE_OPTION) {

                                File f = jFileChooser1.getSelectedFile();
                                fos = new FileOutputStream(f + ".xlsx");
                                workbook = new XSSFWorkbook();
                                sheet = workbook.createSheet("Exported Bookings");

                                String[] titles = new String[]{"Day", "Date", "Time", "Location", "Holder", "Equipment"};
                                row = sheet.createRow(0);
                                for (int i = 0; i < titles.length; i++) {
                                    Cell newCellTitle = row.createCell(i);
                                    newCellTitle.setCellValue(titles[i]);
                                }
                                if (dialogResult == 0) {
                                    int i = 1;
                                    for (Object object : IteratorUtils.toList(handler.getBookingBusinessLayer().iterator())) {
                                        addCells((Booking) object, sheet, i++);
                                    }
                                } else if (dialogResult == 1) {
                                    int i = 1;
                                    if (bookingSystemPanel.selectedRowCount() > 0) {
                                        for (int id : bookingSystemPanel.getSelectedRows()) {
                                            Booking booking = bookingSystemPanel.getBookingFromList(id);
                                            addCells(booking, sheet, i++);
                                        }
                                    } else {
                                        MessageBox.errorMessageBox("No bookings have been selected");
                                    }
                                }
                                workbook.write(fos);
                                fos.flush();
                                fos.close();
                                workbook.close();
                            }
                        }
                    } catch(Exception e) {
                        MessageBox.errorMessageBox(e.toString());
                    }
                break;
                case "Search":
                    if (bookingSystemFindPanel.showDialog() == 0) {
                        bookingSystemShowBookingsFound.clearBookingsFromFoundList();
                        this.bookingIDCurrentlyBeingProcessed = 1;
                        Booking bookingToFind = convertStringArrayToBooking(bookingSystemFindPanel.getBookingStringArray());
                        MessageBox.infoMessageBox("A search will be performed based on the given details." + "\n" + bookingToFind.toString());
                        bookingSystemShowBookingsFound.addBookingsToList(handler.getBookingBusinessLayer().findBookings(bookingToFind));
                        bookingSystemShowBookingsFound.showDialog();
                    }
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Add":
                    if (bookingSystemAddPanel.showDialog() == 0) {
                        Booking newBooking = convertStringArrayToBooking(bookingSystemAddPanel.getBookingStringArray());
                        if (newBooking.isValid()) {
                            if(!newBooking.isBeforeToday()) {
                                handler.getBookingBusinessLayer().insertBooking(newBooking);
                                bookingSystemPanel.addBookingToList(newBooking);
                                checkBookingDateTimeForErrors(newBooking);
                                generateBadBookingTable();
                                log.setBookingIDInserted(newBooking.getBookingID());
                            } else {
                                MessageBox.errorMessageBox("The booking entered has a date before today's.");
                            }
                        } else {
                            MessageBox.errorMessageBox("Please enter all of the required details for booking");
                        }
                    }
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Remove":
                    if(bookingSystemPanel.selectedRowCount() > 1) { //handles multiple removals - cahnge 1 to 0 for a
                        if(JOptionPane.showOptionDialog(null, "Are you sure you wish to remove the selected " + bookingSystemPanel.getSelectedRows().length +" bookings?", "Remove Booking",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                new String[]{"Remove", "Cancel"}, "Remove") == 0) {
                            for (int rowID : bookingSystemPanel.getSelectedRows()) {
                                this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDWithIndex(rowID);
                                if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                                //  handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow()); not needed really..
                                    handler.getBookingBusinessLayer().removeBooking(this.bookingIDCurrentlyBeingProcessed);
                                    if (listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
                                        listOfBadBookingIDs.remove(listOfBadBookingIDs.indexOf(this.bookingIDCurrentlyBeingProcessed));
                                    }
                                    log.setBookingIDDeleted(this.bookingIDCurrentlyBeingProcessed);
                                    handler.getLoggerBusinessLayer().insertLog(log);
                                }
                            }
                            generateBadBookingTable();
                            bookingSystemPanel.removeSelectedRowsFromList();
                        }
                    } else {
                        this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
                        if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                            bookingSystemRemovePanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
                            if (bookingSystemRemovePanel.showDialog() == 0) {
                                handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
                                handler.getBookingBusinessLayer().removeBooking(this.bookingIDCurrentlyBeingProcessed);
                                if (listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
                                    listOfBadBookingIDs.remove(listOfBadBookingIDs.indexOf(this.bookingIDCurrentlyBeingProcessed));
                                }
                                generateBadBookingTable();
                                bookingSystemPanel.removeBookingFromTable();
                                log.setBookingIDDeleted(this.bookingIDCurrentlyBeingProcessed);
                            }
                        } else {
                            MessageBox.warningMessageBox("Please select a booking to remove");
                        }
                        handler.getLoggerBusinessLayer().insertLog(log);
                    }
                    break;
                case "Edit":
                    this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
                    if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                        bookingSystemEditPanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
                        if (bookingSystemEditPanel.showDialog() == 0) {
                            Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
                               if (newBooking.isValid()) {
                                   if(!newBooking.isBeforeToday()) {
                                       checkBookingDateTimeForErrors(newBooking);
                                       generateBadBookingTable();
                                       handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
                                       handler.getBookingBusinessLayer().modifyBooking(this.bookingIDCurrentlyBeingProcessed, newBooking);
                                       bookingSystemPanel.replaceBookingInList(newBooking);
                                       log.setBookingIDEdited(this.bookingIDCurrentlyBeingProcessed);
                                   }
                            } else {
                                MessageBox.errorMessageBox("Please enter all of the required details for booking");
                            }
                        }
                    } else {
                        MessageBox.warningMessageBox("Please select a booking to modify");
                    }
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Load":
                    handler.getBookingBusinessLayer().populateBookingListOnLoad();
                    bookingSystemPanel.removeAllBookings();
                    bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
                    for (Object object : IteratorUtils.toList(handler.getBookingBusinessLayer().iterator())) {
                        if(!((Booking) object).getBookingCompleted()) {
                            checkBookingDateTimeForErrors((Booking) object);
                            bookingSystemPanel.addBookingToList((Booking) object);
                        } else {
                            //add to archived bookings
                        }
                    }
                    generateBadBookingTable();
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Today's":
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.AM_PM, Calendar.AM);
                    date.set(Calendar.HOUR, 00);
                    date.set(Calendar.MINUTE, 00);
                    date.set(Calendar.SECOND, 00);
                    date.set(Calendar.MILLISECOND, 0);
                    Booking b = new Booking(0, "", new Date(), date.getTime(), date.getTime(), "", "", new Equipment(""));
                    bookingSystemPanel.removeAllBookings();
                    bookingSystemPanel.addBookingsToList(handler.getBookingBusinessLayer().findBookings(b));
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Tomorrows":
                    Calendar date2 = Calendar.getInstance();
                    Date d = date2.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("d");
                    String dayOfMonth_STRING = sdf.format(d);
                    int dayOfMonth = Integer.parseInt(dayOfMonth_STRING);
                    date2.set(Calendar.DAY_OF_MONTH, (dayOfMonth + 1));
                    date2.set(Calendar.AM_PM,Calendar.AM);
                    date2.set(Calendar.HOUR, 00);
                    date2.set(Calendar.MINUTE, 00);
                    date2.set(Calendar.SECOND, 00);
                    date2.set(Calendar.MILLISECOND, 0);
                    Booking b1 = new Booking(0, "", date2.getTime(), date2.getTime(), date2.getTime(), "", "", new Equipment(""));
                    bookingSystemPanel.removeAllBookings();
                    bookingSystemPanel.addBookingsToList(handler.getBookingBusinessLayer().findBookings(b1));
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Complete":
                    if(bookingSystemPanel.selectedRowCount() > 0) { //handles multiple removals - cahnge 1 to 0 for a
                        if (JOptionPane.showOptionDialog(null, "Are you sure you wish to complete the selected " + bookingSystemPanel.getSelectedRows().length + " bookings?", "Remove Booking",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                new String[]{"Complete", "Cancel"}, "Add") == 0) {
                            for (int rowID : bookingSystemPanel.getSelectedRows()) {
                                this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDWithIndex(rowID);
                                handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
                                handler.getBookingBusinessLayer().completeBooking(this.bookingIDCurrentlyBeingProcessed);
                                log.setBookingIDEdited(this.bookingIDCurrentlyBeingProcessed);
                                handler.getLoggerBusinessLayer().insertLog(log);
                            }
                            bookingSystemPanel.removeSelectedRowsFromList();
                        }
                        generateBadBookingTable();
                    } else {
                        MessageBox.warningMessageBox("Please select a booking to complete");
                    }
                    break;
                default:
                    System.out.println("control handler not found");
            }
            handler.getView().repaint();
        }
    }

    private void addCells(Booking booking, XSSFSheet sheet, int rowN) {
        int cellN = 0;
        Row row = sheet.createRow(rowN);
        while (cellN <= 5) {
            Cell newCell = row.createCell(cellN);
            switch (cellN++) {
                case 0: newCell.setCellValue(booking.getBookingDay());
                    break;
                case 1: newCell.setCellValue(BOOKING_DATE_FORMAT.format(booking.getBookingDate()));
                    break;
                case 2: newCell.setCellValue(BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()) + "-" +
                        BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()));
                    break;
                case 3: newCell.setCellValue(booking.getBookingLocation());
                    break;
                case 4: newCell.setCellValue(booking.getBookingHolder());
                    break;
                case 5: newCell.setCellValue(booking.getRequiredEquipment().getEquipmentName());
                    break;
            }
        }
        cellN = 0;
    }

    private void checkBookingDateTimeForErrors(Booking booking) {
        if (BOOKING_TIME_FORMAT.format(booking.getBookingStartTime()).equals("00:00")
                || BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()).equals("00:00")
                || BOOKING_DATE_FORMAT.format(booking.getBookingDate()).equals("25.12.15")) {

            if (!listOfBadBookingIDs.contains(booking.getBookingID())) {
                listOfBadBookingIDs.add(booking.getBookingID());
            }
        } else {
            if (listOfBadBookingIDs.contains(booking.getBookingID())) {
                listOfBadBookingIDs.remove(listOfBadBookingIDs.indexOf(booking.getBookingID()));
            }
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
//                if (!listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
//                    listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
//                }
            }
        }
        return date1.getTime();
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
                        if (test > 24 && test % 10 == 0) {
                            test = test / 10;
                        }
                        if (test + 12 < 24) {
                            if (test >= 12) {
                                test = test + 12;
                            }
                        }
                        verifiedStringToConvert = Integer.toString(test);
                        return BOOKING_TIME_FORMAT_2.parse(verifiedStringToConvert);
                    } catch (Exception parseException_3) {
//                        if (!listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
//                            listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
//                        }
                    }
                }
            }
        }
        return date1.getTime();
    }

    private void generateBadBookingTable() {
        bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
        for (Object object : IteratorUtils.toList(handler.getBookingBusinessLayer().iterator())) {
            if(!((Booking) object).getBookingCompleted()) {
                for (int i = 0; i < listOfBadBookingIDs.size(); i++) {
                    if (((Booking) object).getBookingID() == listOfBadBookingIDs.get(i)
                            && !bookingSystemPanel.getBookingSystemViewPanel().isRowInTable(((Booking) object).getBookingID())) { //checks if the booking id is already in the table of problems
                        bookingSystemPanel.getBookingSystemViewPanel().addProblemToList(((Booking) object));
                    }
                }
            }
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