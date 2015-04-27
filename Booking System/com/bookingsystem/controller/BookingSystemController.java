package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.AccountHandler;
import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.controller.handler.LoginHandler;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;
import com.bookingsystem.view.panelparts.UIBookingSystemAdminControlPanel;
import com.bookingsystem.view.panelparts.UIBookingSystemControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;
import com.bookingsystem.view.panes.UIBookingSystemPanel;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;

public class BookingSystemController {

    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private UIBookingSystemTabbedPane bookingSystemTabbedPane;
    private UIBookingSystemPanel bookingSystemPanel;
    private UIBookingSystemControlPanel bookingSystemControlPanel;


    private UIBookingSystemAdminPanel bookingSystemAdminPanel;
    private UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel;

    private BookingBusinessLayer bookingBusinessLayer;
    private AccountBusinessLayer accountBusinessLayer;
    private LoggerBusinessLayer loggerBusinessLayer;

    private LoginHandler loginHandler;
    private BookingHandler bookingHandler;
    private AccountHandler accountHandler;

    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view, BookingBusinessLayer bookingBusinessLayer, AccountBusinessLayer accountBusinessLayer, LoggerBusinessLayer loggerBusinessLayer) {
        this.view = view;
        view.showLoginPanel();
        //
        loginPanel = view.getLoginPanel();
        bookingSystemPanel = view.getBookingSystemTabbedPane().getBookingSystemPanel();
        bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        bookingSystemAdminPanel = bookingSystemTabbedPane.getBookingSystemAdminPanel();
        bookingSystemAdminControlPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel();
        //
        this.bookingBusinessLayer = bookingBusinessLayer;
        this.accountBusinessLayer = accountBusinessLayer;
        this.loggerBusinessLayer = loggerBusinessLayer;
        ///
        loginHandler = new LoginHandler(accountBusinessLayer, view, loggerBusinessLayer);
        bookingHandler = new BookingHandler(bookingBusinessLayer, bookingSystemPanel, loggerBusinessLayer);
        accountHandler = new AccountHandler(accountBusinessLayer, bookingSystemAdminPanel, loggerBusinessLayer);
        ///

        loginPanel.addSubmitListener(loginHandler);
        loginPanel.addClearListener(loginHandler);
        ///
        bookingSystemControlPanel = bookingSystemTabbedPane.getBookingSystemPanel().getBookingSystemControlPanel();
        bookingSystemControlPanel.addListeners(bookingHandler);
        //
        view.getMenuBarLoader().addImportOptionListener(bookingHandler);
        bookingSystemAdminControlPanel.addListeners(accountHandler);


    }
}
