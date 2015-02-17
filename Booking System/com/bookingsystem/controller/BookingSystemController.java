package com.bookingsystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.model.Account;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemPanel;
import com.bookingsystem.view.UIBookingSystemTabbedPane;
import com.bookingsystem.view.UIBookingSystemViewPanel;
import com.bookingsystem.view.UILoginPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BookingSystemController {

    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private UIBookingSystemTabbedPane bookingSystemTabbedPane;
    private UIBookingSystemPanel bookingSystemPanel;
    private UIBookingSystemViewPanel bookingSystemViewPanel;

    private Account accountModel; // only need to instantiate it when used

    private boolean loggedInSuccessful;

    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view) {
        this.view = view;
        view.showLoginPanel();
        
        loginPanel = view.getLoginPanel();
        bookingSystemTabbedPane = view.getbookingSystemTabbedPane();
        bookingSystemPanel = bookingSystemTabbedPane.getBookingSystemPanel();
        bookingSystemViewPanel = bookingSystemPanel.getBookingSystemViewPanel();
     

        loginPanel.addSubmitListener(new LoginHandler());
        loginPanel.addClearListener(new ClearHandler());

        view.getMenuBarLoader().addImportOptionListener(new BookingHandler(bookingSystemPanel));

    }

    public void RegisterAccount() {

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
