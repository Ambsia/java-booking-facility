package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.*;
import com.bookingsystem.model.businessmodel.*;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemAdminControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemArchiveControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;
import com.bookingsystem.view.panes.UIBookingSystemBookingPanel;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;

public class BookingSystemController {
    // account not instantiated until logged in or created!
    public BookingSystemController(BookingSystemUILoader view, BookingBusinessLayer bookingBusinessLayer, AccountBusinessLayer accountBusinessLayer, LoggerBusinessLayer loggerBusinessLayer, AccountManagementBusinessLayer accountManagementBusinessLayer, ArchiveBusinessLayer archiveBusinessLayer) {
        view.showLoginPanel();
        //
        UILoginPanel loginPanel = view.getLoginPanel();

        UIBookingSystemBookingPanel bookingSystemPanel = view.getBookingSystemTabbedPane().getBookingSystemPanel();
        UIBookingSystemTabbedPane bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        UIBookingSystemAdminPanel bookingSystemAdminPanel = bookingSystemTabbedPane.getBookingSystemAdminPanel();
        UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel();
        UIBookingSystemArchiveControlPanel bookingSystemArchiveControlPanel = view.getBookingSystemTabbedPane().getBookingSystemArchive().getUiBookingSystemArchiveControlPanel();
        UIBookingSystemBookingControlPanel bookingSystemControlPanel = bookingSystemTabbedPane.getBookingSystemPanel().getBookingSystemControlPanel();
        //
        Handler handler = new Handler(accountBusinessLayer,accountManagementBusinessLayer,bookingBusinessLayer,loggerBusinessLayer,view, archiveBusinessLayer);
        LoginHandler loginHandler = new LoginHandler(handler);
        BookingHandler bookingHandler = new BookingHandler(handler);
        AccountHandler accountHandler = new AccountHandler(handler);
        ArchiveHandler archiveHandler = new ArchiveHandler(handler);
        ///
        loginPanel.addSubmitListener(loginHandler);
        loginPanel.addClearListener(loginHandler);
        ///
        bookingSystemControlPanel.addListeners(bookingHandler);
        //
        view.getMenuBarLoader().addImportOptionListener(bookingHandler);
        bookingSystemAdminControlPanel.addListeners(accountHandler);
        //
        bookingSystemArchiveControlPanel.addListeners(archiveHandler);
    }
}
