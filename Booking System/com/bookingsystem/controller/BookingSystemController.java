package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.controller.handler.LoginHandler;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemControlPanel;
import com.bookingsystem.view.UIBookingSystemPanel;
import com.bookingsystem.view.UIBookingSystemTabbedPane;
import com.bookingsystem.view.UIBookingSystemViewPanel;
import com.bookingsystem.view.UILoginPanel;

public class BookingSystemController {

    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private UIBookingSystemTabbedPane bookingSystemTabbedPane;
    private UIBookingSystemPanel bookingSystemPanel;
    private UIBookingSystemViewPanel bookingSystemViewPanel;
    private UIBookingSystemControlPanel bookingSystemControlPanel;


    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view) {
        this.view = view;
        view.showLoginPanel();
        
        loginPanel = view.getLoginPanel();
        bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        bookingSystemPanel = bookingSystemTabbedPane.getBookingSystemPanel();
        bookingSystemViewPanel = bookingSystemPanel.getBookingSystemViewPanel();
        bookingSystemControlPanel = bookingSystemPanel.getBookingSystemControlPanel();

        loginPanel.addSubmitListener(new LoginHandler(view));
        loginPanel.addClearListener(new LoginHandler(view));

        view.getMenuBarLoader().addImportOptionListener(new BookingHandler(bookingSystemPanel));
        bookingSystemControlPanel.addListeners(new BookingHandler(bookingSystemPanel));
    }
}
