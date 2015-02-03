package com.bookingsystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookingsystem.model.Account;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemPanel;
import com.bookingsystem.view.UILoginPanel;
import sun.management.counter.Counter;
import sun.management.counter.Units;
import sun.management.counter.Variability;

import javax.swing.*;

public class BookingSystemController {

    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private UIBookingSystemPanel bookingSystemPanel;

    private Account accountModel; // only need to instantiate it when used

    private boolean loggedInSuccessful;

    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view) {
        this.view = view;
        view.showLoginPanel();

        bookingSystemPanel = view.getBookingSystemPanel();
        loginPanel = view.getLoginPanel();

        loginPanel.addSubmitListener(new LoginHandler());
        loginPanel.addClearListener(new ClearHandler());

        bookingSystemPanel = view.getBookingSystemPanel();

        bookingSystemPanel.addSubmitListener(new AddBookingHandler());
    }

    public void RegisterAccount() {

    }

    public class AddBookingHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ///List<Equipment> equipment = new ArrayList<Equipment>();
            //Booking booking = new Booking(0,"","","","",equipment,"");

            //bookingSystemPanel.addBookingToList(booking);
            JFileChooser fc = new JFileChooser();


            long startTime = System.nanoTime();

            try {
                File file = null;
                int returnVal = fc.showOpenDialog(view);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName() + "." + "\n");
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
                //FileOutputStream stream = new FileOutputStream(f);
                FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(new PushbackInputStream(fileInputStream));
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                int rows = sheet.getPhysicalNumberOfRows();

                int cols = 6; // No of columns

                int tmp = 0;


                // This trick ensures that we get the data properly even if it doesn't start from first few rows
              //  for (int i = 0; i < 10 || i < rows; i++) {

              //      row = sheet.getRow(i);
             //       if (row != null) {
             //           tmp = sheet.getRow(i).getPhysicalNumberOfCells();
              //          if (tmp > cols) cols = tmp;
             //       }
             //   }

                List<Booking> test = new ArrayList<Booking>();
                String dayOfBooking = "";
                String bookingname = "";
                String bookingLocation = "";
                String bookingTime = "";
                String bookingDate = "";
                List<Equipment> equipments = new ArrayList<Equipment>();
                String booker = "";
                Equipment e = null;

                for (int r = 0; r < rows; r++) {
                    row = sheet.getRow(r);
                    if (row != null) {
                        for (int c = 0; c < cols; c++) {
                            cell = row.getCell((short) c);
                            if (cell != null) {
                                switch (c) {
                                    case 0:
                                        dayOfBooking = cell.toString();
                                        ;
                                    case 1:
                                        bookingDate = cell.toString();
                                        ;
                                    case 2:
                                        bookingTime = cell.toString();
                                        ;
                                    case 3:
                                        bookingLocation = cell.toString();
                                        ;
                                    case 4:
                                        booker = cell.toString();
                                        ;
                                    case 5:
                                        e = new Equipment(r, cell.toString());
                                        ;
                                }
                                System.out.println(cell.toString().replaceAll("\\s+", ""));
                            }
                        }
                        equipments.add(e);
                        test.add(new Booking(r, dayOfBooking, bookingDate, bookingTime, bookingLocation, booker, equipments));
                        equipments.clear();

                    }
                }
                bookingSystemPanel.addBookingsToList(test);
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
            long endTime = System.nanoTime();
            System.out.println(endTime - startTime / 1000000000);

        }

    }

    public class LoginHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String username, unHashedPassword;
            username = loginPanel.GetLoginUsernameText();
            unHashedPassword = loginPanel.GetLoginPasswordText();

            accountModel = new Account(0, 0, username, unHashedPassword);
            loggedInSuccessful = accountModel.login();
            System.out.println(accountModel.toString() + loggedInSuccessful);

            if (loggedInSuccessful) {
                view.removeLoginPanel();
                view.showBookingSystemPanel();
                view.setVisible(true);
            }
        }
    }

    public class ClearHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            loginPanel.ClearTextBoxes();
        }
    }
}
