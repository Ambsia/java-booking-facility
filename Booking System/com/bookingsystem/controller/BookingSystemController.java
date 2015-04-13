package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.controller.handler.LoginHandler;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.panelparts.UIBookingSystemControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemPanel;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;
import com.bookingsystem.view.panelparts.UIBookingSystemViewPanel;
import com.bookingsystem.view.UILoginPanel;

public class BookingSystemController {

    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private UIBookingSystemTabbedPane bookingSystemTabbedPane;
    private UIBookingSystemPanel bookingSystemPanel;
    private UIBookingSystemViewPanel bookingSystemViewPanel;
    private UIBookingSystemControlPanel bookingSystemControlPanel;
    private BookingBusinessLayer model;


    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view, BookingBusinessLayer model) {
        this.view = view;
        view.showLoginPanel();
        
        loginPanel = view.getLoginPanel();
        bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        bookingSystemPanel = bookingSystemTabbedPane.getBookingSystemPanel();
        bookingSystemViewPanel = bookingSystemPanel.getBookingSystemViewPanel();
        bookingSystemControlPanel = bookingSystemPanel.getBookingSystemControlPanel();
        loginPanel.addSubmitListener(new LoginHandler(view));
        loginPanel.addClearListener(new LoginHandler(view));

        view.getMenuBarLoader().addImportOptionListener(new BookingHandler(model,bookingSystemPanel));
        bookingSystemControlPanel.addListeners(new BookingHandler(model,bookingSystemPanel));
    }
}
