package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.AccountHandler;
import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.controller.handler.Handler;
import com.bookingsystem.controller.handler.LoginHandler;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
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
    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view, BookingBusinessLayer bookingBusinessLayer, AccountBusinessLayer accountBusinessLayer, LoggerBusinessLayer loggerBusinessLayer, AccountManagementBusinessLayer accountManagementBusinessLayer) {
        view.showLoginPanel();
        //
        UILoginPanel loginPanel = view.getLoginPanel();
        UIBookingSystemPanel bookingSystemPanel = view.getBookingSystemTabbedPane().getBookingSystemPanel();
        UIBookingSystemTabbedPane bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        UIBookingSystemAdminPanel bookingSystemAdminPanel = bookingSystemTabbedPane.getBookingSystemAdminPanel();
        UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel();
        //
        Handler handler = new Handler(accountBusinessLayer,accountManagementBusinessLayer,bookingBusinessLayer,loggerBusinessLayer,view);
        LoginHandler loginHandler = new LoginHandler(handler);
        BookingHandler bookingHandler = new BookingHandler(handler);
        AccountHandler accountHandler = new AccountHandler(handler);
        ///
        loginPanel.addSubmitListener(loginHandler);
        loginPanel.addClearListener(loginHandler);
        ///
        UIBookingSystemControlPanel bookingSystemControlPanel = bookingSystemTabbedPane.getBookingSystemPanel().getBookingSystemControlPanel();
        bookingSystemControlPanel.addListeners(bookingHandler);
        //
        view.getMenuBarLoader().addImportOptionListener(bookingHandler);
        bookingSystemAdminControlPanel.addListeners(accountHandler);
    }
}
