package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.ArchiveTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.view.dialogpanels.UIBookingSystemDialogPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemEditPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemFindPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemBookingPanel;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public final class BookingHandler implements ActionListener {

    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT_2 = new SimpleDateFormat("dd.MMM.yy", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT_2 = new SimpleDateFormat("H", Locale.ENGLISH);
    private static final DateFormat BOOKING_TIME_FORMAT_3 = new SimpleDateFormat("(HH:mm)", Locale.ENGLISH);

    private final UIBookingSystemDialogPanel bookingSystemAddPanel;
    private final UIBookingSystemFindPanel bookingSystemFindPanel;
    private final UIBookingSystemEditPanel bookingSystemEditPanel;
    private final UIBookingSystemBookingPanel bookingSystemPanel;
    private final List<Integer> listOfBadBookingIDs;
    private final Handler handler;
    private int bookingIDCurrentlyBeingProcessed;
    private final BookingTableModel bookingTableModel;
    private final ArchiveTableModel archiveTableModel;
    private final Calendar date1 = Calendar.getInstance();

    @SuppressWarnings("unchecked")
    public BookingHandler(Handler handler) {
        this.handler = handler;
        date1.set(Calendar.AM_PM,Calendar.AM);
        date1.set(Calendar.DAY_OF_MONTH,25);
        date1.set(Calendar.MONTH,11);
        date1.set(Calendar.HOUR, 0);
        date1.set(Calendar.MINUTE, 0);
        date1.set(Calendar.SECOND, 0);
        date1.set(Calendar.MILLISECOND, 0);
        date1.set(Calendar.YEAR, 2015);

        //date1's date is 25.12.01 -- never bookings on christmas!
        //date1's time is 00:00 -- never bookings at midnight!

        UIBookingSystemBookingControlPanel bookingSystemControlPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel();
        this.bookingSystemAddPanel = bookingSystemControlPanel.getUIBookingSystemAddPanel();
        this.bookingSystemFindPanel = bookingSystemControlPanel.getUIBookingSystemFindPanel();
        this.bookingSystemEditPanel = bookingSystemControlPanel.getUIBookingSystemEditPanel();
        this.bookingSystemPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel();
        this.bookingTableModel = bookingSystemPanel.getJTableModel();
        this.archiveTableModel = handler.getView().getBookingSystemTabbedPane().getBookingSystemArchive().getJTableModel();
        listOfBadBookingIDs = new ArrayList<>();
        this.checkBookingDateTimeForErrors(IteratorUtils.toList(bookingTableModel.iterator()));
        generateBadBookingTable();
        bookingIDCurrentlyBeingProcessed = 1;
        initialiseDialogs();
        

        //these can be handled else where!
        bookingSystemPanel.getBookingSystemViewPanel().getBookingSystemJTableProblems().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if(handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel()>0) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                int id = (int)bookingSystemPanel.getBookingSystemViewPanel().getBookingSystemJTableProblems().getValueAt(row,0);
                Booking b = bookingTableModel.getBooking(id);
                System.out.println(b);
                if (b != null) {
                    if (me.getClickCount() == 2) {
                        bookingSystemEditPanel.setTextOfComponents(bookingTableModel.getBooking(id));
                        if (bookingSystemEditPanel.showDialog() == 0) {
                            Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
                            if (newBooking.isValid()) {
                                if (!newBooking.isBeforeToday()) {
                                    List<Booking> editBookingCheck = new ArrayList<>();
                                    editBookingCheck.add(newBooking);
                                    checkBookingCollision(newBooking);
                                    checkBookingDateTimeForErrors(editBookingCheck);
                                    generateBadBookingTable();
                                    handler.getBookingBusinessLayer().modifyBooking(bookingTableModel.getBooking(id), newBooking);
                                    ActionEvent actionEvent = new ActionEvent(this, 0, "Refresh");
                                    actionPerformed(actionEvent);
                                }
                            } else {
                                MessageBox.errorMessageBox("Please enter all of the required details for booking");
                            }
                        }
                    } else if (me.getClickCount() == 1) {
                        int rowInBookingList = bookingSystemPanel.returnRowIndexForValue(b.getBookingID());
                        bookingSystemPanel.changeSelection(rowInBookingList);

                    }
                }
                }
            }
        });
        
        bookingSystemPanel.getBookingSystemJTable().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if(handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel()>0 && bookingTableModel.getRowCount() > 0) {
                    JTable table = (JTable) me.getSource();
                    Point p = me.getPoint();
                    int row = table.rowAtPoint(p);
                    int id = (int) bookingSystemPanel.getBookingSystemJTable().getValueAt(row, 0);
                    Booking b = bookingTableModel.getBooking(id);
                    if (b != null) {
                        if (me.getClickCount() == 2) {
                            bookingSystemEditPanel.setTextOfComponents(bookingTableModel.getBooking(id));
                            if (bookingSystemEditPanel.showDialog() == 0) {
                                Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
                                if (newBooking.isValid()) {
                                    if (!newBooking.isBeforeToday()) {
                                        List<Booking> editBookingCheck = new ArrayList<>();
                                        editBookingCheck.add(newBooking);
                                        checkBookingCollision(newBooking);
                                        checkBookingDateTimeForErrors(editBookingCheck);
                                        generateBadBookingTable();
                                        handler.getBookingBusinessLayer().modifyBooking(bookingTableModel.getBooking(id), newBooking);
                                        ActionEvent actionEvent = new ActionEvent(this, 0, "Refresh");
                                        actionPerformed(actionEvent);
                                    }
                                } else {
                                    MessageBox.errorMessageBox("Please enter all of the required details for booking");
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
	@Override
    public void actionPerformed(ActionEvent eventOccurred) {
        Log log = new Log(eventOccurred.getActionCommand(), this.getClass().getSimpleName(), new Date());
        if (handler.getAccountBusinessLayer().isAccountFound()) {
            switch (eventOccurred.getActionCommand()) {
                case "Import":
                	if (handler.getAccountBusinessLayer().getAccountLoggedIn().getUserLevel() >=2) {
                    JFileChooser jFileChooser = new JFileChooser();
                    try {
                        int returnVal = jFileChooser.showOpenDialog(bookingSystemPanel);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if (jFileChooser.getSelectedFile().getName()
                                    .endsWith(".xlsx")) {
                            	System.out.println("opened sheet");
                                bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
                                File file = jFileChooser.getSelectedFile();
                                try (XSSFWorkbook workBook = (XSSFWorkbook) WorkbookFactory.create(file)) { //creates a smaller footprint when creating the file without a stream
                                    int dialogResult = 0;
                                    System.out.println(workBook.getNumberOfSheets());
                                    if (workBook.getNumberOfSheets() > 1) {
                                        String[] sheetNumbers = new String[workBook.getNumberOfSheets()];
                                        int i = 0;
                                        while (i < workBook.getNumberOfSheets()) {
                                            sheetNumbers[i] = ""+ workBook.getSheetAt(i).getSheetName();
                                            i++;
                                        }
                                        dialogResult = JOptionPane.showOptionDialog(null, "Which sheet of the document would you like to import, check if you're not sure.", "Generate Spreadsheet",
                                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                                sheetNumbers, "");
                                    }

                                    XSSFSheet sheet = workBook.getSheetAt(dialogResult);

                                    //check for more sheets!!
                                    int rows = sheet.getPhysicalNumberOfRows();
                                    workBook.close();
                                    ArrayList<Booking> bookingList = new ArrayList<>();
                                    for (int r = 1; r < rows; r++) {
                                        XSSFRow row = sheet.getRow(r);
                                        if (row.toString() != "") {
                                            if (row.getCell((short) 0).toString() != "") {
                                                dialogResult = 0;
                                                Equipment equipment = handler.getBookingBusinessLayer().getEquipments().getEquipmentWithName(row.getCell((short) 5).toString().trim());
                                                if (equipment == null) {
                                                    dialogResult = JOptionPane.showOptionDialog(null, "The equipment " + row.getCell((short) 5).toString() + " could not be found in the database. Would you like to add it?\nIf not the current booking will not be imported.", "Equipment",
                                                            JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,
                                                            new String[]{"Yes", "No"}, "Yes");
                                                    if (dialogResult == 0) {
                                                        equipment = new Equipment(row.getCell((short) 5).toString());
                                                        equipment.setEquipmentDescription("");
                                                        equipment.setEquipmentUsage(0);
                                                        System.out.println(equipment.getEquipmentID());
                                                        handler.getBookingBusinessLayer().getEquipments().addEquipment(equipment);
                                                        System.out.println(equipment.getEquipmentID());
                                                    }
                                                }
                                                if (equipment != null) {
                                                    equipment.increaseEquipmentUsage();
                                                    Booking importedBooking = new Booking(r,
                                                            validateDayAsString(row.getCell((short) 0).toString()),
                                                            stringToDate(row.getCell((short) 1).toString()),
                                                            stringToTime(row.getCell((short) 2).toString(), false),
                                                            stringToTime(row.getCell((short) 2).toString(), true),
                                                            row.getCell((short) 3).toString(),
                                                            row.getCell((short) 4).toString(),
                                                            equipment);
                                                    if (importedBooking.isBeforeToday() && !BOOKING_DATE_FORMAT.format(importedBooking.getBookingDate()).equals("25.12.15")) {
                                                        importedBooking.setBookingCompleted(true); //booking is before today's date therefore doesn't need to be shown
                                                    }
                                                    bookingList.add(importedBooking);
                                                }
                                            }
                                        }
                                    }
                                    handler.getBookingBusinessLayer().insertBookings(bookingList);
                                    ActionEvent actionEvent = new ActionEvent(this, 0, "Refresh");
                                    ActionEvent actionEvent2 = new ActionEvent(this, 0, "Load Archive");
                                    actionPerformed(actionEvent);
                                    actionPerformed(actionEvent2);
                                }
                            } else {
                                MessageBox.errorMessageBox(".xlsx spreadsheets are only accepted.");
                                break;
                            }
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        handler.getLoggerBusinessLayer().exceptionCaused();
                        e.printStackTrace();
                        MessageBox.errorMessageBox(e.toString());
                    }
                	} else {
                		MessageBox.errorMessageBox("Insufficient access privileges for operation.");
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
                                } else {
                                    int i = 1;
                                    if (bookingSystemPanel.selectedRowCount() > 0) {
                                        for (int rowID : bookingSystemPanel.getSelectedRows()) {
                                            int modelRow = bookingSystemPanel.rowViewIndexToModel(rowID);
                                            int bookingID = (int) bookingSystemPanel.getValueAt(modelRow, 0);
                                            System.out.println(bookingID);
                                            Booking booking = bookingTableModel.getBooking(bookingID);
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
                        this.bookingIDCurrentlyBeingProcessed = 1;
                        Booking bookingToFind = convertStringArrayToBooking(bookingSystemFindPanel.getBookingStringArray());
                        bookingTableModel.clearBookingList();
                 
                        System.out.println(bookingToFind.getRequiredEquipment().getEquipmentID());
                        bookingTableModel.addBookingList(handler.getBookingBusinessLayer().findBookings(bookingToFind));
                    }
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Add":
                    if (bookingSystemAddPanel.showDialog() == 0) {
                        Booking newBooking = convertStringArrayToBooking(bookingSystemAddPanel.getBookingStringArray());
                        newBooking.setIsRecurring(bookingSystemAddPanel.getRecurringSelected());
                        List<Booking> bookingListCheck = new ArrayList<>();
                        if(bookingSystemAddPanel.getRecurringSelected()) {
                        	newBooking.setWeeksRecurring(bookingSystemAddPanel.getWeeksRecurringFor());
                        }
                        if (newBooking.isValid()) {
                            if(!newBooking.isBeforeToday()) {
                            	if(newBooking.getIsRecurringBooking() && newBooking.getWeeksRecurring() > 0) {
                            		int week = 0;
                            		for (;week<newBooking.getWeeksRecurring();week++) {
                            			handler.getBookingBusinessLayer().insertBooking(newBooking);
                            			bookingTableModel.addBooking(new Booking(newBooking.getBookingID(),newBooking.getBookingDay(),
                           					newBooking.getBookingDate(), newBooking.getBookingStartTime(),
                           					newBooking.getBookingCollectionTime(), newBooking.getBookingLocation(),
                            					newBooking.getBookingHolder(), newBooking.getRequiredEquipment()));
                            			bookingListCheck.add(newBooking);
    	                                generateBadBookingTable();
                                        checkBookingCollision(newBooking);
    	                                Calendar cal = Calendar.getInstance();   
    	                                cal.setTime(newBooking.getBookingDate());
    	                                System.out.println(cal.getTime().toString());
    	                                cal.add(Calendar.WEEK_OF_YEAR, 1);
                                        newBooking.setBookingDate(cal.getTime());
                            		}
                            	} else {
                                    checkBookingCollision(newBooking);
                                    handler.getBookingBusinessLayer().insertBooking(newBooking);
                                    bookingTableModel.addBooking(newBooking);
	                                generateBadBookingTable();
                                    log.setBookingIDInserted(newBooking.getBookingID());
                                }
                                checkBookingDateTimeForErrors(bookingListCheck);
                                bookingListCheck.clear();
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
                    if(bookingSystemPanel.selectedRowCount() > 0) { //handles multiple removals - change 1 to 0 for a
                        if (JOptionPane.showOptionDialog(null, "Are you sure you wish to remove the selected " + bookingSystemPanel.getSelectedRows().size() + " bookings?", "Remove Booking",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                new String[]{"Remove", "Cancel"}, "Remove") == 0) {
                            for (int rowID : bookingSystemPanel.getSelectedRows()) {
                                this.bookingIDCurrentlyBeingProcessed = (int) bookingSystemPanel.getValueAt(rowID, 0);
                                if (this.bookingIDCurrentlyBeingProcessed >= 0) {
                                    handler.getBookingBusinessLayer().removeBooking(bookingTableModel.getBooking(this.bookingIDCurrentlyBeingProcessed));
                                    if (listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
                                        listOfBadBookingIDs.remove(listOfBadBookingIDs.indexOf(this.bookingIDCurrentlyBeingProcessed));
                                    }
                                    log.setBookingIDDeleted(this.bookingIDCurrentlyBeingProcessed);
                                    handler.getLoggerBusinessLayer().insertLog(log);
                                }
                            }
                            bookingTableModel.removeRows(bookingSystemPanel.getSelectedRows());
                            generateBadBookingTable();
                        }
                    } else {
                        MessageBox.warningMessageBox("Please select a booking to remove");
                    }


                    break;
                case "Edit":
                    if(bookingSystemPanel.selectedRowCount() > 0) {
                    int modelRow = bookingSystemPanel.rowViewIndexToModel(bookingSystemPanel.getSelectedRow());
                    this.bookingIDCurrentlyBeingProcessed = (int) bookingSystemPanel.getValueAt(modelRow, 0);
                        bookingSystemEditPanel.setTextOfComponents(bookingTableModel.getBooking(this.bookingIDCurrentlyBeingProcessed));
                        if (bookingSystemEditPanel.showDialog() == 0) {
                            Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
                               if (newBooking.isValid()) {
                                   if(!newBooking.isBeforeToday()) {
                                	   List<Booking> editBookingCheck = new ArrayList<>();
                                	   editBookingCheck.add(newBooking);
                                       checkBookingCollision(newBooking);
                                       checkBookingDateTimeForErrors(editBookingCheck);
                                       generateBadBookingTable();
                                       handler.getBookingBusinessLayer().modifyBooking(bookingTableModel.getBooking(this.bookingIDCurrentlyBeingProcessed),newBooking);
                                       bookingTableModel.setValueAt(newBooking.getBookingDay(),modelRow,1);
                                       bookingTableModel.setValueAt(newBooking.getBookingDate(),modelRow,2);
                                       bookingTableModel.setValueAt(newBooking.getBookingStartTime(), modelRow, 3);
                                       bookingTableModel.getBooking(this.bookingIDCurrentlyBeingProcessed).setBookingCollectionTime(newBooking.getBookingCollectionTime());
                                       bookingTableModel.setValueAt(newBooking.getBookingLocation(),modelRow,4);
                                       bookingTableModel.setValueAt(newBooking.getBookingHolder(),modelRow,5);
                                       bookingTableModel.setValueAt(newBooking.getRequiredEquipment(), modelRow, 6);

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
                case "Refresh":
                    bookingTableModel.clearBookingList();
                    bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
                    handler.getBookingBusinessLayer().populateBookingListOnLoad();
                    bookingTableModel.addBookingList(IteratorUtils.toList(handler.getBookingBusinessLayer().iterator()));
                    checkBookingDateTimeForErrors(IteratorUtils.toList(handler.getBookingBusinessLayer().iterator()));
                    generateBadBookingTable();
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Load Archive":
                    archiveTableModel.clearArchiveList();
                    archiveTableModel.addBookingList(IteratorUtils.toList(handler.getBookingBusinessLayer().getArchivedBookings().iterator()));
                    break;
                case "Today's":
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.AM_PM, Calendar.AM);
                    date.set(Calendar.HOUR, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);
                    Booking b = new Booking(0, "", new Date(), date.getTime(), date.getTime(), "", "", new Equipment(""));
                    bookingTableModel.clearBookingList();
                    bookingTableModel.addBookingList(handler.getBookingBusinessLayer().findBookings(b));
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
                    date2.set(Calendar.HOUR, 0);
                    date2.set(Calendar.MINUTE, 0);
                    date2.set(Calendar.SECOND, 0);
                    date2.set(Calendar.MILLISECOND, 0);
                    Booking b1 = new Booking(0, "", date2.getTime(), date2.getTime(), date2.getTime(), "", "", new Equipment(""));
                    bookingTableModel.clearBookingList();
                    bookingTableModel.addBookingList(handler.getBookingBusinessLayer().findBookings(b1));
                    handler.getLoggerBusinessLayer().insertLog(log);
                    break;
                case "Complete":
                    if(bookingSystemPanel.selectedRowCount() > 0) { //handles multiple removals - change 1 to 0 for a
                        if (JOptionPane.showOptionDialog(null, "Are you sure you wish to complete the selected " + bookingSystemPanel.getSelectedRows().size() + " bookings?", "Remove Booking",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
                                new String[]{"Complete", "Cancel"}, "Add") == 0) {
                            for (int rowID : bookingSystemPanel.getSelectedRows()) {
                                this.bookingIDCurrentlyBeingProcessed = (int) bookingSystemPanel.getValueAt(rowID, 0);
                                handler.getBookingBusinessLayer().completeBooking(this.bookingIDCurrentlyBeingProcessed);
                                archiveTableModel.addBooking(bookingTableModel.getBooking(this.bookingIDCurrentlyBeingProcessed));
                                log.setBookingIDEdited(this.bookingIDCurrentlyBeingProcessed);
                                handler.getLoggerBusinessLayer().insertLog(log);
                            }
                            bookingTableModel.removeRows(bookingSystemPanel.getSelectedRows());
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
    }

    void checkBookingDateTimeForErrors(List<Booking> bookingList) {
    	for (Booking booking : bookingList) {
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
    }

    void checkBookingCollision(Booking booking) {
            List<Integer> listOfBookingIdsThatCollide = handler.getBookingBusinessLayer().isRoomFree(booking);
        StringBuilder stringBuilder = new StringBuilder();
            if(listOfBookingIdsThatCollide.size() > 0) {
                stringBuilder.append(listOfBookingIdsThatCollide.size() == 1 ? " booking." : " bookings.");
                MessageBox.errorMessageBox("The following booking '" + booking.getBookingDay() +", "+ BOOKING_DATE_FORMAT.format(booking.getBookingDate()) + ", " + booking.getBookingLocation() +", " +
                        BOOKING_TIME_FORMAT.format(booking.getBookingStartTime())+ "-" + BOOKING_TIME_FORMAT.format(booking.getBookingCollectionTime()) + "'" +
                " collides with " + listOfBookingIdsThatCollide.size() + stringBuilder.toString());
            }
    }

    private Date stringToDate(String stringToConvert) {
        String s = stringToConvert.replaceAll("[^A-Za-z0-9. ]", "."); //will replace all non-alpha characters with a period
        try {
            return BOOKING_DATE_FORMAT.parse(s); //tries to match the string with the format dd.MM.yy
        } catch (ParseException parseException_1) {
            try {
                return BOOKING_DATE_FORMAT_2.parse(s); //tries to match the string with teh format dd.MMM.yy
            } catch (ParseException ignored) {
            }
        }
        return date1.getTime();
    }

    private Booking convertStringArrayToBooking(String[] bookingStrings) {
        String stringToValidate;
        Date date = new Date();
        if (bookingStrings[1] == "") {  //checking if the date has been entered as an empty string,
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
                handler.getBookingBusinessLayer().getEquipments().getEqiupment(Integer.parseInt(bookingStrings[6])));
    }

    private Date stringToTime(String unVerifiedStringToConvert, boolean collectionTime) {
        String lstrippedUnverifiedStringToConvert = unVerifiedStringToConvert.replaceAll("[a-zA-z ]", "");
        String strippedUnverifiedStringToConvert = lstrippedUnverifiedStringToConvert.replaceAll("[?().]", "");
        int test;
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
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        return date1.getTime();
    }

    private void generateBadBookingTable() {
        bookingSystemPanel.getBookingSystemViewPanel().removeAllProblems();
        //checks if the booking id is already in the table of problems
//checks if the booking id is already in the table of problems
        IteratorUtils.toList(handler.getBookingBusinessLayer().iterator()).stream().filter(object -> !((Booking) object).getBookingCompleted()).forEach(object -> {  //checks if the booking id is already in the table of problems
            listOfBadBookingIDs.stream().filter(listOfBadBookingID -> ((Booking) object).getBookingID() == listOfBadBookingID && !bookingSystemPanel.getBookingSystemViewPanel().isRowInTable(((Booking) object).getBookingID())).forEach(listOfBadBookingID -> { //checks if the booking id is already in the table of problems
                bookingSystemPanel.getBookingSystemViewPanel().addProblemToList(((Booking) object));
            });
        });
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

    private void initialiseDialogs() {
        bookingSystemAddPanel.setEquipmentJComboBox(IteratorUtils.toList(handler.getBookingBusinessLayer().getEquipments().iterator()));
        bookingSystemFindPanel.setEquipmentJComboBox(IteratorUtils.toList(handler.getBookingBusinessLayer().getEquipments().iterator()));
        bookingSystemEditPanel.setEquipmentJComboBox(IteratorUtils.toList(handler.getBookingBusinessLayer().getEquipments().iterator()));
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