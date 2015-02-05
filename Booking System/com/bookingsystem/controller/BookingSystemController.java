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

        bookingSystemPanel.addSubmitListener(new BookingHandler());
    }

    public void RegisterAccount() {

    }

    public class BookingHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            JFileChooser jFileChooser = new JFileChooser();
            long startTime = System.currentTimeMillis();
            
            File file;
            FileInputStream fileInputStream;
            XSSFWorkbook workBook = null;
            XSSFSheet sheet = null;
            XSSFRow row;
            XSSFCell cell;
            int rows = 0;
            try {
                int returnVal = jFileChooser.showOpenDialog(view);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                	if (jFileChooser.getSelectedFile().getName().endsWith(".xlsx")) {
                		file = jFileChooser.getSelectedFile();
                		System.out.println("Opening: " + file.getName() + "." + "\n");
                		fileInputStream = new FileInputStream(file);
                		workBook = (XSSFWorkbook) WorkbookFactory.create(new PushbackInputStream
                        		(fileInputStream));
                		fileInputStream.close();
                		sheet = workBook.getSheetAt(0);
                        rows = sheet.getPhysicalNumberOfRows();
                	} else { throw new NullPointerException(); }
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
            } catch (Exception e) {
            	
            }
           // int cols = 0;
         //   int tmp = 0;

            //This trick ensures that we get the data properly even if it doesn't start from first few rows
            //for (int i = 0; i < 10 || i < rows; i++) {
                //row = sheet.getRow(i);
                //if (row != null) {
                //   tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                //   if (tmp > cols) cols = tmp;
              // }

            Booking importedBooking = null;

            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    if (row.getCell((short) 0) != null) {
                        importedBooking = new Booking(r,
                                row.getCell((short) 0).toString(),
                                row.getCell((short) 1).toString(),
                                row.getCell((short) 2).toString(),
                                row.getCell((short) 3).toString(),
                                row.getCell((short) 4).toString(),
                                new Equipment(r, row.getCell((short) 5).toString()));
                    }
                    bookingSystemPanel.addBookingToList(importedBooking);
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
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
